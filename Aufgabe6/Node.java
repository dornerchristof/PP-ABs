//TODO: Make this a normal class without Typeparameters
//TODO: Is this in the right place should it be moved inside the Simulation class? or somewhere else? What about visibility
public class Node<T> {
    final T elem;
    Node<T> next;
    public Node(T elem) {
        this.elem = elem;
    }
    public T getElem() {
        return elem;
    }
    public Node<T> getNext() {
        return next;
    }
    public void setNext(Node<T> next) {
        this.next = next;
    }
}
