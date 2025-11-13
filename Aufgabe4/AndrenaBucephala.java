import java.util.Date;
import java.util.Iterator;
import java.util.function.Predicate;

public class AndrenaBucephala implements CommunalBee{
    private boolean valid = true;
    protected AndrenaBucephala(String comment, Date date, boolean solitaryLifestyle) {

    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public boolean valid() {
        return false;
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
    public Lifestyle getLifestyle() {
        return null;
    }

    @Override
    public Iterator<Bee> sameBee() {
        return null;
    }

    @Override
    public Iterator<Bee> sameBee(Boolean flip, Predicate<Observation> between) {
        return null;
    }


    @Override
    public boolean fromBeekeeping() {
        return false;
    }
}
