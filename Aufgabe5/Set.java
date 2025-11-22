import java.util.Iterator;

public abstract class Set<E> {

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
        void add(Node newNode) {
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

        void add(E value){
            add(new Node(value));
        }

        /// Fügt den übergebenen Wert in die Liste, falls dieser noch nicht verhanden ist.
        void addIfAbsent(E value){
            if(findByElement(value) == null){
                add(value);
            }
        }

        /// Fügt die Node in die Liste ein, falls der darin enthaltene Wert noch nicht in der Liste
        /// vorhanden ist.
        void addIfAbsent(Node value){
            if(findByElement(value.element) == null){
                add(value);
            }
        }

        void remove(Node node) {
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
         * Sucht einen Node anhand des Elements (Objektidentität, da equals/hashCode verboten sind).
         * @param element Das zu suchende Element.
         * @return Der gefundene Node oder null.
         */
        Node findByElement(E element) {
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

        public int size(){
           return size;
        }

        // ... Hier würden weitere Hilfsmethoden wie remove/copy etc. folgen ...

        @Override
        public Iterator<Node> iterator() {
            // Gibt den Iterator über die internen Nodes zurück.
            return new NodeList.NodeListIterator(head);
        }

        // --- 3. Der Iterator für NodeList ---

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
