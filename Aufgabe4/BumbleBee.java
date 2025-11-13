import java.util.Date;
import java.util.Iterator;

public class BumbleBee implements SocialBee, WildBee{
    private boolean valid = true;
    //Invarianz: Date != null
    private final Date date;
    //Invarianz: comment != null
    private final String comment;
    private boolean fromBeekeeping = false;
    private Bee earlierObservation = null;
    private int tagNumber = -1;

    //Vorbedingung: date != null, comment != null
    //Nachbedingung: Objekt erstellt und alle Objekt variablen gesetzt und Objekt in die Liste aller Observations eingefügt.
    //               Falls die Objekt-variablen nicht direkt gesetzt werden, dann werden passende default-Werte verwendet.
    public BumbleBee(String comment, Date date, boolean fromBeekeeping) {
        this(comment, date);
        this.fromBeekeeping = fromBeekeeping;
    }
    public BumbleBee(String comment, Date date) {
        this.comment = comment;
        this.date = date;
    }

    public BumbleBee(String comment, Date date, int tagNumber) {
        this(comment, date);
        this.tagNumber = tagNumber;
    }

    public BumbleBee(String comment, Date date, Bee earlierObservation) {
        this(comment, date);
        this.earlierObservation = earlierObservation;
    }

    public BumbleBee(String comment, Date date, boolean fromBeekeeping, int tagNumber) {
        this(comment, date, fromBeekeeping);
        this.tagNumber = tagNumber;
    }

    public BumbleBee(String comment, Date date, boolean fromBeekeeping, Bee earlierObservation) {
        this(comment, date, fromBeekeeping);
        this.earlierObservation = earlierObservation;
    }

    public BumbleBee(String comment, Date date, int tagNumber, Bee earlierObservation) {
        this(comment, date, tagNumber);
        this.earlierObservation = earlierObservation;
    }

    public BumbleBee(String comment, Date date, boolean fromBeekeeping, int tagNumber, Bee earlierObservation) {
        this(comment, date, fromBeekeeping, tagNumber);
        this.earlierObservation = earlierObservation;
    }

    //Nachbedingung: Falls vorhanden wird eine frühere Beobachtung des gleichen Individuums zurückgegeben.
    @Override
    public Bee getEarlierObservation() {
        return earlierObservation;
    }

    //Nachbedingung: Liefert die Nummer der beobachteten Biene zurück.
    //               Falls keine einzelne Biene beobachtet wurde oder die Biene keine Nummer hat,
    //               dann wird -1 zurückgegeben.
    @Override
    public int getTagNumber() {
        return tagNumber;
    }

    //Nachbedingung: Gibt die Lebensweise der Biene während der Beobachtung zurück.
    //               Hummeln leben nur sozial, deswegen wird die Methode immer Lifestyle.SOCIAL zurückgegeben.
    @Override
    public Lifestyle getLifestyle() {
        return Lifestyle.SOCIAL;
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

    //Nachbedingung: Gibt an, ob bekannt ist, ob die Biene aus einer Zucht stammt.
    @Override
    public boolean fromBeekeeping() {
        return fromBeekeeping;
    }
}
