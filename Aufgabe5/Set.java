import java.util.Iterator;

public abstract class Set<E> {

    /// Invarianz: Alle Ordnungsbeziehungen erfüllen diese Ordnung (wenn c != null).
    protected Ordered<? super E, ?> c;

    protected Set(Ordered<? super E, ?> c) {
        this.c = c;
    }
    //Falls x in Relation zu y steht, befindet sich in der NodeList der Weg
    //von y to x
    protected boolean pathToY(E x, E y, NodeList list) {
        var nodeX = elements.findByElement(x);
        var nodeX = elements.findByElement(x);
        if (nodeX == null) return false;
        if (nodeX.successors.findByElement(y) != null) {
            return true;
        } else {
            for (var successor : nodeX.successors) {
                var result = pathToY(successor.element, y, list);
                if (result) {
                    list.add(successor.element);
                    return true;
                }
            }
        }
        return false;
    }

    public abstract Object before(E x, E y);

    //Versucht x und y in eine Ordnungsbeziehung zu setzen
    public void setBefore(E x, E y) {
        if (x == y || before(x, y) != null) throw new IllegalArgumentException("Diese Beziehung existiert bereits.");
        if(c != null && c.before(x, y) ==null) throw new IllegalArgumentException("X ist nicht in Ordnung zu y");
        //noinspection SuspiciousNameCombination
        if(before(y, x) != null) throw new IllegalArgumentException("Y ist bereits in Ordnung zu x");
        elements.addIfAbsent(x);
        elements.addIfAbsent(y);
        elements.findByElement(x).successors.addIfAbsent(elements.findByElement(y).element);
    }


    protected NodeList elements = new NodeList();

     protected class Node {

        final E element;
        Node next;

        // Die Successors-Liste speichert die Kanten (Ordnungsbeziehungen)
        // und wird hier als interne NodeList verwendet (Adjazenzliste).
        // Jeder Node braucht seine eigene, da er Startpunkt einer partiellen Ordnung ist.
        final NodeList successors = new NodeList();

        Node(E element) {
            this.element = element;
        }

        @Override
        public String toString() {
            if (element == null) {
                return "";
            }
            return element.toString();
        }
    }

    public String toString(){
        StringBuilder res = new StringBuilder();
         elements.iterator().forEachRemaining(element -> {
             res.append(element.toString());
             res.append(", ");
         });
         if(!res.isEmpty()){
         res.delete(res.length()-2, res.length());
         }
         return res.toString();
    }

    public Iterator<E> iterator() {
         return new SetIterator(elements.iterator());
    }
    private class SetIterator implements Iterator<E>{
        private final Iterator<Set<E>.Node> i;

        private SetIterator(Iterator<Set<E>.Node> i){
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
    /**
     * Eine einfache, selbstgebaute, verkettete Liste von Nodes.
     * Implementiert Iterable<Node<E>>, damit sie einfach durchlaufen werden kann.
     */
     protected class NodeList implements Iterable<Node> {

        private Node head;
        private int size = 0;

        // Fügt einen Node am Ende der Liste hinzu.
        protected void add(Node newNode) {
            if (head == null) {
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
            size++;
        }

        /// Entfernt das Element aus der Liste
        protected void add(E value){
            add(new Node(value));
        }

        /// Fügt den übergebenen Wert in die Liste, falls dieser noch nicht verhanden ist.
        protected void addIfAbsent(E value){
            if(findByElement(value) == null){
                add(value);
            }
        }

        /// Gibt eine neue Liste mit umgedrehter Reihenfolge zurück.
        protected NodeList reverseOrder() {
            NodeList reversed = new NodeList();
            Node last = null;
            for (int i = 0; i < size(); i++) {
                last = preceding(last) ;
                reversed.add(last.element);
            }
            return reversed;
        }

        /// Gibt die Node direkt vor der übergebenen zurück oder sich selbst, wenn das
        /// erste Element der Liste übergeben wurde. Wenn null übergeben wird, dann wird das
        /// letzte Element zurückgegeben.
        private Node preceding(Node x){
            Node current = head;
            while(current.next != x && current.next != null){
                current = current.next;
            }
            return current;
        }


        /// Entfernt genau die übergebene Node aus der Liste
        protected void remove(Node node) {
            if (head == node) {
                head = head.next;
            } else {
                Node current = head;
                while (current.next != node) {
                    current = current.next;
                }
                current.next = current.next.next;
            }
        }

        /// Entfernt das Element aus der Node
        protected void remove(E value) {
            Node node = findByElement(value);
            if (head == node) {
                head = head.next;
            } else {
                Node current = head;
                while (current.next != node) {
                    current = current.next;
                }
                current.next = current.next.next;
            }
        }

        /**
         * Sucht einen Node anhand des Elements.
         * @param element Das zu suchende Element.
         * @return Der gefundene Node oder null.
         */
        protected Node findByElement(E element) {
            Node current = head;
            while (current != null) {
                // Prüfung auf Objektidentität, wie in der Angabe gefordert[cite: 85].
                if (current.element == element) {
                    return current;
                }
                current = current.next;
            }
            return null;
        }

        protected int size(){
           return size;
        }

        @Override
        public Iterator<Node> iterator() {
            // Gibt den Iterator über die internen Nodes zurück.
            return new NodeList.NodeListIterator(head);
        }
        /**
         * Privater Iterator für die NodeList.
         * Muss nur hasNext() und next() implementieren.
         */
        private class NodeListIterator implements Iterator<Node> {
            private Node current;

            private NodeListIterator(Node current) {
                this.current = current;
            }
            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Node next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                Node temp = current;
                current = current.next;
                return temp;
            }
        }
    }
}
