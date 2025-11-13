import java.util.Date;
import java.util.Iterator;

//Eine einzelne Beobachtung eines Insekts.
public interface Observation {

    //Nachbedingung: Liefert das Datum der Observation
    Date getDate();

    //Zeigt an, ob die Observation (logisch) entfernt wurde.
    //Nachbedingung: Falsch, falls davor remove() ausgeführt wurde.
    boolean valid();

    //Nachbedingung: Entfernt die Observation (logisch) aus allen Iterationen.
    void remove();

    //Nachbedingung: Liefert einen Iterator über alle zeitlich vor dieser Observation gelegenen Observationen,
    // welche valid sind.
    default Iterator<Observation> earlier(){
        return new ObservationIterator(this, ObservationIterator.Direction.EARLIER, Test.observations);
    }

    //Nachbedingung: Liefert einen Iterator über alle zeitlich nach dieser Observation gelegenen Observationen.,
    // welche valid sind.
    default Iterator<Observation> later(){
        return new ObservationIterator(this, ObservationIterator.Direction.LATER, Test.observations);
    }

}
