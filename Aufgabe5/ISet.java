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
        var list = new NodeList();
        if (pathToY(x, y, list)) {
            return new ISetIterator(list);
        }
        return null;
    }

    //Falls x in Relation zu y steht, befindet sich in der NodeList der Weg
    //von y to x
    private boolean pathToY(E x, E y, NodeList list) {
        var nodeX = elements.findByElement(x);
        if (nodeX.successors.findByElement(y) != null) {
            list.add(nodeX.successors.findByElement(y));
            return true;
        } else {
            for (var successor : nodeX.successors) {
                var result = pathToY(successor.element, y, list);
                if (result) {
                    list.add(successor);
                    return true;
                }
            }
        }
        return false;
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
        elements.findByElement(x).successors.add(elements.findByElement(y));
    }

    private class ISetIterator implements Iterator<E>{
        private final NodeList i;
        private final Iterator<Node>  nodeIterator;
        private ISetIterator(NodeList i){
            this.i = i;
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
