import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Random;

/*
 * Abstrakter Datentyp (Klasse) mit nominaler Abstraktion. Modularisierungseinheit: Klasse und Objekt
 * Repräsentiert eine Population von Bienen in einem Ökosystem(Simulation).
 *Simuliert die Population basierend auf Nahrung und Jahreszeit(Vegetation- und Ruhephase).
 */
public class BeePopulation {
    double population;
    Random rand;
    /*
     * Konstruktor  (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * Erstellt eine neue Bienenpopulation mit einer Anfangsgröße und einem Zufallsgenerator.
     */
    public BeePopulation(int population, Random rand){
        this.population = population; this.rand = rand;
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
    public void Tagessimulation(double availableFood){
       // System.out.println("Available Food: "+ availableFood);
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
    /*
     * Simulationsmethode (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * Simulation der ganzen Winterruhephase
     */
    public void simulateRest(){
        double random = 0.1 + rand.nextDouble() * 0.2;
        population = population * random;
    }

    /*
    Anzahl an Bienenköniginen, die das Nest am Ende des Jahres gezeugt hat und ausfliegen.
     */
    public int beeQueens(){
        return 0;
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
