import java.util.Date;
import java.util.Iterator;

public class BumbleBee implements SocialBee, WildBee{
    private boolean valid = true;
    private final Date date;
    private final String comment;
    private boolean fromBeekeeping;
    private Bee earlierObservation = null;
    private int tagNumber = -1;

    public BumbleBee(String comment, Date date, boolean fromBeekeeping) {
        this(comment, date);
        this.fromBeekeeping = fromBeekeeping;
    }
    public BumbleBee(String comment, Date date) {
        this.comment = comment;
        this.date = date;
        Test.addObservation(this);
    }

    public BumbleBee(String comment, Date date, int tagNumber) {
        this(comment, date);
        this.tagNumber = tagNumber;
    }

    public BumbleBee(String comment, Date date, Bee earlierObservation) {
        this(comment, date);
        this.earlierObservation = earlierObservation;
    }

    public BumbleBee(String comment, Date date, boolean fromBeekeeping, int tagNumber) {
        this(comment, date, fromBeekeeping);
        this.tagNumber = tagNumber;
    }

    public BumbleBee(String comment, Date date, boolean fromBeekeeping, Bee earlierObservation) {
        this(comment, date, fromBeekeeping);
        this.earlierObservation = earlierObservation;
    }

    public BumbleBee(String comment, Date date, int tagNumber, Bee earlierObservation) {
        this(comment, date, tagNumber);
        this.earlierObservation = earlierObservation;
    }

    public BumbleBee(String comment, Date date, boolean fromBeekeeping, int tagNumber, Bee earlierObservation) {
        this(comment, date, fromBeekeeping, tagNumber);
        this.earlierObservation = earlierObservation;
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
        return Lifestyle.SOCIAL;
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
        return fromBeekeeping;
    }
}
