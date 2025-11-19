import java.util.Set;

public interface OrdSet <E, R> extends Iterable<E>, Ordered<E, R> {
    // Signaturen der Funktionen iterator, setBefore und Before sind in den implementierten interfaces deffiniert.
    /**
     ** Setzt das Objekt c (kann auch null sein), das für künftige Prüfungen
     * erlaubter Ordnungsbeziehungen verwendet wird.
     * Alle schon bestehenden Ordnungsbeziehungen werden mit dem neuen c überprüft.
     * Bei Fehler bleibt c unverändert und eine IllegalArgumentException wird ausgelöst.
     * @param c Das neue Ordered-Objekt zur Validierung.
     * @throws IllegalArgumentException falls eine bestehende Beziehung nicht mehr erlaubt ist.
     */
    void check(Ordered<?,?> c) throws IllegalArgumentException;

    /**
     * Ähnlich zu check, jedoch wird das neue c auf jeden Fall gesetzt.
     * Alle für das neue c nicht mehr erlaubten Ordnungsbeziehungen werden entfernt.
     * @param c Das neue Ordered-Objekt zur Validierung.
     */
    void checkForced(Ordered<?, ?> c);

    /**
     * Anzahl der Einträge im Container.
     * @return anzahl der Einträge
     */
    int size();

}
