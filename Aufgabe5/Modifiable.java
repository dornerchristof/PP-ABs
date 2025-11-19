public interface Modifiable<X, T> {
    /**
     * Gibt etwas zurück, das this um x erweitert.
     * this und x bleiben unverändert.
     * @param x Der Parameter, um den erweitert wird.
     * @return Ein neues Objekt vom Typ T oder this, falls nicht erweiterbar.
     */
    T add(X x);

    /**
     * Gibt etwas zurück, aus dem x entfernt ist.
     * this und x bleiben unverändert.
     * @param x Der Parameter, der entfernt wird.
     * @return Ein neues Objekt vom Typ T oder this, falls Entfernen nicht möglich.
     */
    T subtract(X x);
}