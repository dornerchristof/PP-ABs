import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/*
 * Abstrakter Datentyp (Klasse) mit nominaler Abstraktion. Modularisierungseinheit: Klasse und Objekt
 * Repräsentiert eine Population von Bienen in einem Ökosystem(Simulation).
 *Simuliert die Population basierend auf Nahrung und Jahreszeit(Vegetation- und Ruhephase).
 */
public class BeePopulation {
    double population;
    int maximaleDistanz;
    double savedFood = 0;
    double initialPopulation;
    double foundFood = 0;
    boolean newHive = false;
    Random rand;
    private BeeGenome genome;

    /**
     * Konstruktor (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * Erstellt eine neue Bienenpopulation mit einer Anfangsgröße und einem Zufallsgenerator.
     * Vorbedingungen: population >= 0, maximaleDistanz >= 0, rand != null
     * Nachbedingungen: keine
     * Invariant: population >= 0 (max. bis zur ersten GrowingDaySimulation), maximaleDistanz >= 0, rand != null
     */
    public BeePopulation(int population, int maximaleDistanz, Random rand, boolean newHive){
        this.population = population; this.rand = rand; this.maximaleDistanz = maximaleDistanz;
        this.initialPopulation = population; this.newHive = newHive; this.genome = BeeGenome.random(rand);
    }
    /**
     * Kopierkonstruktor (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * Erstellt eine unabhängige Kopie einer bestehenden Bienenpopulation (Random Generator als Referenz).
     * Vorbedingungen: other != null
     * Nachbedingungen: keine
     */
    public BeePopulation(BeePopulation other) {
        this.population = other.population;
        this.rand = other.rand;
        this.maximaleDistanz = other.maximaleDistanz;
        this.initialPopulation = other.initialPopulation;
        this.foundFood = other.foundFood;
        this.savedFood = other.savedFood;
        this.newHive = other.newHive;
        this.genome = other.genome;
    }

    /**
     * Getter-Methode (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * Gibt die Größe der Population zurück.
     * Vorbedingungen: keine
     * Nachbedingungen: population >= 0
     * Invariant: population >= 0
     */
    public double getPopulation(){
        return population;
    }
    /*
    GOOD: Macht die Simulation von Tagen sehr einfach und zusätzliche der Großteil der Funktionalität der nicht zwingend in der Funktion sein muss wurde ausgelagert.
    BAD: Sehr viele verschidene Verantwortungen für eine Funktion und schlecht gekapselt.
    Einige Code Abschnitte hätten in die jeweiligen Funktionen ausgelagert werden können.
     */
    /**
     * Simulationsmethode (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * FSimulation eines Tages der Vegitationsphase.
    *„STYLE:"
    Hier wird ein objektorientiertes Paradigma angewendet. Das sieht man daran, dass die Methode
    auf den Zustand des Objekts (population, savedFood, foundFood) zugreift und diesen ändert.
    Die Methode kapselt das Verhalten eines BeePopulation-Objekts und interagiert mit anderen Objekten
    (Chunk[][], Weather) durch deren öffentliche Schnittstellen, wodurch Datenkapselung und
    Verhaltensabstraktion realisiert werden.

     Vorbedingungen: world != null, xKoordinate >= 0, yKoordinate >= 0, weather != null
     Nachbedingungen: keine
    */
    public void simulateGrowingDay(Chunk[][] world, int xKoordinate, int yKoordinate, Weather weather){
        beesFlyOutReturn beesFlying = beesFlyOut(weather);
        if(beesFlying.beesFlyOut) {
            double geneticEfficiency = calculateGeneticEfficiencyFunctional(weather);

            for (int i = 1; i < beesFlying.timesBeesFlyOut; i++) {
                double gatheredFood = sammleEssen(world, xKoordinate, yKoordinate);
                gatheredFood = gatheredFood * geneticEfficiency;
                saveFood(gatheredFood);
            }
        }
        //System.out.println("savedFood: " + savedFood + ", population: " + population + ",");
        adjustPopulation();

        // System.out.println("Available Food: "+ availableFood);
        // calculate how many times how many bees fly

        //Calculate the food and then save the food

        // Use Food for daily upkeep

        // increase population

        //Kil this Hive if the population is less than one on any given day
        if(population < 1){
            world[xKoordinate][yKoordinate].killBeeHive();
        }
        //System.out.println("Bienenanzahl: " + population);
    }

    /**
     * Nominale Abstraktion. Modularisierungseinheit: Objekt
     * Passt die Population der Bienen an nutzt dafür die Werter Population und savedFood. Greift auch auf due redproductionRate des Genom zu
     * Vorbedingungen: population > 0, savedFood >= 0
     * Nachbedingungen: Die population wurde in ihrer Größe verändert und falls diese kleiner als 0 ist, wird sie auf 0 gesetzt.
     */
    private void adjustPopulation() {
        if(savedFood >= population){
            savedFood = savedFood - population;
            population += Math.min(population *  1.2 + (genome.reproductionRate / 10), 2000);
        }
        else {
            double percentage = ((6.0 * savedFood/population) - 3);
            savedFood = 0;
            //System.out.println("percentage: " + percentage);
            population = population * (1 + percentage / 100);
            if(population < 0) population = 0;
        }

    }
    /**
     * Nominale Abstraktion. Modularisierungseinheit: Objekt
     * Fügt einen Wert vom Tpy double zu der Objektvariable savedFood hinzu.
     * Vorbedingungen: keine (kein Side Effect falls availableFood <= 0)
     * Nachbedingung: SavedFood wurde erhöht
     */
    private void saveFood(double availableFood) {
        if(availableFood > 0){
            savedFood += availableFood;
        }
    }
    /**
     * Nominale Abstraktion. Modularisierungseinheit: Klasse
     * Klasse die das RückgabeObjekt der Funktion beesFlyOut definiert
     */
    private class beesFlyOutReturn {
        final boolean beesFlyOut;
        final int timesBeesFlyOut;
        beesFlyOutReturn(boolean beesFlyOut, int timesBeesFlyOut){
            this.beesFlyOut = beesFlyOut;
            this.timesBeesFlyOut = timesBeesFlyOut;
        }
    }

    /**
     * Nominale Abstraktion. Modularisierungseinheit: Objekt
     * Nutzt ein Objekt vom Typ Weather um zu berechnen wie oft bienen ausfliegen
     * Vorbedingung: weather != null
     * Nachbedingung: Eine Anzahl zwischen 0 und 14 die angibt wie oft die Bienen ausfliegen sollen
     */
    private beesFlyOutReturn beesFlyOut(Weather weather) {
        int minimumTimesBeesFlyOut = Math.max(0, 7 - weather.getRainfallHours()); // Minimum Times the BEes can fly out minimum 0
        int maximumTimesBeesFlyOut = Math.max(0, 12 - weather.getRainfallHours()); // Maximum Times the BEes can fly out minimum 0
        int timesBeesFlyOut =  rand.nextInt(minimumTimesBeesFlyOut, maximumTimesBeesFlyOut + 1);
        return new beesFlyOutReturn(true, timesBeesFlyOut);
    }

    /**
     *Nominale Abstraktion. Modularisierungseinheit: Objekt
     * Nimmt ein zwei Dimensionales Chunk Array einen xIndex und einen index also auch eine Distanz.
     * Anhand der Distanz werden in alle Chunks gesucht welche sich auf dieser Distanz zu den Ursprungskoordinaten befinden.
     * Vorbedingung: world != null, xUrsprung >= 0, yUrsprung >= 0, distance >= 0
     * Nachbedingung: Nach ausführung wird eine Liste mit validen Chunks ausgegeben.
     */
    private List<Chunk> validChunksInDistance(Chunk[][] world, int xUrsprung, int yUrsprung, int distance){
        List<Chunk> validChunks = new ArrayList<>();
        if(distance == 0){
            validChunks.add(world[xUrsprung][yUrsprung]);
            return validChunks;
        }
        // Iterate through all positions at this distance
        for (int dx = -distance; dx <= distance; dx++) {
            for (int dy = -distance; dy <= distance; dy++) {
                if (Math.abs(dx) == distance || Math.abs(dy) == distance) {
                    int newX = xUrsprung + dx;
                    int newY = yUrsprung + dy;
                    if (Simulation.isInWorldBounds(world, newX, newY)) {
                        validChunks.add(world[newX][newY]);
                    }
                }
            }
        }
        return validChunks;
    }

    /**
     * Nominale Abstraktion. Modularisierungseinheit: Objekt
     * Führt die Nahrungssammlung für einen Ausflug der Bienen aus.
     * Vorbedingung: world != null, xUrsprung >= 0, yUrsprung >= 0, distance >= 0
     * Nachbedingungen: Gibt einen double Wert zurück der das gesammelte Essen repräsentiert.
     */
    private double sammleEssen(Chunk[][] world, int xUrsprung, int yUrsprung){
        double gesammelteNahrung = 0;
        double remainingBees = population;
        int distance = 0;
        while (remainingBees > 0 && distance <= maximaleDistanz) {
            List<Chunk> validChunks = validChunksInDistance(world, xUrsprung, yUrsprung, distance);
            for(Chunk chunk : validChunks){
                double availableFood = chunk.getNahrungsangebot();
                double gatheredFood = Math.min(remainingBees / validChunks.size(), availableFood);
                gesammelteNahrung += gatheredFood;
                remainingBees -= gatheredFood;
                chunk.addToBeesVisited(gatheredFood);
            }
            distance++;
        }
        return gesammelteNahrung;
    }

    /**
     * Simulationsmethode (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * Simulation der ganzen Winterruhephase
     * Vorbedingungen: keine
     * Nahcbedingeungen: Falls der Bienenschwarm nicht nue ist wird die Population verkleinert.
     */
    public void simulateRestingPeriod(){
        if(!newHive){
            double geneticSurvivalBonus = genome.coldResistance;
            double random = (0.1 + rand.nextDouble() * 0.2) * geneticSurvivalBonus;
            population = population * random;
            genome = evolveGenome();

        } else {newHive = false;}
        initialPopulation = population;
    }



    /**
    Anzahl an Bienenköniginnen, die das Nest am Ende des Jahres gezeugt hat und ausfliegen.
     Vorbedingungen: keine
     Nachbedingeungen: Am Index 0 des Arrays steht die Anzahl der neuen Bienenköniginnen, die ausfliegen. An Index 1 die Größe aller neu entstehenden Schwärme.
     */
    public int[] beeQueens(){
        int newQueenBees = 0;
        if(population >= initialPopulation * 3){
            double reproductionBonus = genome.reproductionRate;
            newQueenBees = (int) (rand.nextInt(1,4) * reproductionBonus);
        };
        int newHiveSize = (int) (population / 10);
        population = population - population * 0.1 * newQueenBees; // Ziehe die ausgeflogenen Bienen von der Population ab
        return new int[]{newQueenBees, newHiveSize};
    }

    /**
     * STYLE: Funktional
     * Gibt eine Funktion zurück, die genutzt wird, um die Effizienz bei nder Nahrungssammlung basierend auf dem Wetter festzulegen.
     */
    private static Function<BeeGenome, Double> createWeatherBasedEfficiencyCalculator(Weather weather) {
        Function<Double, Double> temperatureEffect = efficiency -> {
            double temp = weather.getTemperature();
            if (temp < 10) return efficiency * 0.3;
            if (temp > 30) return efficiency * 0.7;
            return efficiency * (0.5 + temp / 40.0);
        };

        Function<Double, Double> moistureEffect = efficiency -> {
            double moisture = weather.getSoilMoisture();
            return efficiency * (1.0 + (moisture - 0.5) * 0.2);
        };

        return genome -> {
            double base = genome.foragingEfficiency;
            return Stream.of(
                    temperatureEffect.apply(base),
                    moistureEffect.apply(base)
            ).reduce(1.0, (a, b) -> a * b) / 3.0;
        };
    }

    /**
     * STYLE: Funktional
     * gibt einen Wert von Typ Double zurück nutzt die Funktion, die von createWeatherBasedEfficiencyCalculator erstellt
     * wurde, um die Effizienz bei der Nahrungssuche zu berechnen.
     */
    private double calculateGeneticEfficiencyFunctional(Weather weather) {
        Function<BeeGenome, Double> calculator = createWeatherBasedEfficiencyCalculator(weather);
        return calculator.apply(genome);
    }

    /**
     * STYLE: Funktional
     * führt die Evolution einer Liste von BeeGenoms aus in dem es rekursiv die besten behält und schlussendlich den stärksten "Überlebenden" auswählt.
     * Gibt ein BeeGenomn zurück
     */
    private static BeeGenome selectBestGenomeRecursive(
            List<BeeGenome> candidates,
            int evaluationGenerations,
            Function<BeeGenome, Double> fitnessFunc,
            Random rand) {

        if (evaluationGenerations <= 0 || candidates.size() <= 1) {
            return candidates.stream()
                    .max(Comparator.comparingDouble(fitnessFunc::apply))
                    .orElse(candidates.getFirst());
        }

        List<BeeGenome> ranked = candidates.stream()
                .sorted((g1, g2) -> Double.compare(
                        fitnessFunc.apply(g2),
                        fitnessFunc.apply(g1)))
                .toList();

        int eliminate = Math.max(2, ranked.size() / 2);
        List<BeeGenome> survivors = ranked.subList(0, eliminate);
        List<BeeGenome> nextGen = survivors.stream()
                .flatMap(genome -> Stream.of(
                        genome,
                        genome.mutate(rand, 0.1)
                )).collect(Collectors.toList());
        return selectBestGenomeRecursive(nextGen, evaluationGenerations - 1, fitnessFunc, rand);
    }

    /**
     * STYLE: Funktional
     * Startet die Evolution der Bienen, führt eine Simulation über einige Generationen mittels der Funktion selectBestGenomeRecursive.
     * Gibt ein neues BeeGenome zurück, welches dann vom Objekt Orrientierten Teil als neues Genom verwendet wird.
     */
    private BeeGenome evolveGenome() {
        Function<BeeGenome, Double> fitness = genome -> genome.coldResistance * 0.5 + genome.foragingEfficiency * 2.0;

        List<BeeGenome> variants = Stream.generate(() -> genome.mutate(rand, 0.15)).limit(10).collect(Collectors.toList());
        variants.add(genome);
        return selectBestGenomeRecursive(variants, 12, fitness, rand);
    }

    @Override
    public String toString() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);
        return "Bienenpopulation: " + df.format(population) +'\n' ;
    }
}