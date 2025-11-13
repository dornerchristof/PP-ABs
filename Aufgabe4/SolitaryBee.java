import java.util.Iterator;

public interface SolitaryBee extends WildBee {

    default Iterator<SolitaryBee> solitary(){
        return new BeeIterator<>(Test.observations, this, SolitaryBee.class, b -> b.getLifestyle() == Lifestyle.SOLITARY);
    };
}
