import java.util.HashMap;
import java.util.Iterator;


/// Ein Container, in dem die Elemente in einer partiellen Ordnung zueinander stehen, falls ein Objekt c
/// angegeben wird, bestimmt dieses Objekt, ob eine Ordnungsbeziehung erlaubt ist..
public class ISet<E> extends Set<E> implements OrdSet<E, Iterator<E>>{

    public ISet(Ordered<E, ?> c) {
        super(c);
    }

    /// Die Methode versucht, c zu verändern.
    /// Falls nicht alle bereits bestehenden Relationen c erfüllen, wird eine Exception ausgelöst und c bleibt unverändert.
    @Override
    public void check(Ordered<E, ?> c) throws IllegalArgumentException {
        for(var x : elements){
            for(var xRy : x.successors){
               if(c.before(x.element, xRy.element) == null)
                   throw new IllegalArgumentException("Diese Beziehung ist nicht erlaubt.");
            }
        }
        this.c = c;
    }

    /// Die Methode verändert c. Falls eine bestehende Relation c widerspricht, wird diese entfernt.
    @Override
    public void checkForced(Ordered<E, ?> c) {
        for(var x : elements){
            for(var xRy : x.successors){
                if(c.before(x.element, xRy.element) == null)
                    x.successors.remove(xRy);
            }
        }
        this.c = c;
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public Iterator<E> before(E x, E y) {
        var nodeX = elements.findByElement(x);
        if(nodeX  == null) return null;
        var list = new NodeList();
        if (pathToY(x, y, list))
        {
            return new ISetIterator(list.reverseOrder());
        }
        return null;
    }


    private class ISetIterator implements Iterator<E>{
        private final Iterator<Node>  nodeIterator;
        private ISetIterator(NodeList i){
            nodeIterator = i.iterator();
        }

        @Override
        public boolean hasNext() {
             return nodeIterator.hasNext();
        }

        @Override
        public E next() {
            return nodeIterator.next().element;
        }
    }

}
