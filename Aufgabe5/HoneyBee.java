public class HoneyBee extends Bee implements Modifiable<String, HoneyBee>{
    private String heritage = "";

    public HoneyBee(String desc, String heritage) {
        super(desc);
        this.heritage = heritage;
    }

    @Override
    public HoneyBee add(String s) {
        if (s.isEmpty()) {
            return this;
        }
        return new HoneyBee(this.observationDescription, this.heritage + s);
    }

    @Override
    public HoneyBee subtract(String s) {
        String replacement = heritage.replaceFirst(s,"");
        if (s.isEmpty() || heritage.equals(replacement)) {
            return this;
        }
        return new HoneyBee(this.observationDescription, replacement);
    }

}
