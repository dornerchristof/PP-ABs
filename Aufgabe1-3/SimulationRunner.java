import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record SimulationRunner(int worldLength, int worldWidth, Weather weather, List<Flower> flowerSpecies,
                               int startingHives) {
    //Erstellt eine neue Simulation und nutzt die übergebenen Pflanzen und Bienen als
    //Ausgangspunkt für die Simulation.
    //startingHives: >= 2 und mod 2 = 0

    public void startSimulation(int runs) {
        this.startSimulation(runs, 25);
    }
    public void startSimulation(int runs, int years) {
        ArrayList<Simulation> list = new ArrayList<>();
        for (int i = 0; i < runs; i++) {
            List<Flower> flowers = flowerSpecies.stream().map(Flower::copy).toList();
            list.add(new Simulation(worldLength, worldWidth, weather.copy(), flowers, startingHives));
        }

        Stream<Simulation> Simulations = list.parallelStream();
        Simulations.forEach(simulation -> {
            simulation.simulate(years, false);
        });
    }
}
