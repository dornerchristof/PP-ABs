import java.util.Random;

enum BlueteEnum {
    SETZT_EIN,
    ENDED,
    VORBEI
}

/*
Abstrakter Datentyp(Klasse) mit nominaler Abstraktion. Repräsentiert eine Population von
Blütenpflanzen derselben Art in einem Chunk, mit spezifischen Eigenschaften wie Wuchskraft, Blütezeit und
Samenqualität. Simuliert das Verhalten der Pflanzen unter verschiedenen Umweltbedingungen.
 */
public class FlowerPopulation {
    //Anzahl an Wildbienen, die durch die Pflanzenpopulation während der Vollblüte ernährt werden können.
    double currentPopulation;             // yi - 0 <= yia
    //Wie viel Prozent der currentPopulation sind in Blühte
    double bloomPercentage;      // bi - 0 <= bi <= 1

    //Zeigt an, wie gut die Pflanze bestäubt wurde (auch ob sie genug Sonne hatte?)
    private LimitedDouble seedQuality;

    private final Flower flower;
    private final Random random = new Random();

    public char getShortName(){return flower.getShortName();}

    BlueteEnum inBluete;
    double receivedSunshineHours;

    //Erstellt eine neue Population von Blütenpflanzen mit den angegebenen Parametern,
    //die die Eigenschaften und Verhaltensweise der Pflanzenart definieren.
    public FlowerPopulation(Flower flower) {
        this.flower= flower;
        this.inBluete = BlueteEnum.VORBEI;
        seedQuality = new LimitedDouble(new Limits(0,1), 0);
    }


    public FlowerPopulation(Flower flower, double initialPopulation) {
        this(flower);
        currentPopulation = initialPopulation;
    }

    public FlowerPopulation(FlowerPopulation other) {
        this.flower = other.flower;
        this.inBluete = BlueteEnum.VORBEI;
        this.currentPopulation = other.currentPopulation;
        this.bloomPercentage = other.bloomPercentage;
    }

    //Startet eine neue Vegetationsperiode, welche verschiedene Werte zurücksetzt.
    //true wenn die Pflanze den Winter überlebt, sonst false.

    //ERROR: Leere Pflanzenpopulation werden nicht entfernt.
    public boolean simulateRestingPeriod() {
        bloomPercentage = 0;
        receivedSunshineHours = 0;
            currentPopulation *= seedQuality.getValue();
            currentPopulation *= random.nextDouble(flower.getReproductionFactor().getMin(), flower.getReproductionFactor().getMax());
        seedQuality.setValue(0);
        return true;
    }

    //Ändert den Wert der Wuchskraft und stellt sicher, dass der neue Wert innerhalb der Grenzen ist.
    private void changeWuchskraft(double factor) {
        double newWuchskraft = currentPopulation + currentPopulation * factor;
        this.currentPopulation = Math.max(0, newWuchskraft);
    }
    //Ändert die Prozent der sich in Blüte befindenden Pflanzenpopulation und stellt sicher, dass der neue Wert innerhalb der Grenzen ist.
    private void changeProzentInBluete(double factor) {
        double newProzentInBluete = bloomPercentage + factor;
        this.bloomPercentage = Math.max(0, Math.min(1, newProzentInBluete));
        if (this.inBluete == BlueteEnum.SETZT_EIN && this.bloomPercentage == 1) this.inBluete = BlueteEnum.ENDED;
        else if (this.inBluete == BlueteEnum.ENDED && this.bloomPercentage == 0) this.inBluete = BlueteEnum.VORBEI;
    }

    //Nominale Abstraktion.
    //Führt Berechnungen für die Simulation eines Tages für die Pflanzenpopulation durch
    /*„STYLE:"
    Hier wird ein objektorientiertes Paradigma angewendet. Die Methode greift auf den internen Zustand des
    Flower-Objekts (Wuchskraft, ProzentInBluete, Samenqualitaet, bestaeubungswahrscheinlichkeit)
    zu und modifiziert ihn. Die Methode kapselt das Wachstumsverhalten einer Pflanzenpopulation
    und nutzt private Hilfsmethoden (changeWuchskraft, changeProzentInBluete, etc.) zur Zustandsänderung,
    wodurch die Prinzipien der Datenkapselung realisiert werden. Die Interaktion mit
    dem Weather-Objekt erfolgt über die im Interface definierten Schnittstellen.
    */
    //weather = Wetter dieses Tages
    //beesVisited = Anzahl an Bienen, die genau diese Pflanze an diesem Tag besucht haben.
    public void simulateGrowingDay(Weather weather, double beesVisited) {
        if (!flower.getGroundMoistureLimits().inRange(weather.getSoilMoisture())) {
            double factor = flower.getGroundMoistureLimits().getRangeFactor(weather.getSoilMoisture());
            if (factor > 1.0) {
                if (factor >= 2.0) {
                    changeWuchskraft(-0.03);
                } else {
                    changeWuchskraft(-0.01);
                }
            } else {
                if (factor > 0.5) {
                    changeWuchskraft(-0.01);
                } else {
                    changeWuchskraft(-0.03);
                }
            }
        }
        receivedSunshineHours += weather.getSunshineHours();
        switch (this.inBluete) {
            case VORBEI:
                if (flower.getSunlightHoursForBlooming().inRange(receivedSunshineHours)) {
                    this.inBluete = BlueteEnum.SETZT_EIN;
                }
                break;
            case SETZT_EIN:
                changeProzentInBluete(flower.getBloomIntensity() * (receivedSunshineHours + 3));
                break;
            case ENDED:
                changeProzentInBluete(-flower.getBloomIntensity() * (receivedSunshineHours + 3));
                break;
        }
        //seed quality
        double factor = 0;
        if (beesVisited >= currentPopulation) {
            factor = (weather.getSunshineHours() + 1);
        } else {
            factor = ((weather.getSunshineHours() + 1));// * beesVisited/ currentPopulation);
        }
        seedQuality.setValue(seedQuality.getValue() + flower.getBestaeubungswahrscheinlichkeit() * bloomPercentage * factor);
    }

    //Berechnet das zur Verfügung stehende Nahrungsangebot.
    public double getNahrungsangebot() {
        return currentPopulation * bloomPercentage;
    }

    public Flower getFlower() {return flower;}

    //Gibt die Wuchskraft zurück
    public double getCurrentPopulation() {
        return currentPopulation;
    }

    public void addPopulation(double amount){
        currentPopulation += amount;
    }



    public String getName(){
        return flower.getName();
    }

    @Override
    public String toString() {
        return flower.getName() + "(" +
                "Wuchskraft: " + String.format("%.2f, ", currentPopulation) +
                "InBlüte: " + String.format("%.2f", bloomPercentage) + "%, " +
                "Samenqualität: " + String.format("%.2f, ", seedQuality.getValue()) +
                ")";
    }
}
