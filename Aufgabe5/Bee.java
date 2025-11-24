public class Bee {
    protected final String observationDescription;
    // Einzige Angabe war eine toString() Methode die eine Beschreibung zurückgibt.

    /**
     * Stellt eine Bienenbeobachtung dar.
     * @param desc Die Beschreibung der Beobachtung
     */
    public Bee(String desc) {
        this.observationDescription = desc;
    }

    /**
     * Gibt die Beschreibung der Beobachtung aus.
     * @return {@code "Bienenbeobachtung: " + observationDescription}
     */
    @Override
    public String toString() {
        return "Bienenbeobachtung: " + observationDescription;
    }
}
