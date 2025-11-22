import java.util.HashMap;
import java.util.Iterator;

public class ISet<E> extends Set<E> implements OrdSet<E, Iterator<E>>{
    private Ordered<E, ?> c;


    public ISet(Ordered<E, ?> c) {
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
        return elements.size();
    }

    @Override
    public Iterator<E> before(E x, E y) {
        var nodeX = elements.findByElement(x);
        if(nodeX  == null) return null;
        if(nodeX.successors.findByElement(y) != null) //TODO: Transitivität
            return new ISetIterator(nodeX.successors.iterator()); //TODO: Iterator nur die richtigen Elemente
        return null;
    }

    //Versucht x und y in eine Ordnungsbeziehung zu setzen
    @Override
    public void setBefore(E x, E y) {
        if (x == y || before(x, y) != null) throw new IllegalArgumentException("Diese Beziehung existiert bereits.");
        if(c != null && c.before(x, y) ==null) throw new IllegalArgumentException("X ist nicht in Ordnung zu y");
        if(elements.findByElement(x) == null)
            elements.add(x);
        if(elements.findByElement(y) == null)
            elements.add(y);
        elements.findByElement(x).successors.add(y);
    }

    @Override
    public Iterator<E> iterator() {
        return new ISetIterator(elements.iterator());
    }

    private class ISetIterator implements Iterator<E>{
        private final Iterator<Set<E>.Node> i;
        private ISetIterator(Iterator<Set<E>.Node> i){
            this.i = i;
        }
        @Override
        public boolean hasNext() {
             return i.hasNext();
        }

        @Override
        public E next() {
            return i.next().element;
        }
    }

}
