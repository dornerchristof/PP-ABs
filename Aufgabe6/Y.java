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
    public void pollinatedByU(U u) {
        visitedByU++;
        u.gotNectarFromY();
    }

    @Override
    public void pollinatedByV(V v) {
        visitedByV++;
        v.gotNectarFromY();
    }

    @Override
    public void pollinatedByW(W w) {
        throw new UnsupportedOperationException();
    }
}
