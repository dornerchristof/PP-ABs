import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.*;

public class BeeAlgorithm {

	// Anz. der Kundschafterinnen
	private final int n;

	// Anz. der Felder, die weiter untersucht werden,
	// m < n
	private final int m;

	// Anzahl exzellenter Felder, die genau untersucht werden (e < m)
	private final int e;

	// Anzahl der für ein exzellentes Feld rekrutierten Bienen
	private final int p;

	// Anzahl der für ein anderes Feld rekrutierten Bienen
	// (q < p)
	private final int q;

	// Relative Größe des Feldes verglichen zum untersuchten Bereich
	// 0 <= s <= 1
	private final double s;

	// Anzahl der am Ende zurückgegebenen besten gefundenen Stelle
	private final int r;

	public BeeAlgorithm(int n, int m, int e, int p, int q, double s, int r) {
		this.n = n;
		this.m = m;
		this.e = e;
		this.p = p;
		this.q = q;
		this.s = s;
		this.r = r;
	}

	private DoubleStream populateRandomSet(double[][] w) {
		var rng = new Random();
		// Liefert einen Stream mit w.length Zufallszahlen, die in den von w definierten
		// Grenzen liegen.
		return IntStream.range(0, w.length)
				.mapToDouble(i -> {
					return rng.nextDouble() * (w[i][1] - w[i][0]) + w[i][0];
				});
	}

	private SortedSet<Field> globalSearch(Function<List<Double>, Double> f,
			double[][] w, int t, Comparator<Field> c) {
		// Returniert ein Set an Feldern, dessen Argumente zufällig, aber innerhalb der
		// Grenzen w, gewählt werden.
		return IntStream.range(0, t)
				.mapToObj(i -> populateRandomSet(w).boxed().toList())
				.map(params -> new Field(f.apply(params), params, fieldBorders(w, params, s), 0))
				.collect(Collectors.toCollection(() -> new TreeSet<>(c)));
	}

	private double[][] fieldBorders(double[][] w, List<Double> funcPar, double percent) {
		//Erstellt ein Array mit den engeren neuen Grenzen als Abstand von Mittelpunkt
		double[] v = Stream.of(w).mapToDouble(p -> percent / 2 * (Math.abs(p[0]) + Math.abs(p[1]))).toArray();
		//Erstellt das neue Border-Array, wobei die Grenzen jeweils der funcPar + bzw - v[] sind.
		return IntStream.range(0, w.length).mapToObj(i -> new double[] { funcPar.get(i) - v[i], funcPar.get(i) +
				v[i] }).toArray(double[][]::new);
	}

	private Field localSearch(Function<List<Double>, Double> f, Field prev, int t, Comparator<Field> c) {
		if (prev.regressions >= 3) return prev; //Return the previous element if already tried to find better point multiple times

        // Result is the field with the highest highestResult property or prev if there is no improvement.
		Field bestLocal = IntStream.range(0, t)
				.mapToObj(i -> populateRandomSet(prev.borders).boxed().toList()) // Slect random points within the borders of the filed
                // Add a field for every point. Borders are calculated later. This is just because the comparator needs fields to compare.
				.map(params -> new Field(f.apply(params), params, null, 0))
				.max(c) // Get the best field based on the comparator c.
				.orElse(prev); // If there is no field, use the previous one.
		boolean improved = c.compare(bestLocal, prev) > 0; // compare bestLocal to prev
		var winner = improved ? bestLocal : prev;
		double[][] newBorders = fieldBorders(prev.borders, winner.highestResultPar, s);
        // Return new Field with updated borders. Regression is increased if the Result is not improved.
		return new Field(winner.highestResult, winner.highestResultPar, newBorders, improved ? 0 : prev.regressions + 1);
	}

	public SortedSet<Field> search(int t, Function<List<Double>, Double> f, double[][] w, SortedSet<Field> prev,
			Comparator<Field> c) {
		if (t == 0)
			return prev;
		// Local search für die e exzellenten Felder
		var newRes = prev.reversed().stream().limit(e).map(e -> localSearch(f, e, p, c))
				.collect(Collectors.toCollection(() -> new TreeSet<>(c)));
		// Local search für die m-e normalen Felder
		newRes.addAll(prev.reversed().stream().skip(e).limit(m - e).map(e -> localSearch(f, e, q, c))
				.collect(Collectors.toCollection(() -> new TreeSet<>(c))));
		// Global search mit dem Rest und Rekursion
		newRes.addAll(globalSearch(f, w, n - m, c));
		return search(t - 1, f, w, newRes, c);
	}

	public Set<List<Double>> run(Function<List<Double>, Double> f, Comparator<Double> c,
			double[][] w, int t) {
		// Initiale Suche, die die Basis für den weiteren Durchlauf bildet.
		var res = globalSearch(f, w, t, Comparator.comparing(Field::highestResult));

		return search(t, f, w, res, Comparator.comparing(Field::highestResult, c)).reversed().stream().limit(r)
				.map(Field::highestResultPar).collect(Collectors.toSet());
	}

	/// Borders sind die beschränkten Grenzen für dieses Feld.
	/// Regression >= 3 bedeutet, dass das Feld nicht weiter bearbeitet wird.
	/// highestResult ist das Resultat der Funktion mit den Parametern highestResultPar.
	private record Field(Double highestResult, List<Double> highestResultPar, double[][] borders, int regressions) {
	}
}
