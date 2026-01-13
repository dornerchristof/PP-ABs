import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/// Repräsentiert eine Arbeitseinheit, die von einem Thread ausgeführt werden kann.
public abstract class Block implements Runnable{
    /// Anzahl der Bienen, die von diesem Block bearbeitet werden
    protected final int b;
    /// Funktion, die untersucht wird
    protected final Function<List<Double>, Double> f;
    /// Vergleicher, welcher das beste Feld bestimmt
    protected final Comparator<Field> c;

    // Relative Größe des Feldes verglichen zum untersuchten Bereich
    // 0 <= s <= 1
    protected final double s;

    public Block(int b, Function<List<Double>, Double> f, Comparator<Field> c, double s) {
        this.b = b;
        this.f = f;
        this.c = c;
        this.s = s;
    }

    protected static DoubleStream populateRandomSet(double[][] w) {
        var rng = new Random();
        // Liefert einen Stream mit w.length Zufallszahlen, die in den von w definierten
        // Grenzen liegen.
        return IntStream.range(0, w.length)
                .mapToDouble(i -> {
                    return rng.nextDouble() * (w[i][1] - w[i][0]) + w[i][0];
                });
    }


    protected double[][] fieldBorders(double[][] w, List<Double> funcPar, double percent) {
        //Erstellt ein Array mit den engeren neuen Grenzen als Abstand von Mittelpunkt
        double[] v = Stream.of(w).mapToDouble(p -> percent / 2 * (Math.abs(p[0]) + Math.abs(p[1]))).toArray();
        //Erstellt das neue Border-Array, wobei die Grenzen jeweils der funcPar + bzw - v[] sind.
        return IntStream.range(0, w.length).mapToObj(i -> new double[] { funcPar.get(i) - v[i], funcPar.get(i) +
                v[i] }).toArray(double[][]::new);
    }
}


