// Annahme: Alle Basis-Interfaces und die interne NodeList existieren.
// Die Node und NodeList Klassen von ISet werden hier als interne Strukturen übernommen.
public interface ModifiableOrdered<E>
        extends Ordered<E, Boolean>, Modifiable<E, ModifiableOrdered<E>> {

    // Keine zusätzlichen Methoden erforderlich, da alle von den Eltern-Interfaces kommen.
}
