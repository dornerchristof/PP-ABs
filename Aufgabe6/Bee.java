@Annotations.Responsible(Annotations.names.Patrick)
@Annotations.ClientHistoryConstraint("Entweder prefersFlower(flower) == true oder acceptsFlower(flower) == true bevor pollinateFlower aufgerufen wird.")
@Annotations.ClientHistoryConstraint("gotNectarFromX darf nur innerhalb einer Methode aufgerufen werden, die pollinateFlower() aufgerufen wurde")
@Annotations.ClientHistoryConstraint("gotNectarFromY darf nur innerhalb einer Methode aufgerufen werden, die pollinateFlower() aufgerufen wurde")
@Annotations.ClientHistoryConstraint("gotNectarFromZ darf nur innerhalb einer Methode aufgerufen werden, die pollinateFlower() aufgerufen wurde")
@Annotations.ClientHistoryConstraint("sendData darf nur aufgerufen werden, nachdem die Biene nicht mehr aktiv ist.")
@Annotations.ClientHistoryConstraint("sendData darf nur einmal aufgerufen werden.")
@Annotations.ServerHistoryConstraint("Alle variablen zählen ausschließlich hoch")
@Annotations.Invariant("daysLived >= 0")
@Annotations.Invariant("totalActiveDays >= daysLived")
public abstract class Bee {
    protected int collectedX = 0;
    protected int collectedY = 0;
    protected int collectedZ = 0;
    protected int daysLived = 0;
    protected int totalActiveDays = 0;

    @Annotations.Precondition("flower != null")
    @Annotations.Postcondition("Gibt true zurück, wenn die Flower die bevorzugte Blume ist.")
    public abstract boolean prefersFlower(Flower flower);

    @Annotations.Precondition("flower != null")
    @Annotations.Postcondition("Gibt true zurück, wenn die Flower akzeptiert wird.")
    public abstract boolean acceptsFlower(Flower flower);

    @Annotations.Precondition("flower != null")
    @Annotations.Postcondition("Pflanze wurde bestäubt")
    public abstract void pollinateFlower(Flower flower);

    /// "Handshake" oder "Callback" von der Pflanze, damit wir wissen, welche Pflanze
    /// wir in pollinatedFlower aufgerufen haben.
    @Annotations.Postcondition("result >= 0")
    public abstract void gotNectarFromX();
    /// "Handshake" oder "Callback" von der Pflanze, damit wir wissen, welche Pflanze
    /// wir in pollinatedFlower aufgerufen haben.
    @Annotations.Postcondition("result >= 0")
    public abstract void gotNectarFromY();
    /// "Handshake" oder "Callback" von der Pflanze, damit wir wissen, welche Pflanze
    /// wir in pollinatedFlower aufgerufen haben.
    @Annotations.Postcondition("result >= 0")
    public abstract void gotNectarFromZ();

    @Annotations.Postcondition("Gibt die Anzahl an Bestäubungsflügen zu einer Blume X zurück")
    @Annotations.Postcondition("result >= 0")
    public int collectedFromX() {
        return collectedX;
    };

    @Annotations.Postcondition("Gibt die Anzahl an Bestäubungsflügen zu einer Blume Y zurück")
    @Annotations.Postcondition("result >= 0")
    public int collectedFromY() {
        return collectedY;
    };

    @Annotations.Postcondition("Gibt die Anzahl an Bestäubungsflügen zu einer Blume Z zurück")
    @Annotations.Postcondition("result >= 0")
    public int collectedFromZ() {
        return collectedZ;
    };

    @Annotations.Precondition("stats != null")
    public abstract void sendData(Statistics stats);

    @Annotations.Postcondition("Erhöht die Tage, die die Biene aktiv war um eins.")
    public void endDay() {
        if (!isActive()) return;
        daysLived++;
    };

    @Annotations.Postcondition("Gibt zurück, ob die Biene noch aktiv ist.")
    public boolean isActive() {
        return daysLived < totalActiveDays;
    };
}
