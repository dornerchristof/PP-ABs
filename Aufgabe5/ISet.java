import java.util.Iterator;

public class ISet<E> implements OrdSet<E, Iterator<E>{
    private Ordered<?, ?> c;

    public ISet(Ordered<?, ?> c) {
        this.c = c;
    }

    @Override
    public void check(Ordered<?, ?> c) throws IllegalArgumentException {

    }

    @Override
    public void checkForced(Ordered<?, ?> c) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<E> before(E x, E y) {
        return null;
    }

    @Override
    public void setBefore(E x, E y) {

    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }
}
