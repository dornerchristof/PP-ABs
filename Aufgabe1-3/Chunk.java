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
    }

    public void SetBeePopulation(BeePopulation bees){
        this.bees = bees;
    }

    public double getGroundFertility(){return groundFertility;}

    public boolean BeeHive(){
        return bees != null;
    }

}
