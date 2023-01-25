package weatherapp;

import com.google.gson.annotations.SerializedName;

import java.util.*;

public class SubscribedCityInfoSerialized {

    @SerializedName("city")
    City city;
    @SerializedName("memory ")
    Map<City, List<Optional<Stats>>> memory = new HashMap<>();

}
