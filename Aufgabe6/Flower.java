public abstract class Flower { //TODO: Visibility
    protected int visitedByU = 0;
    protected int visitedByV = 0;
    protected int visitedByW = 0;

    private int daysToLive;

    protected Flower(int daysToLive) {
        this.daysToLive = daysToLive;
    }

    public boolean lives() {
        return daysToLive >= 0;
    }

    public void dayPassed() {
        daysToLive--;
    }


    public abstract boolean isPreferredByU();
    public abstract boolean isPreferredByV();
    public abstract boolean isPreferredByW();

    public abstract boolean isAcceptedByU();
    public abstract boolean isAcceptedByV();
    public abstract boolean isAcceptedByW();


    public abstract void pollinatedByU();
    public abstract void pollinatedByV();
    public abstract void pollinatedByW();



    /// Die Blume wurde von Biene U besucht
    public int visitedByU(){
        return visitedByU;
    }
    /// Die Blume wurde von Biene V besucht
    public int visitedByV(){
        return visitedByV;
    }
    /// Die Blume wurde von Biene W besucht
    public int visitedByW(){
        return visitedByW;
    }
}
