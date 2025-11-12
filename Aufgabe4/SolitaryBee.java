import java.util.Iterator;

public interface SolitaryBee extends WildBee {

    default Iterator<SolitaryBee> solitary(){
        if (this.getIndividuum() != null) return null;
        return new FilteredObservationIterator<>(this, o ->
        {
            if (!(o instanceof SolitaryBee s)) return false;
            return this.getIndividuum().equals(s.getIndividuum())
                    && s.getLifestyle() == Lifestyle.SOLITARY;
        }
        );
    };
}
