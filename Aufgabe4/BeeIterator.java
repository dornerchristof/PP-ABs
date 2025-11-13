import java.util.*;
import java.util.function.Predicate;

public class BeeIterator<T extends Bee> implements Iterator<T> {
    private final Bee individuum;
    private int tagNumber;
    private Bee current;
    private LinkedList<Bee> same;
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
        buildList();
        current = same.getFirst();
        this.type = type;
        this.filter = filter;
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
            if( observations.get(currentIndex) instanceof Bee s && type.isInstance(s) && filter.test(s)){
                if(s.getEarlierObservation().equals(current)){
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

    private void buildList(){
        List<Bee> conected = findAllConected(individuum);
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
                List<Bee> conected2 = findAllConected(bee);
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

    private List<Bee> findAllWithTag(){
        List<Bee> list = new LinkedList<>();
        for(Observation obs : observations){
            if(obs instanceof Bee bee && bee.getTagNumber() == tagNumber){
                list.add(bee);
            }
        }
        return list;
    }

    private List<Bee> findAllConected(Bee now){
        // Get all earlier
        Bee curr = now;
        List<Bee> list = new LinkedList<>();
        while(curr.getEarlierObservation() != null){
            if(curr.getEarlierObservation() instanceof Bee s){
                list.add(s);
                curr = s;
            }
        }
        // Get all later
        curr = now;
        int currIndex = observations.indexOf(curr);
        while(currIndex <= observations.size() - 1){
            if(observations.get(currIndex) instanceof Bee s){
                if(s.getEarlierObservation().equals(curr)){
                    list.add(s);
                    curr = s;
                }
            }
            currIndex++;
        }
        return list;
    }

    private T findNext() {
        buildList();
        Bee candidate = null;
        if(same.indexOf(current) <= same.size()){
            candidate = same.get(same.indexOf(current) + 1);
            if (type.isInstance(candidate)) return type.cast(candidate);
        };
        return null;
    }

    private void addSame(Bee bee){
        int i =0;
        while(i < same.size() && same.get(i).getDate().before(bee.getDate())){
            i++;
        }
        same.add(i, bee);
    }

    private void reverseSame(){
        for(int i = 0; i < same.size() / 2; i++){
            Bee temp = same.get(i);
            same.set(i, same.get(same.size() - i - 1));
            same.set(same.size() - i - 1, temp);
        }
    }

    @Override
    public boolean hasNext() {
        return findNext() != null;
    }

    @Override
    public T next() {
        T t = findNext();
        current = t;
        if (current == null) throw new NoSuchElementException();
        return t;
    }
}
