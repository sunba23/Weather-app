package tests;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import weatherapp.City;
import weatherapp.Stats;
import weatherapp.User;

import java.util.*;

public class UserTest {
    @Test
    public void testCalculateAverage(){
        User u = new User("username");
        City city = new City("cityName", 1);
        Map<City, List<Optional<Stats>>> memory = new HashMap<>();
        Stats stats = new Stats(Optional.of(3.0), Optional.of(3.0), Optional.of(3.0));
        List <Optional<Stats>> optionalStatsList = new ArrayList<>();
        optionalStatsList.add(Optional.of(stats));
        memory.put(city, optionalStatsList);

        Assertions.assertEquals(Optional.of(3.0), u.calculateAverage(1, 2, memory));
        Assertions.assertEquals(Optional.of(3.0), u.calculateAverage(1, 3, memory));
        Assertions.assertEquals(Optional.of(3.0), u.calculateAverage(1, 1, memory));
    }

    @Test
    public void testCalculateMinimum(){
        User u = new User("username");
        City city = new City("cityName", 1);
        Map<City, List<Optional<Stats>>> memory = new HashMap<>();
        Stats stats = new Stats(Optional.of(3.0), Optional.of(3.0), Optional.of(3.0));
        List <Optional<Stats>> optionalStatsList = new ArrayList<>();
        optionalStatsList.add(Optional.of(stats));
        memory.put(city, optionalStatsList);

        Assertions.assertEquals(Optional.of(3.0), u.calculateMinimum(1, 2, memory));
        Assertions.assertEquals(Optional.of(3.0), u.calculateMinimum(1, 3, memory));
        Assertions.assertEquals(Optional.of(3.0), u.calculateMinimum(1, 1, memory));
    }

    @Test
    public void testCalculateMaximum(){
        User u = new User("username");
        City city = new City("cityName", 1);
        Map<City, List<Optional<Stats>>> memory = new HashMap<>();
        Stats stats = new Stats(Optional.of(3.0), Optional.of(3.0), Optional.of(3.0));
        List <Optional<Stats>> optionalStatsList = new ArrayList<>();
        optionalStatsList.add(Optional.of(stats));
        memory.put(city, optionalStatsList);

        Assertions.assertEquals(Optional.of(3.0), u.calculateMaximum(1, 2, memory));
        Assertions.assertEquals(Optional.of(3.0), u.calculateMaximum(1, 3, memory));
        Assertions.assertEquals(Optional.of(3.0), u.calculateMaximum(1, 1, memory));
    }

    @Test
    public void testChooseCityFromSubscribedCities(){
        User u = new User("username");
        City city = new City("cityName", 1);
        ArrayList<City> subscribedCities = new ArrayList<>();
        subscribedCities.add(city);
        Assertions.assertEquals(city, u.chooseCityFromSubscribedCities(1,subscribedCities));
        Assertions.assertNull(u.chooseCityFromSubscribedCities(2,subscribedCities));
    }
}
