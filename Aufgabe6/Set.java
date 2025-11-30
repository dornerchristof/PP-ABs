import java.util.Iterator;

@Annotations.Responsible(Annotations.names.Tobias)
@Annotations.Invariant("size >= 0")
public class Set implements Iterable {
    private Node head;
    private int size = 0;

    @Annotations.Responsible(Annotations.names.Tobias)
    private static class Node {
        final Object elem;
        private Node next;

        @Annotations.Postcondition("Node wurde erstellt")
        public Node(Object elem) {
            this.elem = elem;
        }

    }

    @Annotations.Postcondition("neues Set wurde erstellt")
    @Annotations.Postcondition("Set ist leer")
    public Set(){
        head = null;
    }
    @Annotations.Postcondition("obj wurde am Ende des Sets eingefügt")
    @Annotations.Postcondition("size++")
    public boolean add(Object obj){
        Node node = new Node(obj);;
        if (head == null) head = node;
        else {
            Node current = head;
            while (current.next != null) current = current.next;
            current.next = node;
        }
        size++;
        return true;
    }

    @Annotations.Precondition("node ist in Set enthalten")
    @Annotations.Postcondition("node wurde aus Set entfernt")
    @Annotations.Postcondition("size--")
    public void remove (Object node){
        if (head.elem == node) head = head.next;
        else {
            Node current = head;
            while (current.next.elem != node) current = current.next;
            current.next = current.next.next;
        }
        size--;
    }
    @Annotations.Postcondition("Gibt die menge der Elmente im Set zurück size >= 0")
    public int size(){
        return size;
    }

    @Annotations.Postcondition("Falls der Index innerhalb des Arrys liegt wird das Objekt in der zugehörigen Node zurückgegeben")
    @Annotations.Postcondition("Falls sich der Index außerhalb des Arrays befindet wird null zurückgegeben")
    public Object findByIndex(int index){
        if (index < 0 || index >= size) return null;
        Node current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current.elem;
    }

    @Annotations.Postcondition("Ein Iterator über das Set wurde zurückgegeben")
    @Override
    public Iterator iterator() {
        return new SetIterator();
    }

    @Annotations.Responsible(Annotations.names.Christof)
    @Annotations.ClientHistoryConstraint("next() darf nur ausgeführt werden wenn hasNext == true")
    private class SetIterator implements Iterator{
        private Node current = head;

        @Annotations.Postcondition("Gibt true zurück falls es ein nächstes Element gibt sonst false")
        @Override
        public boolean hasNext() {
            return current != null;
        }
        @Annotations.Postcondition("Gibt das nächste Element zurückk")
        @Override
        public Object next() {
            Object elem = current.elem;
            current = current.next;
            return elem;
        }
    }
}
