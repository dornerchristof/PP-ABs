import java.util.List;
import java.util.Random;

public class Simulation {

    private StringBuilder debugInfos;
    private int yearsToSimulate;
    private Random numberGenerator;
    private int totalSunshineHours;
    private double groundMoisture;

    private TotalFlowerPopulation flowers;
    private BeePopulation bees;

    public Simulation(int yearsToSim, TotalFlowerPopulation flowers, BeePopulation initialBees){
        this.flowers = flowers;
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

        double availableFood = flowers.getNahrungsAngebot();

        bees.Tagessimulation((int) availableFood);

        groundMoisture += numberGenerator.nextDouble(-0.1, Math.nextUp(0.1));
        flowers.Tagessimulation(groundMoisture,dailySunshine,bees.getPopulation(),availableFood,false);
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
