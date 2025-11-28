import java.util.Random;

public class Simulation {
    // TODO: inner implementation of Set (for Object?)
    private Set beeSet = new Set();
    private Set flowerSet = new Set();
    private Random rand = new Random();

    public void simulate() {

        //while something still alive
        for (int i = 0; i < 7; i++) {
            addRandomBee();
            addRandomFlower();

            simulateBeeDay();

            for(Object c : flowerSet){
                ((Flower)c).dayPassed();
            }
            for(Object b : beeSet){
                ((Bee)b).endDay();
            }
        }
        while (areBeesAndFlowersActive()) {
            simulateBeeDay();

            for(Object c : flowerSet){
                ((Flower)c).dayPassed();
            }
            for(Object b : beeSet){
                ((Bee)b).endDay();
            }
        }
        System.out.println("Simulation abgeschlossen");
        printStats();
    }

    private void printStats() {
        Statistics stats = new Statistics();
        for(Object c : flowerSet){
            ((Flower)c).sendData(stats);
        }
        for(Object c : beeSet){
           ((Bee)c).sendData(stats);
        }
        stats.print();
    }

    private boolean areBeesAndFlowersActive(){
        boolean flowerAlive = false;
        boolean beeAlive = false;
        for(Object c : flowerSet){
            if(((Flower)c).lives()){
                flowerAlive = true;
                break;
            }
        }
        if (!flowerAlive) return false;
        for(Object b : beeSet){
            if(((Bee)b).isActive()){
                beeAlive = true;
                break;
            }
        }
        return beeAlive;
    }

    private void simulateBeeDay() {
        for (Object o : beeSet) {
            Bee bee = (Bee) o;
            if (!bee.isActive() || !acceptableExists(bee)) {
                continue;
            }
            int randVisits= rand.nextInt(6)+5;
            boolean favoriteExists = favoriteExists(bee);
            for (int j = 0; j < randVisits; j++) {
                Flower ftv = null;
                while (ftv == null || (favoriteExists && !bee.prefersFlower(ftv)) || (!favoriteExists && !bee.acceptsFlower(ftv))) {
                    ftv = (Flower)flowerSet.findByIndex(rand.nextInt(flowerSet.size()));
                    if (!ftv.lives()){
                        ftv = null;
                    }
                }
                bee.pollinateFlower(ftv);
            }
        }
    }

    private boolean favoriteExists(Bee bee) {
        for (Object o : flowerSet) {
            if (bee.prefersFlower((Flower)o) && ((Flower)o).lives()) {
                return true;
            }
        }
        return false;
    }
    private boolean acceptableExists(Bee bee) {
        for (Object o : flowerSet) {
            if (bee.acceptsFlower((Flower)o) && ((Flower)o).lives()) {
                return true;
            }
        }
        return false;
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
            beeSet.add(createRandomBee(x));
        }
        int x2 = rand.nextInt(2);
        y = rand.nextInt(8) + 2;
        if (x2 == x) x2++;
        for (int i = 0; i < y; i++) {
            beeSet.add(createRandomBee(x2));
        }
    }
    private void addRandomFlower() {
        int x = rand.nextInt(3);
        int y = rand.nextInt(8) + 2;
        for (int i = 0; i < y; i++) {
            flowerSet.add(createRandomFlower(x ));
        }
        int x2 = rand.nextInt(2);
        y = rand.nextInt(8) + 2;
        if (x2 == x) x2++;
        for (int i = 0; i < y; i++) {
            flowerSet.add(createRandomFlower(x2));
        }
    }

}
