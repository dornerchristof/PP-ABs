// Quellen für diese Wettersimulation
// Statista:
// https://de.statista.com/statistik/daten/studie/1088733/umfrage/niederschlag-in-oesterreich-nach-jahreszeiten/
// https://de.statista.com/statistik/daten/studie/1088493/umfrage/niederschlag-in-oesterreich/


import java.util.Arrays;
import java.util.Random;

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

    public AustriaWeatherSimulation(Random random) {
        this.random = random;
        startNewYear();
    }

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

    private void updateSoilMoisture() {
        int rainHours = year[currentDay][1];
        int sunHours = year[currentDay][0];
        if (rainHours > 0) {
            this.soilMoisture += this.soilMoisture* random.nextDouble() * 0.08 * rainHours;
        }

        this.soilMoisture -= this.soilMoisture * random.nextDouble() * 0.0083 * sunHours;

        if (this.soilMoisture < 0) this.soilMoisture = 0;
        else if (this.soilMoisture > 1) this.soilMoisture = 1;
    }

    @Override
    public double getTemperature() {
        return this.temperature;
    }

    @Override
    public double getSoilMoisture() {
        return soilMoisture;
    }

    @Override
    public double getSunshineHours() {
        return year[currentDay][0];
    }

    @Override
    public double rainfallHours() {
        return year[currentDay][1];
    }

    @Override
    public void updateWeather() {
        this.currentDay++;
        if (this.currentDay == this.year.length) {
            startNewYear();
        }
        updateTemperature();
        updateSoilMoisture();
    }

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

