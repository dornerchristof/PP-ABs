import java.util.Date;
import java.util.Iterator;

public interface Observation {

    Date getDate();
    void setNextObservation(Observation nextObservation);

    String getComment();
    boolean valid();
    void setValid(boolean valid);
    default void remove(){
        setValid(false);
    };
    Observation getNext();
    Observation getPrevious();

    //Liefert einen Iterator über alle zeitlich vor dieser Observation gelegenen Observationen.
    default Iterator<Observation> earlier(){
        return new ObservationIterator(this, ObservationIterator.Direction.EARLIER);
    };
    //Liefert einen Iterator über alle zeitlich nach dieser Observation gelegenen Observationen.
    default Iterator<Observation> later(){
        return new ObservationIterator(this, ObservationIterator.Direction.LATER);
    };
}
