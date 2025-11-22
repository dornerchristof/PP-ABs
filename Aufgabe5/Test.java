import java.util.Iterator;

//Tobias: Modifiable, Ordere, OrdSet, OSet
//Patrick: Tests, HoneyBee, WildBee, Num
//Christof: ISet, MSet, OrdSet
public class Test {
    static void main(String[] args) {
        Ordered<WildBee, Iterator<WildBee>> wildBeeOrder = new ISet<WildBee>(null);
        WildBee[] wildBees = new WildBee[10];
        for (int i = 0; i < wildBees.length; i++) {
            wildBees[i] = new WildBee("Bee " + i, i);
            if (i > 0)
                wildBeeOrder.setBefore(wildBees[i - 1], wildBees[i]);

        }
        ISet<WildBee> beeSet =new ISet<>(wildBeeOrder);
        beeSet.setBefore(wildBees[0], wildBees[1]);
        beeSet.setBefore(wildBees[0], wildBees[4]);//Transitivität
        try {
            beeSet.setBefore(wildBees[1], wildBees[0]);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception caught:");
        }
        System.out.println("Aufgabe 5:");
    }
}
