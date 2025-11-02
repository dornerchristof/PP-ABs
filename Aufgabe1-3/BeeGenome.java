
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

public class BeeGenome {
    public final double foragingEfficiency;
    public final double coldResistance;
    public final double reproductionRate;


    public BeeGenome(double foraging, double cold, double reproduction) {
        this.foragingEfficiency = clamp(foraging, 0.5, 1.5);
        this.coldResistance = clamp(cold, 0.5, 1.5);
        this.reproductionRate = clamp(reproduction, 0.5, 1.5);
    }


    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }


    public static BeeGenome random(Random rand) {
        return new BeeGenome(
                rand.nextGaussian(1.0, 0.15),
                rand.nextGaussian(1.0, 0.15),
                rand.nextGaussian(1.0, 0.15)
        );
    }


    public BeeGenome mutate(Random rand, double mutationRate) {
        Function<Double, Double> mutateGene = value ->
                rand.nextDouble() < mutationRate
                        ? value + rand.nextGaussian(0, 0.05)
                        : value;

        return new BeeGenome(
                mutateGene.apply(foragingEfficiency),
                mutateGene.apply(coldResistance),
                mutateGene.apply(reproductionRate)
        );
    }


    public double getAverageTrait() {
        return Stream.of(
                foragingEfficiency,
                coldResistance,
                reproductionRate
        ).mapToDouble(d -> d).average().orElse(1.0);
    }

    @Override
    public String toString() {
        return String.format(
                "Genetik[Sammeln=%.2f, Kälte=%.2f, Fortpfl.=%.2f, Avg=%.2f]",
                foragingEfficiency, coldResistance,
                reproductionRate, getAverageTrait()
        );
    }
}