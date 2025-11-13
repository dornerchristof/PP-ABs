import java.util.Iterator;
import java.util.function.Predicate;

public interface Bee extends Pollinator, Wasp{
    Observation getPrevious();
    String getTagNumber();
    Individuum getIndividuum();
    Lifestyle getLifestyle();
    default Iterator<Bee> sameBee(){
        return null; //TODO: implement me
    };
    default Iterator<Bee> sameBee(Boolean flip, Predicate<Observation> between){
        return null; //TODO: implement me
    };
}
