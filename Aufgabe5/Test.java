import java.util.Iterator;

//Tobias: Modifiable, Ordere, OrdSet, OSet
//Patrick: Tests, HoneyBee, WildBee, Num
//Christof: ISet, MSet, OrdSet
public class Test {
    static void main(String[] args) {
        Ordered<WildBee, Iterator<WildBee>> wildBeeOrder = new ISet<WildBee>(null);
        WildBee[] wildBees = new WildBee[10];
        for (int i = 0; i < wildBees.length; i++) {
            wildBees[i] = new WildBee("WildBee " + i, i);
            if (i > 0)
                wildBeeOrder.setBefore(wildBees[i - 1], wildBees[i]);

        }
        ISet<WildBee> beeSet =new ISet<>(wildBeeOrder);
        beeSet.setBefore(wildBees[0], wildBees[1]);
        beeSet.setBefore(wildBees[1], wildBees[2]);
        beeSet.setBefore(wildBees[2], wildBees[3]);
        beeSet.setBefore(wildBees[3], wildBees[4]);
        var iter = beeSet.before(wildBees[0], wildBees[4]);//Transitivität
        while (iter.hasNext()) {
            var e = iter.next();
            System.out.println(e);

        }
        try {
            beeSet.setBefore(wildBees[1], wildBees[0]);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception caught:");
        }
        try {
            beeSet.setBefore(wildBees[5], wildBees[0]);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception caught:");
        }
        System.out.println("Aufgabe 5:");
    }
}
