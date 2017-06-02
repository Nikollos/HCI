package at.ac.univie.cosy.hci_gruppe15.walkthisway;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    // String s zur Übergabe von Info welcher Button gedrückt wurde,
    // um entsprechendes Listing zu öffnen
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* =========================================================================================*/

        //Buttons zur Auswahl der Kategorie + Übergabe von String s

        Context context = getApplicationContext();
        CharSequence text = "Wähle eine Kategorie und erhalte Sehenswürdigkeiten in deiner Nähe.";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        ImageButton kulturbtn = (ImageButton) findViewById(R.id.kulturbtn);

        kulturbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s="kultur";
                Intent intent = new Intent(MainActivity.this, Listing.class);
                intent.putExtra("value", s);
                startActivity(intent);
            }
        });

        ImageButton essentrinkenbtn = (ImageButton) findViewById(R.id.essentrinkenbtn);

        essentrinkenbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s="essentrinken";
                Intent intent = new Intent(MainActivity.this, Listing.class);
                intent.putExtra("value", s);
                startActivity(intent);
            }
        });

        ImageButton nightlifebtn = (ImageButton) findViewById(R.id.nightlifebtn);

        nightlifebtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s="nightlife";
                Intent intent = new Intent(MainActivity.this, Listing.class);
                intent.putExtra("value", s);
                startActivity(intent);
            }
        });

        ImageButton unterhaltungbtn = (ImageButton) findViewById(R.id.unterhaltungbtn);

        unterhaltungbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                s="unterhaltung";
                Intent intent = new Intent(MainActivity.this, Listing.class);
                intent.putExtra("value", s);
                startActivity(intent);
            }
        });

    }
}
