
/*
Repräsentiert die unveränderlichen Eigenschaften einer Pflanze. Alle FlowerPopulations der gleichen Pflanzenart halten
eine Referenz auf eine einzige Instanz dieser Klasse.
*/
public class Flower {

    private final String name;

    public Flower(String name) {
        this.name = name;
    }

    String getName(){
        return name;
    }
}
