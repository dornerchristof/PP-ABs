// Flower
// Active for 8 Days
public class Y extends Flower {

    public Y(){
        super(8);
    }

    @Override
    public boolean isPreferredByU() {
        return false;
    }

    @Override
    public boolean isPreferredByV() {
        return true;
    }

    @Override
    public boolean isPreferredByW() {
        return false;
    }

    @Override
    public boolean isAcceptedByU() {
        return true;
    }

    @Override
    public boolean isAcceptedByV() {
        return false;
    }

    @Override
    public boolean isAcceptedByW() {
        return false;
    }

    @Override
    public void pollinatedByU() {
        visitedByU++;
    }

    @Override
    public void pollinatedByV() {
        visitedByV++;
    }

    @Override
    public void pollinatedByW() {
        throw new UnsupportedOperationException();
    }
}
