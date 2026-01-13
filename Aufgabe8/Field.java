import java.util.List;

/// Borders sind die beschränkten Grenzen für dieses Feld.
/// Regression >= 3 bedeutet, dass das Feld nicht weiter bearbeitet wird.
/// highestResult ist das Resultat der Funktion mit den Parametern highestResultPar.
public record Field(Double highestResult, List<Double> highestResultPar, double[][] borders, int regressions) {
}
