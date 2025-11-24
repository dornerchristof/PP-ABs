public class Num implements Modifiable<Num,Num> {
    private final int value;

    public Num(int value) {
        this.value = value;
    }

    /**
     *
     * Vorbedingung: Num != null<br>
     * Nachbedingung: Gibt ein Num Objekt zurück, dessen <code>value</code> die Summe des <code>values</code> des originalen Num Objektes und des im Parameter übergebenen Num Objektes ist.
     *
     * @param num Ein Num Objekt
     * @return Ein neues Num Objekt mit der Summe des alten und neuen Num Objektes
     */
    @Override
    public Num add(Num num) {
        return new Num(this.value + num.value);
    }

    /**
     *
     * Vorbedingung: Num != null<br>
     * Nachbedingung: Gibt ein Num Objekt zurück, dessen <code>value</code> die Differenz des <code>values</code> des originalen Num Objektes und des im Parameter übergebenen Num Objektes ist.
     *
     * @param num Ein Num Objekt
     * @return Ein neues Num Objekt mit der Differenz des alten und neuen Num Objektes
     */
    @Override
    public Num subtract(Num num) {
        return new Num(this.value - num.value);
    }

    /**
     * Gibt den Wert von <code>value</code> als String zurück.
     *
     * @return <code>value</code> als String
     */
    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
