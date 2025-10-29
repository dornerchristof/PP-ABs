import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//https://github.com/SpongePowered/noise

/*
Abstrakter Datentyp(Klasse) mit nominaler Abstraktion. Simuliert ein Ökosystem,
das aus Pflanzen und Bienen besteht, die voneinander abhängig sind, um überleben zu können
 */
public class Simulation {

    private boolean yearlyOutput;
    private boolean dailyOutput;

    private Weather weather;
    private Chunk[][] world;
    private List<Flower> flowerSpecies;
    private int worldLength;
    private int worldWidth;
    private int startingHives;

    private static final int seed = 1234567890;
    private final Random numberGenerator;

    private List<SimulationState> endOfYearStates;
    private List<SimulationState> weeklyStates;
    private List<SimulationState> debugStates;
    private final int debugIntervalWeeks = 2; // Interval in weeks
    private final int[] debugIntervalYears = {0,25}; // The start and the end year to print the debug infos

    //Erstellt eine neue Simulation und nutzt die übergebenen Pflanzen und Bienen als
    //Ausgangspunkt für die Simulation.
    //startingHives: >= 2 und mod 2 = 0
    public Simulation(int worldLength, int worldWidth, Weather weather, List<Flower> flowerSpecies, int startingHives) {
        endOfYearStates = new ArrayList<>();
        weeklyStates = new ArrayList<>();
        debugStates = new ArrayList<>();
        this.weather = weather;
        this.flowerSpecies = flowerSpecies;
        numberGenerator = new Random(seed);//for testing always the same seed
        this.worldLength = worldLength;
        this.worldWidth = worldWidth;
        this.startingHives = startingHives;


        world = new Chunk[worldLength][worldWidth];
        generateWorld();
        populateWorld();
        plantSeeds();
    }

    public static boolean isInWorldBounds(Chunk[][] world, int x, int y) {
        return x >= 0 && x < world.length && y >= 0 && y < world[0].length;
    }

    private void generateWorld() {
        for (int i = 0; i < worldLength; i++) {
            for (int j = 0; j < worldWidth; j++) {
                world[i][j] = new Chunk(i, j,
                        numberGenerator.nextGaussian(100, 0.5));
            }
        }
    }

    private void populateWorld() {
        int xSpacing = worldLength / (startingHives / 2);
        int ySpacing = worldWidth / 2;

        for (int i = 0; i < (startingHives / 2); i++) {
            int x = numberGenerator.nextInt(xSpacing * i, xSpacing * (i + 1));
            int y = numberGenerator.nextInt(0, ySpacing);
            world[x][y].SetBeePopulation(new BeePopulation(500, 5, numberGenerator, false));
            x = numberGenerator.nextInt(xSpacing * i, xSpacing * (i + 1));
            y = numberGenerator.nextInt(ySpacing, worldWidth);
            world[x][y].SetBeePopulation(new BeePopulation(500, 5, numberGenerator, false));
        }

    }

    //Simuliert durch Wind zufällig verteilte Pflanzensamen, die im Frühling keimen werden.
    private void plantSeeds() {
        //zwischen 1 und 3 Pflanzen
        //normalverteilung der Pflanzensamen
        //chunks werden zufällig gewählt, bis 1/4 Pflanzen besitzen

        int x;
        int y;
        for (int i = 0; i < (worldLength * worldWidth) / 2; i++) {
            x = numberGenerator.nextInt(0, worldLength);
            y = numberGenerator.nextInt(0, worldWidth);
            if (!world[x][y].getFlowers().isEmpty()) continue;

            int flowerCount = numberGenerator.nextInt(1, 3);
            for (int j = 0; j < flowerCount; j++) {
                var f = flowerSpecies.get(numberGenerator.nextInt(flowerSpecies.size()));
                var size = numberGenerator.nextGaussian(2000, 40);
                world[x][y].plantFlower(f, size);
            }

        }
    }

    private String printWorldVerbose() {
        var s = new StringBuilder();
        for (int i = 0; i < worldLength; i++) {
            for (int j = 0; j < worldWidth; j++) {
                s.append(String.format("|b%8.0f", world[i][j].getBeePopulation() != null ? world[i][j].getBeePopulation().getPopulation() : 0));
            }
            s.append("\n");
            //Gibt die 3 größten Pflanzenpopulationen pro Chunk aus.
            for (int k = 0; k < 3; k++) {
                for (int j = 0; j < worldWidth; j++) {
                    var f = world[i][j].getFlowers();
                    f.sort(java.util.Comparator.comparingDouble(FlowerPopulation::getCurrentPopulation).reversed());
                    if (k >= f.size()) {
                        s.append("|f       0");
                    } else {
                        s.append(String.format("|%c%8.0f", f.get(k).getShortName(), f.get(k).getCurrentPopulation()));
                    }
                }
                s.append("\n");
            }
            s.append(new String(new char[worldWidth * 10]).replace("\0", "-"));
            s.append("\n");
        }
        return s.toString();
    }


    //Nominale Abstraktion.
    //Simuliert das Ökosystem für eine bestimmte Anzahl an Jahren eine bestimmte Anzahl
    //an Malen.
    //Die Ergebnisse der Simulation werden auf der Konsole ausgegeben. Wenn debug aktiv ist,
    //werden zusätzliche Informationen für bestimmte Tage und Jahre ausgegeben.
    public void simulate( int years, boolean debug) {
        if (debug) {
            yearlyOutput = dailyOutput = true;
        }
//        System.out.println("Starting simulation with " + runs + " runs");
//        System.out.println("Starting parameters:");
//        System.out.println(printWorldVerbose());


            for (int year = 1; year <= years; year++) {
                simulateYear(year);
                simulateWinter();
                if(yearlyOutput){
                    endOfYearStates.add(new SimulationState(year, 52, world));
                }
                    if(dailyOutput) dailyOutput = false;
            }
            if(yearlyOutput) yearlyOutput = false;
            System.out.println(printWorldVerbose());
            System.out.println(SimulationState.statesAsTable(endOfYearStates, flowerSpecies));
            System.out.println(SimulationState.statesAsTable(weeklyStates, flowerSpecies));



    }


    //Nominale Abstraktion.
    //Führt Berechnungen für die Simulation eines Tages während der
    //Wachstumszeit durch.
    private void growingDay() {
        /*
            STYLE: Objektorientiert. Wir rufen hier die Funktionen von der Klasse Chunk auf, die wiederrum Funktionen
            von der Klasse BeePopulation und FlowerPopulation auf. Diese Funktionen nutzen die Objektvariablen der
            Objekte, um die Simulation durchzuführen.
         */
        weather.updateWeather();
        for (int i = 0; i < worldLength; i++) {
            for (int j = 0; j < worldWidth; j++) {
               world[i][j].simulateGrowingDayFlower(weather);
            }
        }
        for (int i = 0; i < worldLength; i++) {
            for (int j = 0; j < worldWidth; j++) {
                world[i][j].simulateGrowingDayBees(world, weather);
            }
        }
    }

    private void simulateWinter(){
        for(int i = 0; i < worldLength; i++){
            for(int j = 0; j < worldWidth; j++){
                world[i][j].simulateRestingPeriod(world, numberGenerator);
            }
        }
    }

    public void printDebugStates(){
        List<SimulationState> states;
        for(int i = 0; i < debugStates.size(); i+=39){
            if(i + 39 < debugStates.size()){
                states = debugStates.subList(i, i +39);
            }else {
                states = debugStates.subList(i, debugStates.size());
            }
            System.out.println(SimulationState.statesAsTable(states, flowerSpecies));
        }
    }

    //Nominale Abstraktion.
    //Lässt die Simulation für ein ganzes Jahr (Wachstumsphase und Ruhephase) laufen.
    private void simulateYear(int year) {
        for(int d = 0; d < 270; d++){

            growingDay();
            if(d % 7 == 0 && dailyOutput){
                weeklyStates.add(new SimulationState(year, d/7, world));
            }
            if(year >= debugIntervalYears[0] && year <= debugIntervalYears[1]) {
                if (d % 7 == 0 && ((d / 7) + debugIntervalWeeks) % debugIntervalWeeks == 0) {
                    debugStates.add(new SimulationState(year, d / 7, world));
                }
            }
        }
        simulateWinter();
        if(dailyOutput)
            weeklyStates.add(new SimulationState(year, 52, world));
        if(year >= debugIntervalYears[0] && year <= debugIntervalYears[1]) {
            debugStates.add(new SimulationState(year, 52, world));
        }
        weather.startNewYear();
    }


}
