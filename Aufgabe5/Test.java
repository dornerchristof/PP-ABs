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
        ISet<Num> isetnum = new ISet<>(null);
        ISet<Bee> a1 = new ISet<>(null);
        ISet<WildBee> isetwildbee = new ISet<>(null);
        ISet<HoneyBee> c2 = new ISet<>(null);

        OSet<Num> osetnum = new OSet<>(null);
        OSet<Bee> a2 = new OSet<>(null);
        OSet<WildBee> c1 = new OSet<>(null);
        OSet<HoneyBee> osethoneybee = new OSet<>(null);

        MSet<Num,Num> msetnum = new MSet<>(null);
        MSet<WildBee,Integer> b1 = new MSet<>(null);
        MSet<HoneyBee,String> b2 = new MSet<>(null);

        // Elements einfügen
        isetnum.setBefore(integers[0], integers[4]);
        //isetnum.setBefore(integers[4], integers[1]);
        isetnum.setBefore(integers[1], integers[2]);
        //isetnum.setBefore(integers[2], integers[3]);
        isetnum.setBefore(integers[3], integers[5]);

        a1.setBefore(bees[0], bees[1]);
        a1.setBefore(bees[2], bees[3]);
        a1.setBefore(bees[2], bees[5]);
//
        isetwildbee.setBefore(wildBees[0], wildBees[4]);
        isetwildbee.setBefore(wildBees[1], wildBees[2]);
        isetwildbee.setBefore(wildBees[3], wildBees[5]);
//
        c2.setBefore(honeyBees[0], honeyBees[4]);
        c2.setBefore(honeyBees[1], honeyBees[2]);
        c2.setBefore(honeyBees[3], honeyBees[5]);

        System.out.println(isetnum);

        copyElements(isetnum,osetnum);
        //copyRelations(numOrder, isetnum, osetnum);

        copyElements(a1,a2);
        //copyRelations(beeOrder,a1,a2);

        copyElements(isetwildbee,c1);
        //copyRelations(wildBeeOrder,isetwildbee,c1);

        copyElements(c2,osethoneybee);
        //copyRelations(honeyBeeOrder,c2,osethoneybee);
        copyElements(isetnum,msetnum);
        copyElements(isetwildbee,b1);
        copyElements(c2,b2);

        // Teil 2
        System.out.println("\nTeil 2\n");

        Iterator<WildBee> c1it = c1.iterator();
        c1it.forEachRemaining(c -> {
            System.out.println(c.length());
        });
        copyRelations(c1,c1,a1);
        copyRelations(c1,c1,b1);
        //copyElements(c1,a1);
        //copyElements(c1,b1);


        Iterator<HoneyBee> c2it = c2.iterator();
        c2it.forEachRemaining(c -> {
            System.out.println(c.sort());
        });
        copyRelations(c2,c2,a2);
        copyRelations(c2,c2,b2);
        //copyElements(c2,b2);


        // Teil 3
        System.out.println("\nTeil 3\n");
        System.out.println(isetnum);
        System.out.println(osetnum);

        osetnum.check(isetnum);
        osetnum.check(null);
        osetnum.setBefore(integers[4], integers[5]);
        osetnum.checkForced(msetnum);

        // Funktioniert nicht, wie erwartet
        //osetnum.check(b1);

        osethoneybee.checkForced(a2);
        osethoneybee.check(a2);
        c1.check(a1);
        // Funktioniert nicht umgekehrt
        //a1.check(c1);
        //a2.check(osethoneybee);

        // Alle Kombinationen die funktionieren
        // Num
        isetnum.check(isetnum);
        isetnum.check(osetnum);
        isetnum.checkForced(msetnum);

        osetnum.check(isetnum);
        osetnum.check(osetnum);
        osetnum.check(msetnum);

        msetnum.check(isetnum);
        msetnum.check(osetnum);
        msetnum.check(msetnum);

        // Bee
        a1.check(a1);
        a1.checkForced(a2);

        a2.checkForced(a1);
        a2.check(a2);

        // WildBee
        isetwildbee.check(isetwildbee);
        isetwildbee.check(c1);
        isetwildbee.checkForced(b1);

        c1.check(isetwildbee);
        c1.check(c1);
        c1.check(b1);

        b1.check(isetwildbee);
        b1.check(c1);
        b1.check(b1);

        // HoneyBee
        c2.check(c2);
        c2.check(osethoneybee);
        c2.checkForced(b2);

        osethoneybee.check(c2);
        osethoneybee.check(osethoneybee);
        osethoneybee.check(b2);

        b2.check(c2);
        b2.check(osethoneybee);
        b2.check(b2);

        // Cross-Checks: WildBee -> Bee
        isetwildbee.checkForced(a1);
        isetwildbee.check(a2);

        c1.checkForced(a1);
        c1.check(a2);

        b1.checkForced(a1);
        b1.check(a2);

        // Cross-Checks: HoneyBee -> Bee
        c2.checkForced(a1);
        c2.check(a2);

        osethoneybee.checkForced(a1);
        osethoneybee.check(a2);

        b2.checkForced(a1);
        b2.check(a2);

        // Teil 4
        System.out.println("\nTeil 4\n");

        // OSet kann kein Untertyp von MSet sein, da es ein Obertyp ist.
        // OSet kann kein Untertyp von ISet sein, da z. B.: Der Rückgabetyp der Methode before ist bei ISet ein Iterator<E>
        //                                                  und bei OSet ein ModifiableOrdered<E>. Diese beiden Typen
        //                                                  stehen in keiner Beziehung zueinander.
        // ISet kann kein Untertyp von OSet sein, da z. B.: Der Rückgabetyp der Methode before ist bei ISet ein Iterator<E>
        //                                                 und bei OSet ein ModifiableOrdered<E>. Diese beiden Typen
        //                                                 stehen in keiner Beziehung zueinander.

        OSet<Num> test = new MSet<>(null);
        // Funktioniert nicht
        //ISet<WildBee> test2 = new MSet<WildBee, Integer>(null);
        //MSet<WildBee,Integer> test2 = new ISet<>(null);
        //ISet<WildBee> test2 = new OSet<WildBee>(null);
        //OSet<WildBee> test2 = new ISet<WildBee>(null);
    }


    // Kopiert Elemente aus src nach dest, indem aufeinanderfolgende Paare
    // gesetzt werden (vermeidet das problematische setBefore(..., null)).
    public static <E> void copyElements(OrdSet<E,?> src, OrdSet<? super E, ?> dest) {
        Iterator<? extends E> it = src.iterator();
        if (!it.hasNext()) return;
        E prev = it.next();
        while (it.hasNext()) {
            E cur = it.next();
            if (src.before(prev,cur) != null){
                try {
                    dest.setBefore(prev, cur);
                } catch (IllegalArgumentException ex) {
                    System.out.println(prev + ", " + cur + ": " + ex.getMessage());
                }
            }
            prev = cur;
        }
    }
    // Kopiert Ordnungsbeziehungen: für alle Paare (x,y) aus elems
    // ruft src.before(x,y) und bei nicht-null dest.setBefore(x,y)
    public static <E> void copyRelations(Ordered<? super E, ?> srcOrder, Iterable<? extends E> elems, OrdSet<? super E, ?> dest) {
        for (E x : elems) {
            for (E y : elems) {
                //noinspection SuspiciousNameCombination
                if (x !=y && srcOrder.before(x, y) != null && dest.before(x, y) == null && dest.before(y, x) == null) {
                    //System.out.println(x.toString()+ ", "+y + "  =  " + srcOrder.before(x, y));
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
        set.setBefore(arr[1], arr[2]);//Transitivität
        set.setBefore(arr[2], arr[3]);
        set.setBefore(arr[3], arr[4]);
        var it = set.before(arr[0], arr[4]);
        while(it.hasNext()){
            System.out.println(it.next());
        }

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
