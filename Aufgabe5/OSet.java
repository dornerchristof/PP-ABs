import java.util.Iterator;

public class OSet<E> extends Set<E> implements OrdSet<E, ModifiableOrdered<E>> {


    private int size = 0;

    public OSet(Ordered<E, ?> c) {
        super(c);
    }

    @Override public int size() { return size; }

    /**
     * Privater Iterator, der über die Elemente (Typ E) des Containers läuft.
     * Er nutzt den Iterator der internen NodeList und extrahiert das Element.
     */
    private class ElementIterator implements Iterator<E> {

        // Wir nutzen den internen NodeListIterator, um die Kette zu durchlaufen.
        private final Iterator<Node> nodeIterator = elements.iterator();

        @Override
        public boolean hasNext() {
            return nodeIterator.hasNext();
        }

        @Override
        public E next() {
            Set<E>.Node node = nodeIterator.next();
            return node.element;
        }

    }


    @Override public void check(Ordered<? super E, ?> c) throws IllegalArgumentException {

        for(var x : elements){
            for(var xRy : x.successors){
                if(c != null && c.before(x.element, xRy.element) == null) {
                    System.out.println(x.element + " " + xRy.element);
                    throw new IllegalArgumentException("Diese Beziehung ist nicht erlaubt.");
                };
            }
        }
        this.c = c;
    }
    @Override public void checkForced(Ordered<? super E, ?> c) {
        for(var x : elements){
            for(var xRy : x.successors){
                if(c.before(x.element, xRy.element) == null)
                    x.successors.remove(xRy);
            }
        }
        this.c = c;
    }

    @Override
    public ModifiableOrdered<E> before(E x, E y) {
        NodeList intermediateNodes = new NodeList();
        boolean x_before_y_exists = pathToY(x, y, intermediateNodes);

        if (!x_before_y_exists) {
            return null;
        }

        return new OSetView(intermediateNodes, this);
    }


    /**
     * Innere Klasse, die einen Teil der Elemente (nach x, vor y) repräsentiert.
     * Implementiert die geforderten Interfaces Ordered und Modifiable.
     * Dieses Objekt repräsentiert die Ordnung des Eltern-OSet.
     */
    private class OSetView implements ModifiableOrdered<E> {

        // Die Elemente, die in dieser View enthalten sind (als einfache NodeList)
        private final NodeList viewElements;
        private final OSet<E> parentContainer; // Referenz zum ursprünglichen OSet

        // Privater Konstruktor: wird nur von OSet.before erzeugt.
        private OSetView(NodeList elements, OSet<E> parent) {
            this.viewElements = elements;
            this.parentContainer = parent;
        }

        // --- Implementierung von Ordered<E, Boolean>---

        @Override
        public Boolean before(E x, E y) {

            if (viewElements.findByElement(x) == null || viewElements.findByElement(y) == null) {
                return null;
            }

            if (parentContainer.pathToY(x, y, new NodeList())) {
                return true;
            }
            return null;
        }

        /**
         *
         * @param x
         * @param y
         * @throws IllegalArgumentException if x or y are not in this view or if
         */
        @Override
        public void setBefore(E x, E y) throws IllegalArgumentException {
            if (viewElements.findByElement(x) == null || viewElements.findByElement(y) == null) {
                throw new IllegalArgumentException("Beide Elemente müssen in dieser View enthalten sein.");
            }
            parentContainer.setBefore(x, y);
        }


        @Override
        public ModifiableOrdered<E> add(E x) {
            if (viewElements.findByElement(x) != null) {
                return this;
            }
            NodeList newElements = copyAndAdd(viewElements, x);
            return new OSetView(newElements, this.parentContainer);
        }

        @Override
        public ModifiableOrdered<E> subtract(E x) {
            if (viewElements.findByElement(x) == null) {
                return this;
            }

            NodeList newElements = copyAndRemove(viewElements, x);

            return new OSetView(newElements, this.parentContainer);
        }
    }
    // Private Hilfsmethoden für Graphen-Traversierung und Listen-Operationen
    private NodeList copy(NodeList nl){
        NodeList copy = new NodeList();
        for(var x : nl){
            copy.add(x.element);
        }
        return copy;
    }
    private NodeList copyAndAdd(NodeList original, E element) {
        NodeList copy = copy(original);
        copy.add(element);
        return copy;
    }
    private NodeList copyAndRemove(NodeList original, E element) {
        NodeList copy = copy(original);
        copy.remove(element);
        for(var x : copy){
            for(var succ : x.successors){
                if(succ.element == element) x.successors.remove(succ);
            }
        }
        return copy;
    }


}
