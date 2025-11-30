import java.util.Iterator;

//TODO: Make this a normal class without Typeparameters
//TODO: Is this in the right place should it be moved inside the Simulation class? or somewhere else? What about visibility
@Annotations.Responsible(Annotations.names.Tobias)
public class Set implements Iterable {
    private Node head;
    private int size = 0;

    @Annotations.Responsible(Annotations.names.Tobias)
    private static class Node {
        final Object elem;
        Node next;

        public Node(Object elem) {
            this.elem = elem;
        }

        public Object getElem() {
            return elem;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    public Set(){
        head = null;
    }

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

    public void remove (Object node){
        if (head.elem == node) head = head.next;
        else {
            Node current = head;
            while (current.next.elem != node) current = current.next;
            current.next = current.next.next;
        }
        size--;
    }

    public int size(){
        return size;
    }

    public Object findByIndex(int index){
        if (index < 0 || index >= size) return null;
        Node current = head;
        for (int i = 0; i < index; i++) current = current.next;
        return current.elem;
    }

    @Override
    public Iterator iterator() {
        return new SetIterator();
    }

    private class SetIterator implements Iterator{
        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Object next() {
            Object elem = current.elem;
            current = current.next;
            return elem;
        }
    }
}
