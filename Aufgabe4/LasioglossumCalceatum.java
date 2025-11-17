import java.util.Date;
import java.util.Iterator;
import java.util.function.Predicate;

public class LasioglossumCalceatum implements SocialBee, SolitaryBee {
    private boolean valid = true;
    // Invarianz: comment != null
    private String comment;
    // Invarianz: date != null
    private final Date date;
    // Invarianz: lifestyle entweder Social wenn nichts angegeben
    // oder Solitary wenn bei Objekterzeugung festgelget.
    private Lifestyle lifestyle;
    // Invarianz: tagNumber >= -1, nur -1 wenn nicht manuel gesetzt
    private int tagNumber = -1;
    private Bee earlierObservation;
    // Vorbedingung: date != null, comment != null
    // Nachbedingung: Objekt erstellt und alle Objektvariablen gesetzt und Objekt in die Liste aller Observations eingefügt.
    // Wenn Objektvariablen nicht angegeben werden, werden passende Default Werte gesetzt.
    public LasioglossumCalceatum(String comment, Date date) {
        this.comment = comment;
        this.date = date;
        this.lifestyle = Lifestyle.SOCIAL;
    }

    public LasioglossumCalceatum(String comment, Date date, boolean solitaryLifestyle) {
        this(comment, date);
        this.lifestyle = solitaryLifestyle ? Lifestyle.SOLITARY : Lifestyle.SOCIAL;
    }
    public LasioglossumCalceatum(String comment, Date date, int tagNumber) {
        this(comment, date);
        this.tagNumber = tagNumber;
    }

    public LasioglossumCalceatum(String comment, Date date, Bee earlierObservation) {
        this(comment, date);
        this.earlierObservation = earlierObservation;
    }

    public LasioglossumCalceatum(String comment, Date date, boolean solitaryLifestyle, int tagNumber) {
        this(comment, date, solitaryLifestyle);
        this.tagNumber = tagNumber;
    }

    public LasioglossumCalceatum(String comment, Date date, boolean solitaryLifestyle, Bee earlierObservation) {
        this(comment, date, solitaryLifestyle);
        this.earlierObservation = earlierObservation;
    }

    public LasioglossumCalceatum(String comment, Date date, int tagNumber, Bee earlierObservation) {
        this(comment, date, tagNumber);
        this.earlierObservation = earlierObservation;
    }

    public LasioglossumCalceatum(String comment, Date date, boolean solitaryLifestyle, int tagNumber, Bee earlierObservation) {
        this(comment, date, solitaryLifestyle, tagNumber);
        this.earlierObservation = earlierObservation;
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

    // Nachbedingung: Gibt an, ob die Biene aus einer Zucht stammt.
    // Für diese Spezies immer false.
    @Override
    public boolean fromBeekeeping() {
        return false;
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
    @Override
    public Lifestyle getLifestyle() {
        return lifestyle;
    }


}
