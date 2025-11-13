import java.util.Date;
import java.util.Iterator;

public class BumbleBee implements SocialBee, WildBee{
    private boolean valid = true;
    private Date date;
    private String comment;
    private boolean fromBeekeeping;
    protected BumbleBee(String comment, Date date, boolean fromBeekeeping) {
        this.comment = comment;
        this.date = date;
        this.fromBeekeeping = fromBeekeeping;
    }

    @Override
    public Observation getPrevious() {
        return null;
    }

    @Override
    public String getTagNumber() {
        return "";
    }

    @Override
    public Lifestyle getLifestyle() {
        return Lifestyle.SOCIAL;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public boolean valid() {
        return valid;
    }

    @Override
    public void remove() {
        valid = false;
    }

    @Override
    public Iterator<Observation> earlier() {
        return null;
    }

    @Override
    public Iterator<Observation> later() {
        return null;
    }

    @Override
    public boolean fromBeekeeping() {
        return fromBeekeeping;
    }
}
