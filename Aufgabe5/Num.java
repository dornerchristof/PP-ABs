public class Num implements Modifiable<Num,Num> {
    private final int value;

    public Num(int value) {
        this.value = value;
    }

    @Override
    public Num add(Num num) {
        return new Num(this.value + num.value);
    }

    @Override
    public Num subtract(Num num) {
        return new Num(this.value - num.value);
    }

    @Override
    public String toString() {
        return Integer.toString(value); // TODO: Make sure this is allowed
    }
}
