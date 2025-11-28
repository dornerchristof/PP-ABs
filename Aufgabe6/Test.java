import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Test.UsedClasses({
        Test.class,
        Simulation.class,
        Set.class,
        Bee.class,
        Flower.class,
        U.class,
        V.class,
        W.class,
        X.class,
        Y.class,
        Z.class,
        Statistics.class,
        Annotations.class,
        Annotations.Precondition.class,
        Annotations.Postcondition.class,
        Annotations.Invariant.class,
        Annotations.ClientHistoryConstraint.class,
        Annotations.ServerHistoryConstraint.class,
        Annotations.Preconditions.class,
        Annotations.Postconditions.class,
        Annotations.Invariants.class,
        Annotations.ClientHistoryConstraints.class,
        Annotations.ServerHistoryConstraints.class,
        Annotations.Responsible.class,
        Test.UsedClasses.class
})
public class Test {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface UsedClasses {
        Class<?>[] value();
    }
    //TODO: An interface dor an Annotation which describes which classes, interfaces und Annotationen zur Lösung gehören.
    // The above might have to be in a diffrent file and just be called here
    //General TODO's:
    // - All included Classes, Interfaces and Annotations Annotation for test file. (Point 1 of Section B)
    // - Interface for precondition Annotation
    // - Interface for postcondition Annotation
    // - Interface for invariant Annotation
    // - Interface for History Constraints Annotation
    // - Interface for Who built what class/interface/annotation Annotation (This is not a real sentence right?)
    // Not a fucking clue yet:
    // In test there should be a way to:
    // Print how many classes interfaces and annotations a member of team was responsible for.
    // Print how many Pre pots invariants and history constraints a person is responsible for.
    // (╯°□°）╯︵ ┻━┻  My brain just might be a bit fucked:
    // I am not sure if this actually is in the exercise text but i think i saw a rule stating that except for
    // inner classes and interfaces they should all be in separate files. This is only important because i
    // want to put all annotation interfaces in one file. Just keep this in mind :) (:
    // Possible Task Split: (A Tobi, B Patrick, C Cristof) always changeable
    // Annotations:
    // - Precondition Annotation - A
    // - Postcondition Annotation - A
    // - Invariant Annotation - A
    // - History Constraints Annotation - B
    // - Who built what Annotation - B
    // - All included Classes, Interfaces and Annotations Annotation - C
    // Classes and their respective interfaces:
    // - Test Class A part - C
    // - Test Class B part - A
    // - Bee (abstract)- B
    // - Flower (abstract) -C
    // - U -B
    // - V -B
    // - W -B
    // - X -C
    // - Y -C
    // - Z -C
    // - Set - A (Tobi because he already started implementing)
    // - Node- A --"--
    // - Simulation - B

    // Total for now: A-6, B-7, C-6

    public static void main(String[] args) {
        Simulation sim = new Simulation();
        sim.simulate();
    }

}
