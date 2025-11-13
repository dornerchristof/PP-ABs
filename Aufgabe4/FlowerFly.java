import java.util.Date;
import java.util.Iterator;


//Eine Beobachtung einer Schwebfliege. Schwebfliegen sind Bestäuber.
public class FlowerFly implements Pollinator{

    private boolean valid = true;
    //Invarianz: comment != null
    private final String comment;
    //Invarianz: date != null
    private final Date date;

    //Vorbedingung: date != null, comment != null
    //Nachbedingung: Objekt erstellt und alle Objektvariablen gesetzt und Objekt in die Liste aller Observations eingefügt.
    public FlowerFly(Date date, String comment){
        this.comment = comment;
        this.date = date;
        Test.addObservation(this);
    }

    //Nachbedingung: Liefert das Datum der Observation
    @Override
    public Date getDate() {
        return date;
    }

    //Zeigt an, ob die Observation (logisch) entfernt wurde.
    //Nachbedingung: Falsch, falls davor remove() ausgeführt wurde.
    @Override
    public boolean valid() {
        return valid;
    }

    //Nachbedingung: Entfernt die Observation (logisch) aus allen Iterationen.
    @Override
    public void remove() {
        valid = false;
    }

    //Nachbedingung: Liefert einen Iterator über alle zeitlich vor dieser Observation gelegenen Observationen,
    // welche valid sind.
    @Override
    public Iterator<Observation> earlier() {
        return new ObservationIterator(this, ObservationIterator.Direction.EARLIER, Test.observations);
    }

    //Nachbedingung: Liefert einen Iterator über alle zeitlich nach dieser Observation gelegenen Observationen.,
    // welche valid sind.
    @Override
    public Iterator<Observation> later() {
        return new ObservationIterator(this, ObservationIterator.Direction.LATER, Test.observations);
    }
}
