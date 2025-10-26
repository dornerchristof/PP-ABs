import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
Abstrakter Datentyp(Klasse) mit nominaler Abstraktion. Verwaltet eine Sammlung verschiedener
Blütenpflanzen-Populationen und bietet Methoden, um deren kollektives Verhalten zu simulieren
und Informationen über den Gesamtzustand abzufragen.
 */
public class TotalFlowerPopulation {
    private List<FlowerPopulation> flowers;

    //Erstellt eine neue Sammlung von Blütenpflanzen aus der übergebenen Liste.
    public TotalFlowerPopulation(List<FlowerPopulation> flowers) {
        this.flowers = flowers;
    }
    //Erstellt eine neue Sammlung von zufällig generierten Blütenpflanzen-Populationen
    //mit verschiedenen Eigenschaften für die Simulation.
    public TotalFlowerPopulation(int numberOfRandomFlowers, int wuchskraftfaktor, int wuchsstart, double vermehrungsuntergrenze, double vermehrungsobergrenze, double fMinus, double fPlus, long seed) {
        this.flowers = new ArrayList<>();
        Random rand = new Random(seed);
        for (int i = 0; i < numberOfRandomFlowers; i++) {
            double wuchskraft = rand.nextDouble() * wuchskraftfaktor + wuchsstart;
            double vg = rand.nextDouble() * 3;
            RestrictedDouble vermehrungsgrenzen = new RestrictedDouble(vermehrungsuntergrenze, vg + vermehrungsobergrenze);
            double fg = rand.nextDouble() * 0.2;
            RestrictedDouble  feuchtegrenzen = new RestrictedDouble(Math.max(0,fMinus - fg), Math.min(1,fPlus - fg));
            RestrictedDouble bluehgrenzen;
            double bgL;
            double bgH;
            // Frühlingsblumen
            double randomLength = rand.nextDouble() * 200 + 500;
            if (i <= numberOfRandomFlowers / 3 ){
                bgL = rand.nextInt(800) ;
                bgH = randomLength + bgL;
            }
            // Sommerblumen
            else   if (i <= numberOfRandomFlowers / 3 * 2 ){
                bgL = rand.nextInt(400) + 900d;
                bgH = randomLength + bgL;
            }
            //Herbstblumen
            else {
                bgL = rand.nextInt(400) +  800;
                bgH = randomLength + bgL;
            }
            bluehgrenzen = new RestrictedDouble(bgL, bgH);
            double bluehintensitaet = rand.nextDouble() * (1.0/15);
            double bestaeubungswahrscheinlichkeit = Math.min(0.5,rand.nextDouble()) * (1.0 / (bgH - bgL));
//            this.flowers.add(new FlowerPopulation(String.valueOf(i), wuchskraft,vermehrungsgrenzen,feuchtegrenzen,
//                    bluehgrenzen,bluehintensitaet,bestaeubungswahrscheinlichkeit));
            if(i == 0){
                System.out.println("Vermehrungsgrenzen: " + vermehrungsgrenzen.getMin() + " - " + vermehrungsgrenzen.getMax() + "");
                System.out.println("Feuchtegrenzebezug: " + feuchtegrenzen.getMin() + " - " + feuchtegrenzen.getMax() + "");
                System.out.println("Bluegrenhzenezug: " + bluehgrenzen.getMin() + " - " + bluehgrenzen.getMax() + "");
            }

        }
    }

    //Erstellt eine Kopie einer Instanz des Klasse TotalFlowerpopulation
    public TotalFlowerPopulation(TotalFlowerPopulation other) {
        this.flowers = new ArrayList<>();
        for (FlowerPopulation flower : other.flowers) {
            // Create a NEW FlowerPopulation for each one
            //this.flowers.add(new FlowerPopulation(flower));
        }
    }

    //Gibt das gesamte Nahrungsangebot der verschiedenen Pflanzenpopulationen zurück
    public double getNahrungsAngebot(){
        double nahrungsAngebot=0;
        for (FlowerPopulation fp : flowers){
            nahrungsAngebot += fp.getNahrungsangebot();
        }
        return nahrungsAngebot;
    }

    //Erstellt aus den verschiedenen Eigenschaften des Objekts einen String.
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

    //Gibt eine Übersicht über die Werte aller Pflanzen zurück.
    public String GetAverages(){
        if(flowers.isEmpty()) return "";
        var sB = new StringBuilder();
        sB.append("Feuchtigkeit: ").append(String.format("%.2f", flowers.getFirst().feuchtegrenzen.getValue()));
        sB.append("\nBlühgrenze: ").append(String.format("%.2f", flowers.getFirst().bluehgrenzen.getValue()));
        sB.append("\n");
        double minWuchskraft = 0;
        double avgWuchskraft = 0;
        double maxWuchskraft = 0;
        double minInBluete = 0;
        double avgInBluete = 0;
        double maxInBluete = 0;
        double minSamenqualitaet = 0;
        double avgSamenqualitaet = 0;
        double maxSamenqualitaet = 0;

        for(var flower : flowers){
            if(flower.getWuchskraft() < minWuchskraft)
                minWuchskraft = flower.getWuchskraft();
            else if (flower.getWuchskraft() > maxWuchskraft)
                maxWuchskraft = flower.getWuchskraft();
            avgWuchskraft += flower.getWuchskraft();

            if(flower.getProzentInBluete() < minInBluete)
                minInBluete = flower.getProzentInBluete();
            else if (flower.getProzentInBluete() > maxInBluete)
                maxInBluete = flower.getProzentInBluete();
            avgInBluete += flower.getProzentInBluete();

            if(flower.getSamenqualitaet() < minSamenqualitaet)
                minSamenqualitaet = flower.getSamenqualitaet();
            else if (flower.getSamenqualitaet() > maxSamenqualitaet)
                maxSamenqualitaet = flower.getSamenqualitaet();
            avgSamenqualitaet += flower.getSamenqualitaet();
        }
        avgWuchskraft /= flowers.size();
        avgInBluete /= flowers.size();
        avgSamenqualitaet /= flowers.size();

        sB.append("Wuchskraft: {min: ").append(String.format("%.2f,", minWuchskraft));
        sB.append(" avg: ").append(String.format("%.2f,", avgWuchskraft));
        sB.append(" max: ").append(String.format("%.2f", maxWuchskraft));
        sB.append("}\n");

        sB.append("InBlüte: {min: ").append(String.format("%.2f,", minInBluete));
        sB.append(" avg: ").append(String.format("%.2f,", avgInBluete));
        sB.append(" max: ").append(String.format("%.2f", maxInBluete));
        sB.append("}\n");

        sB.append("Samenqualität: {min: ").append(String.format("%.2f,", minSamenqualitaet));
        sB.append(" avg: ").append(String.format("%.2f,", avgSamenqualitaet));
        sB.append(" max: ").append(String.format("%.2f", maxSamenqualitaet));
        sB.append("}\n");

        return sB.toString();
    }

    @Override
    public String toString() {
        var sB = new StringBuilder();
        for(var flower : flowers){
            sB.append(flower);
            sB.append('\n');
        }
        return sB.toString();
    }



    //Gibt die Parameter der verschiedenen Pflanzen aus.
    public void printStartingParameters(){
        for(var flower : flowers){
            System.out.print(flower.printParameters());
        }
        System.out.println();
    }

    //Führt Berechnungen für die Simulation eines Tages für alle Pflanzenpopulationen durch
    public void Tagessimulation(double groundMoisture, int sunshineHours, double beePopulation, double nahrungsangebot, boolean isRuhePhase) {
//        System.out.println("sunshinehours: " +sunshineHours);
        for (FlowerPopulation fp : flowers) {
            //fp.Tagessimulation(groundMoisture,sunshineHours,beePopulation,nahrungsangebot, isRuhePhase);
        }
        //System.out.println(flowers.getFirst());
    }
}
