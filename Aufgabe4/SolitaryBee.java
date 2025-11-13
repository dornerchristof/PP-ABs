import java.util.Iterator;

public interface SolitaryBee extends WildBee {

    // Vorbedingung: Eine static List<Observation> muss in der Klasse Test vorhanden sein.
    //               Test.observations != null
    //               Test.observations.contains(this) == true
    // Nachbedingung: Gibt einen Iterator<SolitaryBee> zurück.
    default Iterator<SolitaryBee> solitary(){
        return new BeeIterator<>(Test.observations, this, SolitaryBee.class, b -> b.getLifestyle() == Lifestyle.SOLITARY);
    };
}
