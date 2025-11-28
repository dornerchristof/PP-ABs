// Bee likes Z also takes X, doesn't like Y
// Active for 10 Days
@Annotations.Responsible(Annotations.names.Patrick)
public class W extends Bee {
    public W() {
        totalActiveDays = 10;
    }


    @Override
    public boolean prefersFlower(Flower flower) {
        return flower.isPreferredByW();
    }

    @Override
    public boolean acceptsFlower(Flower flower) {
        return flower.isAcceptedByW();
    }

    @Override
    public void pollinateFlower(Flower flower) {
        flower.pollinatedByW(this);
    }

    @Override
    public void gotNectarFromX() {
        collectedX++;
    }

    @Override
    public void gotNectarFromY() {
        throw new UnsupportedOperationException("Not supported yet.");
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
