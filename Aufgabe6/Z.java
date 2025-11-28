
@Annotations.Responsible(Annotations.names.Christof)
@Annotations.Invariant("Pflanze blüht für 10 Tage")
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
    public void pollinatedByU(U u) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void pollinatedByV(V v) {
        visitedByV++;
        v.gotNectarFromZ();
    }

    @Override
    public void pollinatedByW(W w) {
        visitedByW++;
        w.gotNectarFromZ();
    }

    @Override
    public void sendData(Statistics stats) {
        stats.collectData(this);
    }
}
