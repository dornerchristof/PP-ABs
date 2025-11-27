// FLower
// Active for 10 Days
public class Z extends Flower {
    public Z() {
        super(10);
    }

    @Override
    public boolean isPreferredByU() {
        return false;
    }

    @Override
    public boolean isPreferredByV() {
        return false;
    }

    @Override
    public boolean isPreferredByW() {
        return true;
    }

    @Override
    public boolean isAcceptedByU() {
        return false;
    }

    @Override
    public boolean isAcceptedByV() {
        return true;
    }

    @Override
    public boolean isAcceptedByW() {
        return false;
    }

    @Override
    public void pollinatedByU() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void pollinatedByV() {
        visitedByV++;
    }

    @Override
    public void pollinatedByW() {
        visitedByW++;
    }
}
