import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record SimulationRunner(int worldLength, int worldWidth, Weather weather, List<Flower> flowerSpecies,
                               int startingHives) {
    //Erstellt eine neue Simulation und nutzt die übergebenen Pflanzen und Bienen als
    //Ausgangspunkt für die Simulation.
    //startingHives: >= 2 und mod 2 = 0

    public void startSimulation(int runs) {
        this.startSimulation(runs, 25, false, false);
    }

    public void startSimulation(int runs, int years, boolean printStates, boolean debug) {
        ArrayList<Simulation> list = new ArrayList<>();
        for (int i = 0; i < runs; i++) {
            List<Flower> flowers = flowerSpecies.stream().map(Flower::copy).toList();
            list.add(new Simulation(worldLength, worldWidth, weather.copy(), flowers, startingHives));
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
