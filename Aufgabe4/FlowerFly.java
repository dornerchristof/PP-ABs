import java.util.Date;
import java.util.Iterator;

public class FlowerFly implements Pollinator{
    private Observation previous;
    private Observation next;


    private boolean valid = true;
    //Invarianz: comment != null
    private String comment;
    //Invarianz: date != null
    private Date date;

    //Vorbedingung: date != null, comment != null
    //Nachbedingung: Objekt erstellt und alle Objektvariablen gesetzt.
    public FlowerFly(Date date, String comment){
        this.comment = comment;
        this.date = date;
    }

    //Nachbedingung: Liefert das Datum der Observation
    @Override
    public Date getDate() {
        return date;
    }

    //Nachbedingung: Setzt den Nachfolger der Observation.
    @Override
    public void setNextObservation(Observation nextObservation) {
        next = nextObservation;
    }

    //Nachbedingung: Liefert den Kommentar zur Observation.
    @Override
    public String getComment() {
        return comment;
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

    //Nachbedingung: Liefert den Nachfolger oder null, falls kein Nachfolger vorhanden ist.
    @Override
    public Observation getNext() {
        return next;
    }

    //Nachbedingung: Liefert den Vorgänger oder null, falls kein Vorgänger vorhanden ist.
    @Override
    public Observation getPrevious() {
        return previous;
    }
}
