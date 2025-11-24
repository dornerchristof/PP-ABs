import java.util.Iterator;

public class OSet<E> extends Set<E> implements OrdSet<E, ModifiableOrdered<E>> {


    private int size = 0;

    public OSet(Ordered<E, ?> c) {
        super(c);
    }

    /**
     * Gibt die Menge aller Elemente im Container zurück.
     * @return Die Menge aller Elemente im Container
     * Invarianz: size >= 0
     */
    @Override public int size() { return size; }

    /**
     ** Setzt das Objekt c (kann auch null sein), das für künftige Prüfungen
     * erlaubter Ordnungsbeziehungen verwendet wird.
     * Alle schon bestehenden Ordnungsbeziehungen werden mit dem neuen c überprüft.
     * Bei Fehler bleibt c unverändert und eine IllegalArgumentException wird ausgelöst.
     * @param c Das neue Ordered-Objekt zur Validierung.
     * @throws IllegalArgumentException falls eine bestehende Beziehung nicht mehr erlaubt ist.
     * Postcondition: Alle Ordnungsbeziehungen stimmen mit dem neuen c überein.
     */
    @Override public void check(Ordered<? super E, ?> c) throws IllegalArgumentException {
        if (c == null) {
            this.c = null;
            return;
        }

        for(var x : elements){
            for(var xRy : x.successors){
                if(c.before(x.element, xRy.element) == null) {
                    System.out.println(x.element + " " + xRy.element);
                    throw new IllegalArgumentException("Diese Beziehung ist nicht erlaubt.");
                };
            }
        }
        this.c = c;
    }

    /**
     * Ähnlich zu check, aber c wird auf jeden Fall gesetzt.
     * @param c Das neue Ordered-Objekt zur Validierung.
     * Postcondition: Alle Beziehungen, die von c nicht erlaubt sind, wurden entfernt.
     */
    @Override public void checkForced(Ordered<? super E, ?> c) {
        if (c == null) {
            this.c = null;
            return;
        }
        for(var x : elements){
            for(var xRy : x.successors){
                if(c.before(x.element, xRy.element) == null)
                    x.successors.remove(xRy);
            }
        }
        this.c = c;
    }
    /**
     * Prüft, ob x vor y liegt.
     * @param x Eintrag von Typ E
     * @param y Eintrag von Typ E
     * @return Ein Ergbnis vom Typ ModifiableOrdered, das != null ist, falls x vor y liegt. In welchem alle Werte zwischen x, und y enthalten sind.
     * Precondition: x und y sind in this enthalten.
     * Postcondition: this, x und y sind werden nicht verändert
     */
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
     * Implementiert die geforderten Interfaces Ordered und Modifiable durch das interface Modifiableordered.
     */
    private class OSetView implements ModifiableOrdered<E> {

        private final NodeList viewElements;
        private final OSet<E> parentContainer;

        private OSetView(NodeList elements, OSet<E> parent) {
            this.viewElements = elements;
            this.parentContainer = parent;
        }

        /**
         * Prüft, ob x vor y liegt.
         * @param x Eintrag von Typ E
         * @param y Eintrag von Typ E
         * @return Ein Ergbnis vom Typ boolean, das != null ist, falls x und y in der Liste enthalten sind und x vor y liegt.
         * Postcondition: this, x und y sind werden nicht verändert
         */
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
         * Setzt x vor y. In der Ordnung von this. Eine Implementierung kann bestimmte Annahmen für das Vorkommen und
         * die Ordnung von x und y setzen und von deren Einhaltung ausgehen. Falls diese Annahmen nicht eingehalten werden,
         * wird eine IllegalArgumentException geworfen.
         * @param x Ein Eintrag vom Typ E
         * @param y Ein Eintrag vom Typ E
         * Precondition: x und y sind in this enthalten.
         */
        @Override
        public void setBefore(E x, E y) throws IllegalArgumentException {
            if (viewElements.findByElement(x) == null || viewElements.findByElement(y) == null) {
                throw new IllegalArgumentException("Beide Elemente müssen in dieser View enthalten sein.");
            }
            parentContainer.setBefore(x, y);
        }

        /**
         * Erweitert this um x
         * @param x
         * @return ein neues Object welche Modifiable und Ordered implementier
         * mit der selben Ordungn und Objekten wie in this nur mit einem zusätzlichen Element x.
         * Postcondition: this und x sind unverändert
         */
        @Override
        public ModifiableOrdered<E> add(E x) {
            if (viewElements.findByElement(x) != null) {
                return this;
            }
            NodeList newElements = copyAndAdd(viewElements, x);
            return new OSetView(newElements, this.parentContainer);
        }

        /**
         * Entfernt x von this
         * @param x
         * @return ein neues Object welche Modifiable und Ordered implementier.
         * Postcondition: this und x sind unverändert.
         */
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
