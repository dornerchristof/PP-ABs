
/*
Repräsentiert die unveränderlichen Eigenschaften einer Pflanze. Alle FlowerPopulations der gleichen Pflanzenart halten
eine Referenz auf eine einzige Instanz dieser Klasse.
*/
public class Flower {

    private final String name;
    private final char shortName;

    //Jedes Jahr vermehrt sich die Pflanzenpopulation um einen Faktor zwischen diesen Grenzwerten.
    private Limits reproductionFactor;
    //Nur in diesen Bodenfeuchtigkeitsgrenzen wächst diese Pflanze.
    private Limits groundMoistureLimits;
    //Wenn die Pflanze die untere Grenze an Sonnenstunden überschreitet, dann fängt sie an zu blühen, wenn sie die
    //obere Grenze überschreitet, dann höhrt sie auf zu blühen.
    private Limits sunlightHoursForBlooming;

    double bestaeubungswahrscheinlichkeit;
    //How fast all flowers start to bloom and how fast they stop blooming.
    double bloomIntensity;
    double Verbreitungswahrscheinlichkeit;

    public Flower(String name, Limits vermehrungsgrenzen,
                  Limits groundMoistureLimits, Limits sunlightHoursForBlooming,
                  double bloomIntensity, double bestaeubungswahrscheinlichkeit) {
        this.name = name;
        this.shortName = name.charAt(0);
        this.reproductionFactor = vermehrungsgrenzen;
        this.groundMoistureLimits = groundMoistureLimits;
        this.sunlightHoursForBlooming = sunlightHoursForBlooming;
        this.bestaeubungswahrscheinlichkeit = bestaeubungswahrscheinlichkeit;
        this.bloomIntensity = bloomIntensity;
    }

    public Flower(String name, char shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    String getName(){
        return name;
    }

    public char getShortName(){ return shortName;}

    public Limits getReproductionFactor() {
        return reproductionFactor;
    }

    public Limits getGroundMoistureLimits() {
        return groundMoistureLimits;
    }

    public Limits getSunlightHoursForBlooming() {
        return sunlightHoursForBlooming;
    }

    public double getBestaeubungswahrscheinlichkeit() {
        return bestaeubungswahrscheinlichkeit;
    }

    public double getBloomIntensity() {
        return bloomIntensity;
    }

    public Flower copy(){
        return new Flower(name, reproductionFactor,groundMoistureLimits,sunlightHoursForBlooming,bloomIntensity,bestaeubungswahrscheinlichkeit);
    }

    @Override
    public String toString() {
        return name;
    }
}
