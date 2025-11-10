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
    Iterator<Observation> earlier();
    Iterator<Observation> later();
}
