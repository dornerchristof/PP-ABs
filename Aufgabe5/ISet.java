import java.util.HashMap;
import java.util.Iterator;


/// Ein Container, in dem die Elemente in einer partiellen Ordnung zueinander stehen, falls ein Objekt c
/// angegeben wird, bestimmt dieses Objekt, ob eine Ordnungsbeziehung erlaubt ist.
public class ISet<E> extends Set<E> implements OrdSet<E, Iterator<E>>{

    public ISet(Ordered<? super E, ?> c) {
        super(c);
    }

    /// Die Methode versucht, c zu verändern.
    /// Nachbedingung: Falls nicht alle bereits bestehenden Relationen c erfüllen, wird eine Exception ausgelöst und c bleibt unverändert.
    @Override
    public void check(Ordered<? super E, ?> c) throws IllegalArgumentException {
        for(var x : elements){
            for(var xRy : x.successors){
               if(c.before(x.element, xRy.element) == null)
                   throw new IllegalArgumentException("Diese Beziehung ist nicht erlaubt.");
            }
        }
        this.c = c;
    }

    /// Die Methode verändert c.
    /// Nachbedingung: Falls eine bestehende Relation c widerspricht, wird diese entfernt.
    @Override
    public void checkForced(Ordered<? super E, ?> c) {
        for(var x : elements){
            for(var xRy : x.successors){
                if(c.before(x.element, xRy.element) == null)
                    x.successors.remove(xRy);
            }
        }
        this.c = c;
    }

    /// Nachbedingung: Liefert die Anzahl an Elemente im Set zurück (>= 0)
    @Override
    public int size() {
        return elements.size();
    }


    /// Nachbedingung: Falls x vor y steht, wird ein Iterator über alle Elemente
    /// zwischen x unf y zurückgeliefert, sonst null.
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

    /// Iteriert über die zwischen zwei Werten liegenden Werte.
    private class ISetIterator implements Iterator<E>{

        /// Invarianz: != null
        private final Iterator<Node>  nodeIterator;

        /// Vorbedingung: i != null
        private ISetIterator(NodeList i){
            nodeIterator = i.iterator();
        }

        /// Nachbedingung: Wahr, wenn es ein nächstes Elemnt gibt
        @Override
        public boolean hasNext() {
             return nodeIterator.hasNext();
        }

        /// Vorbedingung: hasNext() == true
        /// Nachbedingung: Liefert das nächste Element zurück.
        @Override
        public E next() {
            return nodeIterator.next().element;
        }
    }

}
