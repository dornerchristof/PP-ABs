
/*
Abstrakter Datentyp(Klasse) mit nominaler Abstraktion. Repräsentiert einen Gleitkommawert,
der auf einen bestimmten Bereich beschränkt ist. Bietet Funktionen zur Überprüfung, ob ein Wert
innerhalb des erlaubten Bereichs liegt und zur Berechnung von Abweichungsfaktoren.
 */
public class RestrictedDouble {
    private double max;
    private double min;
    private double value;

    //Erstellt einen neuen eingeschränkten Gleitkommawert mit den angegebenen
    //Minimal- und Maximalwerten als Grenzen.
    public RestrictedDouble(double min, double max){
        this.min = min;
        this.max = max;
    }
    public RestrictedDouble(RestrictedDouble other) {
        this.min = other.min;
        this.max = other.max;
        this.value = other.value;
    }


    //Setzt den aktuellen Wert des RestrictedDouble-Objekts.
    public void setValue(double value) {
        this.value = value;
    }

    //Gibt den aktuellen Wert des RestrictedDouble-Objekts zurück.
    public double getValue(){
        return value;
    }

    //Nominale Abstraktion.
    //Überprüft, ob der aktuelle Wert innerhalb der definierten Grenzen liegt.
    public boolean isInRange(){
        return value >= min && value <= max;
    }

    //Nominale Abstraktion.
    //Berechnet einen Faktor, der die Abweichung vom erlaubten Bereich quantifiziert.
    //Gibt 0 zurück, wenn der Wert im erlaubten Bereich liegt.
    public double getRangeFactor() {
        if (isInRange()) return 0.0;
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
