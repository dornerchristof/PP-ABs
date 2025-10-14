public class BeePopulation {
    int population;

    public BeePopulation(int population){
        this.population = population;
    }

    public int getPopulation(){
        return population;
    }

    public void Tagessimulation(int availableFood){
        if(availableFood >= population){
            population = (int) (population * 1.03);
        }
        else {
            double percentage = ((6.0 * availableFood/population) - 3);
            population = (int) (population * (1 + percentage / 100));
            if(population < 0) population = 0;
        }
    }

    public void simulateRest(){
        double random = 0.1 + Math.random() * 0.2;
        population = (int) (population * random);
    }

    @Override
    public String toString() {
        return "BeePopulation: "  + population;
    }
}
