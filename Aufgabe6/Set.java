//TODO: Make this a normal class without Typeparameters
//TODO: Is this in the right place should it be moved inside the Simulation class? or somewhere else? What about visibility
public class Set<T> {
    private Node<T> head;
    private int size = 0;

    public Set(){
        head = null;
    }

    public void add(Node<T> node){
        if (head == null) head = node;
        else {
            Node<T> current = head;
            while (current.next != null) current = current.next;
            current.next = node;
        }
        size++;
    }

    public void remove (Node<T> node){
        if (head == node) head = head.next;
        else {
            Node<T> current = head;
            while (current.next != node) current = current.next;
            current.next = current.next.next;
        }
    }

    public int size(){
        return size;
    }

    public Node<T> findByElement(T element){
        Node<T> current = head;
        while (current != null && current.elem != element) current = current.next;
        return current;
    }

}
