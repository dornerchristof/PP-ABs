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


    public void Tagessimulation(){

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
