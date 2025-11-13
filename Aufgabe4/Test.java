import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

//Schwebfliegen gehören zu den Fliegen und sind damit nicht mit den Wespen oder Bienen verwandt. Daher gibt es keine
//Untertypbeziehungen zwischen Schwebfliegen und Wasp und Bee und vice versa.
//Da die Schwebfliege keine Biene ist, kann sie auch keine WildBee, SocialBee, SolitaryBee oder CommunalBee,
//da die genannten Typen Untertypen von Bee sind.

//Wasp ist kein Pollinator, da viele Wespenarten keine Bestäuber sind und vice versa.

//Honigbienen und Hummeln sind keine SolitaryBee und keine CommunalBee, da sie nur sozial leben.
//Honigbienen sind keine Wildbienen, da sie nur als Nutztiere dienen.

//AndrenaBucephala sind keine SocialBee, da sie nur communal und solitär leben
//LasioglossumCalceatum ist keine CommunalBee, da sie generell sozial und nur in kalten Regionen solitär leben.
//OsmiaCornuta ist keine CommunalBee oder SocialBee, da sie nur solitär leben.

//CommunalBee, SolitaryBee und WildBee sind keine SocialBee, da SocialBee nicht ausschließlich wild leben.
//SocialBee sind keine CommunalBee, SolitaryBee oder WildBee, da SocialBee nicht ausschließlich wild leben.

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