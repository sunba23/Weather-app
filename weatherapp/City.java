package weatherapp;

public class City {

    public int id;
    public String name;
    Station station = new Station(this);

    public City(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Station getStation() {
        return station;
    }

    @Override
    public String toString() {
        return id + ".  " + name;
    }
}
