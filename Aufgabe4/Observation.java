import java.util.Date;
import java.util.Iterator;

public interface Observation {

    //Nachbedingung: Liefert das Datum der Observation
    Date getDate();

    //Nachbedingung: Setzt den Nachfolger der Observation.
    void setNextObservation(Observation nextObservation);

    //Nachbedingung: Liefert den Kommentar zur Observation.
    String getComment();

    //Nachbedingung: Falsch, falls davor remove() ausgeführt wurde.
    boolean valid();

    //Nachbedingung: Entfernt die Observation (logisch) aus allen Iterationen.
    void remove();

    //Nachbedingung: Liefert den Nachfolger oder null, falls kein Nachfolger vorhanden ist.
    Observation getNext();

    //Nachbedingung: Liefert den Vorgänger oder null, falls kein Vorgänger vorhanden ist.
    Observation getPrevious();

    //Nachbedingung: Liefert einen Iterator über alle zeitlich vor dieser Observation gelegenen Observationen,
    // welche valid sind.
    default Iterator<Observation> earlier(){
        return new ObservationIterator(this, ObservationIterator.Direction.EARLIER);
    };
    //Nachbedingung: Liefert einen Iterator über alle zeitlich nach dieser Observation gelegenen Observationen.`,
    // welche valid sind.
    default Iterator<Observation> later(){
        return new ObservationIterator(this, ObservationIterator.Direction.LATER);
    };
}
