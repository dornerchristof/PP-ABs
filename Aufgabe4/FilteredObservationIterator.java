import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;


//EIGENE KLASSE

//View (referenzierender Iterator), die entweder alle zeitlich vor oder nach der übergebenen Observation passierten
//Observationen liefert.
public class FilteredObservationIterator<T extends Observation> implements Iterator<T> {
    //current != null, current.valid() == true
    private Observation current;
    private final Predicate<Observation> predicate;


    //start != null
    public FilteredObservationIterator(T start, Predicate<Observation> filter) {
        this.current = start;
        while (this.current.getPrevious() != null) {
            this.current = this.current.getPrevious();
        }
        while (!this.current.valid() && this.current != null) {
            this.current = this.current.getNext();
        }
        this.predicate = filter;
    }

    private Observation findNext() {
        if (current == null) {return null;}
        while (current.getNext() != null) {
            if (predicate.test(current.getNext())) {
                break;
            }
            current = current.getNext();
        }
        return current.getNext();
    }


    //Findet heraus, ob es ein nächstes Element gibt oder die Iteration am Ende ist.
    //Insbesondere überspringt es Observationen, die !valid() sind.
    @Override
    public boolean hasNext() {
        return findNext() != null;
    }

    //Vorbedingung: hasNext() == true
    //Liefert zeitlich geordnet die nächste Observation
    @Override
    public T next() {
        current = findNext();
        if (current == null) throw new NoSuchElementException();
        return (T) current;
    }
}
