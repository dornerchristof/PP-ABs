import java.util.List;
import java.util.Random;

public class Simulation {

    private StringBuilder debugInfos;
    private int yearsToSimulate;
    private Random numberGenerator;
    private int totalSunshineHours;
    private double groundMoisture;

    private List<FlowerPopulation> _flowers;
    private BeePopulation bees;

    public Simulation(int yearsToSim, List<FlowerPopulation> flowers, BeePopulation initialBees){
        _flowers = flowers;
        bees = initialBees;
        yearsToSimulate = yearsToSim;
        numberGenerator = new Random(1);//for testing always the same seed
    }

    public void simulate(){
        for(int year = 1; year <= yearsToSimulate; year++){
            simulateYear();
        }
        System.out.println("Ergebnisse");
    }

    private void growingDay(){
        int dailySunshine = numberGenerator.nextInt(13);
        totalSunshineHours += dailySunshine;

        int availableFood = 0;
        for(var flower : _flowers){
            availableFood += flower.Tagessimulation(groundMoisture, dailySunshine, bees.getPopulation());
        }

        bees.Tagessimulation(availableFood);

        groundMoisture += numberGenerator.nextDouble(-0.1, Math.nextUp(0.1));
    }

    private void simulateYear(){
        //growing periot
        groundMoisture = numberGenerator.nextDouble(Math.nextUp(1));
        totalSunshineHours = 0;
        for(int d = 1; d <= 270; d++){
            growingDay();
        }
        //winter is comming

        //Bienenpopulation muss mit 0.1 bis 0.3 multipliziert werden --> Habe ich schon mit der Funktion simulateRest in der BeePopulation Klasse gemacht, hoffe das passt :)



    }
}
