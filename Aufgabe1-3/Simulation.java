import java.util.List;

public class Simulation {

    private StringBuilder debugInfos;

    public Simulation(int yearsToSim, List<FlowerPopulation> flowers, BeePopulation initialBees){

    }

    public void simulate(){
        for(int year = 1; year <= 25; year++){
            simulateYear();
        }
        System.out.println("Ergebnisse");
    }

    private void simulateYear(){



    }
}
