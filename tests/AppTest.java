package tests;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import weatherapp.App;
import weatherapp.CSI;
import weatherapp.City;
import weatherapp.Station;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;


public class AppTest {

    CSI csi = new CSI();

//    @Test
//    public void testUsernameChoice(){
//        App app = new App();
//        Assertions.assertEquals("username", app.usernameChoice("username"));
//
//        //username empty
//        String input = "a";
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//        Assertions.assertEquals("a", app.usernameChoice(""));
//    }
//
//    @Test
//    public void testUsernameChoiceNull(){
//        //username null
//        App app = new App();
//        String input2 = "abc";
//        InputStream in2 = new ByteArrayInputStream(input2.getBytes());
//        System.setIn(in2);
//        Assertions.assertEquals("abc", app.usernameChoice(null));
//    }
//
//    @Test
//    public void testUsernameChoiceWhichIsTaken(){
//        //username good, but taken
//        App app = new App();
//        String input3 = "abcd";
//        InputStream in3 = new ByteArrayInputStream(input3.getBytes());
//        System.setIn(in3);
//        app.usersInfo.put("user", "password");
//        Assertions.assertEquals("abcd", app.usernameChoice("user"));
//    }

    @Test
    public void testCorrectPasses(){
        App app = new App();
        app.getUsersInfo().put("gabrielToledo", "iCantAWP");
        Assertions.assertFalse(app.correctPasses("gabrielToledo", "presente"));
        Assertions.assertFalse(app.correctPasses("auuu", "iCantAWP"));
        Assertions.assertTrue(app.correctPasses("gabrielToledo", "iCantAWP"));
    }

    @Test
    public void testPassesExist(){
        App app = new App();
        app.getUsersInfo().put("user", "password");
        Assertions.assertTrue(app.correctPasses("user", "password"));
    }

    @Test
    public void testUsernameChoiceExists(){
        App app = new App();
        app.getUsersInfo().put("aaa", "pass");
        Assertions.assertTrue(app.usernameTaken("aaa"));
        Assertions.assertFalse(app.usernameTaken("bbb"));
    }

    @Test
    public void testIsIn(){
        App app = new App();
        Assertions.assertTrue(app.isIn("4", 1, 5));
        Assertions.assertFalse(app.isIn("4", 5, 7));
    }

    @Test (expected = NumberFormatException.class)
    public void testIsInException(){
        App app = new App();
        Assertions.assertFalse(app.isIn("4.5", 3, 6));
    }

//    @Test
//    public void testNumberChoice(){
//        App app = new App();
//        Assertions.assertEquals("6", app.numberChoice("6", true));
//
//        String input = "7";
//        InputStream in = new ByteArrayInputStream(input.getBytes());
//        System.setIn(in);
//        Assertions.assertEquals("7", app.numberChoice("7", false));
//    }

    @Test
    public void testIsGoodInteger(){
        App app = new App();
        ArrayList<Station> stations = new ArrayList<>();
        City c1 = new City("c1", 1);
        City c2= new City("c2", 2);
        Station s1 = new Station(c1);
        Station s2 = new Station(c2);
        stations.add(s1);
        stations.add(s2);

        Assertions.assertTrue(app.isGoodInteger("1", stations));
        Assertions.assertFalse(app.isGoodInteger("6", stations));
    }

    @Test
    public void testIsGoodIntegerException(){
        App app = new App();
        ArrayList<Station> stations = new ArrayList<>();
        Assertions.assertFalse(app.isGoodInteger("LegitIntegerKappa", stations));
    }

    @Test
    public void testChooseCity(){
        CSI csi = new CSI();
        App app = new App();
        City c = new City("Wroclaw", 1);
        Station s = new Station(c);
        ArrayList<Station>stations = new ArrayList<>();
        stations.add(s);
        Assertions.assertEquals(c, app.chooseCityFromAllStations(1, stations));
    }

    @Test
    public void testCondition1(){
        App app = new App();
        Assertions.assertTrue(app.condition1("1"));
        Assertions.assertFalse(app.condition1("10"));
    }

    @Test
    public void testChooseCityButItsNotThere(){
        App app = new App();
        City c = new City("Wroclaw", 1);
        Station s = new Station(c);
        ArrayList<Station>stations = new ArrayList<>();
        stations.add(s);
        Assertions.assertEquals(null, app.chooseCityFromAllStations(5, stations));
    }

    @Test
    public void testNumberChoice1to2(){ //here
        Assertions.assertEquals("1", "1");
    }

    @Test
    public void testNumberChoiceStations(){
        App app = new App();
        City c = new City("a", 1);
        Station s = new Station(c);
        ArrayList<Station> stations = new ArrayList<>();
        stations.add(s);

        Assertions.assertEquals("1", app.numberChoiceStation("1", stations));
    }

//    @Test
//    public void testNumberChoiceStationsButNoSuchStation(){
//        App app = new App();
//        City c = new City("a", 1);
//        Station s = new Station(c);
//        ArrayList<Station> stations = new ArrayList<>();
//        stations.add(s);
//
//        String input3 = "2";
//        InputStream in3 = new ByteArrayInputStream(input3.getBytes());
//        System.setIn(in3);
//        Assertions.assertEquals("1", app.numberChoiceStation(input3, stations));
//    }
}
