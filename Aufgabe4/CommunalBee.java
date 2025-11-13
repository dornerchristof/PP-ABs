import java.util.Iterator;

public interface CommunalBee extends SolitaryBee{
    // Vorbedingung: Eine static List<Observation> muss in der Klasse Test vorhanden sein.
    //               Test.observations != null
    //               Test.observations.contains(this) == true
    // Nachbedingung: Gibt einen Iterator<CommunalBee> zurück
    default Iterator<CommunalBee> communal() {
        return new BeeIterator<>(Test.observations, this, CommunalBee.class, b-> b.getLifestyle() == Lifestyle.COMMUNAL);
    }
}
