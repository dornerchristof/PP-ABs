/*„STYLE:"
Hier wird ein objektorientiertes Paradigma verwendet. Ein Interface
als Abstraktionsmechanismus, um Schnittstellen zu definieren, ohne die
Implementierung vorzugeben. Das Interface ermöglicht Polymorphismus, indem es
verschiedene Implementierungen austauschbar macht. In unserem Fall nutzt AustrianWeatherSimulation
das Interface. Andere Objekte können mit beliebigen Weather-Implementierungen interagieren,
damit kann später eine Vielzahl anderer Wetterimplementationen realisiert werden.
*/
public interface Weather {

    public double getTemperature();
    public double getSoilMoisture();
    public int getSunshineHours();
    public int getRainfallHours();

   //Wetter des heutigen Tages simuliert
    public void updateWeather();
    public void startNewYear();

    public String toString();
}
