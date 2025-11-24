
/// Ein Untertyp vom OSet, wo zusätzlich
public class MSet<E extends Modifiable<X, E>, X> extends OSet<E> {

    public MSet(Ordered<E, X> c) {
        super(c);
    }
    /// Setzt jedes im Set gespeicherte Element e mit e.add(x) in Relation setBefore(e.add(x), e)
    /// und fügt ggf. die neuen Elemente ins Set hinzu.
    /// Vorbedingung: Alle durch die Methode hinzugefügte Relation müssen von c akzeptiert werden.
    /// Nachbedingung: Das Set ist valide und enthält die hinzugefügten Elemente und Relationen.
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

    /// Setzt jedes im Set gespeicherte Element e mit e.subtract(x) in Relation setBefore(e.subtract(x), e)
    /// und fügt ggf. die neuen Elemente ins Set hinzu.
    /// Vorbedingung: Alle durch die Methode hinzugefügte Relation müssen von c akzeptiert werden.
    /// Nachbedingung: Das Set ist valide und enthält die hinzugefügten Elemente und Relationen.
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