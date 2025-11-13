import java.util.Date;
import java.util.Iterator;

public class HoneyBee implements SocialBee{
    private boolean valid = true;
    private Date date;
    private String comment;
    protected HoneyBee(String comment, Date date) {
        this.comment = comment;
        this.date = date;
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
    public Individuum getIndividuum() {
        return null;
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
}
