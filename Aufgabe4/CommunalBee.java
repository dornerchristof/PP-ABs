import java.util.Iterator;

public interface CommunalBee extends SolitaryBee{
    default Iterator<CommunalBee> communal() {
        return new BeeIterator<>(Test.observations, this, CommunalBee.class, b-> b.getLifestyle() == Lifestyle.COMMUNAL);
    }
}
