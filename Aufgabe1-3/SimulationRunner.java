import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SimulationRunner {


    private final Weather weather;
    private final List<Flower> flowerSpecies;
    private final int worldLength;
    private final int worldWidth;
    private final int startingHives;


    //Erstellt eine neue Simulation und nutzt die übergebenen Pflanzen und Bienen als
    //Ausgangspunkt für die Simulation.
    //startingHives: >= 2 und mod 2 = 0
    public SimulationRunner(int worldLength, int worldWidth, Weather weather, List<Flower> flowerSpecies, int startingHives) {
        this.weather = weather;
        this.flowerSpecies = flowerSpecies;
        this.worldLength = worldLength;
        this.worldWidth = worldWidth;
        this.startingHives = startingHives;
    }

    public void startSimulation(int runs) {
        ArrayList<Simulation> list = new ArrayList<>();
        for(int i = 0; i < runs; i++) {
            List<Flower> flowers = flowerSpecies.stream().map(Flower::copy).toList();
            list.add(new Simulation(worldLength,worldWidth,weather.copy(),flowers,startingHives));
        }
        System.out.println(list);
        Stream<Simulation> Simulations = list.parallelStream();
        Simulations.forEach(simulation -> {simulation.simulate(25, false);});
    }
}
