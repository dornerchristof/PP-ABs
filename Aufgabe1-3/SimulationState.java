import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public record SimulationState(
        int yearsSinceCreation,
        int weeksInYear,
        Stats beePopsStats,
        Map<String, Stats> perFlowerStats
) {

    public static SimulationState create(int yearsSinceCreation, int weeksInYear, Chunk[][] world) {
        Stats beePopsStats = Arrays.stream(world).flatMap(Arrays::stream).filter(Chunk::BeeHive).map(Chunk::getBeePopulation)
                .map(BeePopulation::getPopulation).collect(Collectors.collectingAndThen(Collectors.toList(), SimulationState::createStats));


        // Map to store statistics for each flower type
        // Key: Flower name, Value: List of Wuchskraft values
        Map<String, Stats> perFlowerStats = Arrays.stream(world).flatMap(Arrays::stream).flatMap(chunk -> chunk.getFlowers().stream()).collect(Collectors.groupingBy(
                fp -> fp.getFlower().getName(),
                Collectors.mapping(
                        FlowerPopulation::getCurrentPopulation,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                values -> {
                                    if (values.isEmpty()) {
                                        return new Stats(0, 0, 0, 0);
                                    }
                                    return createStats(values);
                                }
                        )
                )
        ));

        return new SimulationState(yearsSinceCreation, weeksInYear, beePopsStats, perFlowerStats);
    }

    private static Stats createStats(List<Double> values) {
        double count = values.size();
        double _min = values.stream().min(Double::compare).orElse(0.0);
        double _max = values.stream().max(Double::compare).orElse(0.0);
        double _avg = values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        return new Stats(count, _min, _max, _avg);
    }

    //Formatiert alle states als Tabelle
    public static String statesAsTable(List<SimulationState> states, List<Flower> flowerNames) {
        StringBuilder sb = new StringBuilder();
        sb.append("             ");
        for (SimulationState state : states) {
            sb.append("Y").append(String.format("%2d ", state.yearsSinceCreation)).append("W");
            sb.append(String.format("%2d", state.weeksInYear)).append(" |");
        }
        sb.append("\n");
        sb.append("Bee hives   | ");
        for (SimulationState state : states) {
            sb.append(String.format("%6.0f", state.beePopsStats.count)).append(" | ");
        }
        sb.append("\n");
        sb.append("BeePops min | ");
        for (SimulationState state : states) {
            sb.append(String.format("%6.0f", state.beePopsStats.min)).append(" | ");
        }
        sb.append("\n");
        sb.append("BeePops avg | ");
        for (SimulationState state : states) {
            sb.append(String.format("%6.0f", state.beePopsStats.avg)).append(" | ");
        }
        sb.append("\n");
        sb.append("BeePops max | ");
        for (SimulationState state : states) {
            sb.append(String.format("%6.0f", state.beePopsStats.max)).append(" | ");
        }
        for (var flower : flowerNames) {
            sb.append("\n");
            sb.append(String.format("%-7.7s min | ", flower));
            for (var state : states) {
                if (state.perFlowerStats.containsKey(flower.getName())) {
                    sb.append(String.format("%6.1f", state.perFlowerStats.get(flower.getName()).min)).append(" | ");
                }

            }
            sb.append("\n");
            sb.append(String.format("%-7.7s avg | ",flower));
            for (var state : states) {
                if (state.perFlowerStats.containsKey(flower.getName())) {
                    sb.append(String.format("%6.1f", state.perFlowerStats.get(flower.getName()).avg)).append(" | ");
                }

            }
            sb.append("\n");
            sb.append(String.format("%-7.7s max | ",flower));
            for (var state : states) {
                if (state.perFlowerStats.containsKey(flower.getName())) {
                    sb.append(String.format("%6.1f", state.perFlowerStats.get(flower.getName()).max)).append(" | ");
                }

            }
        }
        return sb.toString();
    }


    private record Stats(double count, double min, double max, double avg) {
    }
}
