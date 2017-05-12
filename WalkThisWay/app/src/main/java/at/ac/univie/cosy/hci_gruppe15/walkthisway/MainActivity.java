package at.ac.univie.cosy.hci_gruppe15.walkthisway;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {


    // String s zur Übergabe von Info welcher Button gedrückt wurde,
    // um entsprechendes Listing zu öffnen
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* =========================================================================================*/
        // ToDo Temp Button => Abkürzung auf Aktivitäten für Testzwecke (NachTestphase Löschen)
        /* =========================================================================================*/
        final Button btn_sum = (Button) findViewById(R.id.btn_sum);
        btn_sum.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Summary.class);
                startActivity(intent);
            }
        });
        final Button btn_navi = (Button) findViewById(R.id.btn_navi);
        btn_navi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Summary.class);
                startActivity(intent);
            }
        });
        final Button btn_list = (Button) findViewById(R.id.btn_list);
        btn_list.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Listing.class);
                startActivity(intent);

                /*setTitle("Kultur");
                ListFragment listFragment = new ListFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction().replace(R.id.fragment_frame, listFragment).addToBackStack("Nearby").commit();*/

            }
        });
       /* =========================================================================================*/

        //Buttons zur Auswahl der Kategorie + Übergabe von String s

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
