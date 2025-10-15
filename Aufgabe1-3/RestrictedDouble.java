public class RestrictedDouble {
    private double max;
    private double min;
    private double value;

    public RestrictedDouble(double min, double max){
        this.min = min;
        this.max = max;
    }

    public void setValue(double value) {
        this.value = value;
    }
    public double getValue(){
        return value;
    }

    public boolean isInRange(){
        return value >= min && value <= max;
    }
    public double getRangeFactor() {
        if (isInRange()) return 0.0;
        if (value < min) return value / min;
        return value / max;
    }

    public double getMin() {
        return min;
    }


    public double getMax() {
        return max;
    }

}
