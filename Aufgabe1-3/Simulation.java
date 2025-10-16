import java.util.Random;

public class Simulation {

    private StringBuilder debugInfos;
    private int yearsToSimulate;
    private Random numberGenerator;
    private int totalSunshineHours;
    private double groundMoisture;

    private TotalFlowerPopulation initialFlowers;
    private BeePopulation initialBees;
    private TotalFlowerPopulation workingFlowers;
    private BeePopulation workingBees;

    public Simulation(int yearsToSim, TotalFlowerPopulation flowers, BeePopulation bees){
        initialFlowers = flowers;
        initialBees = bees;
        yearsToSimulate = yearsToSim;
        numberGenerator = new Random(1);//for testing always the same seed
    }

    public void simulate(int runs){
        workingBees = initialBees;
        workingFlowers = initialFlowers;

        System.out.println("Starting simulation with "  + runs + " runs");
        System.out.println("PARAMETER:");
        System.out.println("Bee population: " + workingBees.population);
        workingFlowers.printParameters();
        for(int i = 0; i < runs; i++) {
            for (int year = 1; year <= yearsToSimulate; year++) {
                simulateYear();
            }
            System.out.println("simulation of " + yearsToSimulate + " years completed");
            System.out.println("RESULTS:");
            System.out.println("bee population: " + workingBees.population);
            workingFlowers.printFlowers();
            System.out.println("Resetting parameters");
            workingBees = initialBees;
            workingFlowers = initialFlowers;
        }
    }

    private void growingDay(){
        int dailySunshine = numberGenerator.nextInt(13);
        totalSunshineHours += dailySunshine;

        double availableFood = workingFlowers.getNahrungsAngebot();

        workingBees.Tagessimulation((int) availableFood);

        groundMoisture += numberGenerator.nextDouble(-0.1, Math.nextUp(0.1));
        workingFlowers.Tagessimulation(groundMoisture,dailySunshine, workingBees.getPopulation(),availableFood,false);
    }

    private void simulateYear(){
        //growing periot
        groundMoisture = numberGenerator.nextDouble(Math.nextUp(1));
        totalSunshineHours = 0;
        for(int d = 1; d <= 270; d++){
            growingDay();
        }
        System.out.println("winter is comming");
        workingBees.simulateRest();
        workingFlowers.Tagessimulation(0,0,0,0,true);

    }
}
