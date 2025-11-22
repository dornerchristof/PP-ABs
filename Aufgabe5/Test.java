import java.util.Iterator;

//Tobias: Modifiable, Ordere, OrdSet, OSet
//Patrick: Tests, HoneyBee, WildBee, Num
//Christof: ISet, MSet, OrdSet
public class Test {
    public static void main(String[] args) {

        // Order festlegen
        Ordered<WildBee, Iterator<WildBee>> wildBeeOrder = new ISet<>(null);
        Ordered<Bee, Iterator<Bee>> beeOrder = new ISet<>(null);
        Ordered<HoneyBee, Iterator<HoneyBee>> honeyBeeOrder = new ISet<>(null);
        Ordered<Num, Iterator<Num>> numOrder = new ISet<>(null);

        // Arrays erstellen
        WildBee[] wildBees = new WildBee[9];
        Bee[] bees = new Bee[9];
        HoneyBee[] honeyBees = new HoneyBee[9];
        Num[] integers = new Num[9];

        // Arrays und Order befüllen
        for (int i = 0; i < wildBees.length; i++) {
            wildBees[i] = new WildBee("Bee " + i, i);
            bees[i] = new Bee("Normal Bee " + i *10);
            honeyBees[i] = new HoneyBee("Honey Bee" + i * 100,"aa");
            integers[i] = new Num(i);
            if (i > 0) {
                wildBeeOrder.setBefore(wildBees[i - 1], wildBees[i]);
                beeOrder.setBefore(bees[i - 1], bees[i]);
                honeyBeeOrder.setBefore(honeyBees[i - 1], honeyBees[i]);
                numOrder.setBefore(integers[i - 1], integers[i]);
            };

        }
        ISet<WildBee> beeSet = new ISet<>(wildBeeOrder);
        testISet(wildBees, beeSet);
        System.out.println("Aufgabe 5:");

        /*
          Vorgegebene Objekterzeugung:
        */
        ISet<Num> isetnum = new ISet<>(numOrder);
        ISet<Bee> a1 = new ISet<>(beeOrder);
        ISet<WildBee> isetwildbee = new ISet<>(wildBeeOrder);
        ISet<HoneyBee> c2 = new ISet<>(honeyBeeOrder);

        OSet<Num> osetnum = new OSet<>(null);
        OSet<Bee> a2 = new OSet<>(null);
        OSet<WildBee> c1 = new OSet<>(null);
        OSet<HoneyBee> osethoneybee = new OSet<>(null);

        MSet<Num,Num> msetnum = new MSet<>(null);
        MSet<WildBee,Integer> b1 = new MSet<>(null);
        MSet<HoneyBee,String> b2 = new MSet<>(null);

        isetnum.setBefore(integers[0], integers[4]);
        isetnum.setBefore(integers[1], integers[2]);
        isetnum.setBefore(integers[3], integers[5]);

        a1.setBefore(bees[0], bees[1]);
        a1.setBefore(bees[2], bees[3]);
        a1.setBefore(bees[2], bees[5]);

        isetwildbee.setBefore(wildBees[0], wildBees[4]);
        isetwildbee.setBefore(wildBees[1], wildBees[2]);
        isetwildbee.setBefore(wildBees[3], wildBees[5]);

        c2.setBefore(honeyBees[0], honeyBees[4]);
        c2.setBefore(honeyBees[1], honeyBees[2]);
        c2.setBefore(honeyBees[3], honeyBees[5]);


        copyElements(isetnum,osetnum);
        copyRelations(numOrder, isetnum, osetnum);

        copyElements(a1,a2);
        copyRelations(beeOrder,a1,a2);

        copyElements(isetwildbee,c1);
        copyRelations(wildBeeOrder,isetwildbee,c1);

        copyElements(c2,osethoneybee);
        copyRelations(honeyBeeOrder,c2,osethoneybee);


        Iterator<WildBee> c1it = c1.iterator();
        c1it.forEachRemaining(c -> {
            System.out.println(c.length());
        });
        copyElements(c1,a1);
        copyElements(c1,b1);


        Iterator<HoneyBee> c2it = c2.iterator();
        c2it.forEachRemaining(c -> {
            System.out.println(c.sort());
        });
        copyElements(c2,a2);
        copyElements(c2,b2);

        System.out.println(a1);
    }


    // Kopiert Elemente aus src nach dest, indem aufeinanderfolgende Paare
// gesetzt werden (vermeidet das problematische setBefore(..., null)).
    public static <E> void copyElements(Iterable<? extends E> src,
                                        OrdSet<? super E, ?> dest) {
        Iterator<? extends E> it = src.iterator();
        if (!it.hasNext()) return;
        E prev = it.next();
        while (it.hasNext()) {
            E cur = it.next();
            try {
                dest.setBefore(prev, cur);
            } catch (IllegalArgumentException ex) {
                System.out.println(prev + ", " + cur + ": " + ex.getMessage());
            }
            prev = cur;
        }
    }
    // Kopiert Ordnungsbeziehungen: für alle Paare (x,y) aus elems
    // ruft src.before(x,y) und bei nicht-null dest.setBefore(x,y)
    public static <E> void copyRelations(Ordered<? super E, ?> srcOrder, Iterable<? extends E> elems, OrdSet<? super E, ?> dest) {
        for (E x : elems) {
            for (E y : elems) {
                if (x == y) continue;
                if (srcOrder.before(x, y) != null) {
                    try {
                        dest.setBefore(x, y);
                    } catch (IllegalArgumentException ex) {
                        System.out.println(x + ", " + y + ": " + ex.getMessage());
                    }
                }
            }
        }
    }

    public static <E> void testISet(E[] arr, ISet<E> set) {
        set.setBefore(arr[0], arr[1]);
        set.setBefore(arr[0], arr[4]);//Transitivität
        try {
            set.setBefore(arr[1], arr[0]);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception caught: "+ e.getMessage());
        }


        try {
            set.setBefore(arr[5], arr[0]);
        } catch (IllegalArgumentException e) {
            System.out.println("Exception caught:");
        }
    }

}
