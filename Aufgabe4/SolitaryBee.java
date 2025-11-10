public interface SolitaryBee extends Bee{

    default Iterable<SolitaryBee> solitary(){
        return null; //TODO: implement me
    };
}
