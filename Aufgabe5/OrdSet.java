public interface OrdSet <E, R> extends Iterable<E>, Ordered<E, R> {
    // Signaturen der Funktionen iterator, setBefore und Before sind in den implementierten interfaces deffiniert.
    /**
     ** Setzt das Objekt c (kann auch null sein), das für künftige Prüfungen
     * erlaubter Ordnungsbeziehungen verwendet wird.
     * Alle schon bestehenden Ordnungsbeziehungen werden mit dem neuen c überprüft.
     * Bei Fehler bleibt c unverändert und eine IllegalArgumentException wird ausgelöst.
     * @param c Das neue Ordered-Objekt zur Validierung.
     * @throws IllegalArgumentException falls eine bestehende Beziehung nicht mehr erlaubt ist.
     * Postcondition: Alle Ordnungsbeziehungen stimmen mit dem neuen c überein.
     */
    void check(Ordered<? super E,?> c) throws IllegalArgumentException;

    /**
     * Ähnlich zu check, aber c wird auf jeden Fall gesetzt.
     * @param c Das neue Ordered-Objekt zur Validierung.
     * Postcondition: Alle Beziehungen, die von c nicht erlaubt sind, wurden entfernt.
     */
    void checkForced(Ordered<? super E, ?> c);

    /**
     * Anzahl der Einträge im Container.
     * @return anzahl der Einträge
     * Invarianz: size >= 0
     */
    int size();

}
