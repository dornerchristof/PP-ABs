//Patrick - Simulation der Pflanzen
//Tobias - Simulation der Bienen + Parameterfinden
//Christof - Simulationsklasse und Ausgabe


import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {

        //initDaten
        //Liste an Blumen
        //Bienenpopulation
        // Simulation 1:
        var flowers1 = new TotalFlowerPopulation();
        var bees1 = new BeePopulation(200);
        var sim1 = new Simulation(flowers1, bees1);
        sim1.simulate(1, 25, true);


        //Simulation 2:
        var flowers2 = new TotalFlowerPopulation();
        var bees2 = new BeePopulation(900);
        var sim2 = new Simulation(flowers2, bees2);
        sim2.simulate(10, 25, false);

        // Simulation 3:
        var flowers3 = new TotalFlowerPopulation();
        var bees3 = new BeePopulation(2000);
        var sim3 = new Simulation(flowers3, bees3);
        sim3.simulate(10, 25, false);

        System.out.println("DebugInfos (genau Infos)");
        sim1.printDebugInfos();
    }
}