import java.util.Date;

public class HoneyBee implements SocialBee{
    private boolean valid = true;
    private final Date date;
    private final String comment;
    private Bee earlierObservation = null;
    private int tagNumber = -1;

    protected HoneyBee(String comment, Date date) {
        this.comment = comment;
        this.date = date;
        Test.addObservation(this);
    }

    public HoneyBee(String comment, Date date, int tagNumber) {
        this(comment, date);
        this.tagNumber = tagNumber;
    }

    public HoneyBee(String comment, Date date, Bee earlierObservation) {
        this(comment, date);
        this.earlierObservation = earlierObservation;
    }

    public HoneyBee(String comment, Date date, int tagNumber, Bee earlierObservation) {
        this(comment, date, tagNumber);
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
}
