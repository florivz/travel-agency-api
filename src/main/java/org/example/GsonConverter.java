package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class GsonConverter {

    private static GsonBuilder gsonBuilder;
    private static Gson gson;
    private static String prettyJson;

    /**
     * Converts the list of the fetched data from the database to a JSON.
     * @param list
     * @return
     */
    public String listToJSON(List<?> list){
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.setPrettyPrinting().create();
        prettyJson = gson.toJson(list);
        return prettyJson;
    }
}
