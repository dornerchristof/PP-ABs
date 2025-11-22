


public class MSet<E extends Modifiable<X, E>, X> extends OSet<E> {

    public MSet(Ordered<E, X> c) {
        super(c);
    }

    public void plus(X x){
        var it = iterator();
        while(it.hasNext()){
            var e = it.next();
            if(it.hasNext()){
                this.setBefore(e.add(x), e);
                return;
            }
            this.setBefore(e.add(x), e);
        }
    }

    public void minus(X x){
        var it = iterator();
        while(it.hasNext()){
            var e = it.next();
            if(it.hasNext()) {
                this.setBefore(e.subtract(x), e);
                return;
            }
            this.setBefore(e.subtract(x), e);
        }
    }
}