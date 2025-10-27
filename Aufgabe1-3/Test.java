/*
Aufgabenverteilung
Aufgabe 1:
Patrick:  Implementierung der Klassen FlowerPopulation, TotalFlowerPopulation,
          RestrictedDouble. Fehlerbehebung in den genannten Klassen gemeinsam mit Tobias.
Tobias:   Implementierung der Klasse BeePopulation. Testen der Gesamtapplikation und Feinabstimmung der
          Simulation (Parameterfinden). In Zuge dessen, Fehlerbehebung gemeinsam mit Patrick.
Christof: Implementierung der Simulationsklasse. Implementierung der Ausgabe über alle Klassen hinweg.
---------------
Aufgabe 2:
Welt:
    - Es gibt eine zweidimensionale Welt aus Chunks, in denen jeweils maximal ein Wildbienenstock und eine begrenzte Anzahl an verschiedene Pflanzen leben können.
    - Es gibt einen Jahreszeitenablauf, auf den die Bienen, Pflanzen und das Wetter reagieren.

Pflanzen:

    Pflanzen blühen nur in bestimmten Monaten und wenn bestimmte Temperatur- und Feuchtigkeitsvoraussetzung erfüllt sind.

    Pflanzen vermehren sich und können sich auch in andere Chunks ausbreiten.

    Wir verwenden (soweit möglich), echte Daten von echten Pflanzen

Bienen:

    Vermehren/Sterben ab aufgrund des Nahrungsangebots und Wetterlage

    Bienenkönige fliegen aus und gründen neue Stöcke

    Bienen ziehen um, wenn sie keine Nahrung finden.

    Bienen fliegen nur ein Mal pro Tag aus. Sie können leer zurückkommen. Umso weiter sie fliegen, umso weniger finden eine Pflanze.

    Alle Stöcke suchen zuerst im direkten Umkreis ihres Stocks. Dann im weiteren Durchlauf fliegen die Arbeiter immer weiter (Damit ein Stock nicht die Nahrung eines anderen vollständig wegessen kann).

Wetter:

    Wir lesen vorhandene Wetterdaten ein und nutze diese.

    Es gibt eine kleine Chance auf eine Naturkatastrophe (Dürre, Überschwemmung, Bienenkrankheit)
Patrick: Implementierung des Wettersystems.
Tobias: Implementierung des neuen Bienenverhaltens (Vermehrung, Absterben, etc.). Anpassen und Verbessern der Ausgabe
        auf eine 2d-Welt
Christof: Implementierung der Chunk-Klasse; Bevölkerung der Welt mit Pflanzen und Bienen. Umschreiben der
          Pflanzenpopulation, sodass sie pro Chunk ist. Implementierung eines eigenen Pflanzenklasse für alle
          unveränderlichen Eigenschaften einer Pflanze. Anpassung und Verbesserung der Ausgabe auf eine 2d-Welt.

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

        // Simulation 1:
        Random beeRandom1 = new Random(67246021);
        var weather1 = new AustriaWeatherSimulation(beeRandom1);
        var flowerList = generateFlowerParameter();

        var sim1 = new Simulation(20, 20,  weather1, flowerList, 15, 300);

        sim1.simulate(1, 25, true);

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
                bgL = rand.nextInt(600);
                bgH = randomLength + bgL;
            }
            // Sommerblumen
            else if (i <= 10 / 3 * 2) {
                bgL = rand.nextInt(600) + 400;
                bgH = randomLength + bgL;
            }
            //Herbstblumen
            else {
                bgL = rand.nextInt(600) + 800;
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