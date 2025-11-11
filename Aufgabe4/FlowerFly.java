import java.util.Date;
import java.util.Iterator;

public class FlowerFly implements Pollinator{
    private Observation previous;
    private Observation next;

    private FlowerFly previousEncounter;
    private boolean valid = true;
    private String comment;
    private Date date;

    public FlowerFly(Date date, String comment, Observation lastObservation){

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
        return comment;
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

    @Override
    public Observation getPrevious() {
        return previous;
    }
}
