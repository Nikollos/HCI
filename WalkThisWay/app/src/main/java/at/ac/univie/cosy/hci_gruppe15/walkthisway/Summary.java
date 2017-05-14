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

        Intent intento = getIntent();
        name = intento.getStringExtra("name");
        zeit = intento.getStringExtra("minuten");




        setDestImage("");
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
                                lbl_destName_text.setText(result.getString("name"));
                                destination[0] = result.getString("name");
                                // Befüllen von ist offen
                                String open_now = "nill";
                                if (result.has("opening_hours")) {
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
                else if (destination[0].equals("Burggarten"))
                    wiki_url_param = "Burggarten_(Wien)";
                else if (destination[0].equals("etc"))
                    wiki_url_param = "";
                else if (destination[0].equals(""))
                    wiki_url_param = "";
                else if (destination[0].equals(""))
                    wiki_url_param = "";
                else if (destination[0].equals(""))
                    wiki_url_param = "";
                else if (destination[0].equals(""))
                    wiki_url_param = "";
                else if (destination[0].equals(""))
                    wiki_url_param = "";
                else if (destination[0].equals(""))
                    wiki_url_param = "";
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
                    case "str1": iv_destionation.setImageResource(R.drawable.clock_button);
                        break;
                    case "str2": iv_destionation.setImageResource(R.drawable.clock_button);
                        break;
                    case "str3": iv_destionation.setImageResource(R.drawable.clock_button);
                        break;
                    case "str4": iv_destionation.setImageResource(R.drawable.clock_button);
                        break;
                    case "str5": iv_destionation.setImageResource(R.drawable.clock_button);
                        break;
                    case "str6": iv_destionation.setImageResource(R.drawable.clock_button);
                        break;
                    case "str7": iv_destionation.setImageResource(R.drawable.clock_button);
                        break;
                    case "str8": iv_destionation.setImageResource(R.drawable.clock_button);
                        break;
                    case "str9": iv_destionation.setImageResource(R.drawable.clock_button);
                        break;
                }
                iv_destionation.setImageResource(R.drawable.clock_button);
        }catch (final Exception e){
        Log.e("a", e +" Set Image Error");
    }
        //lbl_oezeiten_text.setText("0 "+"km" ); //ToDo Entfernungsvariable hier eingtragen


    }
}
