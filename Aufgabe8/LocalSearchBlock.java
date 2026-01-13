import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LocalSearchBlock extends Block{

    private final Field prev;
    private Field bestResult;

    /// s ist s im BeeAlgorithmus
    public LocalSearchBlock(int b, Function<List<Double>, Double> f, Comparator<Field> c, Field prev, double s) {
        super(b,f,c,s);
        this.prev = prev;
    }

    public Field getRes(){
        return bestResult;
    }

    @Override
    public void run() {
        if (prev.regressions() >= 3) {
            bestResult = prev; //Return the previous element if already tried to find better point multiple times
            return;
        }
        // Result is the field with the highest highestResult property or prev if there is no improvement.
        Field bestLocal = IntStream.range(0, b)
                .mapToObj(i -> populateRandomSet(prev.borders()).boxed().toList()) // Slect random points within the borders of the filed
                // Add a field for every point. Borders are calculated later. This is just because the comparator needs fields to compare.
                .map(params -> new Field(f.apply(params), params, null, 0))
                .max(c) // Get the best field based on the comparator c.
                .orElse(prev); // If there is no field, use the previous one.
        boolean improved = c.compare(bestLocal, prev) > 0; // compare bestLocal to prev
        var winner = improved ? bestLocal : prev;
        double[][] newBorders = fieldBorders(prev.borders(), winner.highestResultPar(), s);
        // Return new Field with updated borders. Regression is increased if the Result is not improved.
        bestResult = new Field(winner.highestResult(), winner.highestResultPar(), newBorders, improved ? 0 : prev.regressions() + 1);
    }


}
