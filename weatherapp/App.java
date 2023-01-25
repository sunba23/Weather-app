package weatherapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App {

    Scanner scanner = new Scanner(System.in);
    CSI csi = new CSI();
    Map<String, String> usersInfo = new HashMap<>();

    public void run() throws InterruptedException {

        csi.start();
        mainMenu();

        csi.stopNotifications();
        csi.join();
    }

    //main app menu
    public void mainMenu(){
        System.out.print("\n" + "" + "\n" + "1. create account" + "\n" + "2. log in" + "\n");
        String choice = scanner.next();
        int idk = Integer.parseInt(numberChoice1to2(choice));
        switch (idk){
            case 1: {
                createAccount();
                break;
            }
            case 2: {
                logInAccount();
                break;
            }
        }
    }

    public void createAccount(){
        System.out.print("choose username: ");
        String username = usernameChoice(scanner.next());
        System.out.print("choose password: ");
        String password = scanner.next();
        User newUser = new User(username);
        newUser.setPassword(password);
        csi.register(newUser);
        System.out.println("\n" + "successfully added user " + username);
        addUser(username, password);
        userMenu(newUser);
    }

    public String usernameChoice(String username){
        if (username == null || username.equals("")){
            System.out.println("username has to consist of at least one character");
            usernameChoice(scanner.next());
        }
        if (!usernameTaken(username)) {
                return username;
        }
        System.out.println("username taken!");
        usernameChoice(scanner.next());
        return null;
    }

    public boolean usernameTaken(String username){
        return usersInfo.containsKey(username);
    }

    public void logInAccount(){
        System.out.print("username: ");
        String username = scanner.next();
        System.out.print("password: ");
        String password = scanner.next();
        if (!correctPasses(username, password)) {
            System.out.println("incorrect passes");
            mainMenu();
        }
        else logInUser(username,password);
    }

    public boolean correctPasses(String username, String password){
        for (String un : usersInfo.keySet()){
            if (username.equals(un)){
                String pw = usersInfo.get(un);
                if (password.equals(pw)){
                    return true;
                }
            }
        }
        return false;
    }

    public void logInUser(String username, String password){
        for (String un : usersInfo.keySet()){
            if (username.equals(un)){
                String pw = usersInfo.get(un);
                if (password.equals(pw)){
                    System.out.println("access granted");
                    User existingUser;
                    for (User u : csi.users){
                        if (u.username.equals(username)) {
                            existingUser = u;
                            userMenu(existingUser);
                        }
                    }
                }
            }
        }
    }

    //user menu
    public void userMenu(User u) {                                        //zrobic tu printf ladne
        System.out.println();
        System.out.printf("%-3s %-3s","logged in as: ", u.username);
        System.out.println();
        System.out.printf("%-4s %-4s", "", "----- MENU -----");
        System.out.println();
        System.out.printf("%-4s %-4s", "1.", "show available stations");
        System.out.println();
        System.out.printf("%-4s %-4s", "2.", "subscribe to station");
        System.out.println();
        System.out.printf("%-4s %-4s", "3.", "show subscribed");
        System.out.println();
        System.out.printf("%-4s %-4s", "4." ,"unsubscribe from station");
        System.out.println();
        System.out.printf("%-4s %-4s", "5.", "analyze station data");
        System.out.println();
        System.out.printf("%-4s %-4s", "6.", "serialize data");
        System.out.println();
        System.out.printf("%-4s %-4s", "7.", "log out");
        System.out.println();

        String input = scanner.next();
        if (isIn(input, 1, 7)) {
            switch (Integer.parseInt(input)) {
                case 1: {
                        System.out.println();
                        csi.showAllStationLocations();
                        System.out.print("\n" + "press any button to go back ");
                        switch (scanner.next()) {
                            default: {
                                userMenu(u);
                                break;
                            }
                        }
                }
                case 2: {
                    System.out.println();
                    csi.showAllStationLocations();
                    String input2 = numberChoiceStation(scanner.next(), csi.stations);
                    int n = Integer.parseInt(input2);
                    u.subscribeToCity(chooseCityFromAllStations(n, csi.stations));
                    System.out.println("\n" + "success");
                    break;
                }
                case 3: {
                    System.out.println();
                    if (!u.subscribedCities.isEmpty()) {
                        u.showSubInfo();
                    } else System.out.println("currently you are not subbed to any city.");
                    System.out.print("\n" + "\n" + "press any button to go back ");
                    switch (scanner.next()) {
                        default:
                            userMenu(u);
                    }
                    break;
                }
                case 4: {
                    System.out.println();
                    u.showSubbedCities();
                    System.out.print("id of the city you want to unsubscribe: ");
                    String odp = scanner.next();
                    String input2 = numberChoiceStation(odp, csi.stations);
                    int n = Integer.parseInt(input2);
                    u.unsubscribeFromCity(u.chooseCityFromSubscribedCities(n, u.subscribedCities));
                    System.out.println("\n" + "success");
                    break;
                }
                case 5: {
                    System.out.println();
                    u.showSubbedCities();
                    System.out.print("choose a city to analyze: ");
                    String odp = scanner.next();
                    String input2 = numberChoiceStation(odp, csi.stations);
                    int n = Integer.parseInt(input2);
                    String odp1, odp2, odp3;

                    System.out.println();
                    System.out.printf("%-15s %-15s %-15s %-15s", "", "Temperature", "Humidity", "Pressure");
                    System.out.println();

                    odp1 = String.format("%.2f", u.calculateAverage(n, 1, u.memory).get());
                    odp2 = String.format("%.2f", u.calculateAverage(n, 2, u.memory).get());
                    odp3 = String.format("%.2f", u.calculateAverage(n, 3, u.memory).get());
                    if (u.calculateAverage(n, 1, u.memory).get().isNaN()) {
                        odp1 = "unavailable";
                    }
                    if (u.calculateAverage(n, 2, u.memory).get().isNaN()) {
                        odp2 = "unavailable";
                    }
                    if (u.calculateAverage(n, 3, u.memory).get().isNaN()) {
                        odp3 = "unavailable";
                    }

                    System.out.printf("%-15s %-15s %-15s %-15s", "Average", odp1, odp2, odp3);
                    System.out.println();

                    odp1 = String.format("%.2f", u.calculateMinimum(n, 1, u.memory).get());
                    odp2 = String.format("%.2f", u.calculateMinimum(n, 2, u.memory).get());
                    odp3 = String.format("%.2f", u.calculateMinimum(n, 3, u.memory).get());
                    if (u.calculateMinimum(n, 1, u.memory).get() == 9999.0) {
                        odp1 = "unavailable";
                    }
                    if (u.calculateMinimum(n, 2, u.memory).get() == 9999.0) {
                        odp2 = "unavailable";
                    }
                    if (u.calculateMinimum(n, 3, u.memory).get() == 9999.0) {
                        odp3 = "unavailable";
                    }

                    System.out.printf("%-15s %-15s %-15s %-15s", "Minimum", odp1, odp2, odp3);
                    System.out.println();

                    odp1 = String.format("%.2f", u.calculateMaximum(n, 1, u.memory).get());
                    odp2 = String.format("%.2f", u.calculateMaximum(n, 2, u.memory).get());
                    odp3 = String.format("%.2f", u.calculateMaximum(n, 3, u.memory).get());
                    if (u.calculateMaximum(n, 1, u.memory).get() == -100.0) {
                        odp1 = "unavailable";
                    }
                    if (u.calculateMaximum(n, 2, u.memory).get() == -100.0) {
                        odp2 = "unavailable";
                    }
                    if (u.calculateMaximum(n, 3, u.memory).get() == -100.0) {
                        odp3 = "unavailable";
                    }

                    System.out.printf("%-15s %-15s %-15s %-15s", "Maximum", odp1, odp2, odp3);

                    System.out.print("\n" + "press any button to go back: ");
                    switch (scanner.next()) {
                        default: {
                            userMenu(u);
                            break;
                        }
                    }
                }
                case 6: {
                    try {
                        u.dataSerialization();
                        System.out.println("success!");
                    } catch (IOException e) {
                        System.out.println("data serialization failed.");
                    }
                    break;
                }
                case 7: {
                    mainMenu();
                    break;
                }
            }
        } else {
            System.out.println("\n" + "please choose a valid number!");
            userMenu(u);
        }
        userMenu(u);
    }

    //some methods
    public boolean isIn(String num, int a, int b){
        return (a <= Integer.parseInt(num) && b >= Integer.parseInt(num) && Math.ceil(Double.parseDouble(num))==Math.floor(Double.parseDouble(num)));
    }

    public void addUser(String username, String password){
        usersInfo.put(username, password);
    }

    public String numberChoice1to2(String input){
        boolean condition = condition1(input);
        if (!condition){
            System.out.print("invalid input, try again: ");
            return numberChoice1to2(scanner.next());
        }
        return input;
    }

    public boolean condition1(String input){
        return (input.equals("1") || input.equals("2"));
    }

    public String numberChoiceStation(String input, ArrayList<Station>stations){
        for (Station station : stations){
            if (isGoodInteger(input, stations) && station.city.id == Integer.parseInt(input)){
                return input;
            }
        }
        System.out.print("incorrect input, try again: ");
        return numberChoiceStation(scanner.next(), stations);
    }

    public boolean isGoodInteger(String input, ArrayList<Station> stations){
        try {
            Integer.parseInt(input);
            if (Integer.parseInt(input)>0 && Integer.parseInt(input)<=stations.size()){
                return true;
            }
        } catch (NumberFormatException e){
            return false;
        }
        return false;
    }

    public City chooseCityFromAllStations(int id, ArrayList<Station> stations) {
        for (Station station : stations) {
            if (station.city.id == id)
                return station.city;
        }
        return null;
    }

    public Map<String, String> getUsersInfo() {
        return usersInfo;
    }
}
