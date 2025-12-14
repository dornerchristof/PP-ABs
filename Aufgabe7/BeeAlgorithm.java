import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.*;

public class BeeAlgorithm {

	// Anz. der Kundschafterinnen
	private int n;

	// Anz. der Felder, die witer untersucht werden
	// m < n
	private int m;

	// Anzahl exzellenter Felder, die genau auntersucht werden (e < m)
	private int e;

	// Anzahl der für ein exzellentes Feld rekrutierten Bienen
	private int p;

	// Anzahl der für ein anderes Feld rekrutierten Bienen
	// (q < p)
	private int q;

	// Relative Größe des Feldes vergliche zum untersuchten Bereich
	// 0 <= s <= 1
	private double s;

	// Anzahl der am Ende zurückgegebenen besten gefunden Stellen
	private int r;

	public BeeAlgorithm(int n, int m, int e, int p, int q, double s, int r) {
		this.n = n;
		this.m = m;
		this.e = e;
		this.p = p;
		this.q = q;
		this.s = s;
		this.r = r;
	}

	/// Liefert einen Stream mit w.length Zufallszahlen, die in den von w[i\]
	/// definierten Grenzen liegen.
	private DoubleStream populateRandomSet(double[][] w) {
		var rng = new Random();
		return IntStream.range(0, w.length)
				.mapToDouble(i -> {
					return rng.nextDouble() * (w[i][1] - w[i][0]) + w[i][0];
				});
	}

	// Globale Suche
	private SortedSet<Field> globalSearch(Function<List<Double>, Double> f,
			double[][] w, int t, Comparator<Field> c) {

		return IntStream.range(0, t)
				.mapToObj(i -> populateRandomSet(w).boxed().toList())
				.map(params -> new Field(f.apply(params), params, fieldBorders(w, params, s), 0))
				.collect(Collectors.toCollection(() -> new TreeSet<>(c)));
	}

	/// Berechnet schmälere Grenzen, welche um die funcPar liegen und nur percent so
	/// groß sind, wie die in w.
	private double[][] fieldBorders(double[][] w, List<Double> funcPar, double percent) {
		double[] v = Stream.of(w).mapToDouble(p -> percent / 2 * (Math.abs(p[0]) + Math.abs(p[1]))).toArray();
		return IntStream.range(0, w.length).mapToObj(i -> new double[] { funcPar.get(i) - v[i], funcPar.get(i) +
				v[i] }).toArray(double[][]::new);
	}

	/// Sucht innerhalb des Feldes nach den besten Parameter und gibt dieses
	/// "Unterfeld" wieder zurück n ... Anz. an Parameterwerten, die probiert werden
	/// (zwischen exzellent und normalen Feldern unterschiedlich)
	private Field localSearch(Function<List<Double>, Double> f, Field prev, int t, Comparator<Field> c) {
		// return prev after
		if (prev.regressions >= 3)
			return prev;

		Field bestLocal = IntStream.range(0, t)
				.mapToObj(i -> populateRandomSet(prev.borders).boxed().toList())
				.map(params -> new Field(f.apply(params), params, null, 0))
				.max(c)
				.orElse(prev);
		boolean improved = c.compare(bestLocal, prev) > 0;
		var winner = improved ? bestLocal : prev;
		double[][] newBorders = fieldBorders(prev.borders, winner.highestResultPar, s);
		// du kannst hier drinnen wahrscheinlich wieder global search verwenden um t
		// Felder zu bekommen

		// Du musst dann noch das machen (siehe Skript):
		// • Wenn innerhalb weniger Schritte in einem Feld kein Fortschritt er- zielt
		// wird
		// (kein Suchergebnis ist besser als ein früher auf diesem Feld gefundenes),
		// wird dieses Feld nicht weiter
		// bearbeitet, indem die Kundschafterin ein schlechtes Ergebnis zurückmeldet.
		//
		// • Ein Feld wird pro Schritt verkleinert, sodass sich die Suche auf den Teil
		// beschränkt, wo das beste
		// Ergebnis im Feld zu ﬁnden war.
		return new Field(winner.highestResult, winner.highestResultPar, newBorders,
				improved ? 0 : prev.regressions + 1);
	}

	// global und local search
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
		newRes.addAll(globalSearch(f, w, n -m, c));
		return search(t - 1, f, w, newRes, c);
	}

	/// Ausführung des Algorithmus
	public Set<List<Double>> run(Function<List<Double>, Double> f, Comparator<Double> c,
			double[][] w, int t) {
		// init
		var res = globalSearch(f, w, t, Comparator.comparing(Field::highestResult));
		res.reversed().stream().limit(r).map(Field::highestResultPar);
		// liefert die n Einträge mit den höchsten Werten, exclusive der e exzellenten
		// Einträge
		return search(t, f, w, res, Comparator.comparing(Field::highestResult, c)).reversed().stream().limit(r)
				.map(Field::highestResultPar).collect(Collectors.toSet());
	}

	/// Borders sind die beschränkten Grenzen für diese Parameter Regression wird
	/// hochgezählt, wenn das beste gefundene Ergebnis niedriger als das gespeicherte
	/// ist. Wenn regression >= 3?, dann wird das Feld nicht mehr bearbeitet (bleibt
	/// aber erhalten).
	private record Field(Double highestResult, List<Double> highestResultPar, double[][] borders, int regressions) {
	}
}
