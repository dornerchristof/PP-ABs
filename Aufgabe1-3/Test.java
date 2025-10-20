//Patrick - Simulation der Pflanzen
//Tobias - Simulation der Bienen + Parameterfinden
//Christof - Simulationsklasse und Ausgabe


import java.util.Random;

public class Test {
    public static void main(String[] args) {

        //initDaten
        //Liste an Blumen
        //Bienenpopulation
        // Simulation 1:
        Random beeRandom1 = new Random(67246021);
        var flowers1 = new TotalFlowerPopulation(21, 1000, 5000, 4.5, 13, 0.3,0.7,394234234);
        var bees1 = new BeePopulation(800, beeRandom1);
        var sim1 = new Simulation(flowers1, bees1, 897640);
        sim1.simulate(10, 25, false );


        //Simulation 2:
        Random beeRandom2 = new Random(253721312);
        var flowers2 = new TotalFlowerPopulation(16, 1000, 6000, 7, 14, 0.28,0.67,54456787);
        var bees2 = new BeePopulation(2000, beeRandom2); // BeeRandom runs through all runs so every run is different
        var sim2 = new Simulation(flowers2, bees2, 253721312); //Seed runs through all runs so every run is different
        sim2.simulate(10, 25, false);

//        // Simulation 3:
        Random beeRandom3 = new Random(4236472);
        var flowers3 = new TotalFlowerPopulation(24, 1000, 1500, 7, 11, 0.33,0.75,798246);
        var bees3 = new BeePopulation(1700, beeRandom3);
        var sim3 = new Simulation(flowers3, bees3, 64782347);
        sim3.simulate(10, 25, false);

        System.out.println("DebugInfos (genau Infos)");
        sim1.printDebugInfos();
        sim2.printDebugInfos();
        sim3.printDebugInfos();
    }
}