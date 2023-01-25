package weatherapp;

import java.util.Optional;

public class Station {

    City city;
    Optional<Stats> stats;

    public Station(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Optional<Stats> getStats() {
        return stats;
    }

    public void setStats(Optional<Stats> stats) {
        this.stats = stats;
    }
}
