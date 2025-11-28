
@Annotations.Responsible(Annotations.names.Christof)
@Annotations.ServerHistoryConstraint("daysToLive kann nur niedriger werden")
@Annotations.ServerHistoryConstraint("visitedByU kann nur größer werden")
@Annotations.ServerHistoryConstraint("visitedByV kann nur größer werden")
@Annotations.ServerHistoryConstraint("visitedByW kann nur größer werden")
@Annotations.Invariant("visitedByU >= 0")
@Annotations.Invariant("visitedByV >= 0")
@Annotations.Invariant("visitedByW >= 0")
public abstract class Flower {
    protected int visitedByU = 0;
    protected int visitedByV = 0;
    protected int visitedByW = 0;

    private int daysToLive;

    @Annotations.Precondition("daysToLive >= 0")
    @Annotations.Postcondition("Pflanze blüht die übergebene Anzahl an Tagen")
    protected Flower(int daysToLive) {
        this.daysToLive = daysToLive;
    }

    @Annotations.Postcondition("Wahr, falls die Pflanze noch blüht")
    public boolean blooms() {
        return daysToLive >= 0;
    }

    @Annotations.Postcondition("Tage bis zum Verblühen um eins verringert")
    public void dayPassed() {
        daysToLive--;
    }

    @Annotations.Postcondition("Wahr, falls die Pflanze von der Biene U präferiert wird.")
    public abstract boolean isPreferredByU();
    @Annotations.Postcondition("Wahr, falls die Pflanze von der Biene V präferiert wird.")
    public abstract boolean isPreferredByV();
    @Annotations.Postcondition("Wahr, falls die Pflanze von der Biene W präferiert wird.")
    public abstract boolean isPreferredByW();

    @Annotations.Postcondition("Wahr, falls die Pflanze von der Biene U akzeptiert wird.")
    public abstract boolean isAcceptedByU();
    @Annotations.Postcondition("Wahr, falls die Pflanze von der Biene V akzeptiert wird.")
    public abstract boolean isAcceptedByV();
    @Annotations.Postcondition("Wahr, falls die Pflanze von der Biene W akzeptiert wird.")
    public abstract boolean isAcceptedByW();

    @Annotations.Precondition("isPreferredByU() == true || isAcceptedByU == true")
    @Annotations.Postcondition("visitedByU um eins erhöht und u.gotNectarFromU() aufgerufen.")
    public abstract void pollinatedByU(U u);
    @Annotations.Precondition("isPreferredByV() == true || isAcceptedByV == true")
    @Annotations.Postcondition("visitedByV um eins erhöht und u.gotNectarFromV() aufgerufen.")
    public abstract void pollinatedByV(V v);
    @Annotations.Precondition("isPreferredByV() == true || isAcceptedByV == true")
    @Annotations.Postcondition("visitedByW um eins erhöht und u.gotNectarFromW() aufgerufen.")
    public abstract void pollinatedByW(W w);

    @Annotations.Precondition("stats != null")
    @Annotations.Postcondition("Speichert die Statistikdaten im stats Objekt")
    public abstract void sendData(Statistics stats);

    @Annotations.Postcondition("Anzahl an Besuchen der Biene U")
    public int visitedByU(){
        return visitedByU;
    }

    @Annotations.Postcondition("Anzahl an Besuchen der Biene U")
    public int visitedByV(){
        return visitedByV;
    }

    @Annotations.Postcondition("Anzahl an Besuchen der Biene U")
    public int visitedByW(){
        return visitedByW;
    }
}
