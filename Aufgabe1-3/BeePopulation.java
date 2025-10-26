import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
    }

     // Getter-Methode (Nominale Abstraktion). Modularisierungseinheit: Objekt. Gibt die Größe der Population zurück
    public double getPopulation(){
        return population;
    }
    /*
     * Simulationsmethode (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * FSimulation eines Tages der Vegitationsphase.
     */
    public void Tagessimulation(Chunk[][] world,  int xKoordinate, int yKoordinate, Weather weather){
       // System.out.println("Available Food: "+ availableFood);
        double percentageOfFlyingBees = 1 - (double) weather.rainfallHours() / 12;
        double availableFood = sammleEssen(world, xKoordinate, yKoordinate, percentageOfFlyingBees);
        foundFood += availableFood;
        if(availableFood >= population){
            population = population * 1.03;
        }
        else {
            double percentage = ((6.0 * availableFood/population) - 3);
            //System.out.println("percentage: " + percentage);
            population = population * (1 + percentage / 100);
            if(population < 0) population = 0;
        }
        //System.out.println("Bienenanzahl: " + population);
    }

    private double sammleEssen(Chunk[][] world, int xUrsprung, int yUrsprung, double percentageOfFlyingBees){
        double gesammelteNahrung = 0;
        for (int distance = 0; distance <= maximaleDistanz; distance++) {
            // Iterate through all positions at this distance
            for (int dx = -distance; dx <= distance; dx++) {
                for (int dy = -distance; dy <= distance; dy++) {
                    // Only process elements at exactly this distance (forms a square border)
                    if (Math.abs(dx) == distance || Math.abs(dy) == distance) {
                        int newX = xUrsprung + dx;
                        int newY = yUrsprung + dy;

                        if (Simulation.isInWorldBounds(world, newX, newY)) {
                            double beesReachingChunk = (Math.pow(0.7 , (distance + 1) * 2) * percentageOfFlyingBees);
                            gesammelteNahrung += world[newX][newY].getNahrungsangebot() * beesReachingChunk;
                            world[newX][newY].updateBeesVisited(beesReachingChunk);
                        }
                    }
                }
            }
        }
        System.out.printf("Nahrung %7.2f:%n", gesammelteNahrung);
        return gesammelteNahrung;
    }

    /*
     * Simulationsmethode (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * Simulation der ganzen Winterruhephase
     */
    public void simulateRest(){
        if(!newHive){
            double random = 0.1 + rand.nextDouble() * 0.2;
            population = population * random;
        } else { newHive = false;}
        foundFood = 0;
        initialPopulation = population;
    }

    /*
    Anzahl an Bienenköniginen, die das Nest am Ende des Jahres gezeugt hat und ausfliegen.
     */
    public int[] beeQueens(){
        int newQueenBees = (int) (foundFood / initialPopulation / 10);
        int newHiveSize = (int) (population / 10);
        population = population - population * 0.1 * newQueenBees; // Ziehe die ausgeflogenen Bienen von der Population ab
        System.out.printf("New Queen Bees: %d", newQueenBees);
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
