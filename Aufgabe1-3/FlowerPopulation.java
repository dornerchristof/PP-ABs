public class FlowerPopulation {
    int Wuchskraft;
    double ProzentInBlüte;
    double Samenqualität;

    public double getNahrunsangebot(){
        return 0.0;
    }

    public int getWuchskraft(){
        return Wuchskraft;
    }

    int Feuchtegrenze;
    int Blühgrenze;
    double Bestäubungswahrscheinlichkeit;


    public int Tagessimulation(double groundMoisture, int sunshineHours, int beePopulation){
        int availableFood = 0; //Nahrungsangebot einer Pflanze (y * b) siehe Angabe.
        return availableFood;
    }


    @Override
    public String toString() {
        return "FlowerPopulation{" +
                "Wuchskraft=" + Wuchskraft +
                ", ProzentInBlüte=" + ProzentInBlüte +
                ", Samenqualität=" + Samenqualität +
                ", Feuchtegrenze=" + Feuchtegrenze +
                ", Blühgrenze=" + Blühgrenze +
                ", Bestäubungswahrscheinlichkeit=" + Bestäubungswahrscheinlichkeit +
                '}';
    }
}
