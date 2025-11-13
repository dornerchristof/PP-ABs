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
    public void setNextObservation(Observation nextObservation) {

    }

    @Override
    public String getComment() {
        return "";
    }

    @Override
    public boolean valid() {
        return false;
    }

    @Override
    public void remove() {

    }

    @Override
    public Observation getNext() {
        return null;
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
    public Observation getPrevious() {
        return null;
    }

    @Override
    public String getTagNumber() {
        return "";
    }

    @Override
    public Individuum getIndividuum() {
        return null;
    }

    @Override
    public Lifestyle getLifestyle() {
        return null;
    }
}

    @Override
    public Observation getPrevious() {
        return null;
    }

    @Override
    public String getTagNumber() {
        return "";
    }

    @Override
    public Iterator<Bee> sameBee() {
        return null;
    }

    @Override
    public Iterator<Bee> sameBee(Boolean flip, Predicate<Observation> between) {
        return null;
    }

}
