import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Test {

    public static void main(String[] args) {
        int a = 2; //Anzahl der Parameter der zu untersuchenden Funktion
        Function<List<Double>, Double> f = obj -> Math.sin(obj.get(0) + obj.get(1));
        //zu untersuchender Wertebereich pro Argument
        //"Stelle" des Arguments: [min, max]
        double[][] w = new double[][] { {-2, 2}, {0 , 2}};
        //Vergleichsfunktion: ist erster Wert besser als zweiter?
        DoubleCompare c = new DoubleCompare();
        //Suchschritte
        int t;
        BeeAlgorithm bee = new BeeAlgorithm(10, 8, 3, 20, 10, 0.3, 5);
        var res = bee.run(f, c, w, 3);

    }


    private static class DoubleCompare implements Comparator<Double>{

        @Override
        public int compare(Double o1, Double o2) {
            return o1.compareTo(o2);
        }
    }

}
