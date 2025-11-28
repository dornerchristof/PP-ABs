// Bee likes Y also takes Z, doesn't like X
// Active for 8 Days
public class V extends Bee {
    public V() {
        totalActiveDays = 8;
    }


    @Override
    public boolean prefersFlower(Flower flower) {
        return flower.isPreferredByV();
    }

    @Override
    public boolean acceptsFlower(Flower flower) {
        return flower.isAcceptedByV();
    }

    @Override
    public void pollinateFlower(Flower flower) {
        flower.pollinatedByV(this);
    }

    @Override
    public void gotNectarFromX() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void gotNectarFromY() {
        collectedY++;
    }

    @Override
    public void gotNectarFromZ() {
        collectedZ++;
    }

    @Override
    public void sendData(Statistics stats) {
        stats.collectData(this);
    }
}
