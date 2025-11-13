import java.util.Iterator;

public interface SocialBee extends Bee {
    // Nachbedingung: Gibt einen Iterator<SocialBee> zurück oder null, falls in der aktuellen Observation kein Individuum beobachtet wurde.
    default Iterator<SocialBee> social() {
        return new BeeIterator<>(Test.observations, this, SocialBee.class, b -> b.getLifestyle() == Lifestyle.SOCIAL);
    }

}
