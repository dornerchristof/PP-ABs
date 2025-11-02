// Quellen für diese Wettersimulation
// Statista:
// https://de.statista.com/statistik/daten/studie/1088733/umfrage/niederschlag-in-oesterreich-nach-jahreszeiten/
// https://de.statista.com/statistik/daten/studie/1088493/umfrage/niederschlag-in-oesterreich/


import java.util.Random;


/*
 * Abstrakter Datentyp (Klasse) mit nominaler Abstraktion. Modularisierungseinheit: Klasse und Objekt
 * Simuliert typisches Wetter in Österreich, jedoch versimpelt und angepasst mit einer Tagesdauer von 12 Stunden
 * und Regenstunden basierend auf echten Werten.
 */
public class AustriaWeatherSimulation implements Weather {
    private final int NIEDERSCHLAG_BASIS_WERT = 1100;
    private final double NIEDERSCHLAG_ABWEICHUNG = 0.2;
    private final int NIEDERSCHLAG_PRO_STUNDE = 5;

    private final double NIEDERSCHLAG_KOEFFIZIENT_FRUEHLING = 0.21;
    private final double NIEDERSCHLAG_KOEFFIZIENT_SOMMER = 0.27;
    private final double NIEDERSCHLAG_KOEFFIZIENT_HERBST = 0.3;

    private final Random random;
    private int JaehrlicheNiederschlagsmenge;
    private int niederschlagsStundenFruehling;
    private int niederschlagsStundenHerbst;
    private int niederschlagsStundenSommer;

    private int[][] year;                                           // 0 = Sonnenstunden, 1 = Regenstunden
    private int currentDay;

    private double soilMoisture;
    private double temperature;

    /*
     * Konstruktor (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * Erstellt ein neues Wetterobjekt mit einem Zufallsgenerator und initialisiert ein neues Simulationsjahr.
     */
    public AustriaWeatherSimulation(Random random) {
        this.random = random;
        startNewYear();
    }

    /*
     * Initialisierungsmethode (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * Initialisiert alle Werte die benötigt werden, um ein neues Jahr zu simulieren
     */
    @Override
    public void startNewYear() {
        this.currentDay = 0;
        this.soilMoisture = random.nextDouble();
        this.temperature = 10 + 10* random.nextDouble();
        double koeffizient = NIEDERSCHLAG_ABWEICHUNG * 2 * random.nextDouble() - NIEDERSCHLAG_ABWEICHUNG;
        this.JaehrlicheNiederschlagsmenge = (int) (NIEDERSCHLAG_BASIS_WERT + NIEDERSCHLAG_BASIS_WERT * koeffizient);
        this.niederschlagsStundenFruehling = (int) (this.JaehrlicheNiederschlagsmenge * NIEDERSCHLAG_KOEFFIZIENT_FRUEHLING / NIEDERSCHLAG_PRO_STUNDE);
        this.niederschlagsStundenSommer = (int) (this.JaehrlicheNiederschlagsmenge * NIEDERSCHLAG_KOEFFIZIENT_SOMMER / NIEDERSCHLAG_PRO_STUNDE);
        this.niederschlagsStundenHerbst = (int) (this.JaehrlicheNiederschlagsmenge * NIEDERSCHLAG_KOEFFIZIENT_HERBST / NIEDERSCHLAG_PRO_STUNDE);

        this.year = new int[270][2];
        for (int i = 0; i < 3; i++) {
            int hours = switch (i) {
                case 0 -> niederschlagsStundenFruehling;
                case 1 -> niederschlagsStundenSommer;
                default -> niederschlagsStundenHerbst;
            };

            for (int j = 0; j < 90; j++) {
                this.year[i * 90 + j][0] = random.nextInt(13);
            }
            for (int j = 0; j < hours; j++) {
                int day = random.nextInt(90);
                if (this.year[i * 90 + day][1] == 12) {
                    j--;
                    continue;
                }
                this.year[i * 90 + day][1] += 1;
                if (this.year[i * 90 + day][1] + this.year[i * 90 + day][0] >= 12) {
                    this.year[i * 90 + day][0] -= 1;
                }
            }
        }
    }

    @Override
    public Weather copy() {
        return new AustriaWeatherSimulation(random);
    }

    /*
     * Helper-Methode (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * Berechnet die neue Temperatur
     */
    private void updateTemperature() {
        int minTemp ;
        int maxTemp;
        if (currentDay <= 45){
            minTemp = 10;
            maxTemp = 25;
        } else if (currentDay <= 90){
            minTemp = 15;
            maxTemp = 30;
        } else if (currentDay <= 180){
            minTemp = 20;
            maxTemp = 40;
        } else if (currentDay <= 225){
            minTemp = 15;
            maxTemp = 30;
        } else{
            minTemp = 10;
            maxTemp = 25;
        }
        if (this.temperature > maxTemp){
            this.temperature -= random.nextDouble() * 5;
            return;
        }
        if (this.temperature < minTemp){
            this.temperature += random.nextDouble() * 5;
            return;
        }
        this.temperature += random.nextDouble() * 4 -2;
    }

    /*
     * Helper-Methode (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * Berechnet die neue Bodenfeuchtigkeit
     */
    private void updateSoilMoisture() {
        int rainHours = year[currentDay][1];
        int sunHours = year[currentDay][0];
        if (rainHours > 0) {
            this.soilMoisture += random.nextDouble() * 0.08 * rainHours;
        }
        else {
            this.soilMoisture -= random.nextDouble() * 0.01 * sunHours;
        }
        if (this.soilMoisture < 0) this.soilMoisture = 0;
        else if (this.soilMoisture > 1) this.soilMoisture = 1;
    }

    // Getter-Methode (Nominale Abstraktion). Modularisierungseinheit: Objekt. Gibt die Temperatur zurück
    @Override
    public double getTemperature() {
        return this.temperature;
    }

    // Getter-Methode (Nominale Abstraktion). Modularisierungseinheit: Objekt. Gibt die Bodenfeuchtigkeit zurück
    @Override
    public double getSoilMoisture() {
        return soilMoisture;
    }

    // Getter-Methode (Nominale Abstraktion). Modularisierungseinheit: Objekt. Gibt die Sonnenstunden zurück
    @Override
    public int getSunshineHours() {
        return year[currentDay][0];
    }

    // Getter-Methode (Nominale Abstraktion). Modularisierungseinheit: Objekt. Gibt die Regenstunden zurück
    @Override
    public int getRainfallHours() {
        return year[currentDay][1];
    }

    /*
     * Simulationsmethode (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * Simuliert die Werte des nächsten Tages. Enthält eine Fallbackmethode, um ein neues Jahr zu starten, falls der
     * Tag zu hoch wird.
     */
    @Override
    public void updateWeather() {
        this.currentDay++;
        if (this.currentDay == this.year.length) {
            startNewYear();
        }
        updateTemperature();
        updateSoilMoisture();
    }

    /*
     * toString-Methode (Nominale Abstraktion). Modularisierungseinheit: Objekt
     * Erstellt eine formatierte String-Repräsentation des aktuellen Wetters
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("JaehrlicheNiederschlagsmenge: ");
        sb.append(JaehrlicheNiederschlagsmenge);
        sb.append("\nFrühling: ");
        sb.append(niederschlagsStundenFruehling);
        sb.append("h\n");
        sb.append("Sommer: ");
        sb.append(niederschlagsStundenSommer);
        sb.append("h\n");
        sb.append("Herbst: ");
        sb.append(niederschlagsStundenHerbst);
        sb.append("h\n");
        return sb.toString();
    }
}

