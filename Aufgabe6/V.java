// Bee likes Y also takes Z, doesn't like X
// Active for 8 Days
public class V extends Bee {
    public V() {
        totalActiveDays = 8;
    }

    @Override
    boolean collect(X plantX, boolean takesAlternative) {
        return false;
    }

    @Override
    boolean collect(Y plantY, boolean takesAlternative) {
        collectedY++;
        activeFor++;
        return true;
    }

    @Override
    boolean collect(Z plantZ, boolean takesAlternative) {
        if (takesAlternative) {
            collectedZ++;
            activeFor++;
            return true;
        }
        return false;
    }
}
