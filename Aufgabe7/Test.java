import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Test {

    public static void main(String[] args) {
        var out = new ResultPresenter();

        long totalStart = System.nanoTime();

        runSinus(out);
        runMultiOptima(out);
        runZeros(out);

        long totalNanos = System.nanoTime() - totalStart;
        System.out.println();
        System.out.printf("Gesamtlaufzeit aller Tests: %.3f s%n", totalNanos / 1_000_000_000.0);
    }

    private static void runSinus(ResultPresenter out) {
        int runIndex = 1;

        Function<List<Double>, Double> f = x -> Math.sin(Math.toRadians(x.getFirst()));
        double[][] w = new double[][]{{-1800.0, 1800.0}};
        Comparator<Double> c = Comparator.naturalOrder(); // max

        // Parameter sind bewusst eher groß, damit es auch Laufzeit gibt.
        int n = 400, m = 160, e = 40, p = 80, q = 30, r = 10;
        double s = 0.10;
        int t = 15000;

        var bee = new BeeAlgorithm(n, m, e, p, q, s, r);

        long start = System.nanoTime();
        var res = bee.run(f, c, w, t);
        long nanos = System.nanoTime() - start;

        out.printRun(
                runIndex,
                "Sinus-Maximierung (Grad)",
                "f(x) = sin(toRadians(x))",
                w,
                "Maximierung",
                new ResultPresenter.BAParams(n, m, e, p, q, s, r, t),
                res,
                f,
                nanos
        );
    }

    private static void runMultiOptima(ResultPresenter out) {
        int runIndex = 2;

        Function<List<Double>, Double> f = x -> -(
                Math.pow(Math.pow(x.getFirst(), 2) - 1.0, 2)
                        + Math.pow(Math.pow(x.get(1), 2) - 1.0, 2)
        );

        double[][] w = new double[][]{{-5.0, 5.0}, {-5.0, 5.0}};
        Comparator<Double> c = Comparator.naturalOrder(); // max

        int n = 500, m = 200, e = 50, p = 90, q = 35, r = 10;
        double s = 0.12;
        int t = 1200;

        var bee = new BeeAlgorithm(n, m, e, p, q, s, r);

        long start = System.nanoTime();
        var res = bee.run(f, c, w, t);
        long nanos = System.nanoTime() - start;

        out.printRun(
                runIndex,
                "4 gleich gute Optima (±1,±1)",
                "f(x,y) = -(((x^2-1)^2) + ((y^2-1)^2))",
                w,
                "Maximierung (Optimum 0)",
                new ResultPresenter.BAParams(n, m, e, p, q, s, r, t),
                res,
                f,
                nanos
        );
    }

    private static void runZeros(ResultPresenter out) {
        int runIndex = 3;

        Function<List<Double>, Double> g = x -> (x.getFirst() - 1.0) * (x.getFirst() + 1.0) * (x.getFirst() - 2.0) * (x.getFirst() + 2.0);
        Function<List<Double>, Double> score = x -> -Math.abs(g.apply(x));

        double[][] w = new double[][]{{-3.0, 3.0}};
        Comparator<Double> c = Comparator.naturalOrder(); // max score

        int n = 400, m = 160, e = 40, p = 80, q = 30, r = 10;
        double s = 0.08;
        int t = 1500;

        var bee = new BeeAlgorithm(n, m, e, p, q, s, r);

        long start = System.nanoTime();
        var res = bee.run(score, c, w, t);
        long nanos = System.nanoTime() - start;

        out.printRun(
                runIndex,
                "Nullstellen über score(x)=-|g(x)|",
                "g(x)=(x-1)(x+1)(x-2)(x+2), score(x)=-|g(x)|",
                w,
                "Maximierung (score nahe 0 ist gut)",
                new ResultPresenter.BAParams(n, m, e, p, q, s, r, t),
                res,
                score,
                nanos
        );
    }
}