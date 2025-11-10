import java.util.Date;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class Bee implements Observation, Pollinator, Wasp{
    public Observation earlierObservation;
    public String tagNumber;

    protected Bee(String comment, Date date) {
        super(comment, date);
    }
    protected Bee(String comment, Date date, Observation earlierObservation) {
        this.earlierObservation = earlierObservation;
        super(comment, date);
    }
    protected Bee(String comment, Date date, String tagNumber) {
        this.tagNumber = tagNumber;
        super(comment, date);
    }
    protected Bee(String comment, Date date, Observation earlierObservation, String tagNumber) {
        this.earlierObservation = earlierObservation;
        this.tagNumber = tagNumber;
        super(comment, date);
    }

    public abstract Iterator<Bee> sameBee();
    public abstract Iterator<Bee> sameBee(Boolean flip, Predicate<Observation> between);
}
