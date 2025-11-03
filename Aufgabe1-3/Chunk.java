import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chunk {

    private final List<FlowerPopulation> flowers; //flowers != null und flowers.size() >= 0 && flowers.size() <= 3
    private BeePopulation bees; //bees != null
    private final int x; //x >= 0
    private final int y; //y >= 0
    private final double groundFertility; //0 <= n <= 1

    private double beesVisited; // beesVisited >= 0

    public Chunk(int x, int y, double groundFertility){
        this.x = x;
        this.y = y;
        this.groundFertility = groundFertility;
        flowers = new ArrayList<>();
    }

    public void SetBeePopulation(BeePopulation bees){
        this.bees = bees;
    }

    public List<FlowerPopulation> getFlowers(){return flowers;}

    public BeePopulation getBeePopulation(){return bees;}

    public boolean BeeHive(){
        return bees != null;
    }

    //Fügt die Anzahl an Blumen entweder in eine bestehende Population hinzu oder erstellt eine neue, wenn es noch
    //weniger als 3 bestehende gibt.
    //flower != null, initialPopulation >= 0
    public void plantFlower(Flower flower, double count){
        for(FlowerPopulation fp : flowers){
            if(fp.getName().equals(flower.getName())){
                fp.addPopulation(count);
                return;
            }
        }
        if(flowers.size() >= 3) return;
        flowers.add(new FlowerPopulation(flower, count));
    }


    public void simulateGrowingDayFlower(Weather weather){
        double flowerCount = 0;
        for(FlowerPopulation fp : flowers){
           flowerCount += fp.getCurrentPopulation();
        }

        //Wachstum nimmt stark ab, sobald viele Pflanzen vorhanden sind.
        double growingFactor = flowerCount / (groundFertility * 1000); //Beeinflusst die Wachstumsrate Prozentual
        for (FlowerPopulation fp : flowers) {
            if(flowerCount <= 0)
                fp.simulateGrowingDay(weather, 0, growingFactor);
            else
                fp.simulateGrowingDay(weather, beesVisited * (fp.getCurrentPopulation()/flowerCount), growingFactor);
        }
        beesVisited = 0;
    }

    public void simulateGrowingDayBees(Chunk[][] world, Weather weather){
        if (bees != null){
        bees.simulateGrowingDay(world, x, y, weather);
        }
    }

    public void killBeeHive(){
        bees = null;
    }

    public void updateBeesVisited(double beesVisited){
        this.beesVisited += beesVisited;
    }

    public void simulateRestingPeriod(Chunk[][] world, Random r){
        if(bees != null){
            // Versuche die neuen Bienenschwärme anzulegen
            int[] queensResult = bees.beeQueens();
            for (int i = 0; i < queensResult[0]; i++) {
                for (int j = 0; j < 2; j++) { // Jede Königin bekommt zwei Versuche wenn sie es dann nicht schafft stirbt sie
                    int xOffset = x + r.nextInt(-6, 6);
                    int yOffset = y + r.nextInt(-6, 6);
                    //Wenn Koordninaten außerhalb sind, dann hat die Biene den Winter nicht überlebt, gefressen worden, etc.
                    if(Simulation.isInWorldBounds(world, xOffset, yOffset) && !world[xOffset][yOffset].BeeHive()){
                        world[xOffset][yOffset].SetBeePopulation(new BeePopulation(queensResult[1], 4, r, true));
                        break;
                    }
                }
            }
            // Simulate Rest Phase
            bees.simulateRestingPeriod();
        }
        //Pflanzenverbreitung
        if(flowers.isEmpty()) return;
        int xOffset = x + r.nextInt(-1, 1);
        int yOffset = y + r.nextInt(-1, 1);
        int flowerIndex = flowers.size() == 1 ? 0 : r.nextInt(flowers.size() - 1);
        if(Simulation.isInWorldBounds(world, xOffset, yOffset) ){
            world[xOffset][yOffset].plantFlower((flowers.get(flowerIndex)).getFlower(), 1000);
        }
        flowers.removeIf(fp -> !fp.simulateRestingPeriod());
    }

    public double getNahrungsangebot(){
        double nahrungsangebot = 0;
        for(FlowerPopulation fp : flowers){
            nahrungsangebot += fp.getNahrungsangebot();
        }
        return nahrungsangebot;
    }
}
