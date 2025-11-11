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
        if(previous != null) previous.setNextObservation(this);
    }
    public OsmiaCornuta(String tagNumber, Date date, Observation previous, String comment) {
        this(comment, date, previous);
        this.tagNumber = tagNumber;

    }

    public OsmiaCornuta(Date date, String comment, Observation previous,Observation earlierObservation) {
        this(comment, date, previous);
        this.earlierObservation = earlierObservation;
        earlierObservation.setNextObservation(this);
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
    public String getComment() {
        return comment;
    }

    @Override
    public Observation getPrevious() {
        return previous;
    }

    @Override
    public void setNextObservation(Observation laterObservation) {
        this.next = laterObservation;
    }

    @Override
    public String getTagNumber() {
        return tagNumber;
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
    public Observation getNext() {
        return next;
    }
}
