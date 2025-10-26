
/*
Repräsentiert die unveränderlichen Eigenschaften einer Pflanze. Alle FlowerPopulations der gleichen Pflanzenart halten
eine Referenz auf eine einzige Instanz dieser Klasse.
*/
public class Flower {

    private final String name;
    private final char shortName;

    public Flower(String name) {
        this.name = name;
        this.shortName = name.charAt(0);
    }

    public Flower(String name, char shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    String getName(){
        return name;
    }

    public char getShortName(){ return shortName;}
}
