import java.util.Date;
import java.util.Iterator;

public interface Observation {

    Date getDate();

    String getComment();
    boolean valid();
    Iterator<Observation> earlier();
    Iterator<Observation> later();
}
