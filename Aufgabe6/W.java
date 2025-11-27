// Bee likes Z also takes X, doesn't like Y
// Active for 10 Days
public class W extends Bee {
    public W() {
        totalActiveDays = 10;
    }

    @Override
    boolean collect(X plantX, boolean takesAlternative) {
        if (takesAlternative) {
            collectedX++;
            activeFor++;
            return true;
        }
        return false;
    }

    @Override
    boolean collect(Y plantY, boolean takesAlternative) {
        return false;
    }

    @Override
    boolean collect(Z plantZ, boolean takesAlternative) {
        collectedZ++;
        activeFor++;
        return false;
    }
}
