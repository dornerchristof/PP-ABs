import java.util.Iterator;

public interface CommunalBee extends SolitaryBee{
    default Iterator<CommunalBee> communal() {
        if (this.getIndividuum() != null) return null;
        return new FilteredObservationIterator<>(this, o ->
        {
            if (!(o instanceof CommunalBee s)) return false;
            return this.getIndividuum().equals(s.getIndividuum())
                    && s.getLifestyle() == Lifestyle.COMMUNAL;
        }
        );
    }
}
