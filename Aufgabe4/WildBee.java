import java.util.Iterator;

public interface WildBee extends Bee {
    // Gibt an, ob die Biene aus einer Zucht stammt.
    boolean fromBeekeeping();

    // Vorbedingung: Eine static List<Observation> muss in der Klasse Test vorhanden sein.
    //               Test.observations != null
    //               Test.observations.contains(this) == true
    // Nachbedingung: Gibt einen Iterator<WildBee> zurück.
    default Iterator<WildBee> wild(boolean fromBeekeeping) {
        return new BeeIterator<>(Test.observations, this, WildBee.class, b -> b instanceof WildBee w && w.fromBeekeeping() == fromBeekeeping);
    }

}
