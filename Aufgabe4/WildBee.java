import java.util.Iterator;

public interface WildBee extends Bee {
    boolean fromBeekeeping();

    default Iterator<WildBee> wild(boolean fromBeekeeping) {
        return new BeeIterator<>(Test.observations, this, WildBee.class, b -> b instanceof WildBee w && w.fromBeekeeping() == fromBeekeeping);
    }

}
