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
    double Wuchskraft;             // yi - 0 <= yi
    double ProzentInBluete;      // bi - 0 <= bi <= 1
    double Samenqualitaet;       // si - 0 <= si <= 1
    double bestaeubungswahrscheinlichkeit;
    double bluehintensitaet;      // qi - 0 < qi < 1/15

    private final Flower flower;


    public char getShortName(){return flower.getShortName();}

    BlueteEnum inBluete;

    RestrictedDouble vermehrungsgrenzen;
    RestrictedDouble feuchtegrenzen;
    RestrictedDouble bluehgrenzen;

    //Erstellt eine neue Population von Blütenpflanzen mit den angegebenen Parametern,
    //die die Eigenschaften und Verhaltensweise der Pflanzenart definieren.
    public FlowerPopulation(Flower flower) {
        this.vermehrungsgrenzen = flower.getVermehrungsgrenzen();
        this.feuchtegrenzen = flower.getFeuchtegrenzen();
        this.bluehgrenzen = flower.getBluehgrenzen();
        this.bluehintensitaet = flower.getBluehintensitaet();
        this.bestaeubungswahrscheinlichkeit = flower.getBestaeubungswahrscheinlichkeit();
        this.flower= flower;
        this.inBluete = BlueteEnum.VORBEI;

        startVegetationsPeriode();
    }


    public FlowerPopulation(Flower flower, double initialPopulation) {
        this(flower);
        Wuchskraft = initialPopulation;
    }

    //Startet eine neue Vegetationsperiode, welche verschiedene Werte zurücksetzt.
    private void startVegetationsPeriode() {
        this.ProzentInBluete = 0;
        this.Samenqualitaet = 0;
        this.bluehgrenzen.setValue(0);
    }

    //Ändert den Wert der Wuchskraft und stellt sicher, dass der neue Wert innerhalb der Grenzen ist.
    private void changeWuchskraft(double factor) {
        double newWuchskraft = Wuchskraft + Wuchskraft * factor;
        this.Wuchskraft = Math.max(0, newWuchskraft);
    }
    //Ändert die Prozent der sich in Blüte befindenden Pflanzenpopulation und stellt sicher, dass der neue Wert innerhalb der Grenzen ist.
    private void changeProzentInBluete(double factor) {
        double newProzentInBluete = ProzentInBluete + factor;
        this.ProzentInBluete = Math.max(0, Math.min(1, newProzentInBluete));
        if (this.inBluete == BlueteEnum.SETZT_EIN && this.ProzentInBluete == 1) this.inBluete = BlueteEnum.ENDED;
        else if (this.inBluete == BlueteEnum.ENDED && this.ProzentInBluete == 0) this.inBluete = BlueteEnum.VORBEI;
    }

    //Ändert die Bestäubungswahrscheinlichkeit und stellt sicher, dass der neue Wert innerhalb der Grenzen ist.
    private void changeBestaeubungswahrscheinlichkeit(double factor) {
        double newSamenqualitaet = Samenqualitaet + bestaeubungswahrscheinlichkeit * ProzentInBluete * factor;
        this.Samenqualitaet = Math.max(0, Math.min(1, newSamenqualitaet));
    }


    public double getProzentInBluete(){
        return ProzentInBluete;
    }

    public double getSamenqualitaet(){
        return Samenqualitaet;
    }

    //Nominale Abstraktion.
    //Führt Berechnungen für die Simulation eines Tages für die Pflanzenpopulation durch
    public void Tagessimulation(Weather weather, double faktor, boolean isRuhePhase) {
        var groundMoisture = weather.getSoilMoisture();
        var sunshineHours = weather.getSunshineHours();
        if (isRuhePhase) {
            double randomNumber = Math.random() * (vermehrungsgrenzen.getMax()  - vermehrungsgrenzen.getMin()) + vermehrungsgrenzen.getMin();
            this.Wuchskraft *= this.Samenqualitaet * randomNumber;

            startVegetationsPeriode();
            return;
        }

        this.feuchtegrenzen.setValue(groundMoisture);
        if (!feuchtegrenzen.isInRange()) {
            double factor = feuchtegrenzen.getRangeFactor();
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

        this.bluehgrenzen.setValue(this.bluehgrenzen.getValue() + sunshineHours);
        switch (this.inBluete) {
            case VORBEI:
                if (this.bluehgrenzen.isInRange()) {
                    this.inBluete = BlueteEnum.SETZT_EIN;
                }
                break;
            case SETZT_EIN:
                changeProzentInBluete(bluehintensitaet * (sunshineHours + 3));
                break;
            case ENDED:
                changeProzentInBluete(-bluehintensitaet * (sunshineHours + 3));
                break;
        }

            changeBestaeubungswahrscheinlichkeit(faktor);

    }

    //Berechnet das zur Verfügung stehende Nahrungsangebot.
    public double getNahrungsangebot() {
        return Wuchskraft * ProzentInBluete;
    }

    public Flower getFlower() {return flower;}

    //Gibt die Wuchskraft zurück
    public double getWuchskraft() {
        return Wuchskraft;
    }

    //Erstellt aus den verschiedenen Eigenschaften des Objekts einen String.
    public String printParameters(){
        return flower.getName() + "(" +
                "Wuchskraft: " + String.format("%.2f, ", Wuchskraft) +
                "Vermehrungsgrenze: " + String.format("%.2f", vermehrungsgrenzen.getMin()) + "-" +
                String.format("%.2f, ", vermehrungsgrenzen.getMax()) +
                "Feuchtegrenze: " + String.format("%.2f", feuchtegrenzen.getMin()) + "-" +
                String.format("%.2f, ", feuchtegrenzen.getMax()) +
                "Blühgrenzen: " + String.format("%.2f", bluehgrenzen.getMin()) + "-" +
                String.format("%.2f, ", bluehgrenzen.getMax()) +
                "Blühintensität: " + String.format("%.2f, ", bluehintensitaet) +
                "Bestäubungswahrscheinlichkeit: " + String.format("%.4f", bestaeubungswahrscheinlichkeit) +
                ")" + "\n";
    }

    @Override
    public String toString() {
        return flower.getName() + "(" +
                "Wuchskraft: " + String.format("%.2f, ", Wuchskraft) +
                "InBlüte: " + String.format("%.2f", ProzentInBluete) + "%, " +
                "Samenqualität: " + String.format("%.2f, ", Samenqualitaet) +
                ")";
    }
}
