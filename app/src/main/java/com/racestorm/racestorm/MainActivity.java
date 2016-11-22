package com.racestorm.racestorm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialisierung der Buttons der main_activity
        Button btnStart = (Button) findViewById(R.id.btnStart);
        Button btnOptions = (Button) findViewById(R.id.btnOptions);
        Button btnAbout = (Button) findViewById(R.id.btnAbout);

        //Aktion bei klickens des Start Buttons
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetooth();

            }
        });



    }

    //Methode bluetooth (managed die Bluetooth verbindung)
    public void bluetooth(){
        setContentView(R.layout.bluetooth);

    }



}
