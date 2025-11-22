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

        // Teil 5
        System.out.println("\nTeil 5\n");

        System.out.println("=== 1. Test der Domänen-Objekte und Modifiable Schnittstelle ===");

        // 1.1 Num Test
        Num n1 = new Num(10);
        Num n2 = new Num(5);
        System.out.println("Num Original: " + n1);
        System.out.println("Num Add (10+5): " + n1.add(n2));
        System.out.println("Num Subtract (10-5): " + n1.subtract(n2));

        // 1.2 WildBee Test
        WildBee wb = new WildBee("WildBiene A", 20);
        System.out.println("WildBee Original: " + wb + ", Länge: " + wb.length());
        System.out.println("WildBee Add (Length + 5): " + wb.add(5));
        System.out.println("WildBee Subtract (Length - 5): " + wb.subtract(5));

        // 1.3 HoneyBee Test
        HoneyBee hb = new HoneyBee("HonigBiene B", "Kärntner");
        System.out.println("HoneyBee Original: " + hb + ", Sort: " + hb.sort());
        System.out.println("HoneyBee Add (' Landrasse'): " + hb.add(" Landrasse"));
        System.out.println("HoneyBee Subtract ('Kärntner'): " + hb.subtract("Kärntner"));

        System.out.println("\n=== 2. Initialisierung der Container ===");

        // Elemente vorbereiten
        Num[] nums2 = { new Num(1), new Num(2), new Num(3), new Num(4) };
        Bee[] bees2 = { new Bee("Bee1"), new Bee("Bee2"), new Bee("Bee3") };
        WildBee[] wbees = { new WildBee("WB1", 10), new WildBee("WB2", 12), new WildBee("WB3", 14) };
        HoneyBee[] hbees = { new HoneyBee("HB1", "A"), new HoneyBee("HB2", "B"), new HoneyBee("HB3", "C") };

        // Container Instanziierung (wie in der Anforderung benannt)
        ISet<Num> isetnum2 = new ISet<>(null);
        OSet<Num> osetnum2 = new OSet<>(null);
        MSet<Num,Num> msetnum2 = new MSet<>(null);

        ISet<Bee> isetbee2 = new ISet<>(null);
        OSet<Bee> osetbee2 = new OSet<>(null);

        ISet<WildBee> isetwildbee2 = new ISet<>(null);
        OSet<WildBee> osetwildbee2 = new OSet<>(null);
        MSet<WildBee,Integer> msetwildbee2 = new MSet<>(null);

        ISet<HoneyBee> isethoneybee2 = new ISet<>(null);
        OSet<HoneyBee> osethoneybee2 = new OSet<>(null);
        MSet<HoneyBee,String> msethoneybee2 = new MSet<>(null);

        // Daten befüllen und Relationen setzen
        fillSet(isetnum2, nums2);
        fillSet(osetnum2, nums2);
        fillSet(msetnum2, nums2);

        fillSet(isetbee2, bees2);
        fillSet(osetbee2, bees2);

        fillSet(isetwildbee2, wbees);
        fillSet(osetwildbee2, wbees);
        fillSet(msetwildbee2, wbees);

        fillSet(isethoneybee2, hbees);
        fillSet(osethoneybee2, hbees);
        fillSet(msethoneybee2, hbees);

        System.out.println("Sets initialisiert und befüllt.");

        System.out.println("\n=== 3. Test spezifischer Set-Methoden ===");

        // 3.1 ISet: before (Iterator)
        System.out.println("-- ISet.before (Iterator) --");
        Iterator<Num> it = isetnum2.before(nums2[0], nums2[2]); // Pfad 0->1->2 existiert durch fillSet
        if (it != null) {
            while(it.hasNext()) {
                System.out.println("Pfad Element: " + it.next());
            }
        } else {
            System.out.println("Kein Pfad gefunden (Erwartet falls fillSet keine Pfade setzt)");
        }

        // 3.2 OSet: before (View & ModifiableOrdered)
        System.out.println("-- OSet.before (View) --");
        var view = osetnum2.before(nums2[0], nums2[2]);
        if (view != null) {
            System.out.println("View erhalten.");
            // Test view modification
            Num extra = new Num(99);
            var newView = view.add(extra);
            System.out.println("View add ausgeführt (Test der Rückgabe).");
        }

        // 3.3 MSet: plus / minus
        System.out.println("-- MSet Operations --");
        System.out.println("MSet vor plus: " + msetnum2);
        msetnum2.plus(new Num(100));
        System.out.println("MSet nach plus(100): " + msetnum2);
        msetnum2.minus(new Num(50));
        System.out.println("MSet nach minus(50): " + msetnum2);

        System.out.println("\n=== 4. Ausführung der Check-Kombinationen ===");
        // Wir verwenden Hilfsmethoden, um Exceptions abzufangen

        // -- NUM --
        System.out.println("--- Num Checks ---");
        safeCheck(isetnum2, isetnum2, "isetnum.check(isetnum)");
        safeCheck(isetnum2, osetnum2, "isetnum.check(osetnum)");
        safeCheckForced(isetnum2, msetnum2, "isetnum.checkForced(msetnum)");

        safeCheck(osetnum2, isetnum2, "osetnum.check(isetnum)");
        safeCheck(osetnum2, osetnum2, "osetnum.check(osetnum)");
        safeCheck(osetnum2, msetnum2, "osetnum.check(msetnum)");

        safeCheck(msetnum2, isetnum2, "msetnum.check(isetnum)");
        safeCheck(msetnum2, osetnum2, "msetnum.check(osetnum)");
        safeCheck(msetnum2, msetnum2, "msetnum.check(msetnum)");

        // -- BEE --
        System.out.println("--- Bee Checks ---");
        safeCheck(isetbee2, isetbee2, "a1.check(a1)");
        safeCheck(isetbee2, osetbee2, "a1.check(a2)");
        // a2 Check (nicht in Liste, aber der Vollständigkeit halber aus dem Code-Block)
        // a2.check(a1); a2.check(a2);

        // -- WILDBEE --
        System.out.println("--- WildBee Checks ---");
        safeCheck(isetwildbee2, isetwildbee2, "isetwildbee.check(isetwildbee)");
        safeCheck(isetwildbee2, osetwildbee2, "isetwildbee.check(c1)"); // c1 ist OSet<WildBee>
        safeCheckForced(isetwildbee2, msetwildbee2, "isetwildbee.checkForced(b1)"); // b1 ist MSet

        safeCheck(osetwildbee2, isetwildbee2, "c1.check(isetwildbee)");
        safeCheck(osetwildbee2, osetwildbee2, "c1.check(c1)");
        safeCheck(osetwildbee2, msetwildbee2, "c1.check(b1)");

        safeCheck(msetwildbee2, isetwildbee2, "b1.check(isetwildbee)");
        safeCheck(msetwildbee2, osetwildbee2, "b1.check(c1)");
        safeCheck(msetwildbee2, msetwildbee2, "b1.check(b1)");

        // -- HONEYBEE --
        System.out.println("--- HoneyBee Checks ---");
        safeCheck(isethoneybee2, isethoneybee2, "c2.check(c2)");
        safeCheck(isethoneybee2, osethoneybee2, "c2.check(osethoneybee)");
        safeCheckForced(isethoneybee2, msethoneybee2, "c2.checkForced(b2)");

        safeCheck(osethoneybee2, isethoneybee2, "osethoneybee.check(c2)");
        safeCheck(osethoneybee2, osethoneybee2, "osethoneybee.check(osethoneybee)");
        safeCheck(osethoneybee2, msethoneybee2, "osethoneybee.check(b2)");

        safeCheck(msethoneybee2, isethoneybee2, "b2.check(c2)");
        safeCheck(msethoneybee2, osethoneybee2, "b2.check(osethoneybee)");
        safeCheck(msethoneybee2, msethoneybee2, "b2.check(b2)");

        // -- CROSS CHECKS (WildBee -> Bee) --
        System.out.println("--- Cross Checks (WildBee -> Bee) ---");
        safeCheck(isetwildbee2, isetbee2, "isetwildbee.check(a1)");
        safeCheck(isetwildbee2, osetbee2, "isetwildbee.check(a2)");

        safeCheck(osetwildbee2, isetbee2, "c1.check(a1)");
        safeCheck(osetwildbee2, osetbee2, "c1.check(a2)");

        safeCheck(msetwildbee2, isetbee2, "b1.check(a1)");
        safeCheck(msetwildbee2, osetbee2, "b1.check(a2)");

        // -- CROSS CHECKS (HoneyBee -> Bee) --
        System.out.println("--- Cross Checks (HoneyBee -> Bee) ---");
        safeCheck(isethoneybee2, isetbee2, "c2.check(a1)");
        safeCheck(isethoneybee2, osetbee2, "c2.check(a2)");

        safeCheck(osethoneybee2, isetbee2, "osethoneybee.check(a1)");
        safeCheck(osethoneybee2, osetbee2, "osethoneybee.check(a2)");

        safeCheck(msethoneybee2, isetbee2, "b2.check(a1)");
        safeCheck(msethoneybee2, osetbee2, "b2.check(a2)");

        System.out.println("\nTests abgeschlossen.");
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
    // Hilfsmethode zum Befüllen und Verknüpfen der Sets für Tests
    private static <E> void fillSet(Set<E> set, E[] items) {
        if (items.length < 2) return;
        // Erzeugt eine einfache Kette: items[0] -> items[1] -> items[2] ...
        for (int i = 0; i < items.length - 1; i++) {
            try {
                set.setBefore(items[i], items[i+1]);
            } catch (IllegalArgumentException e) {
                // Ignorieren, falls schon gesetzt
            }
        }
    }

    // Hilfsmethode für check (fängt Exceptions ab)
    private static <E> void safeCheck(OrdSet<E, ?> subject, Ordered<? super E, ?> criteria, String desc) {
        System.out.print("Test " + desc + ": ");
        try {
            subject.check(criteria);
            System.out.println("OK");
        } catch (Exception e) {
            System.out.println("FAIL (" + e.getMessage() + ")");
        }
    }

    // Hilfsmethode für checkForced
    private static <E> void safeCheckForced(OrdSet<E, ?> subject, Ordered<? super E, ?> criteria, String desc) {
        System.out.print("Test " + desc + ": ");
        try {
            subject.checkForced(criteria);
            System.out.println("OK (Forced)");
        } catch (Exception e) {
            System.out.println("FAIL (" + e.getMessage() + ")");
        }
    }

}
