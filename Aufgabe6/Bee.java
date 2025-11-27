public abstract class Bee { //TODO: Visibility
    protected int collectedX = 0;
    protected int collectedY = 0;
    protected int collectedZ = 0;
    protected int activeFor = 0;
    protected int totalActiveDays = 0;

    public abstract boolean prefersFlower(Flower flower);
    public abstract boolean acceptsFlower(Flower flower);

    /// Vorbedingung: prefersFlower(flower) == true || acceptsFlower(flower) == true
    /// Nachbedingung: Pflanze wurde bestäubt.
    public abstract void pollinateFlower(Flower flower);

    /// "Handshake" oder "Callback" von der Pflanze, damit wir wissen, welche Pflanze
    /// wir in pollinatedFlower aufgerufen haben.
    /// Client-History-Constraint: Darf nur innerhalb einer Methode aufgerufen werden, die
    /// von pollinateFlower() aufgerufen wurde.
    public abstract void gotNectarFromX();
    public abstract void gotNectarFromY();
    public abstract void gotNectarFromZ();

    public int collectedFromX() {
        return collectedX;
    };
    public int collectedFromY() {
        return collectedY;
    };
    public int collectedFromZ() {
        return collectedZ;
    };

    public boolean isActive() {
        return activeFor < totalActiveDays;
    };
}
