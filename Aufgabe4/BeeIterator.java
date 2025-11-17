import java.util.*;
import java.util.function.Predicate;

//EIGENE KLASSE

// View (referenzierenden Iterator), alle Beobachtungen eines bestimmten Individums.
public class BeeIterator<T extends Bee> implements Iterator<T> {
    // Invarianz: individuum != null
    private final Bee individuum;
    private int tagNumber;
    // Invarianz: current != null
    private Bee current;
    private LinkedList<Bee> same;
    // Invarianz: oberations != null, observations.contains(current) == true, Die Liste aller Observationen die in der Klasse Test vorhanden sind.
    private final List<Observation> observations;
    private boolean asc = true;
    private Date from;
    private Date to;

    @SuppressWarnings("unchecked")
    private Class<T> type = (Class<T>) Bee.class;
    private Predicate<Bee> filter = b -> true;

    public BeeIterator(List<Observation> observations, Bee individuum) {
        this.individuum = individuum;
        this.observations = observations;
        buildList();
        current = same.getFirst();
    }
    public BeeIterator(List<Observation> observations, Bee individuum, Class<T> type, Predicate<Bee> filter) {
        this.individuum = individuum;
        this.observations = observations;
        this.type = type;
        this.filter = filter;
        buildList();
        current = same.getFirst();
    }

    public BeeIterator(List<Observation> observations, Bee individuum, Boolean asc, Date from, Date to) {
        this.individuum = individuum;
        this.observations = observations;
        this.asc = asc;
        this.from = from;
        this.to = to;
        buildList();
        if(!asc){
          reverseSame();
        }
        current = same.getFirst();
    }
    // Sucht eine Tag Nummer die irgendeiner Beobachtung des selben Individuums zugeordnet ist.
    // Vorbedingung: Die angegeben Tag Nummern aller Beobachtungen eines Individums sind gleich.
    private void findTagNumber() {
        Bee current = individuum;
        while (current.getEarlierObservation() != null) {
            if (current.getEarlierObservation() instanceof Bee s) {
                if (s.getTagNumber() != -1) {
                    tagNumber = s.getTagNumber();
                    return;
                }
                current = s;
            }
        }
        current = individuum;
        int currentIndex = observations.indexOf(individuum);
        while(currentIndex <= observations.size() - 1){
            Observation obs = observations.get(currentIndex);
            if( isValid(obs)){
                Bee s = (Bee) obs;
                if(s.getEarlierObservation() != null && s.getEarlierObservation().equals(current)){
                    if(s.getTagNumber() != -1) {
                        tagNumber = s.getTagNumber();
                        return;
                    }
                    current = s;
                }
            }
            currentIndex++;
        }
    }
    // Gibt zurück ob eine eingabe Beobachtung valid ist.
    private boolean isValid(Observation obs){
        return obs instanceof Bee s && type.isInstance(s) && filter.test(s);
    }

    // Führt alle wichtigen Aktionen aus um die Liste neu aufzubauen.
    // Sie wird aus den observations immer wieder neu gebaut um auf Änderungen nach dem erstellen des Iterators weiterhin bezug nehmen zu können.
    private void buildList(){
        List<Bee> conected = findAllConnected(individuum);
        same = new LinkedList<>();
        for(Bee bee : conected){
            addSame(bee);
        }
        findTagNumber();
        List<Bee> tagNum = findAllWithTag();
        for(Bee bee : tagNum){
            if(same.contains(bee)) continue;
            addSame(bee);
            if(bee.getEarlierObservation() != null){
                List<Bee> conected2 = findAllConnected(bee);
                for(Bee bee2 : conected2){
                    if(!same.contains(bee2)) addSame(bee2);
                }
            }
        }
        for(Bee bee : same){
            if(from != null && bee.getDate().before(from)) same.remove(bee);
            if(to != null && bee.getDate().after(to)) same.remove(bee);
        }
        if(!asc) reverseSame();
    }

    // Sucht alle Individuen die die slebe Tag Nummer wie in der Objektvariable tagNumber gespeichert wird.
    private List<Bee> findAllWithTag(){
        List<Bee> list = new LinkedList<>();
        for(Observation obs : observations){
            if(isValid(obs)){
                Bee bee = (Bee) obs;
                if(bee.getTagNumber() == tagNumber){
                    list.add(bee);
                }
            }
        }
        return list;
    }
    // Liefert alle Beobachtungen die über die die getEarlierObservation() Funktion von individuum verbunden sind.
    private List<Bee> findAllConnected(Bee now){
        // Get all earlier
        Bee curr = now;
        List<Bee> list = new LinkedList<>();
        list.add(now);

        while(curr.getEarlierObservation() != null){
            Bee obs = curr.getEarlierObservation();
            if(isValid(obs)){
                list.add(obs);
            }
            curr = obs;
        }
        // Get all later
        curr = now;
        int currIndex = observations.indexOf(curr);
        while(currIndex <= observations.size() - 1){
            Observation obs = observations.get(currIndex);
            if(isValid(obs)){
                Bee s = (Bee) obs;
                if(s.getEarlierObservation() != null && s.getEarlierObservation().equals(curr)){
                    list.add(s);
                    curr = s;
                }
            }
            currIndex++;
        }
        return list;
    }
    // Liefert die nächste Observation in der Iteration zurück, oder null, falls das Ende der Iteration erreicht ist.
    private T findNext() {
        buildList();
        Bee candidate = null;
        int index = same.indexOf(current) + 1;
        while(index <= same.size() - 1){
            candidate = same.get(index);
            if(isValid(candidate) && type.isInstance(candidate)){
                 return type.cast(candidate);
            }
            index++;
        }
        return null;
    }
    // Fügt einen nuen eintrag in die same Liste zeitlich sortiert hinzu.
    // Vorbedingung: same != null
    private void addSame(Bee bee){
        int i =0;
        while(i < same.size() && same.get(i).getDate().before(bee.getDate())){
            i++;
        }
        same.add(i, bee);
    }

    // Dreht die Sortierung ddr Liste um.
    private void reverseSame(){
        for(int i = 0; i < same.size() / 2; i++){
            Bee temp = same.get(i);
            same.set(i, same.get(same.size() - i - 1));
            same.set(same.size() - i - 1, temp);
        }
    }

    //Evaluiert zu wahr, wenn es noch ein Element in der Iteration gibt (next() eine Observation zurückliefert)
    //Nachbedingung: falls wahr zurückgegeben wird, liefert next() das nächste Objekt in der Iteration
    @Override
    public boolean hasNext() {
        return findNext() != null;
    }

    //Vorbedingung: hasNext() == true
    //Liefert die nächste Observation
    // Nachbedingung: Setzt current auf den nächsten gültigen wert.
    @Override
    public T next() {
        T t = findNext();
        current = t;
        if (current == null) throw new NoSuchElementException();
        return t;
    }
}
