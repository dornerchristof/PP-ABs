import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/// Führt eine globale Suche durch, indem zufällige Punkte im übergebenen Suchraum generiert werden.
public class GlobalSearchBlock extends Block{
    private SortedSet<Field> res;
    private double[][] w;

    public GlobalSearchBlock(int b, Function<List<Double>, Double> f, Comparator<Field> c, double s) {
        super(b, f, c, s);
    }

    public SortedSet<Field> getRes() {
        return res;
    }

    @Override
    public void run() {
        // Returniert ein Set an Feldern, dessen Argumente zufällig, aber innerhalb der
        // Grenzen w, gewählt werden.
        res = IntStream.range(0, b)
                .mapToObj(i -> populateRandomSet(w).boxed().toList())
                .map(params -> new Field(f.apply(params), params, fieldBorders(w, params, s), 0))
                .collect(Collectors.toCollection(() -> new TreeSet<>(c)));
    }
}
