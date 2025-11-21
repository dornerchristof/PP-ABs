import java.util.Iterator;

public abstract class Set {
    
     protected class Node<E> {

        final E element;
        Node<E> next;

        // Die Successors-Liste speichert die Kanten (Ordnungsbeziehungen)
        // und wird hier als interne NodeList verwendet (Adjazenzliste).
        // Jeder Node braucht seine eigene, da er Startpunkt einer partiellen Ordnung ist.
        final NodeList<E> successors = new NodeList<>();

        Node(E element) {
            this.element = element;
        }
    }

    /**
     * Eine einfache, selbstgebaute, verkettete Liste von Nodes.
     * Implementiert Iterable<Node<E>>, damit sie einfach durchlaufen werden kann.
     */
     protected class NodeList<E> implements Iterable<Node<E>> {

        private Node<E> head;
        private int size = 0;

        // Fügt einen Node am Ende der Liste hinzu.
        void add(Node<E> newNode) {
            if (head == null) {
                head = newNode;
            } else {
                Node<E> current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = newNode;
            }
            size++;
        }

        void remove(Node<E> node) {
            if (head == node) {
                head = head.next;
            } else {
                Node<E> current = head;
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
        Node<E> findByElement(E element) {
            Node<E> current = head;
            while (current != null) {
                // Prüfung auf Objektidentität, wie in der Angabe gefordert[cite: 85].
                if (current.element == element) {
                    return current;
                }
                current = current.next;
            }
            return null;
        }

        // ... Hier würden weitere Hilfsmethoden wie remove/copy etc. folgen ...

        @Override
        public Iterator<Node<E>> iterator() {
            // Gibt den Iterator über die internen Nodes zurück.
            return new NodeList.NodeListIterator();
        }

        // --- 3. Der Iterator für NodeList ---

        /**
         * Privater Iterator für die NodeList.
         * Muss nur hasNext() und next() implementieren.
         */
        private class NodeListIterator implements Iterator<Node<E>> {
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Node<E> next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                Node<E> temp = current;
                current = current.next;
                return temp;
            }
        }
    }
}
