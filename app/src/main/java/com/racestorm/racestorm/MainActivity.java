package com.racestorm.racestorm;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import static com.racestorm.racestorm.R.layout.optionen;
import static com.racestorm.racestorm.R.string.About;
import static java.util.logging.Logger.global;
//Ende der Imports



//@Benedikt schau mal ganz am ende die methode public void Send(View view) { an weil da ist der alte sende
//Befehl den du evtl nutzen kannst bzw umschreiben und dann in deinem code aufrufen kannst


//Anfang der Klasse
public class MainActivity extends AppCompatActivity {

    static int theme = 4;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //Anfang der main Methode
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(theme == 0) {
            setTheme(android.R.style.Theme_Holo_Light);

        } else if(theme == 1) {
            setTheme(android.R.style.Theme_Holo);
        }

        setContentView(R.layout.activity_main);

        start();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }//Ende der main Methode


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //-------------------------------Steuerung----------------------------------------------------------------------------------------
    public void steuerung() {
        setContentView(R.layout.steuerung);
        //Buttons deklarieren
        Button btnLinks = (Button) findViewById(R.id.btnLinks);
        Button btnRechts = (Button) findViewById(R.id.btnRechts);
        Button btnGas = (Button) findViewById(R.id.btnGas);
        Button btnBack = (Button) findViewById(R.id.btnBack);

        //Rechts Button
        btnRechts.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()== MotionEvent.ACTION_DOWN) {
                    SendLeft();
                    return true;

                }
                else if(event.getAction()== MotionEvent.ACTION_UP){
                    SendNothing();
                    return true;
                }
                return false;
            }
        });

        //Links Button
        btnLinks.setOnTouchListener(new View.OnTouchListener() {
           @Override
            public boolean onTouch(View v, MotionEvent event) {
               if (event.getAction()== MotionEvent.ACTION_DOWN) {
                   SendRight();
                   return true;

               }
               else if(event.getAction()== MotionEvent.ACTION_UP){
                   SendNothing();
                   return true;
               }
               return false;
           }
        });

        //Gas Button
        btnGas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()== MotionEvent.ACTION_DOWN) {
                    SendGas();
                    return true;

                }
                else if(event.getAction()== MotionEvent.ACTION_UP){
                    SendNothing();
                    return true;
                }
                return false;
            }
        });

        //Rückwärts Button
        btnBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()== MotionEvent.ACTION_DOWN) {
                    SendBack();
                    return true;

                }
                else if(event.getAction()== MotionEvent.ACTION_UP){
                    SendNothing();
                    return true;
                }
                return false;
            }
        });
    }
    //-----------------------------Steuerung--Ende-------------------------------------------------------------------------------------


    //-----------------------------Start----------------------------------------------------------------------------------------------

    public void start() {

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
                selectTheme();
            }
        });


        btnSteuerungOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public void selectSprachen() {
        CharSequence sprache[] = new CharSequence[]{"Deutsch", "English", "Russisch"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Language");
        builder.setItems(sprache, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    setLocale("de");
                } else if (which == 1) {
                    setLocale("en");
                } else if (which == 2) {
                    setLocale("ru");
                }

            }
        });
        builder.show();

    }



    public void selectTheme() {
        CharSequence selectTheme[] = new CharSequence[]{"Hell", "Dunkel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Theme");
        builder.setItems(selectTheme, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    MainActivity.theme = 0;
                    MainActivity.this.recreate();
                } else if (which == 1) {
                    MainActivity.theme = 1;
                    MainActivity.this.recreate();
                }

            }
        });
        builder.show();

    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }


    //-----------------------------Ende Optionen--------------------------------------------------------------------------------------------------


    //-----------------------------Bluetooth----------------------------------------------------------------------------------------------

    //Muss den code noch anpassen und zusammenfassen


    //Variablen werden definiert
    public void bluetoothGeraete() {

        setContentView(R.layout.bluetooth);
        createDeviceList();


    } //Ende bluetoothGeraete()

    public void setStatus(String string) {
        ((TextView) findViewById(R.id.btGeraete)).setText(string);
    }

    //---------------------------------------------------------------
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    BluetoothAdapter BT_Adapter;
    Set<BluetoothDevice> BT_PairedDevices;
    BluetoothDevice BT_Device;
    BluetoothSocket BT_Socket;
    OutputStream BT_OutStream;
    InputStream BT_InStream;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

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

    //---------------------------------------------------------------
    public void createDeviceList() {
        BT_Adapter = BluetoothAdapter.getDefaultAdapter();
        BT_PairedDevices = BT_Adapter.getBondedDevices();

        ArrayList<String> list = new ArrayList<String>();

        for (BluetoothDevice BT_Device : BT_PairedDevices) {
            list.add(new String(BT_Device.getName()));
        }

        final ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list);

        ListView lv = (ListView) findViewById(R.id.btListe);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new myOnItemClickListener());

        Button btnBtLos = (Button) findViewById(R.id.btnBtLos);
        btnBtLos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((((TextView) findViewById(R.id.btGeraete)).getText()).equals("not connected")) {
                } else if ((((TextView) findViewById(R.id.btGeraete)).getText()).equals("Gekoppelte Bluetooth Geräte :")) {
                } else {
                    steuerung();
                }

            }
        });

        Button btnBtBack = (Button) findViewById(R.id.btnBtBack);
        btnBtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                start();

            }
        });
    }
    //---------------------------------------------------------------------------------------------------------------------------------------

    /*
     *   Byte 0-1: Command length LSB first.
     *   Byte 2: Command type- direct command. For direct command with response message use 0x00, otherwise, for direct command without the reply message, use 0x80.
     *   Byte 3: Command- set motor output state.
     *   Byte 4: Motor output port. See explanation for motor output port below.
     *   Byte 5: Motor power set point. See explanation for motor power set point below.
     *   Byte 6: Motor mode byte (bit field). See explanation for motor byte below.
     *   Byte 7: Regulation mode. It is valid only when the motor mode is regulated, otherwise use 0x00 value. See explanation for regulation mode below.
     *   Byte 8: Turn ratio. It is valid only when using a motors synchronization regulation mode, otherwise use 0x00 value. See explanation for a turn ratio below.
     *   Byte 9: Run state. See explanation for Run state below.
     *   Byte 10-13: Tacho limit LSB first. Valid only when using a ramp-up or ramp-down as a Run stae, otherwise use 0x00 value. See explanation for tacho limit below.
     */
    public void SendLeft() {
        byte[] buffer2 = new byte[]{0x0C, 0x00, 0x00, 0x04, 0x02, 0x32, 0x05, 0x01, 0x32, 0x20, 0x00, 0x00, 0x00, 0x00};
        try {
            BT_OutStream.write(buffer2);

        } catch (IOException e) {
            setStatus("Error");
        }

    }

    public void SendNothing(){
        byte[] buffer3 = new byte[]{0x0C, 0x00, 0x00, 0x04,(byte)0xFF, 0x00, 0x05, 0x01, 0x32, 0x20, 0x00, 0x00, 0x00, 0x00};
        try {
            BT_OutStream.write(buffer3);

        } catch (IOException e) {
            setStatus("Error");
        }
    }

    public void SendRight() {
        byte[] buffer4 = new byte[]{0x0C, 0x00, 0x00, 0x04, 0x01, 0x32, 0x05, 0x01, 0x32, 0x20, 0x00, 0x00, 0x00, 0x00};
        try {
            BT_OutStream.write(buffer4);

        } catch (IOException e) {
            setStatus("Error");
        }
    }

    public void SendGas() {
        byte[] buffer5 = new byte[]{0x0C, 0x00, 0x00, 0x04, (byte) 0xFF, 0x32, 0x05, 0x01, 0x32, 0x20, 0x00, 0x00, 0x00, 0x00};
        try {
            BT_OutStream.write(buffer5);

        } catch (IOException e) {
            setStatus("Error");
        }
    }
    public void SendBack() {
        byte[] buffer6 = new byte[]{0x0C, 0x00, 0x00, 0x04, (byte)0xFF,(byte)0xCE, 0x05, 0x01, 0x32, 0x20, 0x00, 0x00, 0x00, 0x00};
        try {
            BT_OutStream.write(buffer6);

        } catch (IOException e) {
            setStatus("Error");
        }
    }

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



