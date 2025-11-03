
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Nominale Abstraktion: Modularisierungseinheit: Klasse Objekt
 */
public class BeeGenome {
    public final double foragingEfficiency;
    public final double coldResistance;
    public final double reproductionRate;

    /**
     * Konstruktor (Nominale Abstraktion). Modularisierungseinheit: Objekt
     */
    public BeeGenome(double foraging, double cold, double reproduction) {
        this.foragingEfficiency = clamp(foraging, 0.5, 1.5);
        this.coldResistance = clamp(cold, 0.5, 1.5);
        this.reproductionRate = clamp(reproduction, 0.5, 1.5);
    }

    /**
     * STYLE: Funktional
     * Statische Methode um einen double Wert in einer unter und einer Obergrenze zu halten
     * Vorbedingung: min <= max
     * Nachbedingungen: Der zurückgegebene double Wert liegt innerhalb der Werte min und max
     */
    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * STYLE: Funktional
     * Statische Methode um ein zufälliges BeeGenome Objekt zu erzeugen.
     * Vorbedingung: rand != null
     * Nachbedingungen: Gibt ein Beegenome zurück, deren foragingEfficiency, coldResistance, reproductionRate durch die festgelegten Gaussian Funktionen deffiniert wird
     */
    public static BeeGenome random(Random rand) {
        return new BeeGenome(
                rand.nextGaussian(1.0, 0.15),
                rand.nextGaussian(1.0, 0.15),
                rand.nextGaussian(1.0, 0.15)
        );
    }

    /**
     * STYLE: Funktional
     * Erzeugt ein neues Beegenome welches einer Mutation ausgeetzt wurde
     * Vorbedingung: rand != null, mutationRate >= 0
     * Nachbedingung: Gibt ein neues BeeGenome zurück, welches mutiert wurde falls die mutationRate größer ist als ein Random Wert
     * Verändert keine Werte des aktuellen Objekts. Jedoch dsind die Werte des neuen Objekts vom alten abhängig.
     */
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

    /**
     * Nomenale Abstraktion: Modularisierungseinheit: Objekt
     * Gibt den Durschnitt der Verschiedene Charakteristiken zurück.
     *
     */
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