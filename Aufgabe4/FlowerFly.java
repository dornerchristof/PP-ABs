import java.util.Date;
import java.util.Iterator;

public class FlowerFly implements Pollinator{
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
    public void setValid(boolean valid) {

    }

    @Override
    public Observation getNext() {
        return null;
    }

    @Override
    public Observation getPrevious() {
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
}
