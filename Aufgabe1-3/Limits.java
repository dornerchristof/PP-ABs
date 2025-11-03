
/*
Abstrakter Datentyp(Klasse) mit nominaler Abstraktion. Repräsentiert die Grenzen einer Variable in der Simulation.
Bietet Funktionen zur Überprüfung, ob ein Wert innerhalb des erlaubten Bereichs liegt und
zur Berechnung von Abweichungsfaktoren.
 */
public class Limits {
    private final double max;  //max != 0
    private final double min; //min != 0 und min < max

    //Erstellt einen neuen eingeschränkten Gleitkommawert mit den angegebenen
    //Minimal- und Maximalwerten als Grenzen.
    //Vorbedingung: min < max
    public Limits(double min, double max){
        this.min = min;
        this.max = max;
    }
    //Kopierkonstruktor
    public Limits(Limits other) {
        this.min = other.min;
        this.max = other.max;
    }

    //Nominale Abstraktion.
    //Überprüft, ob der aktuelle Wert innerhalb der definierten Grenzen liegt.
    public boolean inRange(double value){
        return value >= min && value <= max;
    }

    //Nominale Abstraktion.
    //Berechnet einen Faktor, der die Abweichung vom erlaubten Bereich quantifiziert.
    //Gibt 0 zurück, wenn der Wert im erlaubten Bereich liegt.
    public double getRangeFactor(double value) {
        if (inRange(value)) return 0.0;
        if (value < min) return value / min;
        return value / max;
    }

    //Gibt die untere Grenze des erlaubten Bereichs zurück.
    public double getMin() {
        return min;
    }

    //Gibt die obere Grenze des erlaubten Bereichs zurück.
    public double getMax() {
        return max;
    }
}
