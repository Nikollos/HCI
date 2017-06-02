package at.ac.univie.cosy.hci_gruppe15.walkthisway;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Listing extends AppCompatActivity {

    String address;
    String minutes;
    String s;

    //Standort-Koordinaten
    double latitude;
    double longitude;

    public static final String ADDRESS = "ADDRESS";


    //TAG zum Debuggen
    String TAG = "MAIN_LOG: ";

    //Listview für die Sehenswürdigkeiten Liste
    ListView listView;

    //Textview zum ausgeben der Adresse
    TextView textView;

    //LocationManager und Listener zum berechnen der Standortkoordinaten
    private LocationManager locationManager;
    private LocationListener locationListener;

    //String x um Auswahl durch Button auszulesen
    String x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        textView = (TextView) findViewById(R.id.current_location_textview);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //LocationListener Konstruktor
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {

                //Berechnung der Koordinaten
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                //Url updaten
                updateAddress(latitude, longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        configureButton();


        //String x wird duch Intent ausgelesen
        Intent intent = getIntent();
        x = intent.getStringExtra("value");


        //Liste erstellen für die Sehenswürdigkeiten
        ArrayList<String> list = new ArrayList<>();

        //durch Switch-Cases festlegen, welche Liste angezeigt werden soll
        listView = (ListView) findViewById(R.id.list);
        switch (x) {
            case ("kultur"):
                list.add("Schloss Schönbrunn");
                list.add("Belvedere");
                list.add("Kunsthistorisches Museum");

                break;
            case ("essentrinken"):
                list.add("Bitzingers Augustinerkeller");
                list.add("Gerstner Cafe-Restaurant");
                list.add("Figlmüller");
                break;
            case ("nightlife"):
                list.add("Fluc");
                list.add("B72");
                list.add("Futuregarden");
                break;
            case ("unterhaltung"):
                list.add("Haus des Meeres");
                list.add("Volksoper");
                list.add("Volkstheater");
                break;
        }

        createUrl(list);

        //Adapter für die Listview erstellen
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.list_item_textview, list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                s = listView.getItemAtPosition(position).toString();

                //hier wird die Summary Activity aufgerufen
                Intent y = new Intent(Listing.this, Summary.class);
                y.putExtra("name", s);
                y.putExtra("minuten", minutes);
                startActivity(y);
            }
        });
    }

    public void createUrl(ArrayList<String> list){
        ArrayList<String> newlist = new ArrayList<>();
        for(String item : list) {
            item = item.replaceAll(" ", "+");
            item = item.concat(""+"+wien");
            newlist.add(item);
        }

        //hier stoppt die App immer aus irgendeinem Grund
        /*Intent intent = getIntent();
        String address = intent.getStringExtra("ADDRESS");
        Log.i(TAG, address);*/

        //HARDCODE TEST, weil ich es nicht geschafft habe die globale Variable address zu verwenden.
        String addressTest = "Währingerstraße+29+Wien";
        String url1 = "https://maps.googleapis.com/maps/api/directions/json?origin="+ addressTest + "&destination=" + newlist.get(0) +"4&key=AIzaSyBvmVXFnWpSLHGLiLZINkChy_xoJVtj3hI";

        LegDistanceHandler legDistanceHandler = new LegDistanceHandler();
        legDistanceHandler.execute(url1);
        //Log.i("STRING_TEST", address);
        Log.i("STRING_TEST", url1);
    }

    //Methode zum Updaten der URL, mit Koordinaten als Parameter
    private void updateAddress(double latitude, double longitude) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&key=AIzaSyBvmVXFnWpSLHGLiLZINkChy_xoJVtj3hI";

        MapHandler maphandler = new MapHandler();
        maphandler.execute(url);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configureButton();
                break;
            default:
                break;
        }
    }

    //Standortberechtigungen abfragen
    private void configureButton() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                }, 10);
            }
        } else {
            locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
        }
    }

    //Klasse zum Managen der Http-Verbindung und des JSON-Parsing
    public class MapHandler extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {
            //Http-Request
            Request.Builder builder = new Request.Builder();

            builder.url(params[0]);
            Request request = builder.build();
            try {
                //Http-Response
                Response response = client.newCall(request).execute();

                return response.body().string();
            } catch (Exception e) {
                Log.e(TAG, "HttpRequest Failed");
                Log.e(TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                //Koordinaten in Addressenstring umwandeln
                address = AddressJSONParser.getAddress(s);

                Intent intent = new Intent(getApplicationContext(), Listing.class);
                intent.putExtra("ADDRESS", address);
                //Adresse in Textfeld schreiben
                textView.append("\n " + address);

            } catch (JSONException e) {
                Log.e(TAG, "JSON Parse failed.");
                Log.e(TAG, e.getMessage());
            }
        }
    }

    //gleiches wie bei MapHandler, kümmert sich um Http connection und ruft JSONParser Klasse auf
    public class LegDistanceHandler extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {
            //Http-Request
            Request.Builder builder = new Request.Builder();

            builder.url(params[0]);
            Request request = builder.build();
            try {
                //Http-Response
                Response response = client.newCall(request).execute();

                return response.body().string();
            } catch (Exception e) {
                Log.e(TAG, "HttpRequest for calculating leg distance Failed");
                Log.e(TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                //ruft Klasse zum Auslesen der Gehminuten auf

               minutes = LegDistanceJSONParser.getLegDistance(s);

            } catch (JSONException e) {
                Log.e(TAG, "JSON Parsing failed");
                Log.e(TAG, e.getMessage());
            }

        }
    }
}
