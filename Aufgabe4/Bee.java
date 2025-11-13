import java.util.Date;
import java.util.Iterator;
import java.util.function.Predicate;

public interface Bee extends Pollinator, Wasp{
    Bee getEarlierObservation();
    int getTagNumber();
    Lifestyle getLifestyle();
    default Iterator<Bee> sameBee(){

        return new BeeIterator<>(Test.observations, this);
    };
    default Iterator<Bee> sameBee(Boolean flip, Date from, Date to){
        return new BeeIterator<>(Test.observations, this, flip, from, to);
    };
}
