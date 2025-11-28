import java.util.Random;

@Annotations.Responsible(Annotations.names.Patrick)
@Annotations.Invariant("beeSet != null")
@Annotations.Invariant("flowerSet != null")
@Annotations.Invariant("rand != null")
public class Simulation {
    private final Set beeSet = new Set();
    private final Set flowerSet = new Set();
    private final Random rand = new Random();

    @Annotations.Postcondition("Simulation wurde durchgeführt und Statistiken ausgegeben")
    public void simulate() {
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

    @Annotations.Postcondition("Statistiken wurden auf der Konsole ausgegeben")
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

    @Annotations.Postcondition("Gibt true zurück, falls mindestens eine Biene und eine Blume noch aktiv sind")
    private boolean areBeesAndFlowersActive(){
        boolean flowerAlive = false;
        boolean beeAlive = false;
        for(Object c : flowerSet){
            if(((Flower)c).blooms()){
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

    @Annotations.Postcondition("Ein Simulations-Tag für alle Bienen wurde durchgeführt")
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
                    if (!ftv.blooms()){
                        ftv = null;
                    }
                }
                bee.pollinateFlower(ftv);
            }
        }
    }

    @Annotations.Precondition("bee != null")
    @Annotations.Postcondition("Gibt true zurück, wenn eine bevorzugte Blume im Set existiert")
    private boolean favoriteExists(Bee bee) {
        for (Object o : flowerSet) {
            if (bee.prefersFlower((Flower)o) && ((Flower)o).blooms()) {
                return true;
            }
        }
        return false;
    }
    private boolean acceptableExists(Bee bee) {
        for (Object o : flowerSet) {
            if (bee.acceptsFlower((Flower)o) && ((Flower)o).blooms()) {
                return true;
            }
        }
        return false;
    }

    @Annotations.Precondition("x ist 0, 1 oder 2")
    @Annotations.Postcondition("Gibt eine neue Bienen-Instanz zurück (U, V oder W) und erhöht den Zähler")
    private Bee createRandomBee(int x) {
        return switch (x) {
            case 0 -> new U();
            case 1 -> new V();
            case 2 -> new W();
            default -> null;
        };
    }

    @Annotations.Precondition("x ist 0, 1 oder 2")
    @Annotations.Postcondition("Gibt eine neue Blumen-Instanz zurück (X, Y oder Z) und erhöht den Zähler")
    private Flower createRandomFlower(int x) {
        return switch (x) {
            case 0 -> new X();
            case 1 -> new Y();
            case 2 -> new Z();
            default -> null;
        };
    }

    @Annotations.Postcondition("Zufällige Bienen wurden zum beeSet hinzugefügt")
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

    @Annotations.Postcondition("Zufällige Blumen wurden zum flowerSet hinzugefügt")
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
