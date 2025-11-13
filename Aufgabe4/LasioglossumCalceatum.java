import java.util.Date;
import java.util.Iterator;
import java.util.function.Predicate;

public class LasioglossumCalceatum implements SocialBee, SolitaryBee {
    private boolean valid = true;
    private String comment;
    private final Date date;
    private Lifestyle lifestyle;
    private int tagNumber = -1;
    private Bee earlierObservation;

    public LasioglossumCalceatum(String comment, Date date) {
        this.comment = comment;
        this.date = date;
        this.lifestyle = Lifestyle.SOCIAL;
    }

    public LasioglossumCalceatum(String comment, Date date, boolean solitaryLifestyle) {
        this(comment, date);
        this.lifestyle = solitaryLifestyle ? Lifestyle.SOLITARY : Lifestyle.SOCIAL;
    }
    public LasioglossumCalceatum(String comment, Date date, int tagNumber) {
        this(comment, date);
        this.tagNumber = tagNumber;
    }

    public LasioglossumCalceatum(String comment, Date date, Bee earlierObservation) {
        this(comment, date);
        this.earlierObservation = earlierObservation;
    }

    public LasioglossumCalceatum(String comment, Date date, boolean solitaryLifestyle, int tagNumber) {
        this(comment, date, solitaryLifestyle);
        this.tagNumber = tagNumber;
    }

    public LasioglossumCalceatum(String comment, Date date, boolean solitaryLifestyle, Bee earlierObservation) {
        this(comment, date, solitaryLifestyle);
        this.earlierObservation = earlierObservation;
    }

    public LasioglossumCalceatum(String comment, Date date, int tagNumber, Bee earlierObservation) {
        this(comment, date, tagNumber);
        this.earlierObservation = earlierObservation;
    }

    public LasioglossumCalceatum(String comment, Date date, boolean solitaryLifestyle, int tagNumber, Bee earlierObservation) {
        this(comment, date, solitaryLifestyle, tagNumber);
        this.earlierObservation = earlierObservation;
    }

    @Override
    public Date getDate() {
        return date;
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
    public boolean fromBeekeeping() {
        return false;
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


}
