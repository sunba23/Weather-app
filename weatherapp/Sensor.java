package weatherapp;

public class Sensor {

    public City city;
    public Stats stats;
    String type;
    public int cityId;

    public Sensor(Stats stats, City city, String type) {
        this.stats = stats;
        this.city = city;
        this.type = type;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
