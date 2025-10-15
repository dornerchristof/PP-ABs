enum BlueteEnum {
    SETZT_EIN,
    ENDED,
    VORBEI
}


public class FlowerPopulation {
    double Wuchskraft;             // yi - 0 <= yi
    double ProzentInBluete;      // bi - 0 <= bi <= 1
    double Samenqualitaet;       // si - 0 <= si <= 1
    double bestaeubungswahrscheinlichkeit;
    double bluehintensitaet;      // qi - 0 < qi < 1/15

    BlueteEnum inBluete;

    RestrictedDouble vermehrungsgrenzen;
    RestrictedDouble feuchtegrenzen;
    RestrictedDouble bluehgrenzen;

    public FlowerPopulation(double wuchskraft, RestrictedDouble vermehrungsgrenzen, RestrictedDouble feuchtegrenzen, RestrictedDouble bluehgrenzen, double bluehintensitaet, double bestaeubungswahrscheinlichkeit) {
        this.Wuchskraft = wuchskraft;
        this.vermehrungsgrenzen = vermehrungsgrenzen;
        this.feuchtegrenzen = feuchtegrenzen;
        this.bluehgrenzen = bluehgrenzen;
        this.bluehintensitaet = bluehintensitaet;
        this.bestaeubungswahrscheinlichkeit = bestaeubungswahrscheinlichkeit;

        this.inBluete = BlueteEnum.VORBEI;

        startVegetationsPeriode();
    }

    private void startVegetationsPeriode() {
        this.ProzentInBluete = 0;
        this.Samenqualitaet = 0;
        this.bluehgrenzen.setValue(0);
    }

    private void changeWuchskraft(double factor) {
        double newWuchskraft = Wuchskraft + Wuchskraft * factor;
        this.Wuchskraft = Math.max(0, newWuchskraft);
    }

    private void changeProzentInBluete(double factor) {
        double newProzentInBluete = ProzentInBluete + factor;
        this.ProzentInBluete = Math.max(0, Math.min(1, newProzentInBluete));
        if (this.inBluete == BlueteEnum.SETZT_EIN && this.ProzentInBluete == 1) this.inBluete = BlueteEnum.ENDED;
        else if (this.inBluete == BlueteEnum.ENDED && this.ProzentInBluete == 0) this.inBluete = BlueteEnum.VORBEI;
    }

    private void changeBestaeubungswahrscheinlichkeit(double factor) {
        double newBestaeubungswahrscheinlichkeit = bestaeubungswahrscheinlichkeit + bestaeubungswahrscheinlichkeit * ProzentInBluete * factor;
        this.bestaeubungswahrscheinlichkeit = Math.max(0, Math.min(1/(this.bluehgrenzen.getMax() - this.bluehgrenzen.getMin()), newBestaeubungswahrscheinlichkeit));
    }


    public void Tagessimulation(double groundMoisture, int sunshineHours, double beePopulation, double nahrungsangebot, boolean isRuhePhase) {
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
                changeProzentInBluete(bluehintensitaet * (this.bluehgrenzen.getValue() + 3));
                break;
            case ENDED:
                changeProzentInBluete(-bluehintensitaet * (this.bluehgrenzen.getValue() + 3));
                break;
        }

        if (beePopulation >= nahrungsangebot) {
            changeBestaeubungswahrscheinlichkeit(this.bluehgrenzen.getValue() + 1);
        } else {
            changeBestaeubungswahrscheinlichkeit((this.bluehgrenzen.getValue() + 1) * beePopulation / nahrungsangebot);
        }

    }

    public double getNahrungsangebot() {
        return Wuchskraft * ProzentInBluete;
    }

    public double getWuchskraft() {
        return Wuchskraft;
    }

//    @Override
//    public String toString() {
//        return "FlowerPopulation{" +
//                "Wuchskraft=" + Wuchskraft +
//                ", ProzentInBluete=" + ProzentInBluete +
//                ", Samenqualitaet=" + Samenqualitaet +
//                ", Feuchtegrenze=" + Feuchtegrenze +
//                ", Bluehgrenze=" + Bluehgrenze +
//                ", Bestaeubungswahrscheinlichkeit=" + Bestaeubungswahrscheinlichkeit +
//                '}';
//    }
}
