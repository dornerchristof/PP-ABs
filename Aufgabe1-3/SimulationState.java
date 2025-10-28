import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SimulationState {
    private int yearsSinceCreation;
    private int daysSinceCreation;

    private double beeHives;
    private Stats beePopsStats;
    private Map<String, Stats> perFlowerStats;

    public SimulationState(int yearsSinceCreation, int daysSinceCreation, Chunk[][] world){
        perFlowerStats = new HashMap<>();
        this.yearsSinceCreation = yearsSinceCreation;

        double total = 0;
        var hives = new ArrayList<Double>();
        for(Chunk[] chunk : world){
            for(Chunk c : chunk){
                if(c.BeeHive()){
                    hives.add(c.getBeePopulation().getPopulation());
                    beeHives++;
                }
            }
        }

        double min = hives.stream().min(Double::compare).orElse(0.0);
        double max = hives.stream().max(Double::compare).orElse(0.0);
        double avg = hives.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        beePopsStats = new Stats(beeHives, min, max, avg);

        // Map to store statistics for each flower type
        // Key: Flower name, Value: List of Wuchskraft values
        Map<String, List<Double>> flowerStats = new HashMap<>();

        for (Chunk[] chunk : world) {
            for (Chunk c : chunk) {
                List<FlowerPopulation> fps = c.getFlowers();
                for (FlowerPopulation fp : fps) {
                    String flowerName = fp.getFlower().getName();
                    flowerStats.computeIfAbsent(flowerName, k -> new java.util.ArrayList<>())
                            .add(fp.getCurrentPopulation());
                }
            }
        }

        // Build the output string with statistics for each flower type

        for (Entry<String, List<Double>> entry : flowerStats.entrySet()) {
            String flowerName = entry.getKey();
            List<Double> values = entry.getValue();

            if (values.isEmpty()) continue;

            min = values.stream().min(Double::compare).orElse(0.0);
            max = values.stream().max(Double::compare).orElse(0.0);
            avg = values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
            perFlowerStats.put(flowerName, new Stats(values.size(), min, max, avg));
        }
    }

    public static String statesAsTable(List<SimulationState> states){
        StringBuilder sb = new StringBuilder();
        sb.append("             ");
        for (SimulationState state : states){
           sb.append("Year ").append(String.format("%2d", state.yearsSinceCreation)).append(" |");
        }
        sb.append("\n");
        sb.append("Bee hives   | ");
        for(SimulationState state : states){
            sb.append(String.format("%6.1f", state.beePopsStats.count)).append(" | ");
        }
        sb.append("\n");
        sb.append("BeePops min | ");
        for(SimulationState state : states){
            sb.append(String.format("%6.1f", state.beePopsStats.min)). append(" | ");
        }
        sb.append("\n");
        sb.append("BeePops avg | ");
        for(SimulationState state : states){
            sb.append(String.format("%6.1f", state.beePopsStats.avg)). append(" | ");
        }
        sb.append("\n");
        sb.append("BeePops max | ");
        for(SimulationState state : states){
            sb.append(String.format("%6.1f", state.beePopsStats.max)). append(" | ");
        }
        return sb.toString();
    }


    private class Stats{
        private double count;
        private double min;
        private double max;
        private double avg;

        private Stats(double count, double min, double max, double avg){
            this.count = count;
            this.min = min;
            this.max = max;
            this.avg = avg;
        }
    }
}
