import java.util.Iterator;

public interface SocialBee extends Bee {
    // Nachbedingung: Gibt einen Iterator<SocialBee> zurück oder null, falls in der aktuellen Observation kein Individuum beobachtet wurde.
    default Iterator<SocialBee> social() {
        if (this.getIndividuum() != null) return null;
        return new FilteredObservationIterator<>(this, o ->
            {
                if (!(o instanceof SocialBee s)) return false;
                return this.getIndividuum().equals(s.getIndividuum())
                        && s.getLifestyle() == Lifestyle.SOCIAL;
            }
        );
    }

}
