import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record SimulationState(
        int yearsSinceCreation,
        int weeksInYear,
        Stats beePopsStats,
        Map<String, Stats> perFlowerStats
) {

    public static SimulationState create(int yearsSinceCreation, int weeksInYear, Chunk[][] world) {
        Stats beePopsStats = Arrays.stream(world).flatMap(Arrays::stream)
                .filter(Chunk::BeeHive)
                .map(Chunk::getBeePopulation)
                .map(BeePopulation::getPopulation)
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        values -> {
                            if (values.isEmpty()) {
                                return new Stats(0, 0, 0, 0);
                            }
                            return createStats(values);
                        }));

        Map<String, Stats> perFlowerStats = Arrays.stream(world).flatMap(Arrays::stream)
                .flatMap(chunk -> chunk.getFlowers().stream())
                .collect(Collectors.groupingBy(
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

    private static String statColumn(String title, double[] values) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(String.format("%-12.12s", title));
        sb.append(" | ");
        sb.append(Arrays.stream(values).mapToObj(value -> String.format("%6.0f | ", value)).collect(Collectors.joining()));
        return sb.toString();
    }

    private static String flowerStatColumn(String title, Flower flower, List<SimulationState> states, Function<Stats, Double> statExtractor) {
        String fn = flower.getName();
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append(String.format("%-7.7s  ", flower));
        sb.append(title);
        sb.append(" | ");
        sb.append(states.stream()
                .filter(state -> state.perFlowerStats.containsKey(fn))
                .map(state -> String.format("%6.1f | ", statExtractor.apply(state.perFlowerStats.get(fn))))
                .collect(Collectors.joining()));
        return sb.toString();
    }

    //Formatiert alle states als Tabelle
    public static String statesAsTable(List<SimulationState> states, List<Flower> flowerNames) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n              ");
        sb.append(states.stream()
                .map(state -> String.format("Y%2d W%2d |", state.yearsSinceCreation, state.weeksInYear))
                .collect(Collectors.joining()));

        sb.append(statColumn("Bee hives", states.stream().mapToDouble(state -> state.beePopsStats.count()).toArray()));
        sb.append(statColumn("BeePops  min", states.stream().mapToDouble(state -> state.beePopsStats.min).toArray()));
        sb.append(statColumn("BeePops  avg", states.stream().mapToDouble(state -> state.beePopsStats.avg).toArray()));
        sb.append(statColumn("BeePops  max", states.stream().mapToDouble(state -> state.beePopsStats.max).toArray()));

        sb.append(
                flowerNames.stream().map(flower -> String.join(
                        flowerStatColumn("min", flower, states, Stats::min),
                        flowerStatColumn("avg", flower, states, Stats::avg),
                        flowerStatColumn("max", flower, states, Stats::max))
                ).collect(Collectors.joining())
        );

        return sb.toString();
    }

    public static void printHeading(String title) {
        String border = "=".repeat(title.length() + 4);
        System.out.println("\n" + border);
        System.out.println("= " + title + " =");
        System.out.println(border);
    }

    public static void printStateTable(String title, List<SimulationState> states, List<Flower> flowerNames){
        printHeading(title);
        System.out.println(statesAsTable(states, flowerNames));
    }

    public static String generateSummaryTable(List<SimulationState> states) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\n%21s","|"));
        sb.append(IntStream.range(1, states.size()+1).mapToObj(i -> String.format(" Run %-5s",i)).collect(Collectors.joining("|")));
        sb.append(String.format("\n%-20s|","Bees"));
        sb.append(states.stream().map(s-> String.format(" %-9.0f",s.beePopsStats.avg * s.beePopsStats.count)).collect(Collectors.joining("|")));

        // Add a separator line
        sb.append("\n").append("-".repeat(21 + states.size() * 11));

        // Get all unique flower names from all states
        Set<String> allFlowerNames = states.stream()
                .flatMap(state -> state.perFlowerStats.keySet().stream())
                .collect(Collectors.toSet());

        // Add a row for each flower species (sorted alphabetically for consistency)
        allFlowerNames.stream().sorted().forEach(flowerName -> {
            String displayName = String.format("%-20s", flowerName);

            sb.append(String.format("\n%20s|", displayName));

            // Add flower population for each simulation run
            sb.append(states.stream()
                    .map(s -> {
                        // Get stats for this flower, or use zero if not present
                        if (s.perFlowerStats.containsKey(flowerName)) {
                            var stats = s.perFlowerStats.get(flowerName);
                            return String.format(" %-9.0f", stats.avg * stats.count);
                        } else {
                            return " 0        ";
                        }
                    })
                    .collect(Collectors.joining("|")));
        });

        // Add a total flowers row
        sb.append("\n").append("-".repeat(21 + states.size() * 11));
        sb.append(String.format("\n%-20s|", "Total Flowers"));
        sb.append(states.stream()
                .map(s -> {
                    // Sum up all flower populations
                    double total = s.perFlowerStats.values().stream()
                            .mapToDouble(stats -> stats.avg * stats.count)
                            .sum();
                    return String.format(" %-9.0f", total);
                })
                .collect(Collectors.joining("|")));

        return sb.toString();
    }


    private record Stats(double count, double min, double max, double avg) {
    }
}
