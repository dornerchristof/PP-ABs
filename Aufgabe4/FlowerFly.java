import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FlowerFly implements Pollinator{
    private Observation previous;
    private Observation next;

    private boolean valid = true;
    //Invarianz: comment != null
    private final String comment;
    //Invarianz: date != null
    private final Date date;

    //Vorbedingung: date != null, comment != null
    //Nachbedingung: Objekt erstellt und alle Objektvariablen gesetzt.
    public FlowerFly(Date date, String comment, List<Observation> observations){
        this.comment = comment;
        this.date = date;
        Test.addObservation(this);
    }

    //Nachbedingung: Liefert das Datum der Observation
    @Override
    public Date getDate() {
        return date;
    }

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

    @Override
    public Iterator<Observation> earlier() {
        return new ObservationIterator(this, ObservationIterator.Direction.EARLIER, Test.observations);
    }

    @Override
    public Iterator<Observation> later() {
        return new ObservationIterator(this, ObservationIterator.Direction.LATER, Test.observations);
    }
}
