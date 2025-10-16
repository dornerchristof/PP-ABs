import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TotalFlowerPopulation {
    private List<FlowerPopulation> flowers;

    public TotalFlowerPopulation(List<FlowerPopulation> flowers) {
        this.flowers = flowers;
    }
    public TotalFlowerPopulation() {
        this.flowers = new ArrayList<>();
        Random rand = new Random(324234234);
        int randomNumber = rand.nextInt(15) + 10;
        for (int i = 0; i < randomNumber; i++) {
            double wuchskraft = rand.nextDouble() * 1000 + 200;
            double vg = rand.nextDouble() * 0.5525;
            RestrictedDouble vermehrungsgrenzen = new RestrictedDouble(5, 20);
            double fg = rand.nextDouble() * 0.5;
            RestrictedDouble  feuchtegrenzen = new RestrictedDouble(0,  1);
            RestrictedDouble bluehgrenzen;
            double bgL;
            double bgH;
            if (i <= randomNumber / 3 ){
                double randomOffset = rand.nextDouble() * 500 ;
                bgL = rand.nextInt(2);
                bgH = rand.nextInt(600) + randomOffset + bgL;
                bluehgrenzen = new RestrictedDouble(bgL, bgH);
            }
            else   if (i <= randomNumber / 3 * 2 ){
                double randomOffset = rand.nextDouble() * 500;
                bgL = rand.nextInt(600) + 400;
                bgH = rand.nextInt(6000) + randomOffset + bgL;
                bluehgrenzen = new RestrictedDouble(bgL, bgH);
            }
            else {
                double randomOffset = rand.nextDouble() * 500;
                bgL = rand.nextInt(600) + 1500;
                bgH = rand.nextInt(600) + randomOffset + bgL;
                bluehgrenzen = new RestrictedDouble(bgL, bgH);
            }
            double bluehintensitaet = rand.nextDouble() * (1.0/15);
            double bestaeubungswahrscheinlichkeit = (1.0/(bgH-bgL));
            this.flowers.add(new FlowerPopulation(wuchskraft,vermehrungsgrenzen,feuchtegrenzen,bluehgrenzen,bluehintensitaet,bestaeubungswahrscheinlichkeit));
            if(i == 0){
                System.out.println("Vermehrungsgrenzen: " + vermehrungsgrenzen.getMin() + " - " + vermehrungsgrenzen.getMax() + "");
                System.out.println("Feuchtegrenzebezug: " + feuchtegrenzen.getMin() + " - " + feuchtegrenzen.getMax() + "");
                System.out.println("Bluegrenhzenezug: " + bluehgrenzen.getMin() + " - " + bluehgrenzen.getMax() + "");
            }

        }
    }

    public double getNahrungsAngebot(){
        double nahrungsAngebot=0;
        for (FlowerPopulation fp : flowers){
            nahrungsAngebot += fp.getNahrungsangebot();
        }
        return nahrungsAngebot;
    }

    public void printFlowers(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < flowers.size(); i++) {
            sb.append("Blütenpflanzenart ");
            sb.append(i);
            sb.append(": yi = ");
            FlowerPopulation fp = flowers.get(i);
            sb.append(fp.getWuchskraft());
            sb.append('\n');
        }
        System.out.println(sb);
    }

    public void printParameters(){
        System.out.println("Flower paramters:");
       //Alle Parameter von allen PFlanzen
    }

    public void Tagessimulation(double groundMoisture, int sunshineHours, double beePopulation, double nahrungsangebot, boolean isRuhePhase) {
//        System.out.println("sunshinehours: " +sunshineHours);
        for (FlowerPopulation fp : flowers) {
            fp.Tagessimulation(groundMoisture,sunshineHours,beePopulation,nahrungsangebot, isRuhePhase);
        }
        System.out.println(flowers.getFirst());
    }
}
