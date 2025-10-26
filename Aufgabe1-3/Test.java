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

//javac -cp ".:flow-noise-1.0.0.jar" Test.java

/*
 * Test-Klasse: Modularisierungseinheit: Modul
 * Einstiegspunkt zur ausführung der Simulation.
 */
public class Test {
    //Hauptmethode (Nominale Abstraktion). Modularisierungseinheit: Modul. Einstiegspunkt des Programms
    public static void main(String[] args) {

        // Simulation 1:
        Random beeRandom1 = new Random(67246021);
        var weather1 = new AustriaWeatherSimulation(beeRandom1);
        var flowerList = generateFlowerParameter();

        var sim1 = new Simulation(20, 20,  weather1, flowerList, 15, 200);

        sim1.simulate(1, 5, true);


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

    private static List<Flower> generateFlowerParameter(){
        var flowers = new ArrayList<Flower>();
        Random rand = new Random(1234);
        /*„STYLE:“
        Hier wird ein prozeduralen Prog. Paradigmus angewendet. Dies sieht man daran, dass es eine Abfolge von
        Statements sind, die nur auf die davor ausgeführten Statements angewiesen sind.
        */
        for (int i = 0; i < 10; i++) {
            double wuchskraft = rand.nextDouble() * 1000 + 5000;
            double vg = rand.nextDouble() * 3;
            RestrictedDouble vermehrungsgrenzen = new RestrictedDouble(4.5, vg + 13);
            double fg = rand.nextDouble() * 0.2;
            RestrictedDouble feuchtegrenzen = new RestrictedDouble(Math.max(0, 0.3 - fg), Math.min(1, 0.7 - fg));
            RestrictedDouble bluehgrenzen;
            double bgL;
            double bgH;
            // Frühlingsblumen
            double randomLength = rand.nextDouble() * 200 + 500;
            if (i <= 10 / 3) {
                bgL = rand.nextInt(800);
                bgH = randomLength + bgL;
            }
            // Sommerblumen
            else if (i <= 10 / 3 * 2) {
                bgL = rand.nextInt(400) + 900d;
                bgH = randomLength + bgL;
            }
            //Herbstblumen
            else {
                bgL = rand.nextInt(400) + 800;
                bgH = randomLength + bgL;
            }
            bluehgrenzen = new RestrictedDouble(bgL, bgH);
            double bluehintensitaet = rand.nextDouble() * (1.0 / 15);
            double bestaeubungswahrscheinlichkeit = Math.min(0.5, rand.nextDouble()) * (1.0 / (bgH - bgL));
            flowers.add(new Flower("test", wuchskraft, vermehrungsgrenzen, feuchtegrenzen,
                    bluehgrenzen, bluehintensitaet, bestaeubungswahrscheinlichkeit));
        }
        return flowers;
    }
}