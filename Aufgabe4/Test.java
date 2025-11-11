import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

//Schwebfliegen gehören zu den Fliegen und sind damit nicht mit den Wespen oder Bienen verwandt. Daher gibt es keine
//Untertypbeziehungen zwischen Schwebfliegen und Wasp und Bee und vice versa.
//Da die Schwebfliege keine Biene ist, kann sie auch keine WildBee, SocialBee, SolitaryBee oder CommunalBee,
//da die genannten Typen Untertypen von Bee sind.

//Wasp ist kein Pollinator, da viele Wespenarten keine Bestäuber sind und vice versa.

public class Test {
    public static void main(String[] args) throws ParseException {

        //Observation-Iterator-Tests
        var dates = new String[]{"2025-10-1", "2025-10-2", "2025-10-3", "2025-04-1", "2025-04-2", "2025-11-23"};
        var observationList = new LinkedList<Observation>();
        Observation prev = null;
        var timeFormat =new SimpleDateFormat("yyyy-MM-dd");
        for (String date : dates) {
            prev = new OsmiaCornuta("obs.. " + date, timeFormat.parse(date), prev);
            observationList.add(prev);
        }
        //Testen, ob der Vorherige gefunden wird.
        for(Observation observation : observationList){
            var i = observation.earlier();
            if(i.hasNext())
                System.out.println(observation.getDate() + ": " + i.next().getDate());
        }
        //Testen, ob der Nachfolger gefunden wird.
        for(Observation observation : observationList){
            var i = observation.later();
            if(i.hasNext())
                System.out.println(observation.getDate() + ": " + i.next().getDate());
        }


    }
}