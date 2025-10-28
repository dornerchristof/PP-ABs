public class LimitedDouble {
    private Limits limits;
    private double value;
    public LimitedDouble(Limits limits, double value){
        this.limits = limits;
        this.value = value;
    }

    public double getValue(){
        return value;
    }

    public void setValue(double value){
        if(value < limits.getMin()) this.value = limits.getMin();
        if(value > limits.getMax()) this.value = limits.getMax();
        else this.value = value;
    }
}
