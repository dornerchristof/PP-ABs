//Patrick - Simulation der Pflanzen
//Tobias - Simulation der Bienen + Parameterfinden
//Christof - Simulationsklasse und Ausgabe


import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {

        //initDaten
        //Liste an Blumen
        //Bienenpopulation
        var flowers1 = new ArrayList<FlowerPopulation>();
        var bees1 = new BeePopulation();
        var sim1 = new Simulation(25, flowers1, bees1);
        System.out.println("Parameter");
        sim1.simulate();

        var flowers2 = new ArrayList<FlowerPopulation>();
        var bees2 = new BeePopulation();
        var sim2 = new Simulation(25, flowers2, bees2);
        System.out.println("Parameter");
        sim2.simulate();


        System.out.println("DebugInfos (genau Infos)");
    }
}