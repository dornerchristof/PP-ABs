

/*
Repräsentiert eine Gleitkommazahl, die sich nur innerhalb der definierten Grenzen bewegen kann.
 */
public class LimitedDouble {
    private final Limits limits; //limits != null
    private double value; //value >= limits.min && value <= limits.max

    //Erstellt ein neues LimitedDouble. Wenn value außerhalb eines Limits liegt, dann wir value auf Limit.Min oder
    // Limit.Max gesetzt.
    //Vorbedingung: Limits != null
    public LimitedDouble(Limits limits, double value){
        this.limits = limits;
        setValue(value);
    }

    public double getValue(){
        return value;
    }

    // Updated den Wert. Wenn value außerhalb eines Limits liegt, dann wir value auf Limit.Min oder
    // Limit.Max gesetzt.
    public void setValue(double value){
        if(value < limits.getMin()) this.value = limits.getMin();
        if(value > limits.getMax()) this.value = limits.getMax();
        else this.value = value;
    }
}
