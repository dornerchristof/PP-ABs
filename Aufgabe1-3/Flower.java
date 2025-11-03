
/*
Repräsentiert die unveränderlichen Eigenschaften einer Pflanze. Alle FlowerPopulations der gleichen Pflanzenart halten
eine Referenz auf eine einzige Instanz dieser Klasse.
*/
public class Flower {

    private final String name;
    private final char shortName;

    //NOTE: Jedes Jahr vermehrt sich die Pflanzenpopulation um einen Faktor zwischen diesen Grenzwerten.
    private final Limits reproductionFactor;
    //NOTE: Nur in diesen Bodenfeuchtigkeitsgrenzen wächst diese Pflanze.
    private final Limits groundMoistureLimits;
    //NOTE: Wenn die Pflanze die untere Grenze an Sonnenstunden überschreitet, dann fängt sie an zu blühen, wenn sie die
    //obere Grenze überschreitet, dann höhrt sie auf zu blühen.
    private final Limits sunlightHoursForBlooming;

    private final double bestaeubungswahrscheinlichkeit;
    //NOTE: How fast all flowers start to bloom and how fast they stop blooming.
    private final double bloomIntensity;

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
        return new Flower(name, reproductionFactor, groundMoistureLimits, sunlightHoursForBlooming, bloomIntensity,
                bestaeubungswahrscheinlichkeit);
    }

    @Override
    public String toString() {
        return name;
    }
}
