package weatherapp;

import java.util.Optional;

public class Stats {

    public Optional<Double> temperature;
    public Optional<Double> humidity;
    public Optional<Double> pressure;

    public Stats(Optional<Double> temperature, Optional<Double> humidity, Optional<Double> pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
    }

    public Optional<Double> getTemperature() {
        return temperature;
    }

    public void setTemperature(Optional<Double> temperature) {
        this.temperature = temperature;
    }

    public Optional<Double> getHumidity() {
        return humidity;
    }

    public void setHumidity(Optional<Double> humidity) {
        this.humidity = humidity;
    }

    public Optional<Double> getPressure() {
        return pressure;
    }

    public void setPressure(Optional<Double> pressure) {
        this.pressure = pressure;
    }

    @Override
    public String toString() {

        String t = String.valueOf(temperature);
        String h = String.valueOf(humidity);
        String p = String.valueOf(pressure);

        if (temperature.isEmpty()){
            t = "unavailable";
        }
        if (humidity.isEmpty()){
            h = "unavailable";
        }
        if (pressure.isEmpty()){
            p = "unavailable";
        }

        return "temperature: " + t + ", humidity: " + h + ", pressure: " + p;
    }
}
