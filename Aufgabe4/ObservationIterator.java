import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


//EIGENE KLASSE

//View (referenzierender Iterator), die entweder alle zeitlich vor oder nach der übergebenen Observation passierten
//Observationen liefert.
public class ObservationIterator implements Iterator<Observation> {
    //current != null
    private Observation current;
    private final Direction direction;
    private final List<Observation> observations;

    public enum Direction {
        LATER,
        EARLIER
    }

    //start != null
    public ObservationIterator(Observation start, Direction direction, List<Observation> observations) {
        this.current = start;
        this.direction = direction;
        this.observations = observations;
    }

    //Liefert die nächste Observation in der Iteration zurück, oder null, falls das Ende der Iteration erreicht ist.
    private Observation findNext() {
        int index = observations.indexOf(current);
        if(direction == Direction.EARLIER){
           if(index == 0) return null;
           return observations.get(index-1);
        }
            if(index == observations.size() - 2) return null;
            return observations.get(index + 1);
    }

    //Evaluiert zu wahr, wenn es noch ein Element in der Iteration gibt (next() eine Observation zurückliefert)
    //Nachbedingung: falls wahr zurückgegeben wird, liefert next() das nächste Objekt in der Iteration
    @Override
    public boolean hasNext() {
        return findNext() != null;
    }

    //Vorbedingung: hasNext() == true
    //Liefert zeitlich geordnet die nächste Observation
    @Override
    public Observation next() {
        current = findNext();
        if (current == null) throw new NoSuchElementException();
        return current;
    }
}
