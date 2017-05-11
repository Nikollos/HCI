package at.ac.univie.cosy.hci_gruppe15.walkthisway;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Listing extends AppCompatActivity {

    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);

        listView = (ListView) findViewById(R.id.list);

        String[] culture = new String[] {
                "Schloss Schönbrunn",
                "Belvedere",
                "Kunsthistorisches Museum"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.list_item_textview, culture);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPostition = position;

                String itemValue = (String) listView.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),
                        "Position:" + itemPostition + "ListItem:" +itemValue, Toast.LENGTH_LONG).show();
            }
        });
    }

}
