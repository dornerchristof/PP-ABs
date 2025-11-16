import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
/**
 * Christof:
 * - Definition der Untertypen beziehungen
 * - interfaces: Observation, Pollinator, Wasp.
 * - Klassen: FlowerFly, ObservationIterator
 * Patrick:
 * - interfaces: SocialBee, WildBee, SolitaryBee, CommunalBee
 * - Klassen: Bumblebee, HoneyBee, BeeIterator
 * Tobias:
 * - Testen der Untertyp beziehungen
 * - interfaces: Bee
 * - Klassen: LasioglossumCalceatum, OsmiaCornuta, AndrenaBucephala, BeeIterator
 * */


/*
Schwebfliegen gehören zu den Fliegen und sind damit nicht mit den Wespen verwandt. Daher ist FlowerFly
nicht Untertyp von Wasp und genauso ist Wasp nicht Untertyp von FlowerFly.
Da Bee ein Untertyp von Wasp ist, aber FlowerFly kein Untertyp von Wasp ist, ist FlowerFly auch kein Untertyp von Bee.
(Sonst wäre FlowerFly auch eine Wasp). Aus dem gleichen Grund ist FlowerFly auch kein Untertyp von allen
Untertypen von Bee.
Da keine Biene eine Schwebfliege ist, ist weder Bee noch einer ihrer Untertypen ein Untertyp von FlowerFly.

Wasp ist kein Untertyp von Pollinator, da viele Wespenarten keine Bestäuber sind und da nicht alle Pollinator
Wespen sind (z. B. FlowerFly) ist auch Pollinator kein Untertyp von Wasp.

Da nicht alle sozialen Bienen wild sind (Honigbienen), ist SocialBee kein Untertyp von WildBee. Und daraus folgt,
dass SocialBee auch kein Untertyp von allen Untertypen von WildBee ist, weil sonst SocialBee auch eine Wildbiene wäre (Transitivität).
Da nicht alle wilden Bienen sozial sind (AndrenaBucephala, OsmiaCornuta), ist WildBee kein Untertyp von SocialBee und
somit ist WildBee auch kein Untertyp von allen Untertypen von SocialBee.

Da alle Arten (OsmiaConuta, AndrenaBucephala, LasioglossumCalceatum, HoneyBee, BumbleBee, FlowerFly) biologisch
unterschieden werden, gibt es keine Untertypbeziehungen zwischen diesen Typen.

Da BumbleBees nicht solitär leben, ist BumbleBee kein Untertyp von SolitaryBee oder von dessen Untertypen.
Da HoneyBees weder wild leben noch solitär, ist es kein Untertyp von WildBee.
Da LasioglossumCalceatum nicht kommunal lebt, ist LasioglossumCalceatum kein Untertyp von CommunalBee.
Da AndrenaBucephala nicht sozial lebt, ist AndrenaBucephala kein Untertyp von SocialBee.
Da OsmiaCornuta nicht kommunal oder sozial lebt, ist OsmiaCornuta kein Untertyp von CommunalBee oder von SocialBee.
*/


public class Test {

    public static List<Observation> observations = new LinkedList<>();

    public static void addObservation(Observation observation){
        int i =0;
        while(i < observations.size() && observations.get(i).getDate().before(observation.getDate())){
            i++;
        }
        observations.add(i, observation);
    }

    public static void main(String[] args) throws ParseException {
        //Erstelle objekte aller Typen:
        var timeFormat = new SimpleDateFormat("yyyy-MM-dd");
        // FLowerFly:
        Observation ff1 = new FlowerFly(timeFormat.parse("2025-05-10"), "Schwebfliege an Lavendel");
        Test.addObservation(ff1);
        Observation ff2 = new FlowerFly(timeFormat.parse("2025-06-15"), "Schwebfliege an Sonnenblume");
        Test.addObservation(ff2);
        Test.addObservation(new FlowerFly(timeFormat.parse("2025-07-20"), "Schwebfliege an Margerite"));
        Test.addObservation(new FlowerFly(timeFormat.parse("2025-08-05"), "Schwebfliege an Klee"));
        Test.addObservation(new FlowerFly(timeFormat.parse("2025-09-12"), "Schwebfliege an Astern"));
        // OsmiaCornuta:
        Observation osmia1_1 = new OsmiaCornuta("Osmia Cornuta an Kirschblüte", timeFormat.parse("2025-04-05"));
        Test.addObservation(osmia1_1);
        Test.addObservation(new OsmiaCornuta("Osmia Cornuta nochmal gesehen", timeFormat.parse("2025-04-10"), (Bee) osmia1_1));

        Observation osmia2_1 = new OsmiaCornuta("Osmia beim Nistplatz", timeFormat.parse("2025-04-12"));
        Test.addObservation(osmia2_1);
        Observation osmia2_2 = new OsmiaCornuta("Osmia sammelt Pollen", timeFormat.parse("2025-04-15"), 102, (Bee) osmia2_1);
        Test.addObservation(osmia2_2);
        Observation osmia2_3 = new OsmiaCornuta("Osmia an Apfelblüte", timeFormat.parse("2025-04-18"), (Bee) osmia2_2);
        Test.addObservation(osmia2_3);
        // LasioglossumCalceatum:
        Observation lasio1_1 = new LasioglossumCalceatum("Lasioglossum bei Kolonie", timeFormat.parse("2025-05-05"), false, 201);
        Test.addObservation(lasio1_1);
        Test.addObservation(new LasioglossumCalceatum("Lasioglossum zurück zur Kolonie", timeFormat.parse("2025-05-08"), false, 201, (Bee) lasio1_1));

        Observation lasio2_1 = new LasioglossumCalceatum("Lasioglossum allein", timeFormat.parse("2025-05-12"), true, 202);
        Test.addObservation(lasio2_1);
        Observation lasio2_2 = new LasioglossumCalceatum("Lasioglossum gräbt Nest", timeFormat.parse("2025-05-15"), true, 202, (Bee) lasio2_1);
        Test.addObservation(lasio2_2);
        Test.addObservation(new LasioglossumCalceatum("Lasioglossum am Nest", timeFormat.parse("2025-05-20"), true, 202, (Bee) lasio2_2));

        // AndrenaBucephala Observations:
        Observation andrena1_1 = new AndrenaBucephala("Andrena bei Gemeinschaftsnest", timeFormat.parse("2025-04-20"), false, 301);
        Test.addObservation(andrena1_1);
        Test.addObservation(new AndrenaBucephala("Andrena zurück zum Nest", timeFormat.parse("2025-04-25"), false, 301, (Bee) andrena1_1));

        Observation andrena2_1 = new AndrenaBucephala("Andrena alleine unterwegs", timeFormat.parse("2025-04-28"), true, 302);
        Test.addObservation(andrena2_1);
        Observation andrena2_2 = new AndrenaBucephala("Andrena an Weidenblüte", timeFormat.parse("2025-05-02"), true, 302, (Bee) andrena2_1);
        Test.addObservation(andrena2_2);
        Test.addObservation(new AndrenaBucephala("Andrena sammelt Pollen", timeFormat.parse("2025-05-06"), true, 302, (Bee) andrena2_2));

        // HoneyBee:
        Observation honey1_1 = new HoneyBee("Honigbiene am Bienenstock", timeFormat.parse("2025-06-01"), 401);
        Test.addObservation(honey1_1);
        Test.addObservation(new HoneyBee("Honigbiene sammelt Nektar", timeFormat.parse("2025-06-05"), 401, (Bee) honey1_1));

        Observation honey2_1 = new HoneyBee("Honigbiene an Rapsblüte", timeFormat.parse("2025-06-08"), 402);
        Test.addObservation(honey2_1);
        Observation honey2_2 = new HoneyBee("Honigbiene auf Rückflug", timeFormat.parse("2025-06-10"), 402, (Bee) honey2_1);
        Test.addObservation(honey2_2);
        Test.addObservation(new HoneyBee("Honigbiene beim Schwänzeltanz", timeFormat.parse("2025-06-12"), 402, (Bee) honey2_2));

        // BumbleBee:
        Observation bumble1_1 = new BumbleBee("Hummel in freier Wildbahn", timeFormat.parse("2025-05-25"), false);
        Test.addObservation(bumble1_1);
        Test.addObservation(new BumbleBee("Hummel an Klatschmohn", timeFormat.parse("2025-05-28"), false, (Bee) bumble1_1));

        Observation bumble2_1 = new BumbleBee("Hummel aus Zucht", timeFormat.parse("2025-06-02"), true, 502);
        Test.addObservation(bumble2_1);
        Observation bumble2_2 = new BumbleBee("Hummel im Gewächshaus", timeFormat.parse("2025-06-06"), true, 502);
        Test.addObservation(bumble2_2);
        Test.addObservation(new BumbleBee("Hummel bestäubt Tomaten", timeFormat.parse("2025-06-10"), true, 502, (Bee) bumble2_1));

        System.out.println("======Test sameBee()=======");

        // Teste ein paar sameBee() Iteratoren
        System.out.println("Test sameBee() für Osmia 2_2:");
        var osmiaIterator = ((Bee) osmia2_1).sameBee();
        while (osmiaIterator.hasNext()) {
            Bee b = osmiaIterator.next();
            System.out.println("Gefunden: tag " + b.getTagNumber() + " am " + b.getDate());
        }

        System.out.println("Test für BumbleBee 1_1:");
        var bumblebee1Iterator = ((Bee) bumble1_1).sameBee();
        while (bumblebee1Iterator.hasNext()) {
            Bee b = bumblebee1Iterator.next();
            System.out.println("Gefunden: tag " + b.getTagNumber() + " am " + b.getDate());
        }

        System.out.println("Test für LasioglossumCalceatum 1_1:");
        var lasioIterator = ((Bee) lasio1_1).sameBee();
        while (lasioIterator.hasNext()) {
            Bee b = lasioIterator.next();
            System.out.println("Gefunden: tag " + b.getTagNumber() + " am " + b.getDate());
        }

        System.out.println("Test für AndrenaBucephala 1_1:");
        var andrenaIterator = ((Bee) andrena1_1).sameBee();
        while (andrenaIterator.hasNext()) {
            Bee b = andrenaIterator.next();
            System.out.println("Gefunden: tag " + b.getTagNumber() + " am " + b.getDate());
        }

        System.out.println("Test für Honeybee 1_1:");
        var honeyBeeIterator = ((Bee) honey1_1).sameBee();
        while (honeyBeeIterator.hasNext()) {
            Bee b = honeyBeeIterator.next();
            System.out.println("Gefunden: tag " + b.getTagNumber() + " am " + b.getDate());
        }
        System.out.println("======Test solitary()=======");
        System.out.println("Test für AndrenaBucephala 2_1:");
        var andrenaSolitary = ((SolitaryBee) andrena2_1).solitary();
        while (andrenaSolitary.hasNext()) {
            Bee b = andrenaSolitary.next();
            System.out.println("Gefunden: tag " + b.getTagNumber() + " am " + b.getDate());
        }
        System.out.println("Test für OsmiaCornuta 1_1:");
        var osmiaSolitary = ((SolitaryBee) osmia1_1).solitary();
        while (osmiaSolitary.hasNext()) {
            Bee b = osmiaSolitary.next();
            System.out.println("Gefunden: tag " + b.getTagNumber() + " am " + b.getDate());
        }
        System.out.println("Test für Lasio 1_1:");
        var lasioSolitary = ((SolitaryBee) lasio1_1).solitary();
        while (lasioSolitary.hasNext()) {
            Bee b = lasioSolitary.next();
            System.out.println("Gefunden: tag " + b.getTagNumber() + " am " + b.getDate());
        }

        System.out.println("======Test comunal()=======");
        System.out.println("Test für Lasio 1_1:");
        var andrenaComunal = ((CommunalBee) andrena1_1).communal();
        while (andrenaComunal.hasNext()) {
            Bee b = andrenaComunal.next();
            System.out.println("Gefunden: tag " + b.getTagNumber() + " am " + b.getDate());
        }

        System.out.println("======Test social()=======");
        System.out.println("Test für Lasio 1_1:");
        var lasioSocial = ((SocialBee) lasio1_1).social();
        while (lasioSocial.hasNext()) {
            Bee b = lasioSocial.next();
            System.out.println("Gefunden: tag " + b.getTagNumber() + " am " + b.getDate());
        }
        System.out.println("Test für BumbleBee 1_1:");
        var bumbleSocial = ((SocialBee) bumble1_1).social();
        while (bumbleSocial.hasNext()) {
            Bee b = bumbleSocial.next();
            System.out.println("Gefunden: tag " + b.getTagNumber() + " am " + b.getDate());
        }
        System.out.println("Test für Honeybee 1_1:");
        var honeySocial = ((SocialBee) honey1_1).social();
        while (honeySocial.hasNext()) {
            Bee b = honeySocial.next();
            System.out.println("Gefunden: tag " + b.getTagNumber() + " am " + b.getDate());
        }
        System.out.println("======Test wild()=======");
        System.out.println("Test für OsmiaCornuta 1_1:");
        var  osmiaWild = ((WildBee) osmia1_1).wild(false);
        while (osmiaWild.hasNext()) {
            Bee b = osmiaWild.next();
            System.out.println("Gefunden: tag " + b.getTagNumber() + " am " + b.getDate());
        }

        System.out.println("Test für Lasio 1_1:");
        var  lasioWild = ((WildBee) lasio1_1).wild(false);
        while (lasioWild.hasNext()) {
            Bee b = lasioWild.next();
            System.out.println("Gefunden: tag " + b.getTagNumber() + " am " + b.getDate());
        }

        System.out.println("Test für BumbleBee 1_1:");
        var  bumbleWild = ((WildBee) bumble1_1).wild(false);
        while (bumbleWild.hasNext()) {
            Bee b = bumbleWild.next();
            System.out.println("Gefunden: tag " + b.getTagNumber() + " am " + b.getDate());
        }

        System.out.println("Test für Andrena 1_1:");
        var  andrenaWild = ((WildBee) andrena1_1).wild(false);
        while (andrenaWild.hasNext()) {
            Bee b = andrenaWild.next();
            System.out.println("Gefunden: tag " + b.getTagNumber() + " am " + b.getDate());
        }



//            //Observation-Iterator-Tests
//            var dates = new String[]{"2025-10-1", "2025-10-2", "2025-10-3", "2025-04-1", "2025-04-2", "2025-11-23"};
//            var observationList = new LinkedList<Observation>();
//            Observation prev = null;
//            for (String date : dates) {
//                prev = new OsmiaCornuta("obs.. " + date, timeFormat.parse(date));
//                observationList.add(prev);
//                addObservation(prev);
//            }
//            //Testen, ob der Vorherige gefunden wird.
//            for (Observation observation : observationList) {
//                var i = observation.earlier();
//                if (i.hasNext())
//                    System.out.println(observation.getDate() + " before " + i.next().getDate());
//            }
//            //Testen, ob der Nachfolger gefunden wird.
//            for (Observation observation : observationList) {
//                var i = observation.later();
//                if (i.hasNext())
//                    System.out.println(observation.getDate() + " after " + i.next().getDate());
//            }


    }
}