import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    /*
     * Konstruktor  (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * Erstellt eine neue Bienenpopulation mit einer Anfangsgröße und einem Zufallsgenerator.
     */
    public BeePopulation(int population, int maximaleDistanz, Random rand, boolean newHive){
        this.population = population; this.rand = rand; this.maximaleDistanz = maximaleDistanz;
        this.initialPopulation = population; this.newHive = newHive;
    }
    /*
     * Kopierkonstruktor (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * Erstellt eine unabhängige Kopie einer bestehenden Bienenpopulation (Random Generator als Referenz).
     */
    public BeePopulation(BeePopulation other) {
        this.population = other.population;
        this.rand = other.rand;
        this.maximaleDistanz = other.maximaleDistanz;
        this.initialPopulation = other.initialPopulation;
        this.foundFood = other.foundFood;
        this.savedFood = other.savedFood;
        this.newHive = other.newHive;
    }

     // Getter-Methode (Nominale Abstraktion). Modularisierungseinheit: Objekt. Gibt die Größe der Population zurück
    public double getPopulation(){
        return population;
    }
    /*
     * Simulationsmethode (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * FSimulation eines Tages der Vegitationsphase.
     */
    /*„STYLE:"
    Hier wird ein objektorientiertes Paradigma angewendet. Das sieht man daran, dass die Methode
    auf den Zustand des Objekts (population, savedFood, foundFood) zugreift und diesen ändert.
    Die Methode kapselt das Verhalten eines BeePopulation-Objekts und interagiert mit anderen Objekten
    (Chunk[][], Weather) durch deren öffentliche Schnittstellen, wodurch Datenkapselung und
    Verhaltensabstraktion realisiert werden.
    */
    public void simulateGrowingDay(Chunk[][] world, int xKoordinate, int yKoordinate, Weather weather){
        beesFlyOutReturn beesFlying = beesFlyOut(weather);
        if(beesFlying.beesFlyOut) {
            for (int i = 1; i < beesFlying.timesBeesFlyOut; i++) {
                saveFood(sammleEssen(world, xKoordinate, yKoordinate));
            }
        }
        System.out.println("savedFood: " + savedFood + ", population: " + population + ",");
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

    private void adjustPopulation() {
        if(savedFood >= population){
            savedFood = savedFood - population;
            population += Math.min(population * 1.2, 2000);
        }
        else {
            double percentage = ((6.0 * savedFood/population) - 3);
            savedFood = 0;
            //System.out.println("percentage: " + percentage);
            population = population * (1 + percentage / 100);
            if(population < 0) population = 0;
        }

    }

    private void saveFood(double availableFood) {
        if(availableFood > 0){
            savedFood += availableFood;
        }
    }

    private class beesFlyOutReturn {
        final boolean beesFlyOut;
        final int timesBeesFlyOut;
        beesFlyOutReturn(boolean beesFlyOut, int timesBeesFlyOut){
            this.beesFlyOut = beesFlyOut;
            this.timesBeesFlyOut = timesBeesFlyOut;
        }
    }

    private beesFlyOutReturn beesFlyOut(Weather weather) {
        int minimumTimesBeesFlyOut = Math.max(0, 7 - weather.getRainfallHours()); // Minimum Times the BEes can fly out minimum 0
        int maximumTimesBeesFlyOut = Math.max(0, 12 - weather.getRainfallHours()); // Maximum Times the BEes can fly out minimum 0
        int timesBeesFlyOut =  rand.nextInt(minimumTimesBeesFlyOut, maximumTimesBeesFlyOut + 1);
        return new beesFlyOutReturn(weather.getTemperature() >= 10, timesBeesFlyOut);
    }

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
                chunk.updateBeesVisited(gatheredFood);
            }
            distance++;
        }
        return gesammelteNahrung;
    }

    /*
     * Simulationsmethode (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * Simulation der ganzen Winterruhephase
     */
    public void simulateRestingPeriod(){
        if(!newHive){
            double beessurviving = savedFood / 10;
            population = population - beessurviving;
            double random = 0.1 + rand.nextDouble() * 0.2;
            population = population * random;
            population = population + beessurviving;
        } else { newHive = false;}
        initialPopulation = population;
    }



    /*
    Anzahl an Bienenköniginen, die das Nest am Ende des Jahres gezeugt hat und ausfliegen.
     */
    public int[] beeQueens(){
        int newQueenBees = 0;
        if(population >= initialPopulation * 3){
            newQueenBees = (int) rand.nextInt(1,4);
        };
        int newHiveSize = (int) (population / 10);
        population = population - population * 0.1 * newQueenBees; // Ziehe die ausgeflogenen Bienen von der Population ab
        return new int[]{newQueenBees, newHiveSize};
    }

    /*
     * toString-Methode (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * Erstellt eine formatierte String-Repräsentation der Bienenpopulation
     */
    @Override
    public String toString() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');  // space separates thousands

        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);

        return "Bienenpopulation: " + df.format(population) +'\n' ;
    }
}
