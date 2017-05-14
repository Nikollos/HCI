package at.ac.univie.cosy.hci_gruppe15.walkthisway;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nikollos on 14.05.2017.
 */

public class LegDistanceJSONParser {
    public static String getLegDistance(String jString) throws JSONException {
        JSONObject json = new JSONObject(jString);

        //Auslesen aus JSON file
        //Gehminuten werden als String zurückgegeben in der Form "19 mins"
        JSONArray routes = json.getJSONArray("routes");
        JSONObject route = routes.getJSONObject(0);
        JSONArray legs = route.getJSONArray("legs");
        JSONObject leg = legs.getJSONObject(0);
        JSONObject duration = leg.getJSONObject("duration");
        String minutes = duration.getString("text");
        //Log.i("JSON_LOG", minutes);

        return minutes;
    }
}
