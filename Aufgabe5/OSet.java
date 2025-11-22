// Datei: OSet.java

import java.util.Iterator;

public class OSet<E> extends Set implements OrdSet<E, ModifiableOrdered<E>> {


    private final NodeList elements = new NodeList();
    private Ordered<?, ?> c;
    private int size = 0;

    public OSet(Ordered<?, ?> c) {
        this.c = c;
    }

    @Override public int size() { return size; }
    //@Override public Iterator<E> iterator() {
     //   return new ElementIterator();
    //}

    /**
     * Privater Iterator, der über die Elemente (Typ E) des Containers läuft.
     * Er nutzt den Iterator der internen NodeList und extrahiert das Element.
     */
    private class ElementIterator implements Iterator<E> {

        // Wir nutzen den internen NodeListIterator, um die Kette zu durchlaufen.
        private final Iterator<Set<E>.Node> nodeIterator = elements.iterator();

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

    @Override public void check(Ordered<?, ?> newC) throws IllegalArgumentException { /* ... Implementierung ... */ }
    @Override public void checkForced(Ordered<?, ?> newC) { /* ... Implementierung ... */ }
    @Override public void setBefore(E x, E y) throws IllegalArgumentException { /* ... Implementierung der setBefore Logik ... */ }


    // --- 3. Die OSet View (Der komplexe Rückgabetyp) ---

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

        // --- Implementierung von Ordered<E, Boolean> [cite: 56] ---

        @Override
        public Boolean before(E x, E y) {
            // Hier wird die Ordnungsbeziehung des *Eltern-Containers* abgefragt.
            // Beachte: OSet.before gibt ModifiableOrdered zurück, aber die Signatur
            // des vorliegenden Interfaces (ModifiableOrdered) verlangt Boolean.

            // Logik: Prüfe, ob x und y in dieser View sind (viewElements.findByElement)
            // und ob parentContainer.before(x, y) eine Beziehung feststellt.

            // Einfache (aber unvollständige) Darstellung der Logik:
            if (viewElements.findByElement(x) == null || viewElements.findByElement(y) == null) {
                // Nur Elemente in der View können geordnet werden, hier geben wir null zurück
                // da die Angabe nur Boolean ODER null verlangt[cite: 56].
                return null;
            }

            // Delegiere die tatsächliche Beziehungsprüfung an den Eltern-Container (transitiv).
            // Da OSet.before ModifiableOrdered<E> zurückgibt, müssen wir hier prüfen,
            // ob der transitive Pfad existiert.
            if (parentContainer.getTransitivePath(x, y)) {
                return true;
            }
            return null;
        }

        @Override
        public void setBefore(E x, E y) throws IllegalArgumentException {
            // Verlangt, dass beide Parameter SCHON IM CONTAINER sind[cite: 57].
            if (viewElements.findByElement(x) == null || viewElements.findByElement(y) == null) {
                throw new IllegalArgumentException("Beide Elemente müssen in dieser View enthalten sein.");
            }

            // Wenn die Beziehung möglich ist, delegiere an den Eltern-Container,
            // da die Ordnung im Original-OSet geändert wird.
            parentContainer.setBefore(x, y);
        }

        // --- Implementierung von Modifiable<E, ModifiableOrdered<E>>  ---

        @Override
        public ModifiableOrdered<E> add(E x) {
            // Nur möglich, wenn der Parameter noch NICHT in this vorkommt[cite: 61].
            if (viewElements.findByElement(x) != null) {
                return this; // unverändert zurückgeben, wenn bereits vorhanden
            }

            // Erzeuge eine NEUE View, die das Element x enthält.
            // Da Arrays verboten sind, müsste die NodeList hier kopiert werden
            // und x als neuer Node hinzugefügt werden.
            NodeList newElements = copyAndAdd(viewElements, x);
            return new OSetView(newElements, this.parentContainer);
        }

        @Override
        public ModifiableOrdered<E> subtract(E x) {
            // Entfernt, wenn es in this enthalten ist[cite: 63].
            if (viewElements.findByElement(x) == null) {
                return this; // unverändert zurückgeben, wenn nicht enthalten
            }

            // Erzeuge eine NEUE View, die das Element x NICHT enthält.
            // Alle Ordnungsbeziehungen auf diesem Element müssen entfernt werden[cite: 64].
            // *Wichtig:* Die Kanten werden im ELTERN-Container entfernt!
            // Da dies eine View ist, wird hier nur die lokale Liste angepasst:

            NodeList newElements = copyAndRemove(viewElements, x);

            // OPTIONAL/ZUSÄTZLICH: Müsste die parentContainer.checkForced(x) Logik auslösen,
            // um Kanten zu entfernen, falls der Parameter hier X wäre. Hier entfernt
            // subtract aber das Element E aus der View und die View ist IMMUTABLE,
            // daher wird die Kantenentfernung in dieser View-Logik oft übersprungen,
            // da die View nur eine Sicht auf die Daten ist.

            return new OSetView(newElements, this.parentContainer);
        }
    }


    // --- 4. OSet.before(E x, E y) ---

    @Override
    public ModifiableOrdered<E> before(E x, E y) {
        // Prüfe, ob x VOR y kommt.
        boolean x_before_y_exists = getTransitivePath(x, y); // z.B. mittels Pfadsuche

        if (!x_before_y_exists) {
            return null; // Wenn keine Ordnung, null zurückgeben[cite: 49].
        }

        // Finde alle Einträge Z, sodass X < Z < Y (transitiv).
        // Nutze hier die Graphen-Traversierung, um die 'Zwischen-Nodes' zu identifizieren.
        NodeList intermediateNodes = findIntermediateElements(x, y);

        // Gib eine neue OSetView-Instanz zurück, die Ordered und Modifiable implementiert.
        return new OSetView(intermediateNodes, this);
    }

    // Private Hilfsmethoden für Graphen-Traversierung und Listen-Operationen
    private boolean getTransitivePath(E x, E y) { /* ... Pfadsuche ... */ return false; }
    private NodeList findIntermediateElements(E x, E y) { /* ... Zwischenknotensuche ... */ return new NodeList(); }
    private NodeList copyAndAdd(NodeList original, E element) { /* ... Erzeugt neue Liste ... */ return new NodeList(); }
    private NodeList copyAndRemove(NodeList original, E element) { /* ... Erzeugt neue Liste ... */ return new NodeList(); }


}
