public interface SolitaryBee extends WildBee {

    default Iterable<SolitaryBee> solitary(){
        return null; //TODO: implement me
    };
}
