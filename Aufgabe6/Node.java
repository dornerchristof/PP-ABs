//TODO: Make this a normal class without Typeparameters
//TODO: Is this in the right place should it be moved inside the Simulation class? or somewhere else? What about visibility
@Annotations.Responsible(Annotations.names.Tobias)
public class Node {
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
