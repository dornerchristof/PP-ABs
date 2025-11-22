public class Bee {
    protected final String observationDescription;
    // Stellt eine Bienenbeobachtung da.
    // Einzige Angabe war eine toString() Methode die eine Beschreibung zurückgibt.


    public Bee(String desc) {
        this.observationDescription = desc;
    }

    @Override
    public String toString() {
        return observationDescription;
    }
}
