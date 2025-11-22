public class WildBee extends Bee implements Modifiable<Integer, WildBee> {

    private final int length;
    public WildBee(String observationDescription, Integer length) {
       super(observationDescription) ;
       this.length = length;
    }

    public int length(){
        return this.length;
    }

    @Override
    public WildBee add(Integer integer) {
        if(integer > 0)
            return new WildBee(this.observationDescription, this.length + integer);
        else
            return this;
    }

    @Override
    public WildBee subtract(Integer integer) {
        if(integer > 0 && integer < this.length)
        return new WildBee(this.observationDescription, this.length - integer);
        else
            return this;
    }

    @Override
    public String toString() {
        String bee = super.toString();
        return bee +"\nWildBee: " + this.length + "mm";
    }

}
