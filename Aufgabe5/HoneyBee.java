public class HoneyBee extends Bee implements Modifiable<String, HoneyBee>{
    private String heritage = "";

    public HoneyBee(String desc, String heritage) {
        super(desc);
        this.heritage = heritage;
    }

    /**
     * Addiert einen nicht leeren {@code String} zu der variable {@code heritage} des bestehenden {@code HoneyBee} Objekt und gibt es als neues Objekt zurück.
     * <br>
     * Vorbedingung: {@code s != null}
     * <br>
     * Nachbedingung: Gibt ein neues {@code HoneyBee} zurück, falls {@code s} nicht leer ist. Ansonsten wird das originale Objekt zurückgegeben.
     *
     * @param s Der nicht leere {@code String}, der zu {@code heritage} concatiniert wird.
     * @return Gibt ein neues {@code HoneyBee} zurück, falls {@code s} nicht leer ist. Ansonsten wird das originale Objekt zurückgegeben.
     */
    @Override
    public HoneyBee add(String s) {
        if (s.isEmpty()) {
            return this;
        }
        return new HoneyBee(this.observationDescription, this.heritage + s);
    }

    /**
     * Entfernt einen nicht leeren {@code String} von der variable {@code heritage} des bestehenden {@code HoneyBee} Objekt und gibt es als neues Objekt zurück.
     * <br>
     * Vorbedingung: {@code s != null}
     * <br>
     * Nachbedingung: Gibt ein neues {@code HoneyBee} zurück, falls {@code s} nicht leer ist und in {@code heritage} vorkommt. Ansonsten wird das originale Objekt zurückgegeben.
     *
     * @param s Der nicht leere {@code String}, der aus {@code heritage} entfernt wird.
     * @return Gibt ein neues {@code HoneyBee} zurück, falls {@code s} nicht leer ist und in {@code heritage} vorkommt. Ansonsten wird das originale Objekt zurückgegeben.
     */
    @Override
    public HoneyBee subtract(String s) {
        String replacement = heritage.replaceFirst(s,"");
        if (s.isEmpty() || heritage.equals(replacement)) {
            return this;
        }
        return new HoneyBee(this.observationDescription, replacement);
    }

    /**
     * Gibt den Wert der {@code toString} Methode von {@code Bee} und den Wert von <code>heritage</code> als String zurück.
     *
     *
     */
    @Override
    public String toString() {
        String bee = super.toString();
        return bee +" HoneyBee: " + this.heritage;
    }

    /**
     * Gibt den Wert von <code>heritage</code> als {@code String} zurück.
     *
     *
     */
    public String sort() {
        return this.heritage;
    }

}
