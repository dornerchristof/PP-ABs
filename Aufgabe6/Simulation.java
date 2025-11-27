import java.util.Random;

public class Simulation {
    // TODO: inner implementation of Set (for Object?)
    private Set<Object> beeSet = new Set<>();
    private Set<Object> flowerSet = new Set<>();
    private Random rand = new Random();

    public void simulate() {

        //while something still alive
        for (int i = 0; i < 7; i++) {
            addRandomBee();
            addRandomBee();
            addRandomFlower();
            addRandomFlower();
            for(Object c : flowerSet){
                ((Flower)c).dayPassed();
            }
        }
    }
    private Bee createRandomBee(int x) {
        return switch (x) {
            case 0 -> new U();
            case 1 -> new V();
            case 2 -> new W();
            default -> null;
        };
    }
    private Flower createRandomFlower(int x) {
        return switch (x) {
            case 0 -> new X();
            case 1 -> new Y();
            case 2 -> new Z();
            default -> null;
        };
    }

    private void addRandomBee() {
        int x = rand.nextInt(3);
        int y = rand.nextInt(8) + 2;
        for (int i = 0; i < y; i++) {
            beeSet.add(createRandomBee(x ));
        }
    }
    private void addRandomFlower() {
        int x = rand.nextInt(3);
        int y = rand.nextInt(8) + 2;
        for (int i = 0; i < y; i++) {
            flowerSet.add(createRandomFlower(x ));
        }
    }

}
