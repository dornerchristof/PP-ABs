import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chunk {

    private List<FlowerPopulation> flowers;
    private BeePopulation bees;
    private int x;
    private int y;
    private double groundFertility; //0 <= n <= 1

    private double beesVisited;

    public Chunk(int x, int y, double groundFertility){
        this.x = x;
        this.y = y;
        this.groundFertility = groundFertility;
        flowers = new ArrayList<FlowerPopulation>();
    }

    public void SetBeePopulation(BeePopulation bees){
        this.bees = bees;
    }

    public double getGroundFertility(){return groundFertility;}

    public List<FlowerPopulation> getFlowers(){return flowers;}

    public BeePopulation getBeePopulation(){return bees;}

    public boolean BeeHive(){
        return bees != null;
    }

    public void plantFlower(Flower flower, double initialPopulation){
        flowers.add(new FlowerPopulation(flower, initialPopulation));
    }


    public void simulatePlantDay(Weather weather){
        double flowerCount = 0;
        for(FlowerPopulation fp : flowers){
           flowerCount += fp.getFlowersInChunk();
        }
        //Wachstum nimmt stark ab, sobald viele Pflanzen vorhanden sind.
        double growingFactor = flowerCount / (groundFertility * 1000); //Beeinflusst die Wachstumsrate Prozentual
        for (FlowerPopulation fp : flowers) {
            if (beesVisited>= getNahrungsangebot()) {
                fp.Tagessimulation(weather, 1 * growingFactor, false);
            } else {
                fp.Tagessimulation(weather, 0.7 * growingFactor, false);
            }
        }
        beesVisited = 0;
    }

    public void simulateBeeDay(Chunk[][] world, Weather weather){
        if (bees != null){
        bees.Tagessimulation(world, x, y, weather); //TODO Welche Werte?
        }
    }

    public void killBeeHive(){
        bees = null;
    }

    public void updateBeesVisited(double beesVisited){
        this.beesVisited += beesVisited;
    }

    public void simulateWinter(Chunk[][] world, Random r){
        if(bees != null){
            // Versuche die neuen Bienenschwärme anzulegen
            int[] queensResult = bees.beeQueens();
            for (int i = 0; i < queensResult[0]; i++) {
                for (int j = 0; j < 2; j++) { // Jede Königin bekommt zwei Versuche wenn sie es dann nicht schafft stirbt sie

                }
                int xOffset = x + r.nextInt(-6, 6);
                int yOffset = y + r.nextInt(-6, 6);
                //Wenn Koordninaten außerhalb sind, dann hat die Biene den Winter nicht überlebt, gefressen worden, etc.
                if(Simulation.isInWorldBounds(world, xOffset, yOffset) && !world[xOffset][yOffset].BeeHive())
                    continue;
                world[xOffset][yOffset].SetBeePopulation(new BeePopulation(queensResult[1], 3, r, true));
            }
            // Simulate Rest Phase
            bees.simulateRest();
        }
        for(FlowerPopulation fp : flowers){
        }
        //TODO: Simulation von Pflanzenvermehrung.
    }

    public double getNahrungsangebot(){
        double nahrungsangebot = 0;
        for(FlowerPopulation fp : flowers){
            nahrungsangebot += fp.getNahrungsangebot();
        }
        return nahrungsangebot;
    }

}
