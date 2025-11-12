import java.util.Iterator;

public interface WildBee extends Bee{
    boolean fromBeekeeping();

    default Iterator<WildBee> wild(boolean fromBeekeeping) {
        if (this.getIndividuum() != null) return null;
        return new FilteredObservationIterator<>(this, o ->
        {
            if (!(o instanceof WildBee s)) return false;
            return this.getIndividuum().equals(s.getIndividuum())
                    && s.fromBeekeeping() == fromBeekeeping;
        }
        );
    }

}
