import java.util.Date;
import java.util.Iterator;
import java.util.function.Predicate;

public class OsmiaCornuta implements SolitaryBee{
    private Bee earlierObservation;
    private int tagNumber = -1;
    private final Date date;
    private String comment;
    private boolean valid = true;
    private final Lifestyle lifestyle = Lifestyle.SOLITARY;
    public OsmiaCornuta(String comment, Date date) {
        this.comment = comment;
        this.date = date;
    }
    public OsmiaCornuta(String comment, Date date, int tagNumber) {
        this(comment, date);
        this.tagNumber = tagNumber;
    }
    public OsmiaCornuta(String comment, Date date, Bee earlierObservation) {
        this(comment, date);
        this.earlierObservation = earlierObservation;
    }

    public OsmiaCornuta(String comment, Date date, int tagNumber, Bee earlierObservation) {
        this(comment, date, tagNumber);
        this.earlierObservation = earlierObservation;
    }


    @Override
    public Date getDate() {
        return date;
    }


    @Override
    public Bee getEarlierObservation() {
        return earlierObservation;
    }

    @Override
    public int getTagNumber() {
        return tagNumber;
    }

    @Override
    public Lifestyle getLifestyle() {
        return lifestyle;
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
