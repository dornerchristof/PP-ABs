public class Bee {
    // Stellt eine Bienenbeobachtung da.
    // Einzige Angabe war eine toString() Methode die eine Beschriebung zurück gibt.
    protected String observationDescription;
    public Bee(String observationDescription){
       this.observationDescription = observationDescription;
    }

    @Override
    public String toString() {
        return "Bienenbeobachtung-Test";
    }
}
