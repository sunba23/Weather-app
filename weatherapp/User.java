package weatherapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class User {

    public int uid;
    public String username;
    String password;
    ArrayList<City> subscribedCities = new ArrayList<>();
    Map<City, List<Optional<Stats>>> memory = new HashMap<>();

    public User(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void subscribeToCity(City c){
        if (!subscribedCities.contains(c)) {
            List<Optional<Stats>> newList = new ArrayList<>();
            subscribedCities.add(c);
            memory.put(c, newList);
        }
    }

    public void unsubscribeFromCity(City c){
        subscribedCities.remove(c);
        memory.remove(c);
    }

    public void showSubInfo(){
        System.out.println("here are stats from all subscribed to cities: " + "\n");
        System.out.printf("%-15s %-15s %-15s %-15s", "Name", "Temperature", "Humidity", "Pressure");
        System.out.println();
        for (City c: subscribedCities){
            String o1 = "", o2 = "", o3 = "";
            if (c.station.getStats().get().temperature.isPresent()) {
                o1 = String.format("%.2f", c.station.getStats().get().temperature.get());
            } else o1 = "unavailable";
            if (c.station.getStats().get().humidity.isPresent()){
                o2 = String.format("%.2f", c.station.getStats().get().humidity.get());
            } else o2 = "unavailable";
            if (c.station.getStats().get().pressure.isPresent()){
                o3 = String.format("%.2f", c.station.getStats().get().pressure.get());
            } else o3 = "unavailable";
            System.out.printf("%-15s %-15s %-15s %-15s", c.name, o1, o2, o3);
            System.out.println();
        }
    }

    public void showSubbedCities(){
        for (City c : subscribedCities){
            System.out.println(c.toString());
        }
    }

    public Optional<Double> calculateAverage(int cityId, int type, Map<City, List<Optional<Stats>>> memory) {
        double result = Optional.of(0.0).get();
        double n = 0;
        for (City c : memory.keySet()){
            if (c.id == cityId){
                if (type == 1){
                    for (Optional<Stats> s : memory.get(c)){
                        if (s.get().temperature.isPresent()) {
                            result = result + s.get().temperature.get();
                        }
                    }
                    for (Optional<Stats> s : memory.get(c)){
                        if (s.get().temperature.isPresent())
                            n++;
                    }
                    result = result / n;
                }
                if (type == 2){
                    for (Optional<Stats> s : memory.get(c)){
                        if (s.get().humidity.isPresent()) {
                            result = result + s.get().humidity.get();
                        }
                    }
                    for (Optional<Stats> s : memory.get(c)){
                        if (s.get().humidity.isPresent())
                            n++;
                    }
                    result = result / n;
                }
                if (type == 3){
                    for (Optional<Stats> s : memory.get(c)){
                        if (s.get().pressure.isPresent()) {
                            result = result + s.get().pressure.get();}
                    }
                    for (Optional<Stats> s : memory.get(c)){
                        if (s.get().pressure.isPresent())
                            n++;
                    }
                    result = result / n;
                }
            }
        }
        return Optional.of(result);
    }

    public Optional<Double> calculateMinimum (int cityId, int type, Map<City, List<Optional<Stats>>> memory){
        double mini = Optional.of(9999.0).get();

        for (City c : memory.keySet()){
            if (c.id == cityId){
                if (type == 1){
                    for (Optional<Stats> s : memory.get(c)){
                        if (s.isPresent()) {
                            if (s.get().temperature.isPresent()) {
                                if (s.get().temperature.get() < mini)
                                    mini = s.get().temperature.get();
                            }
                        }
                    }
                }
                if (type == 2){
                    for (Optional<Stats> s : memory.get(c)){
                        if (s.isPresent()) {
                            if (s.get().humidity.isPresent()) {
                                if (s.get().humidity.get() < mini)
                                    mini = s.get().humidity.get();
                            }
                        }
                    }
                }
                if (type == 3){
                    for (Optional<Stats> s : memory.get(c)){
                        if (s.isPresent()) {
                            if (s.get().pressure.isPresent()) {
                                if (s.get().pressure.get() < mini)
                                    mini = s.get().pressure.get();
                            }
                        }
                    }
                }
            }
        }
        return Optional.of(mini);
    }

    public Optional<Double> calculateMaximum(int cityId, int type, Map<City, List<Optional<Stats>>> memory){
        double maxi = Optional.of(-100.0).get();

        for (City c : memory.keySet()){
            if (c.id == cityId){
                if (type == 1){
                    for (Optional<Stats> s : memory.get(c)){
                        if (s.isPresent()) {
                            if (s.get().temperature.isPresent()) {
                                if (s.get().temperature.get() > maxi)
                                    maxi = s.get().temperature.get();
                            }
                        }
                    }
                }
                if (type == 2){
                    for (Optional<Stats> s : memory.get(c)){
                        if (s.isPresent()) {
                            if (s.get().humidity.isPresent()) {
                                if (s.get().humidity.get() > maxi)
                                    maxi = s.get().humidity.get();
                            }
                        }
                    }
                }
                if (type == 3){
                    for (Optional<Stats> s : memory.get(c)){
                        if (s.isPresent()) {
                            if (s.get().pressure.isPresent()) {
                                if (s.get().pressure.get() > maxi)
                                    maxi = s.get().pressure.get();
                            }
                        }
                    }
                }
            }
        }
        return Optional.of(maxi);
    }

    public City chooseCityFromSubscribedCities(int id, ArrayList<City>subscribedCities){
        for (City city : subscribedCities){
            if (city.id == id)
                return city;
        }
        return null;
    }

    public void dataSerialization() throws IOException {

        //serialization
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(OptionalTypeAdapter.FACTORY).create();

        List<SubscribedCityInfoSerialized> results = new ArrayList<>();
        SubscribedCityInfoSerialized info = new SubscribedCityInfoSerialized();

        for (City cityFromMemory : memory.keySet()){
            info.memory.put(cityFromMemory, memory.get(cityFromMemory));
        }
        results.add(info);

        String resultString = gson.toJson(results);

        System.out.print(".txt file name: ");
        Scanner scanner = new Scanner(System.in);
        FileWriter fw = new FileWriter(scanner.next());
        BufferedWriter bw= new BufferedWriter(fw);
        try (bw) {
            bw.write(resultString);
        } catch (Exception e) {
            System.out.println("something went wrong :(");
        }
    }
}
