import java.util.Date;
import java.util.Iterator;

// Interface der Beobachtung einer Biene ist eine Wespe und ein Pollinator
public interface Bee extends Pollinator, Wasp{
    //Gibt eine vorhergehende Beobachtung desselben Individums zurück falls vorhanden.
    Bee getEarlierObservation();
    // Gibt die Tag Nummer zurück falls die Beobachtung von einem Individum ist und diese bei Erstellung gesetzt wurde.
    // Nachbedingung: eine positiver Integer falls die Nummer nicht gesetzt ist wird -1 zurück gegben
    int getTagNumber();
    // Gibt den sozialen Lebensstil der Beobachtung als Enum zurück
    Lifestyle getLifestyle();
    //Gibt einen Iterator über alle anderen Beobachtungen desselben Individums zurück
    // Vorbedingung: Eine static List<Observation> muss in der Klasse Test vorhanden sein.
    //               Test.observations != null
    //               Test.observations.contains(this) == true
    // Nachbedingung: Gibt einen Iterator<Bee> zurück.
    default Iterator<Bee> sameBee(){

        return new BeeIterator<>(Test.observations, this);
    };
    //Gibt einen Iterator über alle anderen Beobachtungen desselben Individums zurück
    // einschränkbar nach Zeitraum und sortierbarkeit nach Datum aufsteigend mit flip == true
    // und absteigend mit flip == false
    // Vorbedingung: Eine static List<Observation> muss in der Klasse Test vorhanden sein.
    //               Test.observations != null
    //               Test.observations.contains(this) == true
    //      from != null und to != null
    // Nachbedingung: Gibt einen Iterator<Bee> zurück.
    default Iterator<Bee> sameBee(Boolean flip, Date from, Date to){
        return new BeeIterator<>(Test.observations, this, flip, from, to);
    };
}
