import java.util.Date;
import java.util.Iterator;
import java.util.function.Predicate;

public class OsmiaCornuta implements SolitaryBee{
    private Bee earlierObservation;
    // Invarianz: tagNumber >= -1, nur -1 wenn nicht manuel gesetzt
    private int tagNumber = -1;
    // Invarianz: date != null
    private final Date date;
    // Invarianz: comment != null
    private String comment;
    private boolean valid = true;
    // Invarianz: lifestyle == Solitary (da diese Spezies nur Solitär leben kann)
    private final Lifestyle lifestyle = Lifestyle.SOLITARY;
    // Vorbedingung: date != null, comment != null
    // Nachbedingung: Objekt erstellt und alle Objektvariablen gesetzt und Objekt in die Liste aller Observations eingefügt.
    // Wenn Objektvariablen nicht angegeben werden, werden passende Default Werte gesetzt.
    public OsmiaCornuta(String comment, Date date) {
        this.comment = comment;
        this.date = date;
    }
    public OsmiaCornuta(String comment, Date date, int tagNumber) {
        this(comment, date);
        this.tagNumber = tagNumber;
    }
    public OsmiaCornuta(String comment, Date date, Bee earlierObservation) {
        this(comment, date);
        this.earlierObservation = earlierObservation;
    }

    public OsmiaCornuta(String comment, Date date, int tagNumber, Bee earlierObservation) {
        this(comment, date, tagNumber);
        this.earlierObservation = earlierObservation;
    }

    //Nachbedingung: Liefert das Datum der Observation
    @Override
    public Date getDate() {
        return date;
    }

    //Nachbedingung: Liefert eine frühere Beobachtung desselben Individums, falls bei Objekterzeugung angegeben.
    @Override
    public Bee getEarlierObservation() {
        return earlierObservation;
    }
    //Nachbedingung: Liefert die Tagnumber der Observation falls diese bei Objekterzuegung angegeben wurde.
    @Override
    public int getTagNumber() {
        return tagNumber;
    }
    //Nachbedingung: Liefert den sozialen Liefstyle der bei der Beobachtung festgestellt wurde.
    // gibt für diese Spezies immer den enum Wert Solitary zurück
    @Override
    public Lifestyle getLifestyle() {
        return lifestyle;
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
    // Nachbedingung: Gibt an, ob die Biene aus einer Zucht stammt.
    // Für diese Spezies immer false.
    @Override
    public boolean fromBeekeeping() {
        return false;
    }
}
