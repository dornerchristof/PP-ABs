import java.lang.annotation.*;

// Loop over all of the Classes and methods in test
// The classes / interfaces/ annotations that we loop over are defined with an annotation
// of itself so we access the class names at runtime from the test class using reflection
// we dont need to specify the Signature we can access it at runtime

public class Annotations {
    @Responsible(names.Tobias)
    @Retention(RetentionPolicy.RUNTIME) //So it doesn't get deleted when compiling
    @Repeatable(Preconditions.class) //So there can be multiple
    @Inherited //So they get inherited from superclass
    @Target({ElementType.METHOD, ElementType.CONSTRUCTOR}) // Only on Mehods and Constructors
    @Documented // So it shows up in Javadoc
    public @interface Precondition {
        String value();
    }
    @Responsible(names.Tobias)
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
    @Inherited
    @Documented
    public @interface Preconditions {
        Precondition[] value();
    }

    @Responsible(names.Tobias)
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
    @Repeatable(Postconditions.class)
    @Inherited
    @Documented
    public @interface Postcondition {
        String value();
    }

    @Responsible(names.Tobias)
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
    @Inherited
    @Documented
    public @interface Postconditions {
        Postcondition[] value();
    }

    @Responsible(names.Tobias)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Repeatable(Invariants.class)
    @Inherited
    @Documented
    public @interface Invariant {
        String value();
    }

    @Responsible(names.Tobias)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    @Documented
    public @interface Invariants {
        Invariant[] value();
    }

    // TODO: Should this show up in the javadoc
    @Responsible(names.Tobias)
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Responsible {
        names value();
    }

    public enum names {
        Christof,
        Patrick,
        Tobias,
    }

}
