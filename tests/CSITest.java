package tests;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import weatherapp.*;

import java.util.ArrayList;
import java.util.Optional;

public class CSITest {
    @Test
    public void testCreateSensorsAndStations(){

    }

    @Test
    public void testUpdateSensors(){
        CSI csi = new CSI();
        StatsFactory sf = new StatsFactory();
        City city1 = new City("city1", 1);
        Stats stats1 = new Stats(Optional.empty(), Optional.of(1.0), Optional.of(1.0));
        Sensor s1 = new Sensor(stats1, city1, "HP");
        ArrayList<Sensor>sensors = new ArrayList<>();
        sensors.add(s1);
        csi.updateSensors(sensors, sf);
    }

    @Test
    public void testSetStationsToMatchSensors(){
        CSI csi = new CSI();
        StatsFactory sf = new StatsFactory();
        City city1 = new City("city1", 1);
        Stats stats1 = new Stats(Optional.empty(), Optional.of(1.0), Optional.of(1.0));
        ArrayList<Sensor>sensors = new ArrayList<>();
        Sensor s1 = new Sensor(stats1, city1, "HP");
        sensors.add(s1);
        ArrayList<Station> stations = new ArrayList<>();
        Station station1 = new Station(city1);
        stations.add(station1);
        csi.setStationsStatsToMatchSensors(sensors,stations);
    }
}
