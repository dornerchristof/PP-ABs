import java.util.Date;
import java.util.Iterator;
import java.util.function.Predicate;

public class LasioglossumCalceatum implements SocialBee, SolitaryBee {
    protected LasioglossumCalceatum(String comment, Date date, boolean solitaryLifestyle) {
    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public boolean valid() {
        return false;
    }

    @Override
    public void remove() {

    }

    @Override
    public Iterator<Observation> earlier() {
        return new ObservationIterator(this, ObservationIterator.Direction.EARLIER, Test.observations);
    }

    @Override
    public Iterator<Observation> later() {
        return new ObservationIterator(this, ObservationIterator.Direction.LATER, Test.observations);
    }

    @Override
    public boolean fromBeekeeping() {
        return false;
    }


    @Override
    public Bee getEarlierObservation() {
        return null;
    }

    @Override
    public int getTagNumber() {
        return 0;
    }


    @Override
    public Lifestyle getLifestyle() {
        return null;
    }
}
