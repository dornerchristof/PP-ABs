import java.util.Random;

/*
Abstrakter Datentyp(Klasse) mit nominaler Abstraktion. Simuliert ein Ökosystem,
das aus Pflanzen und Bienen besteht, die voneinander abhängig sind, um überleben zu können
 */
public class Simulation {

    private StringBuilder debugInfos;
    private Random numberGenerator;
    private double groundMoisture;

    private TotalFlowerPopulation initialFlowers;
    private BeePopulation initialBees;
    private TotalFlowerPopulation workingFlowers;
    private BeePopulation workingBees;

    private boolean yearlyOutput;
    private boolean dailyOutput;

    //Erstellt eine neue Simulation und nutzt die übergebenen Pflanzen und Bienen als
    //Ausgangspunkt für die Simulation.
    public Simulation(TotalFlowerPopulation flowers, BeePopulation bees){
        initialFlowers = flowers;
        initialBees = bees;
        debugInfos = new StringBuilder();
        numberGenerator = new Random(1);//for testing always the same seed
    }

    //Nominale Abstraktion.
    //Simuliert das Ökosystem für eine bestimmte Anzahl an Jahren eine bestimmte Anzahl
    //an Malen.
    //Die Ergebnisse der Simulation werden auf der Konsole ausgegeben. Wenn debug aktiv ist,
    //werden zusätzliche Informationen für bestimmte Tage und Jahre ausgegeben.
    public void simulate(int runs, int yearsPerRun, boolean debug){
        workingBees = initialBees;
        workingFlowers = initialFlowers;

       if(debug) {
           yearlyOutput = dailyOutput = true;
       }

        System.out.println("Starting simulation with "  + runs + " runs");
        System.out.println("PARAMETER:");
        System.out.println("Bee population: " + workingBees.population);
        workingFlowers.printParameters();
        for(int i = 0; i < runs; i++) {
            for (int year = 1; year <= yearsPerRun; year++) {
                    simulateYear();
                    if(yearlyOutput){
                    debugInfos.append("Year ").append(year).append(" results:\n");
                    debugInfos.append(workingBees);
                    debugInfos.append(workingFlowers);
                    debugInfos.append("\n");
                }
                    if(dailyOutput) dailyOutput = false;
            }
            if(yearlyOutput) yearlyOutput = false;

            System.out.println("simulation of " + yearsPerRun + " years completed");
            System.out.println("RESULTS:");
            System.out.println("bee population: " + workingBees.population);
            workingFlowers.printFlowers();
            System.out.println("Resetting parameters");

            workingBees = initialBees;
            workingFlowers = initialFlowers;
        }

    }

    //Nominale Abstraktion.
    //Führt Berechnungen für die Simulation eines Tages während der
    //Wachstumszeit durch.
    private void growingDay(){
        int dailySunshine = numberGenerator.nextInt(13);

        double availableFood = workingFlowers.getNahrungsAngebot();

        workingBees.Tagessimulation((int) availableFood);

        groundMoisture += numberGenerator.nextDouble(-0.1, Math.nextUp(0.1));
        workingFlowers.Tagessimulation(groundMoisture,dailySunshine,
                workingBees.getPopulation(),availableFood,false);

    }

    //Nominale Abstraktion.
    //Lässt die Simulation für ein ganzes Jahr (Wachstumsphase und Ruhephase) laufen.
    private void simulateYear(){
        groundMoisture = numberGenerator.nextDouble(Math.nextUp(1));
        for(int d = 1; d <= 270; d++){
            growingDay();
            if(dailyOutput){
                debugInfos.append("Result of day ").append(d).append(":\n");
                debugInfos.append(workingBees);
                debugInfos.append(workingFlowers);
                debugInfos.append("\n");
            }
        }
        workingBees.simulateRest();
        workingFlowers.Tagessimulation(0,0,0,0,true);

    }

    public void printDebugInfos(){
        System.out.println(debugInfos);
    }
}
