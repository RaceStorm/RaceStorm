package com.racestorm.racestorm;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
//Ende der Imports



//@Benedikt schau mal ganz am ende die methode public void Send(View view) { an weil da ist der alte sende
//Befehl den du evtl nutzen kannst bzw umschreiben und dann in deinem code aufrufen kannst




//Anfang der Klasse
public class MainActivity extends AppCompatActivity {


    //Anfang der main Methode
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
                bluetoothGeraete();
            }
        });

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionen();
            }
        });


        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                about();
            }
        });



    }//Ende der main Methode




    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }













    //-----------------------------Optionen----------------------------------------------------------------------------------------------
    public void optionen() {
        setContentView(R.layout.optionen);

        Button btnSprachen = (Button) findViewById(R.id.btnSprachenOption);
        Button btnThemen = (Button) findViewById(R.id.btnThemenOption);
        Button btnSteuerungOption = (Button) findViewById(R.id.btnSteuerungOption);
        Button btnOptionsBack = (Button) findViewById(R.id.btnOptionsBack);


        btnOptionsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               setContentView(R.layout.activity_main);
            }
        });


        btnSprachen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnThemen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnSteuerungOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    //-----------------------------Ende--------------------------------------------------------------------------------------------------







    //-----------------------------Bluetooth----------------------------------------------------------------------------------------------

    //Muss den code noch anpassen und zusammenfassen


    //Variablen werden definiert
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    BluetoothAdapter btAdapter;
    Set<BluetoothDevice> btGekoppelt;
    BluetoothDevice btGeraet;
    BluetoothSocket btSocket;
    OutputStream btOutput;
    InputStream btInput;
    TextView btGeraete = (TextView) findViewById(R.id.btGeraete);

    //Methode bluetooth (managed die Bluetooth verbindung und ruft die weiteren methoden auf, wird aber noch verkürzt)
    public void bluetoothGeraete() {

        setContentView(R.layout.bluetooth);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        btGekoppelt = btAdapter.getBondedDevices();

        ArrayList<String> Liste = new ArrayList<String>();

        for (BluetoothDevice btGeraet : btGekoppelt) {
            Liste.add(new String(btGeraet.getName()));
        }

        final ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                Liste);

        ListView btListe = (ListView) findViewById(R.id.btListe);
        btListe.setAdapter(adapter);
        btListe.setOnItemClickListener(new myOnItemClickListener());
    } //Ende bluetoothGeraete()


    //---------------------------------------------------------------------------------------------------------------------------------------



    class myOnItemClickListener implements AdapterView.OnItemClickListener {//Anfang class myOnItemClickListener
        //-----------------------------------------------------------
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) { //Anfang Methode
            btGeraete.setText("onItemClick:" + pos);

            btGeraet = (BluetoothDevice) btGekoppelt.toArray()[pos];

            try {
                btSocket = btGeraet.createRfcommSocketToServiceRecord(MY_UUID);
                btSocket.connect();

                btOutput = btSocket.getOutputStream();
                btInput = btSocket.getInputStream();

                btGeraete.setText("verbunden mit " + btGeraet.getName() + " (" + btGeraet.getAddress() + " )");

            } catch (IOException e) {
                btGeraete.setText("not connected");
            }
        } //Ende Methode OnItemClick
    }//ende class


    //---------------------------------------------------------------------------------------------------------------------------------------
    /* Alter sendebefehl
    public void Send(View view) {
        setStatus("send to " + btGeraet.getName());

        byte[] buffer = new String("Hello World!nr").getBytes();

        try {
            btOutput.write(buffer);
        } catch (IOException e) {
        }
    }
    */
    //--------------------------------------------Ende Bluetooth------------------------------------------------------------------------------




    //----------------------------------------About----------------------------------------------------------------------------------------------


    public void about() {
        setContentView(R.layout.about);

        Button btnBackAbout = (Button) findViewById(R.id.btnBackAbout);

        btnBackAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            setContentView(R.layout.activity_main);
            }
        });

    }
    //-----------------------------Ende----------------------------------------------------------------------------------------------





}



