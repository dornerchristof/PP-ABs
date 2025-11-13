import java.util.Iterator;

public interface SocialBee extends Bee {
    // Vorbedungung: Eine static List<Observation> muss in der Klasse Test vorhanden sein.
    //               Test.observations != null
    //               Test.observations.contains(this) == true
    // Nachbedingung: Gibt einen Iterator<SocialBee> zurück.
    default Iterator<SocialBee> social() {
        return new BeeIterator<>(Test.observations, this, SocialBee.class, b -> b.getLifestyle() == Lifestyle.SOCIAL);
    }

}
