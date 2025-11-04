import java.util.Date;
import java.util.Iterator;

public abstract class Observation {

    protected String comment;
    protected Date date;

    protected Observation(String comment, Date date){
        this.comment = comment;
    }

    public final Date getDate(){
        return date;
    }

    public final String getComment(){
        return comment;
    }
    public abstract boolean valid();
    public abstract Iterator<Observation> earlier();
    public abstract Iterator<Observation> later();
}
