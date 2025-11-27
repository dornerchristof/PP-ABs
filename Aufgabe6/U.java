// Bee likes X takes Y doesn't like Z
// Active for 9 Days
public class U extends Bee {
    public U() {
        totalActiveDays = 9;
    }

    @Override
    public boolean prefersFlower(Flower flower) {
        return flower.isPreferredByU();
    }

    @Override
    public boolean acceptsFlower(Flower flower) {
        return flower.isAcceptedByU();
    }

    @Override
    public void pollinateFlower(Flower flower) {
        flower.pollinatedByU();
    }

    @Override
    boolean collect(X plantX, boolean takesAlternative) {
        collectedX++;
        activeFor++;
        return true;
    }

    @Override
    boolean collect(Y plantY, boolean takesAlternative) {
        if (takesAlternative) {
            collectedY++;
            activeFor++;
            return true;
        }
        return false;
    }

    @Override
    boolean collect(Z plantZ, boolean takesAlternative) {
        return false;
    }

}
