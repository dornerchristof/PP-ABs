/*
Aufgabenverteilung
Aufgabe 1:
Patrick:  Implementierung der Klassen FlowerPopulation, TotalFlowerPopulation,
          RestrictedDouble. Fehlerbehebung in den genannten Klassen gemeinsam mit Tobias.
Tobias:   Implementierung der Klasse BeePopulation. Testen der Gesamtapplikation und Feinabstimmung der
          Simulation (Parameterfinden). In Zuge dessen, Fehlerbehebung gemeinsam mit Patrick.
Christof: Implementierung der Simulationsklasse. Implementierung der Ausgabe über alle Klassen hinweg.
---------------
Aufgabe 2 (vorläufig):
Patrick - Pflanzen, Wetter, Naturkatastrophen
Tobias - Bienenstock und Ausgabe
Christof - Welt, Parameter, Zusammenspiel zwischen den Klassen

*/
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Test-Klasse: Modularisierungseinheit: Modul
 * Einstiegspunkt zur ausführung der Simulation.
 */
public class Test {
    //Hauptmethode (Nominale Abstraktion). Modularisierungseinheit: Modul. Einstiegspunkt des Programms
    public static void main(String[] args) {

        //initDaten
        //Liste an Blumen
        //Bienenpopulation
        // Simulation 1:
        Random beeRandom1 = new Random(67246021);
        var weather1 = new AustriaWeatherSimulation(beeRandom1);
        var flowers1 = new TotalFlowerPopulation(21, 1000, 5000, 4.5, 13, 0.3,0.7,394234234);
        var bees1 = new BeePopulation(800, 3, beeRandom1, false);
        var flowerList = new ArrayList<Flower>();
        flowerList.add(new Flower("Rose"));
        flowerList.add(new Flower("Edelweiss"));

        var sim1 = new Simulation(20, 20,  weather1, flowerList, 6, 50);

        sim1.simulate(10, 25, true);


        //Simulation 2:
//        Random beeRandom2 = new Random(253721312);
//        var weather2 = new AustriaWeatherSimulation(beeRandom2);
//        var flowers2 = new TotalFlowerPopulation(16, 1000, 6000, 7, 14, 0.28,0.67,54456787);
//        var bees2 = new BeePopulation(2000, beeRandom2); // BeeRandom runs through all runs so every run is different
//        var sim2 = new Simulation(flowers2, bees2, 253721312,weather2); //Seed runs through all runs so every run is different
//        sim2.simulate(10, 25, false);
//
//        // Simulation 3:
//        Random beeRandom3 = new Random(4236472);
//        var weather3 = new AustriaWeatherSimulation(beeRandom3);
//        var flowers3 = new TotalFlowerPopulation(24, 1000, 1500, 7, 11, 0.33,0.75,798246);
//        var bees3 = new BeePopulation(1700, beeRandom3);
//        var sim3 = new Simulation(flowers3, bees3, 64782347,weather3);
//        sim3.simulate(10, 25, false);

        System.out.println("DebugInfos (genau Infos)");
        sim1.printDebugInfos();
    }
}