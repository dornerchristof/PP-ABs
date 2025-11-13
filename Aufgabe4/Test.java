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
nicht Untertyp von Wasp und genauso ist deshalb Wasp nicht Untertyp von FlowerFly.
Da Bee ein Untertyp von Wasp ist, aber FlowerFly kein Untertyp von Wasp ist, ist FlowerFly auch kein Untertyp von Bee. (Sonst wäre FlowerFly auch eine Wasp.)
Da Bienen eine Wespe sind und somit nicht mit Fliegen verwandt sind, ist Bee kein Untertyp von FlowerFly.
Aus demselben Grund sind alle Untertypen von Bee (WildBee, SocialBee, SolitaryBee, CommunalBee, AndrenaBucephala,
LasioglossumCalceatum, OsmiaCornuta) keine Untertypen von FlowerFly und FlowerFly keine Untertyp von diesen Typen.

Wasp ist kein Untertyp von Pollinator, da viele Wespenarten keine Bestäuber sind und da nicht alle Pollinator
Wespen sind (z. B. FlowerFly) ist auch Pollinator kein Untertyp von Wasp.

//Wild vs Social
//Solitary vs Social
//Honey Bee
//Bumble Bee
//Osmia Cornuta
//AndrenaBucephala
//LasioglossumCalceatum


Honigbienen und Hummeln sind keine SolitaryBee und keine CommunalBee, da sie nur sozial leben.
Honigbienen sind keine Wildbienen, da sie nur als Nutztiere dienen.

AndrenaBucephala ist nicht Untertyp von SocialBee, da sie nur communal bzw. solitär leben. Da nicht alle sozialen Bienen Andrena
Bucephala sind, ist AndrenaBucephala kein Obertyp von SocialBee.
LasioglossumCalceatum ist keine CommunalBee, da sie generell sozial und nur in kalten Regionen solitär leben.
OsmiaCornuta ist keine CommunalBee oder SocialBee, da sie nur solitär leben.

CommunalBee, SolitaryBee und WildBee sind keine SocialBee, da SocialBee nicht ausschließlich wild leben.
SocialBee sind keine CommunalBee, SolitaryBee oder WildBee, da SocialBee nicht ausschließlich wild leben.
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

        //Observation-Iterator-Tests
        var dates = new String[]{"2025-10-1", "2025-10-2", "2025-10-3", "2025-04-1", "2025-04-2", "2025-11-23"};
        var observationList = new LinkedList<Observation>();
        Observation prev = null;
        var timeFormat =new SimpleDateFormat("yyyy-MM-dd");
        for (String date : dates) {
            prev = new OsmiaCornuta("obs.. " + date, timeFormat.parse(date));
            observationList.add(prev);
        }
        //Testen, ob der Vorherige gefunden wird.
        for(Observation observation : observationList){
            var i = observation.earlier();
            if(i.hasNext())
                System.out.println(observation.getDate() + " before " + i.next().getDate());
        }
        //Testen, ob der Nachfolger gefunden wird.
        for(Observation observation : observationList){
            var i = observation.later();
            if(i.hasNext())
                System.out.println(observation.getDate() + " after " + i.next().getDate());
        }
    }
}