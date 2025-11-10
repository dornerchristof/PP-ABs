import java.util.Iterator;
import java.util.function.Predicate;

public interface Bee extends Observation, Pollinator, Wasp{
    Observation getPrevious();
    String getTagNumber();
    Iterator<Bee> sameBee();
    Iterator<Bee> sameBee(Boolean flip, Predicate<Observation> between);
}
