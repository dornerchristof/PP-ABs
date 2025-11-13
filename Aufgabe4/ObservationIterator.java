import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


//EIGENE KLASSE

//View (referenzierender Iterator), die entweder alle zeitlich vor oder nach der übergebenen Observation passierten
//Observationen liefert.
public class ObservationIterator implements Iterator<Observation> {
    //current != null, current.valid() == true
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

    //currentDate != null, next != null
    //Liefert wahr zurück, wenn next zwischen currentDate und currentClosest liegt und valide ist (und damit ein besserer
    //Kandidat für die nächste Observation in der Iteration ist) oder falls currentClosest == null und next valide ist.
    private boolean isCloser(Date currentDate, Observation currentClosest, Observation next) {
        if (next.valid()) {
            if (currentClosest == null) {
                return direction == Direction.LATER && next.getDate().after(currentDate)
                        || direction == Direction.EARLIER && next.getDate().before(currentDate);
            } else {
                if (direction == Direction.LATER) {
                    return next.getDate().after(currentDate) && next.getDate().before(currentClosest.getDate());
                } else if (direction == Direction.EARLIER) {
                    return next.getDate().before(currentDate) && next.getDate().after(currentClosest.getDate());
                }
            }

        }
        return false;
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
    public Observation next() {
        current = findNext();
        if (current == null) throw new NoSuchElementException();
        return current;
    }
}
