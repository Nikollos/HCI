package at.ac.univie.cosy.hci_gruppe15.walkthisway;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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
                Intent intent = new Intent(MainActivity.this, Summary.class);
                startActivity(intent);
            }
        });
       /* =========================================================================================*/
    }
}
