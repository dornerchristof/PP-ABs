import java.util.Date;
import java.util.Iterator;

public interface Observation {

    //Nachbedingung: Liefert das Datum der Observation
    Date getDate();

    //Nachbedingung: Falsch, falls davor remove() ausgeführt wurde.
    boolean valid();

    //Nachbedingung: Entfernt die Observation (logisch) aus allen Iterationen.
    void remove();

    //Nachbedingung: Liefert einen Iterator über alle zeitlich vor dieser Observation gelegenen Observationen,
    // welche valid sind.
    Iterator<Observation> earlier();
    //Nachbedingung: Liefert einen Iterator über alle zeitlich nach dieser Observation gelegenen Observationen.,
    // welche valid sind.
    Iterator<Observation> later();
}
