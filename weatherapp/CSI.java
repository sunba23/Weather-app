package weatherapp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CSI extends Thread{

    ArrayList<Sensor> sensors = new ArrayList<>();
    ArrayList<Station> stations = new ArrayList<>();
    List <User> users = new ArrayList<>();
    private boolean shouldContinue = true;
    private final Object synchronizer = new Object();
    StatsFactory sf = new StatsFactory();

    String inputFileName = "sensors.txt";
    File sensorsFile = new File(inputFileName);
    FileReader fr;
    {
        try {
            fr = new FileReader(sensorsFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    BufferedReader br = new BufferedReader(fr);

    public void createSensorsAndStations() throws IOException {
        synchronized (synchronizer) {
            String input;
            while ((input = br.readLine()) != null && input.length() != 0) {

                String[] elements = input.split("\\s+");
                String name = elements[0];
                String type = elements[1];
                Stats randomStats = sf.getStats(type);

                switch (elements[0]) {
                    case "Wroclaw": {
                        sensors.add(new Sensor(randomStats, new City(name, 1), type));
                        if (!stations.contains(new Station(new City(name, 1)))) {
                            stations.add(new Station(new City(name, 1)));
                        }
                        break;
                    }
                    case "JeleniaGora": {
                        sensors.add(new Sensor(randomStats, new City(name, 2), type));
                        if (!stations.contains(new Station(new City(name, 2)))) {
                            stations.add(new Station(new City(name, 2)));
                        }
                        break;
                    }
                    case "Legnica": {
                        sensors.add(new Sensor(randomStats, new City(name, 3), type));
                        if (!stations.contains(new Station(new City(name, 3)))) {
                            stations.add(new Station(new City(name, 3)));
                        }
                        break;
                    }
                    case "Swidnica": {
                        sensors.add(new Sensor(randomStats, new City(name, 4), type));
                        if (!stations.contains(new Station(new City(name, 4)))) {
                            stations.add(new Station(new City(name, 4)));
                        }
                        break;
                    }
                    case "Walbrzych": {
                        sensors.add(new Sensor(randomStats, new City(name, 5), type));
                        if (!stations.contains(new Station(new City(name, 5)))) {
                            stations.add(new Station(new City(name, 5)));
                        }
                        break;
                    }
                }
            }
        }
    }

    public void updateSensors(ArrayList<Sensor> sensors, StatsFactory sf){
        synchronized(synchronizer) {
            for (Sensor s : sensors) {
                s.setStats(sf.getStats(s.type));
            }
        }
    }

    public void setStationsStatsToMatchSensors(ArrayList<Sensor>sensors, ArrayList<Station> stations) {
        synchronized (synchronizer) {
            for (Sensor sensor : sensors) {
                for (Station station : stations) {
                    if (sensor.city.id == station.city.id) {
                        Optional<Double> t = sensor.stats.temperature;
                        Optional<Double> h = sensor.stats.humidity;
                        Optional<Double> p = sensor.stats.pressure;
                        Optional<Stats> stats = Optional.of(new Stats(t, h, p));
                        station.setStats(stats);
                    }
                }
            }
        }
    }

    public void updateUsers() {
        synchronized (synchronizer) {
            for (User user : users) {
                for (int k = 0; k < user.subscribedCities.size(); k++) {
                    for (Station station : stations) {
                        if (user.subscribedCities.get(k).id == station.city.id) {
                            int index = user.subscribedCities.indexOf(station.city);
                            user.subscribedCities.get(index).station.setStats(station.getStats());
                            user.memory.get(station.city).add(station.getStats());
                        }
                    }
                }
            }
        }
    }


    @Override
    public void run(){

        try {
            createSensorsAndStations();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (shouldContinue){

            updateSensors(sensors,sf);                                      // 1. update sensors stats using a stats factory
            setStationsStatsToMatchSensors(sensors, stations);              // 2. match sensors' stats to the stations
            updateUsers();                                                  // 3. send users info from subscribed cities

            try {                                                           // 4. wait 5 seconds
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void register (User d){
        synchronized (synchronizer) {
            users.add(d);
        }
    }

    public void stopNotifications(){
        shouldContinue = false;
    }

    public void showAllStationLocations(){
        for (Station s : stations){
            System.out.println(s.city.toString());
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }
}
