import java.util.ArrayList;
import java.util.List;

// STYLE: Parallel
// Ziel ist es, die urprüngliche Programmlogik unverändert zu lassen, aber die verschiedenen runs
// einer Simulation parallel auszuführen.

/**
 * Modul: SimulationRunner.
 * Diese Klasse managed das Erzeugen und Ausführen mehrerer Simulationen.
 * <p>
 * Vorbedingungen:
 *   - worldLength, worldWidth, weather, flowerSpecies und startingHives sind valide und nicht null.
 *   - runs >= 1
 *   - startingHives >= 2 und mod 2 = 0
 * Nachbedingungen:
 *   - Für jede angeforderte Simulation wird am Ende ein SimulationState erhoben.
 *   - Die Methode liefert eine konsole Ausgabe mit einer kompakten Zusammenfassung.
 * Invarianten:
 *   - Der Zustand der übergebenen flowerSpecies-Listen wird nicht verändert (nur Kopien werden benutzt).
 * History-Constraints:
 *   - Es gibt keine Annahme über vorherige Ausführungsschritte; jede gestartete Simulation ist unabhängig.
 *</p>
 * @param worldLength      Legt die Höhe der simulierten Welt fest.
 * @param worldWidth       Legt die Breite der simulierten Welt fest.
 * @param weather          Ein Weather-Objekt für die Wettersimulation
 * @param flowerSpecies    Eine Liste der verschiedenen Pflanzenarten
 * @param startingHives    Anzahl an Bienenstöcken zum Beginn der Simulation
 */
// GOOD: Die Klasse kapselt Erzeugung + Ausführung, sodass parallele Ausführung lokalisiert ist.
// BAD: Die verschiedenen runs hängen von einem Zufallszahlengenerator ab, welcher aber, auch mit Seed, nicht
//      ein reproduzierbares Ergebnis liefert durch die nicht festgelegte Reihenfolge durch die Parallelität
public record SimulationRunner(
        int worldLength,
        int worldWidth,
        Weather weather,
        List<Flower> flowerSpecies,
        int startingHives
) {

    /**
     * Ruft startSimulation(runs, years, printStates, debug) mit Standardwerten auf.
     * <p>
     * Vorbedingungen:
     *  - runs >= 1
     * Nachbedingungen:
     *  - Für jede Simulation existiert ein Endzustand, der in der Summary verwendet wird.
     * </p>
     * @param runs Anzahl der Simulationsdurchläufe.
     */
    public void startSimulation(int runs) {
        this.startSimulation(runs, 25, false, false);
    }

    /**
     * Erstellt und simuliert neue Simulationen anhand von den mitgegebenen Parametern und den fixen Simulationswerten im record
     * <p>
     * Vorbedingungen:
     *  - runs >= 1
     *  - years >= 1
     * Nachbedingungen:
     *  - Für jede Simulation existiert ein Endzustand, der in der Summary verwendet wird.
     *  - Wenn debug == true, wird genau ein Lauf mit detaillierten Debug-Ausgaben gefahren.
     *</p>
     * @param runs         Anzahl der Simulationsdurchläufe.
     * @param years        Anzahl der simulierten Jahre.
     * @param printStates  Gibt den Stand nach jedem Jahr und nach jeder Woche im ersten Jahr aus.
     * @param debug        Gibt weitere Debug-Werte aus
     */
    public void startSimulation(int runs, int years, boolean printStates, boolean debug) {
        ArrayList<Simulation> list = new ArrayList<>();
        for (int i = 0; i < runs; i++) {
            List<Flower> flowers = flowerSpecies.stream().map(Flower::copy).toList();
            list.add(new Simulation(worldLength, worldWidth, weather.copy(), flowers, startingHives,
                    (worldLength * worldWidth)/2));
        }

        SimulationState.printHeading("Initialised World");
        System.out.println(
                list.getFirst().printWorldVerbose()
        );

        List<Simulation> processedSimulations = list.parallelStream()
                .skip(debug ? 1 : 0)
                .peek(simulation -> simulation.simulate(years, false))
                .toList();

        Simulation sim = processedSimulations.getFirst();

        if (debug) {
            sim = list.getFirst();
            sim.simulate(years, true);

            System.out.println("DebugInfos (genau Infos)");
            sim.printDebugStates();
        }
        if (printStates) {
            sim.printYearlyStates();
            sim.printWeeklyStates();
        }

        List<SimulationState> states = new ArrayList<>();
        if (debug) {
            states.add(sim.getEndState());
        }
        processedSimulations.forEach((sim1) -> {
            states.add(sim1.getEndState());
        });

        SimulationState.printHeading("Simulation Summary");
        System.out.println(SimulationState.generateSummaryTable(states));
    }
}
