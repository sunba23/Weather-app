package weatherapp;

import java.util.Optional;
import java.util.Random;

public class StatsFactory {

    public Stats getStats(String sensorType){

        Random r = new Random();
        Optional<Double> temperature = Optional.of(r.nextDouble(35.0)-10.0);
        Optional<Double> humidity = Optional.of(r.nextDouble(99.9));
        Optional<Double> pressure = Optional.of(r.nextDouble(40.0) + 993.0);

        switch (sensorType) {
            case "THP","TPH","HTP","HPT","PTH","PHT" -> {
                return new Stats(temperature, humidity, pressure);
            }
            case "TH","HT" -> {
                return new Stats(temperature, humidity, Optional.empty());
            }
            case "TP","PT" -> {
                return new Stats(temperature, Optional.empty(), pressure);
            }
            case "HP","PH" -> {
                return new Stats(Optional.empty(), humidity, pressure);
            }
            case "T" -> {
                return new Stats(temperature, Optional.empty(), Optional.empty());
            }
            case "H" -> {
                return new Stats(Optional.empty(), humidity, Optional.empty());
            }
            case "P" -> {
                return new Stats(Optional.empty(), Optional.empty(), pressure);
            }
            default -> {
                return new Stats(null, null, null);
            }
        }
    }
}
