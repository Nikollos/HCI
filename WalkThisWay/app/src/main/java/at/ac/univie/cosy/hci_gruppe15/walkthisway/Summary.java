package at.ac.univie.cosy.hci_gruppe15.walkthisway;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Summary extends AppCompatActivity {

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

        ImageView btn_wiki_logo= (ImageView) findViewById(R.id.iv_wiki_logo);
        btn_walk_button_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Summary.this, Browser.class);
                // Setzen von globalen Variable
                intent.putExtra("var_website_url", String.valueOf ("https://www.saldo.at"));
                startActivity(intent);
                //Startet Google Chrome
                //startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.google.com")));
            }
        });


    }

}
