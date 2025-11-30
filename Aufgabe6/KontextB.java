import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

@Annotations.Responsible(Annotations.names.Tobias)
public class KontextB {
        @Annotations.Responsible(Annotations.names.Tobias)
        private static class StudentStats {
            int classCount = 0;
            int methodCount = 0;
            int assertionCount = 0;
        }

        @Annotations.Precondition( "rootClass != null")
        public static void run(Class<?> rootClass) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println(" KONTEXT B:");
            System.out.println("=".repeat(60));

            Test.UsedClasses projectClasses = rootClass.getAnnotation(Test.UsedClasses.class);

            Map<Annotations.names, StudentStats> statsMap = new HashMap<>();

            for (Class<?> clazz : projectClasses.value()) {
                analyzeClass(clazz, statsMap);
            }

            System.out.println("\n" + "-".repeat(60));
            System.out.println(" STATISTIK PRO GRUPPENMITGLIED");
            System.out.println("-".repeat(60));

            for (Map.Entry<Annotations.names, StudentStats> entry : statsMap.entrySet()) {
                StudentStats s = entry.getValue();
                System.out.printf("Student: %-20s | Klassen: %2d | Methoden/Konstr: %2d | Zusicherungen: %2d%n",
                        entry.getKey(), s.classCount, s.methodCount, s.assertionCount);
            }
        }

        private static void analyzeClass(Class<?> clazz, Map<Annotations.names, StudentStats> statsMap) {
            String isInnerClass = clazz.getDeclaringClass() != null ? " (Innere Klasse von " + clazz.getDeclaringClass().getSimpleName() + ")" : "";
            System.out.println("\n>>> Analyse Klasse: " + clazz.getSimpleName() + isInnerClass);
            Annotations.names authorName = Annotations.names.Tobias; // Just for initialization this should be present in all classes anyway.
            if (clazz.isAnnotationPresent(Annotations.Responsible.class)) {
                authorName = clazz.getAnnotation(Annotations.Responsible.class).value();
            }
            System.out.println("    Verantwortlich: " + authorName.toString());

            StudentStats stats = statsMap.computeIfAbsent(authorName, k -> new StudentStats());
            stats.classCount++;

            stats.assertionCount += printClassAnnotations(clazz, Annotations.Invariant.class, "Invariant");
            stats.assertionCount += printClassAnnotations(clazz, Annotations.ClientHistoryConstraint.class, "ClientHistoryConstraint");
            stats.assertionCount += printClassAnnotations(clazz, Annotations.ServerHistoryConstraint.class, "ServerHistoryConstraint");

            for (Constructor<?> c : clazz.getDeclaredConstructors()) {
                System.out.println("    [Konstruktor] " + c.toString());
                stats.methodCount++;

                // Annotationen direkt am Konstruktor
                stats.assertionCount += printExecutableAnnotations(c, Annotations.Precondition.class, "Pre");
                stats.assertionCount += printExecutableAnnotations(c, Annotations.Postcondition.class, "Post");
            }

            if(!clazz.isInterface()){
                for (Method m : clazz.getDeclaredMethods()) {
                    System.out.println("    [Methode] " + buildMethodSignature(m));

                    if (!clazz.isInterface()) {
                        stats.methodCount++;
                    }

                    stats.assertionCount += printInheritedMethodAnnotations(m, Annotations.Precondition.class, "Pre");
                    stats.assertionCount += printInheritedMethodAnnotations(m, Annotations.Postcondition.class, "Post");
                }
            }
            Class<?>[] innerClasses = clazz.getDeclaredClasses();
            for (Class<?> innerClass : innerClasses) {
                analyzeClass(innerClass, statsMap);
            }
        }


        private static <A extends Annotation> int printClassAnnotations(Class<?> clazz, Class<A> type, String label) {
            int count = 0;
            A[] anns = clazz.getAnnotationsByType(type);
            for (A ann : anns) {
                System.out.println("      @" + label + ": " + extractValue(ann));
                count++;
            }
            return count;
        }

        private static <A extends Annotation> int printExecutableAnnotations(Executable exec, Class<A> type, String label) {
            int count = 0;
            A[] anns = exec.getAnnotationsByType(type);
            for (A ann : anns) {
                System.out.println("      @" + label + ": " + extractValue(ann));
                count++;
            }
            return count;
        }

        private static <A extends Annotation> int printInheritedMethodAnnotations(Method m, Class<A> type, String label) {
            int count = 0;
            Set foundValues = new Set();

            List<Method> hierarchyMethods = findOverriddenMethods(m, m.getDeclaringClass());

            for (Method pm : hierarchyMethods) {
                A[] anns = pm.getAnnotationsByType(type);
                for (A ann : anns) {
                    String val = extractValue(ann);
                    if (foundValues.add(val)) {
                        String suffix = (pm != m) ? " (geerbt von " + pm.getDeclaringClass().getSimpleName() + ")" : "";
                        System.out.println("      @" + label + ": " + val + suffix);
                        count++;
                    }
                }
            }
            return count;
        }

        private static List<Method> findOverriddenMethods(Method original, Class<?> currentClass) {
            List<Method> methods = new ArrayList<>();
            if (currentClass == null) return methods;

            try {
                Method m = currentClass.getDeclaredMethod(original.getName(), original.getParameterTypes());
                methods.add(m);
            } catch (NoSuchMethodException _) {
                // Not found in this class
            }

            methods.addAll(findOverriddenMethods(original, currentClass.getSuperclass()));

            for (Class<?> iface : currentClass.getInterfaces()) {
                methods.addAll(findOverriddenMethods(original, iface));
            }
            return methods;
        }

        private static String extractValue(Annotation a) {
            try {
                Method m = a.annotationType().getMethod("value");
                return (String) m.invoke(a);
            } catch (Exception e) {
                return "Fehler beim Lesen";
            }
        }

        private static String buildMethodSignature(Method m) {
            StringBuilder sb = new StringBuilder();
            sb.append(Modifier.toString(m.getModifiers())).append(" ");
            sb.append(m.getReturnType().getSimpleName()).append(" ");
            sb.append(m.getName()).append("(");
            Parameter[] params = m.getParameters();
            for (int i = 0; i < params.length; i++) {
                sb.append(params[i].getType().getSimpleName());
                if (i < params.length - 1) sb.append(", ");
            }
            sb.append(")");
            return sb.toString();
        }
    }
