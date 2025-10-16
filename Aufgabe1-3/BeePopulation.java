import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class BeePopulation {
    double population;

    public BeePopulation(int population){
        this.population = population;
    }

    public double getPopulation(){
        return population;
    }

    public void Tagessimulation(double availableFood){
       // System.out.println("Available Food: "+ availableFood);
        if(availableFood >= population){
            population = population * 1.03;
        }
        else {
            double percentage = ((6.0 * availableFood/population) - 3);
            //System.out.println("percentage: " + percentage);
            population = population * (1 + percentage / 100);
            if(population < 0) population = 0;
        }
        //System.out.println("Bienenanzahl: " + population);
    }

    public void simulateRest(){
        double random = 0.1 + Math.random() * 0.2;
        population = population * random;
    }

    @Override
    public String toString() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');  // space separates thousands

        DecimalFormat df = new DecimalFormat("#,##0.00", symbols);

        return "Bienenpopulation: " + df.format(population) + '\n';
    }
}
