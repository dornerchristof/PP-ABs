public interface Weather {

    public double getTemperature();
    public double getSoilMoisture();
    public double getSunshineHours();
    public double rainfallHours();

   //Wetter des heutigen Tages simuliert
    public void updateWeather();

    public String toString();
}
