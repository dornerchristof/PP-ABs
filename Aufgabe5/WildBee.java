public class WildBee extends Bee implements Modifiable<Integer, WildBee> {

    private final int length;
    public WildBee(String observationDescription, Integer length) {
       super(observationDescription) ;
       this.length = length;
    }

    /**
     * Gibt die Länge einer Biene in Millimeter zurück.
     *
     * @return Die Länge einer Biene als <code>int</code>
     */
    public int length(){
        return this.length;
    }

    /**
     * Addiert einen positiven {@code Integer} zu dem bestehenden {@code WildBee} Objekt und gibt es als neues Objekt zurück.
     * <br>
     * Vorbedingung: {@code integer != null}
     * <br>
     * Nachbedingung: Gibt ein neues {@code WildBee} zurück, falls der {@code integer} positiv ist. Ansonsten wird das originale Objekt zurückgegeben.
     *
     * @param integer Der positive Integer, der zur Bienenlänge addiert wird.
     * @return Gibt ein neues {@code WildBee} zurück, falls der {@code integer} positiv ist. Ansonsten wird das originale Objekt zurückgegeben.
     */
    @Override
    public WildBee add(Integer integer) {
        if(integer > 0)
            return new WildBee(this.observationDescription, this.length + integer);
        else
            return this;
    }

    /**
     * Subtrahiert einen positiven {@code Integer} von dem bestehenden {@code WildBee} Objekt und gibt es als neues Objekt zurück.
     * <br>
     * Vorbedingung: {@code integer != null}
     * <br>
     * Nachbedingung: Gibt ein neues {@code WildBee} zurück, falls der {@code integer} positiv und kleiner als {@code length} ist. Ansonsten wird das originale Objekt zurückgegeben.
     *
     * @param integer Der positive Integer, der von der Bienenlänge subtrahiert wird.
     * @return Gibt ein neues {@code WildBee} zurück, falls der {@code integer} positiv und kleiner als {@code length} ist. Ansonsten wird das originale Objekt zurückgegeben.
     */
    @Override
    public WildBee subtract(Integer integer) {
        if(integer > 0 && integer < this.length)
        return new WildBee(this.observationDescription, this.length - integer);
        else
            return this;
    }


    /**
     * Gibt den Wert der {@code toString} Methode von {@code Bee} und den Wert von <code>length</code> als String zurück.
     *
     *
     */
    @Override
    public String toString() {
        String bee = super.toString();
        return bee +" " + this.length + "mm";
    }


}
