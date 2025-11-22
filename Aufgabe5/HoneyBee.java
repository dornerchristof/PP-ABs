public class HoneyBee extends Bee implements Modifiable<String, HoneyBee>{
    public HoneyBee(String observationDescription) {
        super(observationDescription);
    }

    @Override
    public HoneyBee add(String s) {
        return null;
    }

    @Override
    public HoneyBee subtract(String s) {
        return null;
    } // TODO: implements modifiable<String, HoneyBee>

}
