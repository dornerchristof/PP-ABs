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
        var bees1 = new BeePopulation(1200);
        var sim1 = new Simulation(25, flowers1, bees1);
        System.out.println("Parameter");
        sim1.simulate();


        //Simulation 2:
        var flowers2 = new TotalFlowerPopulation();
        var bees2 = new BeePopulation(900);
        var sim2 = new Simulation(25, flowers2, bees2);
        System.out.println("Parameter");
        sim2.simulate();

        // Simulation 3:
        var flowers3 = new TotalFlowerPopulation();
        var bees3 = new BeePopulation(2000);
        var sim3 = new Simulation(25, flowers3, bees3);
        System.out.println("Parameter");
        sim3.simulate();

        System.out.println("DebugInfos (genau Infos)");
    }
}