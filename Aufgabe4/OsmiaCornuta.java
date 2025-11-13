import java.util.Date;
import java.util.Iterator;
import java.util.function.Predicate;

public class OsmiaCornuta implements SolitaryBee{
    Observation previous;
    Observation next;
    Observation earlierObservation;
    String tagNumber;
    Date date;
    String comment;
    boolean valid = true;
    public OsmiaCornuta(String comment, Date date, Observation previous) {
        this.comment = comment;
        this.date = date;
        this.previous = previous;
        Test.addObservation(this);
    }
    public OsmiaCornuta(String tagNumber, Date date, Observation previous, String comment) {
        this(comment, date, previous);
        this.tagNumber = tagNumber;

    }

    public OsmiaCornuta(Date date, String comment, Observation previous,Observation earlierObservation) {
        this(comment, date, previous);
        this.earlierObservation = earlierObservation;
    }

    public OsmiaCornuta(String tagNumber, Date date, Observation previous, String comment, Observation earlierObservation) {
        this(tagNumber, date, previous,comment);
        this.earlierObservation = earlierObservation;
    }


    @Override
    public Date getDate() {
        return date;
    }


    @Override
    public Observation getPrevious() {
        return previous;
    }

    @Override
    public String getTagNumber() {
        return tagNumber;
    }

    @Override
    public Lifestyle getLifestyle() {
        return null;
    }

    @Override
    public Iterator<Bee> sameBee() {
        return null;
    }

    @Override
    public Iterator<Bee> sameBee(Boolean flip, Predicate<Observation> between) {
        return null;
    }

    @Override
    public boolean valid() {
        return valid;
    }

    @Override
    public void remove() {
        valid = false;
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
}
