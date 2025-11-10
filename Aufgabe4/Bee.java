import java.util.Iterator;
import java.util.function.Predicate;

public interface Bee extends Observation, Pollinator, Wasp{
    Observation getPrevious();
    String getTagNumber();
    default Iterator<Bee> sameBee(){
        return null; //TODO: implement me
    };
    default Iterator<Bee> sameBee(Boolean flip, Predicate<Observation> between){
        return null; //TODO: implement me
    };
}
