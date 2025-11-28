
@Annotations.Responsible(Annotations.names.Christof)
@Annotations.Invariant("Pflanze blüht für 9 Tage")
public class X extends Flower {

    public X(){
        super(9);
    }

    @Override
    public boolean isPreferredByU() {
        return true;
    }

    @Override
    public boolean isPreferredByV() {
        return false;
    }

    @Override
    public boolean isPreferredByW() {
        return false;
    }

    @Override
    public boolean isAcceptedByU() {
        return false;
    }

    @Override
    public boolean isAcceptedByV() {
        return false;
    }

    @Override
    public boolean isAcceptedByW() {
        return true;
    }

    @Override
    public void pollinatedByU(U u) {
        visitedByU++;
        u.gotNectarFromX();
    }

    @Override
    public void pollinatedByV(V v) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void pollinatedByW(W w) {
        visitedByW++;
        w.gotNectarFromX();
    }

    @Override
    public void sendData(Statistics stats) {
        stats.collectData(this);
    }
}
