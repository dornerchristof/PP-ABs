import java.util.Iterator;

//TODO: Make this a normal class without Typeparameters
//TODO: Is this in the right place should it be moved inside the Simulation class? or somewhere else? What about visibility
@Annotations.Responsible(Annotations.names.Tobias)
public class Set implements Iterable {
    private Node head;
    private int size = 0;

    public Set(){
        head = null;
    }

    public void add(Object node){
        add(new Node(node));
    }

    public void add(Node node){
        if (head == null) head = node;
        else {
            Node current = head;
            while (current.next != null) current = current.next;
            current.next = node;
        }
        size++;
    }

    public void remove (Node node){
        if (head == node) head = head.next;
        else {
            Node current = head;
            while (current.next != node) current = current.next;
            current.next = current.next.next;
        }
        size--;
    }

    public int size(){
        return size;
    }

    public Node findByElement(Object element){
        Node current = head;
        while (current != null && current.elem != element) current = current.next;
        return current;
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
