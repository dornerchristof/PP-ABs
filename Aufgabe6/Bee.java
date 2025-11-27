public abstract class Bee { //TODO: Visibility
    protected int collectedX = 0;
    protected int collectedY = 0;
    protected int collectedZ = 0;
    protected int activeFor = 0;
    protected int totalActiveDays = 0;

    public int collectedFromX() {
        return collectedX;
    };
    public int collectedFromY() {
        return collectedY;
    };
    public int collectedFromZ() {
        return collectedZ;
    };

    abstract boolean collect(X plantX, boolean takesAlternative);
    abstract boolean collect(Y plantY, boolean takesAlternative);
    abstract boolean collect(Z plantZ, boolean takesAlternative);

    public boolean isActive() {
        return activeFor < totalActiveDays;
    };
}
