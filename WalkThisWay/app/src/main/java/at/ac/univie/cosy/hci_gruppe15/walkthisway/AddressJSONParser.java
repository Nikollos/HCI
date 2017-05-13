package at.ac.univie.cosy.hci_gruppe15.walkthisway;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nikollos on 13.05.2017.
 */

public class AddressJSONParser {
    public static String getAddress(String jString) throws JSONException {

        //JSONObjekt erstellen
        JSONObject json = new JSONObject(jString);

        //Adresse aus JSON-Array auslesen
        JSONArray results = json.getJSONArray("results");
        JSONObject o = results.getJSONObject(0);
        String formatted_address = o.getString("formatted_address");


        return formatted_address;
    }
}
