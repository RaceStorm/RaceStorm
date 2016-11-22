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

//Anfang der Klasse
public class MainActivity extends AppCompatActivity {


    //Anfang der main Methode
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sorry für die unnordnung werde das so schnell wie möglich zusammenfassen und übersichtlicher machen,
        // sobald ich alle fehler beseitigt und den code komplett fertig habe -Lars


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



    }//Ende der main Methode






 /* Funktioniert irgendwie nicht... suche noch lösung
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    */




















    //-----------------------------Bluetooth-------------------------------------------------------------------------------------

    //Muss den code noch anpassen und zusammenfassen

    //Methode bluetooth (managed die Bluetooth verbindung und ruft die weiteren methoden auf, wird aber noch verkürzt)
    public void bluetoothGeraete() {
        setContentView(R.layout.bluetooth);
        BT_Adapter = BluetoothAdapter.getDefaultAdapter();
        BT_PairedDevices = BT_Adapter.getBondedDevices();

        ArrayList<String> Liste = new ArrayList<String>();

        for (BluetoothDevice BT_Device : BT_PairedDevices) {
            Liste.add(new String(BT_Device.getName()));
        }

        final ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                Liste);

        ListView btListe = (ListView) findViewById(R.id.btListe);
        btListe.setAdapter(adapter);
        btListe.setOnItemClickListener(new myOnItemClickListener());
    }

    //---------------------------------------------------------------
    public void setStatus(String string) {
        TextView btGeraete = (TextView) findViewById(R.id.btGeraete);
        btGeraete.setText(string);
    }

    //---------------------------------------------------------------
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    BluetoothAdapter BT_Adapter;
    Set<BluetoothDevice> BT_PairedDevices;
    BluetoothDevice BT_Device;
    BluetoothSocket BT_Socket;
    OutputStream BT_OutStream;
    InputStream BT_InStream;

    /* Alter sendebefehl
    public void Send(View view) {
        setStatus("send to " + BT_Device.getName());

        byte[] buffer = new String("Hello World!nr").getBytes();

        try {
            BT_OutStream.write(buffer);
        } catch (IOException e) {
        }
    }
    */

    //---------------------------------------------------------------
    class myOnItemClickListener implements AdapterView.OnItemClickListener {
        //-----------------------------------------------------------
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) {
            setStatus("onItemClick:" + pos);

            BT_Device = (BluetoothDevice) BT_PairedDevices.toArray()[pos];

            try {
                BT_Socket = BT_Device.createRfcommSocketToServiceRecord(MY_UUID);
                BT_Socket.connect();

                BT_OutStream = BT_Socket.getOutputStream();
                BT_InStream = BT_Socket.getInputStream();

                setStatus("connected to " + BT_Device.getName() + " (" + BT_Device.getAddress() + " )");

            } catch (IOException e) {
                setStatus("not connected");
            }
        }
    }

    //--------------------------------------------Ende Bluetooth------------------------------------------------------------------------------


















}



