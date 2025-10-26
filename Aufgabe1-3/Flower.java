
/*
Repräsentiert die unveränderlichen Eigenschaften einer Pflanze. Alle FlowerPopulations der gleichen Pflanzenart halten
eine Referenz auf eine einzige Instanz dieser Klasse.
*/
public class Flower {

    private final String name;
    private final char shortName;
    private RestrictedDouble vermehrungsgrenzen;
    private RestrictedDouble feuchtegrenzen;
    private RestrictedDouble bluehgrenzen;
    double bestaeubungswahrscheinlichkeit;
    double bluehintensitaet;

    public Flower(String name, double wuchskraft, RestrictedDouble vermehrungsgrenzen,
                            RestrictedDouble feuchtegrenzen, RestrictedDouble bluehgrenzen,
                            double bluehintensitaet, double bestaeubungswahrscheinlichkeit) {
        this.name = name;
        this.shortName = name.charAt(0);
        this.vermehrungsgrenzen = vermehrungsgrenzen;
        this.feuchtegrenzen = feuchtegrenzen;
        this.bluehgrenzen = bluehgrenzen;
        this.bestaeubungswahrscheinlichkeit = bestaeubungswahrscheinlichkeit;
        this.bluehintensitaet = bluehintensitaet;
    }

    public Flower(String name, char shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    String getName(){
        return name;
    }

    public char getShortName(){ return shortName;}

    public RestrictedDouble getVermehrungsgrenzen() {
        return vermehrungsgrenzen;
    }

    public RestrictedDouble getFeuchtegrenzen() {
        return feuchtegrenzen;
    }

    public RestrictedDouble getBluehgrenzen() {
        return bluehgrenzen;
    }

    public double getBestaeubungswahrscheinlichkeit() {
        return bestaeubungswahrscheinlichkeit;
    }

    public double getBluehintensitaet() {
        return bluehintensitaet;
    }

}
