import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/// Macht nur Ausgabe (Seiteneffekt). Der Algorithmus soll davon unabhängig bleiben.
public final class ResultPresenter {

    public record BAParams(int n, int m, int e, int p, int q, double s, int r, int t) {}

    private record Scored(List<Double> x, double y) {}

    public void printRun(
            int runIndex,
            String description,
            String functionText,
            double[][] w,
            String goalText,
            BAParams params,
            Set<List<Double>> points,
            Function<List<Double>, Double> f,
            long runtimeNanos
    ) {
        System.out.println();
        System.out.println("--- Testlauf " + runIndex + ": " + description + " ---");
        System.out.println("Funktion: " + functionText);
        System.out.println("Wertebereich w = " + rangeToString(w));
        System.out.println("Vergleich: " + goalText);
        System.out.println("BA-Parameter: n=" + params.n()
                + ", m=" + params.m()
                + ", e=" + params.e()
                + ", p=" + params.p()
                + ", q=" + params.q()
                + ", s=" + params.s()
                + ", t=" + params.t()
                + ", r=" + params.r());

        var list = new ArrayList<Scored>(points.size());
        for (var x : points) list.add(new Scored(x, f.apply(x)));
        list.sort(Comparator.comparingDouble(Scored::y).reversed());

        System.out.println();
        System.out.println("Gefundene Top-Ergebnisse:");
        int limit = Math.min(params.r(), list.size());
        for (int i = 0; i < limit; i++) {
            var sp = list.get(i);
            System.out.printf("%2d. Stelle: %s -> Funktionswert: %.12f%n", i + 1, vecToString(sp.x), sp.y);
        }

        System.out.println();
        System.out.printf("Laufzeit dieses Tests: %.3f s%n", runtimeNanos / 1_000_000_000.0);
    }

    private String rangeToString(double[][] w) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < w.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append("[").append(w[i][0]).append(", ").append(w[i][1]).append("]");
        }
        sb.append("]");
        return sb.toString();
    }

    private String vecToString(List<Double> x) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < x.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(String.format("%.6f", x.get(i)));
        }
        sb.append("]");
        return sb.toString();
    }
}