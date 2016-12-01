package com.racestorm.racestorm;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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

    start();
    }//Ende der main Methode




    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //-------------------------------Steuerung----------------------------------------------------------------------------------------
    public void steuerung(){
        setContentView(R.layout.steuerung);
        //Buttons deklarieren
        Button btnLinks = (Button) findViewById(R.id.btnLinks) ;
        Button btnRechts = (Button) findViewById(R.id.btnRechts) ;
        Button btnGas  = (Button) findViewById(R.id.btnGas) ;

        //Links Button
        btnLinks.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                }
        });

    }
    //-----------------------------Steuerung--Ende-------------------------------------------------------------------------------------



    //-----------------------------Start----------------------------------------------------------------------------------------------

    public void start(){

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
        //Aktion bei klickens des Option Buttons
        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionen();
            }
        });

        //Aktion bei klickens des About Buttons
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                about();
            }
        });
    }


    //-----------------------------Ende Start----------------------------------------------------------------------------------------------




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
               start();
            }
        });





        btnSprachen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectSprachen();
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

    public void selectSprachen(){
        CharSequence sprache[] = new CharSequence[] {"Deutsch", "English"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Language");
        builder.setItems(sprache, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
            }
        });
        builder.show();

    }


    //-----------------------------Ende Optionen--------------------------------------------------------------------------------------------------







    //-----------------------------Bluetooth----------------------------------------------------------------------------------------------

    //Muss den code noch anpassen und zusammenfassen


    //Variablen werden definiert
    public void bluetoothGeraete() {

        setContentView(R.layout.bluetooth);
        createDeviceList();
    } //Ende bluetoothGeraete()

    public void setStatus(String string)
    {
        ((TextView) findViewById(R.id.btGeraete)).setText(string);
    }

    //---------------------------------------------------------------
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    BluetoothAdapter     BT_Adapter;
    Set<BluetoothDevice> BT_PairedDevices;
    BluetoothDevice      BT_Device;
    BluetoothSocket      BT_Socket;
    OutputStream         BT_OutStream;
    InputStream          BT_InStream;

    //---------------------------------------------------------------
    class myOnItemClickListener implements AdapterView.OnItemClickListener
    {
        //-----------------------------------------------------------
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id)
        {
            setStatus("onItemClick:"+pos);

            BT_Device = (BluetoothDevice)BT_PairedDevices.toArray()[pos];

            try
            {
                BT_Socket  = BT_Device.createRfcommSocketToServiceRecord(MY_UUID);
                BT_Socket.connect();

                BT_OutStream = BT_Socket.getOutputStream();
                BT_InStream  = BT_Socket.getInputStream();

                setStatus( "connected to "+BT_Device.getName()+" ("+BT_Device.getAddress()+" )");

            }
            catch(IOException e)
            {
                setStatus( "not connected" );
            }
        }
    }

    //---------------------------------------------------------------
    public void createDeviceList()
    {
        BT_Adapter       = BluetoothAdapter.getDefaultAdapter();
        BT_PairedDevices = BT_Adapter.getBondedDevices();

        ArrayList<String> list = new ArrayList<String>();

        for(BluetoothDevice BT_Device : BT_PairedDevices)
        {
            list.add(new String(BT_Device.getName()));
        }

        final ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list);

        ListView lv = (ListView)findViewById(R.id.btListe);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener( new myOnItemClickListener() );
    }
    //---------------------------------------------------------------------------------------------------------------------------------------

    /*
    public void Send(View view)
    {
        setStatus("send to "+BT_Device.getName());

        byte[] buffer = new String("Hello World!nr").getBytes();

        try
        {
            BT_OutStream.write(buffer);
        }
        catch (IOException e)
        {
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
            start();
            }
        });

    }
    //-----------------------------Ende About----------------------------------------------------------------------------------------------





}



