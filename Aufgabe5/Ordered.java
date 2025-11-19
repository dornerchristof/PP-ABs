public interface Ordered <E,R>{
    /**
     * Prüft, ob x vor y liegt.
     * @param x Eintrag von Typ E
     * @param y Eintrag von Typ E
     * @return Ein Ergbnis vom Typ R, das != null ist, falls x vor y liegt (z.B ein Iterator oder ein boolean Wert).
     */
    R before(E x, E y);

    /**
     * Setzt x vor y. In der Ordnung von this. Eine Implementierung kann bestimmte Annahmen für das Vorkommen und
     * die Ordnung von x und y setzen und von deren Einhaltung ausgehen. Falls diese Annahmen nicht eingehalten werden,
     * wird eine IllegalArgumentException geworfen.
     * @param x Ein Eintrag vom Typ E
     * @param y Ein Eintrag vom Typ E
     */
    void setBefore(E x, E y);

}
