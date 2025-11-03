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

*Erweiterungen*:
Welt:
    - Es gibt eine zweidimensionale Welt aus Chunks, in denen jeweils maximal ein Wildbienenstock und eine begrenzte
    Anzahl an verschiedenen Pflanzen leben können.
    - Es gibt einen Jahresablauf, auf den die Bienen, Pflanzen und das Wetter reagieren.
Pflanzen:
    - Pflanzen blühen nur in bestimmten Monaten und wenn bestimmte Temperatur- und Feuchtigkeitsvoraussetzung erfüllt
    sind.
    - Pflanzen vermehren sich und können sich auch in andere Chunks ausbreiten.
Bienen:
    - Vermehren/Sterben ab aufgrund des Nahrungsangebots und Wetterlage
    - Bienenkönige fliegen aus und gründen neue Stöcke
    - Bienenstöcke sterben aus, wenn sie zu klein werden.
    - Bienen fliegen nur ein Mal pro Tag aus. Umso weiter sie fliegen, umso weniger kommen bei einer Pflanze an.
Wetter:
    - Wir können das Wetter verschiedener Regionen simulieren und sie miteinander austauschen.

*Arbeitsteilung*
Patrick: Implementierung des Wettersystems.
Tobias: Implementierung des neuen Bienenverhaltens (Vermehrung, Absterben, etc.). Anpassen und Verbessern der Ausgabe
        auf eine 2d-Welt
Christof: Implementierung der Chunk-Klasse; Bevölkerung der Welt mit Pflanzen und Bienen. Umschreiben der
          Pflanzenpopulation, sodass sie pro Chunk ist. Implementierung eines eigenen Pflanzenklasse für alle
          unveränderlichen Eigenschaften einer Pflanze. Anpassung und Verbesserung der Ausgabe auf eine 2d-Welt.
------------------------------------------------------------------------------------------------------------------------
Aufgabe 3

*Arbeitsaufteilung*:
Patrick: Implementierung der parallelen Ausführung der Simulation (SimulationRunner, SimulationState, ...).
         Design-By-Contract- und good- und bad-Kommentare in den genannten Klassen
Tobias: Implementierung der funktionalen Ausführung der BeePopulation und BeeGenome. Design-By-Contract- und good- und
        bad-Kommentare in den genannten Klassen.
Christof: Implementierung der verbesserten Ausgabe (SimulationState) um die Simulation debuggen zu können.
          Überarbeitung der FlowerPopulation, Überarbeitung von LimitedDouble und Limits. Design-By-Contract- und
          good- und bad-Kommentare in den genannten Klassen und in Chunks und Simulation.
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

        var sim1 = new SimulationRunner(10, 10,  weather1, flowerList, 15);

        sim1.startSimulation(10);
    }

    private static List<Flower> generateFlowerParameter(){
        var flowers = new ArrayList<Flower>();
        Random rand = new Random(1234);
        /*„STYLE:“
        Hier wird ein prozeduralen Prog. Paradigmus angewendet. Dies sieht man daran, dass es eine Abfolge von
        Statements sind, die nur auf die davor ausgeführten Statements angewiesen sind.
        GOOD:
        Der Prozessfluss wird durch Kontrollstrukturen gesteuert und ist einfach verständlich.
        Es werden keine komplexen Sprachkonzepte verwendet.
        */
        String[] nameList = {"Rose", "Edelweiss", "Sonnenblume", "Maiglöckchen", "Veilchen", "Nelke", "Orchidee",
        "Gänseblümchen", "Lilie"};
        for (int i = 0; i < 9; i++) {
            double vg = rand.nextDouble() * 3;
            Limits vermehrungsgrenzen = new Limits(5.5, vg + 15);
            double fg = rand.nextDouble() * 0.2;
            Limits feuchtegrenzen = new Limits(0.3 - fg, 0.7 + fg);
            Limits bluehgrenzen;
            double bgL;
            double bgH;
            // Frühlingsblumen
            double randomLength = rand.nextDouble() * 200 + 500;
            if (i <= i / 3) {
                bgL = rand.nextInt(600);
                bgH = randomLength + bgL;
            }
            // Sommerblumen
            else if (i <= i / 3 * 2) {
                bgL = rand.nextInt(600) + 400;
                bgH = randomLength + bgL;
            }
            //Herbstblumen
            else {
                bgL = rand.nextInt(600) + 800;
                bgH = randomLength + bgL;
            }
            bluehgrenzen = new Limits(bgL, bgH);
            double bluehintensitaet = rand.nextDouble() * (1.0 / 15);
            double bestaeubungswahrscheinlichkeit = Math.min(0.5, rand.nextDouble()) * (1.0 / (bgH - bgL));
            flowers.add(new Flower(nameList[i],vermehrungsgrenzen, feuchtegrenzen,
                    bluehgrenzen, bluehintensitaet, bestaeubungswahrscheinlichkeit));
        }
        return flowers;
    }
}