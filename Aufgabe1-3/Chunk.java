import java.util.ArrayList;
import java.util.List;

public class Chunk {

    private List<FlowerPopulation> flowers;
    private BeePopulation bees;
    private int x;
    private int y;
    private double groundFertility; //0 <= n <= 1


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
            //fp.Tagessimulation(weather, growingFactor);//TODO Welche Werte?
        }
    }

    public void simulateBeeDay(Chunk[][] world){
        if (bees != null){
        //bees.Tagessimulation(world); //TODO Welche Werte?
        }
    }

    public void simulateWinter(){

    }

}
