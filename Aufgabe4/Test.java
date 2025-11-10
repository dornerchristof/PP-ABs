import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class Test {
    public static void main(String[] args) throws ParseException {

        //Observation-Tests
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