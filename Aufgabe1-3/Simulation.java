import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//https://github.com/SpongePowered/noise

/*
Abstrakter Datentyp(Klasse) mit nominaler Abstraktion. Simuliert ein Ökosystem,
das aus Pflanzen und Bienen besteht, die voneinander abhängig sind, um überleben zu können
BAD: Objekt-Kopplung ist hoch und Klassenzusammenhalt ist niedrig. Die diversen Funktionen für die Ausgabe des
     Simulationszustandes sollten in eine eigene Klasse verschoben werden, um den Klassenzusammenhalt zu erhöhen.
 */
public class Simulation {
    private boolean dailyOutput;


    private final Weather weather; //weather != null
    //world.length == worldLength und world[i].length == worldWidth
    private final Chunk[][] world;
    private final List<Flower> flowerSpecies; //flowerSpecies != null und flowerSpecies.size() > 0
    private final int worldLength; //length > 0
    private final int worldWidth; //width >
    private final int chunksWithBees; //startingHives >= 2 und startingHives % 2 == 0

    private final Random numberGenerator;

    private final List<SimulationState> endOfYearStates;
    private final List<SimulationState> weeklyStates;
    private final List<SimulationState> debugStates;
    // The start and the end year to print the debug infos
    // intervall[0] >= 0 und intervall[0] <= intervall[1] und intervall[1] <= yearsToRuns
    private final int[] debugIntervalYears = {0, 25};

    //chunksWithPlants >= 0
    private final int chunksWithPlants;

    //Erstellt eine neue Simulation und nutzt die übergebenen Pflanzen und Bienen als Ausgangspunkt für die Simulation.
    //BAD: Sehr hohe Koppelung zwischen Simulation und Pflanzen und Wetter.
    //BAD: Jede Simulation hat einen eigenen Zufallszahlengenerator, ohne Seed, wodurch debuggen schwieriger wird.
    //worldLength: > 0
    //worldWidth: > 0
    //weather: != null
    //flowerSpecies: != null und flowerSpecies.size() > 0
    //chunksWithBees: >= 2 und chunksWithBees % 2 == 0
    //chunksWithPlants: >= 0
    public Simulation(int worldLength, int worldWidth, Weather weather, List<Flower> flowerSpecies, int chunksWithBees,
                      int chunksWithPlants) {
        endOfYearStates = new ArrayList<>();
        weeklyStates = new ArrayList<>();
        debugStates = new ArrayList<>();
        this.weather = weather;
        this.flowerSpecies = flowerSpecies;
        numberGenerator = new Random();
        this.worldLength = worldLength;
        this.worldWidth = worldWidth;
        this.chunksWithBees = chunksWithBees;
        this.chunksWithPlants = chunksWithPlants;

        world = new Chunk[worldLength][worldWidth];
        generateWorld();
        populateWorldWithBees();
        populateWorldWithPlants();
    }

    //Berechnet, ob die übergebenen Koordinaten innerhalb der welt liegen.
    //world != null
    public static boolean isInWorldBounds(Chunk[][] world, int x, int y) {
        return x >= 0 && x < world.length && y >= 0 && y < world[0].length;
    }

    //Generiert alle Chunks in der Welt.
    private void generateWorld() {
        for (int i = 0; i < worldLength; i++) {
            for (int j = 0; j < worldWidth; j++) {
                world[i][j] = new Chunk(i, j,
                        numberGenerator.nextGaussian(100, 0.5));
            }
        }
    }

    //Verteilt die Startpopulationen von Bienen. Anzahl an Bienen wird durch
    //die Objektvariable chunksWithBees festgelegt.
    private void populateWorldWithBees() {
        int xSpacing = worldLength / (chunksWithBees / 2);
        int ySpacing = worldWidth / 2;

        for (int i = 0; i < (chunksWithBees / 2); i++) {
            int x = numberGenerator.nextInt(xSpacing * i, xSpacing * (i + 1));
            int y = numberGenerator.nextInt(0, ySpacing);
            world[x][y].SetBeePopulation(new BeePopulation(500, 5, numberGenerator, false));
            x = numberGenerator.nextInt(xSpacing * i, xSpacing * (i + 1));
            y = numberGenerator.nextInt(ySpacing, worldWidth);
            world[x][y].SetBeePopulation(new BeePopulation(500, 5, numberGenerator, false));
        }

    }

    //Verteilt die Startpopulationen von Pflanzen. Anzahl an Pflanzen wird durch die
    //Objektvariable chunksWithPlants festgelegt.
    //In jedem Chunk werden zwischen 1 und 3 Pflanzen gepflanzt.
    private void populateWorldWithPlants() {
        int x;
        int y;
        for (int i = 0; i < chunksWithPlants; i++) {
            x = numberGenerator.nextInt(0, worldLength);
            y = numberGenerator.nextInt(0, worldWidth);
            if (!world[x][y].getFlowers().isEmpty()) continue;

            int flowerCount = numberGenerator.nextInt(1, 4);
            for (int j = 0; j < flowerCount; j++) {
                var f = flowerSpecies.get(numberGenerator.nextInt(flowerSpecies.size()));
                var size = numberGenerator.nextGaussian(2000, 40);
                world[x][y].plantFlower(f, size);
            }

        }
    }

    public String printWorldVerbose() {
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

    //Simuliert die erstellte Welt für die übergebene Anzahl an Jahren. Die Ergebnisse werden auf der Konsole ausgegeben.
    //Wenn debug == true, dann werden zusätzliche Debuginformationen ausgegeben.
    //years >= 1
    public void simulate(int years, boolean debug) {
        if (debug) {
            dailyOutput = true;
        }
        for (int year = 1; year <= years; year++) {
            simulateYear(year);
            simulateWinter();
            endOfYearStates.add(SimulationState.create(year, 52, world));
            if (dailyOutput) dailyOutput = false;
        }
    }

    public void printYearlyStates() {
        if (endOfYearStates.isEmpty()) {
            return;
        }
        SimulationState.printStateTable("Yearly states", endOfYearStates, flowerSpecies);
    }

    public void printWeeklyStates() {
        if (weeklyStates.isEmpty()) {
            return;
        }
        SimulationState.printStateTable("Weekly states", weeklyStates, flowerSpecies);
    }


    //Nominale Abstraktion.
    //Führt Berechnungen für die Simulation eines Tages während der
    //Wachstumszeit durch.
    //GOOD: Zwei getrennte Schleifen verhindern Raceconditions, welche sonst alte Werte auslesen könnten
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

   //Simuliert die Ruhephase.
    private void simulateWinter() {
        for (int i = 0; i < worldLength; i++) {
            for (int j = 0; j < worldWidth; j++) {
                world[i][j].simulateRestingPeriod(world, numberGenerator);
            }
        }
    }

    public void printDebugStates() {
        List<SimulationState> states;
        for (int i = 0; i < debugStates.size(); i += 39) {
            if (i + 39 < debugStates.size()) {
                states = debugStates.subList(i, i + 39);
            } else {
                states = debugStates.subList(i, debugStates.size());
            }
            System.out.println(SimulationState.statesAsTable(states, flowerSpecies));
        }
    }

    public SimulationState getEndState() {
        return endOfYearStates.getLast();
    }

    //Simuliert einen ganzen Jahreszyklus.
    private void simulateYear(int year) {
        for (int d = 0; d < 270; d++) {
            growingDay();
            if (d % 7 == 0 && dailyOutput) {
                weeklyStates.add(SimulationState.create(year, d / 7, world));
            }
            if (year >= debugIntervalYears[0] && year <= debugIntervalYears[1]) {
                // intervall >= 1
                int debugIntervalWeeks = 2;
                if (d % 7 == 0 && ((d / 7) + debugIntervalWeeks) % debugIntervalWeeks == 0) {
                    debugStates.add(SimulationState.create(year, d / 7, world));
                }
            }
        }
        simulateWinter();
        if (dailyOutput)
            weeklyStates.add(SimulationState.create(year, 52, world));
        if (year >= debugIntervalYears[0] && year <= debugIntervalYears[1]) {
            debugStates.add(SimulationState.create(year, 52, world));
        }
        weather.startNewYear();
    }
}
