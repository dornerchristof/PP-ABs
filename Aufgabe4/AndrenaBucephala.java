import java.util.Date;
import java.util.Iterator;
import java.util.function.Predicate;

public class AndrenaBucephala implements Bee{
    private boolean valid = true;
    protected AndrenaBucephala(String comment, Date date) {

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
        valid = false;
    }

    @Override
    public Observation getNext() {
        return null;
    }

    @Override
    public Iterator<Observation> earlier() {
        return null;
    }

    @Override
    public Iterator<Observation> later() {
        return null;
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
