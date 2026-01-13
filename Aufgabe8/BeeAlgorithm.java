import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.*;


/// Plan:
/// Wir müssen die Bienen in Batches abarbeiten. Wir brauchen auch die Info, in welchen Bereich sie suchen sollen.
/// Wir sollen in der Rekrutierungsphase keine schweren Berechnungen machen.
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


	public SortedSet<Field> search(int t, Function<List<Double>, Double> f, double[][] w, SortedSet<Field> prev,
			Comparator<Field> c) {
		if (t == 0)
			return prev;
		// Local search für die e exzellenten Felder
		// Du musst für jeden der Einträge p/b LocaleSearchBlocks erstellen.
		var newRes = prev.reversed().stream().limit(e).map(e -> new LocalSearchBlock(8, f,e, p, c) localSearch(f, e, p, c))
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

}
