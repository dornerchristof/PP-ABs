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
