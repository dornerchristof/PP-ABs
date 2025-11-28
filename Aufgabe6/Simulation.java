import java.util.Random;
import java.util.concurrent.Flow;

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
        Z z = new Z();

        int ZbyV = 0;
        int YbyV = 0;
        int XbyV = 0;
        int ZbyU = 0;
        int YbyU = 0;
        int XbyU = 0;
        int ZbyW = 0;
        int YbyW = 0;
        int XbyW = 0;

        for (Object o : beeSet) {
            Bee bee = (Bee) o;
            if (bee.prefersFlower(z)){ // Bee is W
                ZbyW += bee.collectedZ;
                YbyW += bee.collectedY;
                XbyW += bee.collectedX;
            }
            else if (bee.acceptsFlower(z)){ // Bee is V
                ZbyV += bee.collectedZ;
                YbyV += bee.collectedY;
                XbyV += bee.collectedX;
            }
            else{ // Bee is U
                ZbyU += bee.collectedZ;
                YbyU += bee.collectedY;
                XbyU += bee.collectedX;
            }
        }

        int WviZ = 0;
        int WviY = 0;
        int WviX = 0;
        int VviZ = 0;
        int VviY = 0;
        int VviX = 0;
        int UviZ = 0;
        int UviY = 0;
        int UviX = 0;

        for (Object o : flowerSet) {
            Flower flower = (Flower) o;
            if (flower.isPreferredByU()){ // Flower is X
                WviX += flower.visitedByW;
                VviX += flower.visitedByV;
                UviX += flower.visitedByU;
            }
            else if (flower.isPreferredByV()){ // Flower is Y
                WviY += flower.visitedByW;
                VviY += flower.visitedByV;
                UviY += flower.visitedByU;
            }
            else{ // Flower is Z
                WviZ += flower.visitedByW;
                VviZ += flower.visitedByV;
                UviZ += flower.visitedByU;
            }
        }
        System.out.println("\nSTATISTIK: GESAMMELTER NEKTAR (Bienen-Sicht)");
        System.out.println("+-------+-------+-------+-------+-------+");
        System.out.println("| Biene |   X   |   Y   |   Z   | Gesamt|");
        System.out.println("+-------+-------+-------+-------+-------+");
        System.out.printf("|   U   | %-5d | %-5d | %-5d | %-5d |\n", XbyU, YbyU, ZbyU, XbyU + YbyU + ZbyU);
        System.out.printf("|   V   | %-5d | %-5d | %-5d | %-5d |\n", XbyV, YbyV, ZbyV, XbyV + YbyV + ZbyV);
        System.out.printf("|   W   | %-5d | %-5d | %-5d | %-5d |\n", XbyW, YbyW, ZbyW, XbyW + YbyW + ZbyW);
        System.out.println("+-------+-------+-------+-------+-------+");

        System.out.println("\nSTATISTIK: ERHALTENE BESUCHE (Blumen-Sicht)");
        System.out.println("+-------+-------+-------+-------+-------+");
        System.out.println("| Blume |   U   |   V   |   W   | Gesamt|");
        System.out.println("+-------+-------+-------+-------+-------+");
        System.out.printf("|   X   | %-5d | %-5d | %-5d | %-5d |\n", UviX, VviX, WviX, UviX + VviX + WviX);
        System.out.printf("|   Y   | %-5d | %-5d | %-5d | %-5d |\n", UviY, VviY, WviY, UviY + VviY + WviY);
        System.out.printf("|   Z   | %-5d | %-5d | %-5d | %-5d |\n", UviZ, VviZ, WviZ, UviZ + VviZ + WviZ);
        System.out.println("+-------+-------+-------+-------+-------+");
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
