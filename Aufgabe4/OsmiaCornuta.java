import java.util.Date;
import java.util.Iterator;
import java.util.function.Predicate;

public class OsmiaCornuta extends SolitaryBee{
    protected OsmiaCornuta(String comment, Date date) {
        super(comment, date);
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
    public boolean valid() {
        return false;
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
