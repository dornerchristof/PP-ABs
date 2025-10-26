import java.awt.image.CropImageFilter;
import java.util.List;
import java.util.Random;
//https://github.com/SpongePowered/noise
import com.flowpowered.noise.module.Module;
import com.flowpowered.noise.module.source.Perlin;

/*
Abstrakter Datentyp(Klasse) mit nominaler Abstraktion. Simuliert ein Ökosystem,
das aus Pflanzen und Bienen besteht, die voneinander abhängig sind, um überleben zu können
 */
public class Simulation {
    private final StringBuilder debugInfos;
    private boolean yearlyOutput;
    private boolean dailyOutput;

    private Weather weather;
    private Chunk[][] world;
    private Chunk[][] backupWorld;
    private List<Flower> flowerSpecies;
    private int worldLength;
    private int worldWidth;
    private int startingHives;

    private static final int seed = 1234567890;
    private final Random numberGenerator;
    private static final int fertilityScale = 10;
    private static final Module perlin = new Perlin();

    //Erstellt eine neue Simulation und nutzt die übergebenen Pflanzen und Bienen als
    //Ausgangspunkt für die Simulation.
    //startingHives: >= 2 und mod 2 = 0
    public Simulation(int worldLength, int worldWidth, Weather weather, List<Flower> flowerSpecies, int startingHives,
                      int meanSeedsPerChunk) {
        debugInfos = new StringBuilder();
        this.weather = weather;
        this.flowerSpecies = flowerSpecies;
        numberGenerator = new Random(seed);//for testing always the same seed
        Perlin perlin = new Perlin();
        perlin.setSeed(seed);
        perlin.setOctaveCount(4); // Multiple octaves for varied terrain
        perlin.setFrequency(1.0);
        perlin.setPersistence(0.5);
        perlin.setLacunarity(2.0);
        this.worldLength = worldLength;
        this.worldWidth = worldWidth;
        this.startingHives = startingHives;


        world = new Chunk[worldLength][worldWidth];
        generateWorld();
        populateWorld();
        plantSeeds();
        //printFertility();
        //printBeeHives();
        //printWorldVerbose();
    }

    public static boolean isInWorldBounds(Chunk[][] world, int x, int y) {
        return x >= 0 && x < world.length && y >= 0 && y < world[0].length;
    }

    private void generateWorld() {
        for (int i = 0; i < worldLength; i++) {
            for (int j = 0; j < worldWidth; j++) {
                world[i][j] = new Chunk(i, j,
                        perlin.getValue((double) i / fertilityScale, (double) j / fertilityScale, 0) + 1.);
            }
        }
    }


    private void populateWorld() {
        int xSpacing = worldLength / (startingHives / 2);
        int ySpacing = worldWidth / 2;

        for (int i = 0; i < (startingHives / 2); i++) {
            int x = numberGenerator.nextInt(xSpacing * i, xSpacing * (i + 1));
            int y = numberGenerator.nextInt(0, ySpacing);
            world[x][y].SetBeePopulation(new BeePopulation(1000, 3, numberGenerator, false));
            x = numberGenerator.nextInt(xSpacing * i, xSpacing * (i + 1));
            y = numberGenerator.nextInt(ySpacing, worldWidth);
            world[x][y].SetBeePopulation(new BeePopulation(1000, 3, numberGenerator, false));
        }

    }

    //Simuliert durch Wind zufällig verteilte Pflanzensamen, die im Frühling keimen werden.
    private void plantSeeds() {
        //zwischen 1 und 3 Pflanzen
        //normalverteilung der Pflanzensamen
        //chunks werden zufällig gewählt, bis 1/4 Pflanzen besitzen

        int x;
        int y;
        for (int i = 0; i < (worldLength * worldWidth) / 6; i++) {
            x = numberGenerator.nextInt(0, worldLength);
            y = numberGenerator.nextInt(0, worldWidth);
            if (!world[x][y].getFlowers().isEmpty()) continue;

            int flowerCount = numberGenerator.nextInt(1, 3);
            for (int j = 0; j < flowerCount; j++) {
                var f = flowerSpecies.get(numberGenerator.nextInt(flowerSpecies.size() - 1));
                var size = numberGenerator.nextGaussian(1000, 20);
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
                    f.sort(java.util.Comparator.comparingDouble(FlowerPopulation::getWuchskraft).reversed());
                    if (k >= f.size()) {
                        s.append("|f       0");
                    } else {
                        s.append(String.format("|%c%8.0f", f.get(k).getShortName(), f.get(k).getWuchskraft()));
                    }
                }
                s.append("\n");
            }
            s.append(new String(new char[worldWidth * 10]).replace("\0", "-"));
            s.append("\n");
        }
        return s.toString();
    }

    private void printFertility() {
        for (int i = 0; i < worldLength; i++) {
            var s = new StringBuilder();
            for (int j = 0; j < worldWidth; j++) {
                s.append(String.format("%.2f", world[i][j].getGroundFertility()));
                s.append(" ");
            }
            System.out.println(s);
        }
    }

    private void printBeeHives() {
        for (int i = 0; i < worldLength; i++) {
            var s = new StringBuilder();
            for (int j = 0; j < worldWidth; j++) {
                if (world[i][j].BeeHive())
                    s.append("1 ");
                else
                    s.append("  ");
            }
            System.out.println(s);
        }
    }


    //Nominale Abstraktion.
    //Simuliert das Ökosystem für eine bestimmte Anzahl an Jahren eine bestimmte Anzahl
    //an Malen.
    //Die Ergebnisse der Simulation werden auf der Konsole ausgegeben. Wenn debug aktiv ist,
    //werden zusätzliche Informationen für bestimmte Tage und Jahre ausgegeben.
    public void simulate(int runs, int yearsPerRun, boolean debug) {

        if (debug) {
            yearlyOutput = dailyOutput = true;
        }

        System.out.println("Starting simulation with " + runs + " runs");
        System.out.println("Starting parameters:");
        System.out.println(printWorldVerbose());
        backupWorld = deepCopyWorld(world);

        for(int i = 1; i <= runs; i++) {
            for (int year = 1; year <= yearsPerRun; year++) {
                simulateYear();
                simulateWinter();
                if(yearlyOutput){
                    debugInfos.append("Year ").append(year).append(" results:\n");
                    debugInfos.append(printWorldVerbose());
                    debugInfos.append("\n");
                }
                    if(dailyOutput) dailyOutput = false;
            }
            if(yearlyOutput) yearlyOutput = false;
            System.out.println("Results of " + i + ". simulation run:");
            System.out.println(printWorldVerbose());
            world = deepCopyWorld(backupWorld); // Reset World am Ende jedes Runs
        }

    }

    private Chunk[][] deepCopyWorld(Chunk[][] original) {
        Chunk[][] copy = new Chunk[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                copy[i][j] =  new Chunk(original[i][j]); // You'll need to implement copy() in Chunk class
            }
        }
        return copy;
    }

    //Nominale Abstraktion.
    //Führt Berechnungen für die Simulation eines Tages während der
    //Wachstumszeit durch.
    private void growingDay() {
        weather.updateWeather();
        for (int i = 0; i < worldLength; i++) {
            for (int j = 0; j < worldWidth; j++) {
               world[i][j].simulatePlantDay(weather);
            }
        }
        for (int i = 0; i < worldLength; i++) {
            for (int j = 0; j < worldWidth; j++) {
                world[i][j].simulateBeeDay(world, weather);
            }
        }
        //int dailySunshine = weather.getSunshineHours();
//        groundMoisture = weather.getSoilMoisture();
//
//        double availableFood = workingFlowers.getNahrungsAngebot();
//
//        workingBees.Tagessimulation(availableFood);
//
//        if(groundMoisture > 1) groundMoisture = 1;
//        if(groundMoisture < 0) groundMoisture = 0;
//        workingFlowers.Tagessimulation(groundMoisture,dailySunshine,
//                workingBees.getPopulation(),availableFood,false);

    }

    private void simulateWinter(){
        for(int i = 0; i < worldLength; i++){
            for(int j = 0; j < worldWidth; j++){
                world[i][j].simulateWinter(world, numberGenerator);
            }
        }
    }

    //Nominale Abstraktion.
    //Lässt die Simulation für ein ganzes Jahr (Wachstumsphase und Ruhephase) laufen.
    private void simulateYear() {
        for(int d = 1; d <= 270; d++){
            growingDay();
            if(dailyOutput){
                debugInfos.append("Result of day ").append(d).append(":\n");
                debugInfos.append(String.format("Bee population: %8.0f\n", getTotalBees()));
                debugInfos.append(String.format("Flower population: %8.0f\n", getTotalFlowers()));
                debugInfos.append("\n");
            }
        }
        simulateWinter();
        weather.startNewYear();

    }

    private double getTotalBees() {
        double total = 0;
        for(Chunk[] chunk : world){
            for(Chunk c : chunk){
                if(c.BeeHive()){
                    total += c.getBeePopulation().getPopulation();
                }
            }
        }
        return total;
    }

    private double getTotalFlowers(){
        double totalWuchskraft = 0;
        for(Chunk[] chunk : world){
            for(Chunk c : chunk){
                List<FlowerPopulation> fps = c.getFlowers();
                for (FlowerPopulation fp : fps){
                   totalWuchskraft += fp.getWuchskraft();
                }
            }
        }
        return totalWuchskraft;
    }

    //Nominale Abstraktion. Ausgabe genauerer Informationen über den Simulationsablauf.
    public void printDebugInfos() {
        System.out.println(debugInfos);
    }
}
