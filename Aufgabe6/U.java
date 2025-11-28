// Bee likes X takes Y doesn't like Z
// Active for 9 Days
@Annotations.Responsible(Annotations.names.Patrick)
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
        flower.pollinatedByU(this);
    }

    @Override
    public void gotNectarFromX() {
        collectedX++;
    }

    @Override
    public void gotNectarFromY() {
        collectedY++;
    }

    @Override
    public void gotNectarFromZ() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendData(Statistics stats) {
        stats.collectData(this);
    }
}
