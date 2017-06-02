package at.ac.univie.cosy.hci_gruppe15.walkthisway;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Summary extends AppCompatActivity {

    //Hier sind Name der ausgewählten Sehenwürdigkeit und die Zeit die man hin braucht
    String name;
    String zeit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        ImageView btn_walk_button_icon= (ImageView) findViewById(R.id.iv_walk_button_icon);
        btn_walk_button_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Summary.this, Navigation.class);
                startActivity(intent);
            }
        });

        //ToDo  Holen von Deststinrg von Nici
        // Hier werden die Daten bzw. Informationen von Listing Activity geholt
        Intent intento = getIntent();
        name = intento.getStringExtra("name");
        zeit = intento.getStringExtra("minuten");

        TextView lbl_entfernung_text = (TextView) findViewById(R.id.lbl_entfernung);
        lbl_entfernung_text.setText(name);

            // URL Eigenschaften
            String urlBasis = "https://maps.googleapis.com/maps/api/place/details/json?";
            String placeID  = "placeid="    + "ChIJdWDYUZoHbUcRTq4MHzRMp2s" + "&";
            String apiKey   = "key="        + "AIzaSyAzdauCpmxKiik9Ey4leoOJGCtIgELm2mU" + "&";
            String language = "language="   + "de";
            String jsonURL = urlBasis + placeID + apiKey + language;
            //String jsonURL  = "https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJdWDYUZoHbUcRTq4MHzRMp2s&key=AIzaSyAzdauCpmxKiik9Ey4leoOJGCtIgELm2mU&language=de";

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(jsonURL )
                    .build();
            Call call = client.newCall(request);
            final String[] destination = new String[1];
            call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("a", "JSON Verbindungsfehler");
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String json = response.body().string();
                Summary.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //Quelle: https://www.tutorialspoint.com/android/android_json_parser.htm
                            // Erstellen von JSON Objekt und anschließend String parsing
                            JSONObject reader = new JSONObject(json);
                            if (reader.has("result")) {
                                JSONObject result = reader.getJSONObject("result");
                                // Befüllen von Destinations Name
                                TextView lbl_destName_text = (TextView) findViewById(R.id.lbl_destName);
                                //lbl_destName_text.setText(result.getString("name")); // kann auch von Json geholt werden
                                lbl_destName_text.setText(name);
                                //destination[0] = result.getString("name");    // kann auch von Json geholt werden
                                destination[0] = name;
                                // Befüllen von ist offen                   // befüllen von Status Feld
                                setDestImage(name);
                                String open_now = "nill";
                                if (result.has("opening_hours")) {
                                    // Hier wird mit Json
                                    JSONObject opening_hours = result.getJSONObject("opening_hours");
                                    if (opening_hours.has("open_now")) {
                                        open_now = opening_hours.getString("open_now");
                                        open_now = open_now.equals("false") ? "geschlossen" : "offen";
                                        TextView lbl_oezeiten_text = (TextView) findViewById(R.id.lbl_oezeiten);
                                        lbl_oezeiten_text.setText(open_now);
                                    }
                                }
                            }else {
                                Log.e("a", "Abfragefehler - URL oder Einstellungen überprüfen");
                            }
                        } catch (final JSONException e) {
                            Log.e("a", "JSON Parsfehler");
                        } catch (final ArithmeticException e){
                            Log.e("a", "JSON Arithmetische Fehler");
                        }catch (final Exception e){
                            Log.e("a", "JSON Fehler");
                        }
                    }
                });
            }
        });

        TextView lbl_oezeiten_text = (TextView) findViewById(R.id.lbl_entfernung);
        lbl_oezeiten_text.setText(zeit); //ToDo Entfernungsvariable hier eingtragen
        /*============================================================================================================================
            Wiki Button
         ============================================================================================================================*/
        ImageView btn_wiki_logo= (ImageView) findViewById(R.id.iv_wiki_logo);
        btn_wiki_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Summary.this, Browser.class);
                // Setzen von globalen Variable für Wiki Seite
                String wiki_base_url = "https://de.wikipedia.org/wiki/";
                String wiki_url_param ="";
                //ToDo Setzten von Vergleichsoptionen
                if (destination[0].equals(""))
                    wiki_url_param = "";
                else if (destination[0].equals("Schloss Schönbrunn"))
                    wiki_url_param = "Schloss_Schönbrunn";
                else if (destination[0].equals("Belvedere"))
                    wiki_url_param = "";
                else if (destination[0].equals("Kunsthistorisches Museum"))
                    wiki_url_param = "Kunsthistorisches_Museum";
                else if (destination[0].equals("Bitzingers Augustinerkeller"))
                    wiki_url_param = "";
                else if (destination[0].equals("Gerstner Cafe-Restaurant"))
                    wiki_url_param = "Café_Guerbois";
                else if (destination[0].equals("Figlmüller"))
                    wiki_url_param = "Hans_Figlmüller";
                else if (destination[0].equals("Fluc"))
                    wiki_url_param = "";
                else if (destination[0].equals("B72"))
                    wiki_url_param = "Weizer_Straße";
                else if (destination[0].equals("Futuregarden"))
                    wiki_url_param = "";
                else if (destination[0].equals("Haus des Meeres"))
                    wiki_url_param = "Haus_des_Meeres";
                else if (destination[0].equals("Volksoper"))
                    wiki_url_param = "Volksoper_Wien";
                else if (destination[0].equals("Volkstheater"))
                    wiki_url_param = "Volkstheater_(Wien)";
                String wiki_url = wiki_base_url + wiki_url_param ;
                intent.putExtra("var_website_url", String.valueOf (wiki_url));
                startActivity(intent);
                //Startet Google Chrome
                //startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.google.com")));
            }
        });
    }
    /*============================================================================================================================
    Methode: setDestImage für setzen von Sehenswürdigkeits Fotos
    ============================================================================================================================*/
    void setDestImage(String str){
        try {
                //ToDo str n erstezten durcz Dest name oder place_id
                ImageView iv_destionation = (ImageView) findViewById(R.id.iv_destionation_icon);
                switch (str) {
                    case "Schloss Schönbrunn": iv_destionation.setImageResource(R.drawable.schoenbrunn);
                        break;
                    case "Belvedere": iv_destionation.setImageResource(R.drawable.belvedere);
                        break;
                    case "Kunsthistorisches Museum": iv_destionation.setImageResource(R.drawable.kunsthistorischesmuseum);
                        break;
                    case "Bitzingers Augustinerkeller": iv_destionation.setImageResource(R.drawable.bitzingers);
                        break;
                    case "Gerstner Cafe-Restaurant": iv_destionation.setImageResource(R.drawable.gerstner);
                        break;
                    case "Figlmüller": iv_destionation.setImageResource(R.drawable.gerstner);
                        break;
                    case "Fluc": iv_destionation.setImageResource(R.drawable.fluc);
                        break;
                    case "B72": iv_destionation.setImageResource(R.drawable.b72);
                        break;
                    case "Futuregarden": iv_destionation.setImageResource(R.drawable.futuregarden);
                        break;
                    case "Haus des Meeres": iv_destionation.setImageResource(R.drawable.hausdesmeeres);
                        break;
                    case "Volksoper": iv_destionation.setImageResource(R.drawable.volksoper);
                        break;
                    case "Volkstheater": iv_destionation.setImageResource(R.drawable.volktheater);
                        break;
                }
        }catch (final Exception e){
        Log.e("a", e +" Set Image Error");
    }
        //lbl_oezeiten_text.setText("0 "+"km" ); //ToDo Entfernungsvariable hier eingtragen
    }
}
