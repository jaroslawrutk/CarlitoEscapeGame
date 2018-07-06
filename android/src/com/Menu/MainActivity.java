package com.Menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import com.Tools.MyCallbackListener;
import com.android.AndroidLauncher;
import com.brentaureli.mariobros.android.R;

/**
 * Created by romek95a on 14.05.2018.
 */
public class MainActivity extends Activity {
    ImageButton bWlaczBluetooth;
    ImageButton btn_info;
    Button bDolaczDoGry;
    Button bUtworzNowaGre;
    Button bMario;
    ServerBluetooth serwer;
    BluetoothAdapter ba;
    boolean czy=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bWlaczBluetooth=(ImageButton) findViewById(R.id.btnWlaczBt);
        btn_info=(ImageButton) findViewById(R.id.btn_info);
        bDolaczDoGry=(Button) findViewById(R.id.bDolaczDoGry);
        bUtworzNowaGre=(Button) findViewById(R.id.bUtworzNowaGre);
        bMario=(Button) findViewById(R.id.bMario);
        bWlaczBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wlaczBluetooth();
            }
        });
        ba =BluetoothAdapter.getDefaultAdapter();

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context;
                context = getApplicationContext();
                Intent intent = new Intent(context,Informations.class);
                startActivity(intent);
            }
        });

        bDolaczDoGry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ba.isEnabled()){
                    createBluetoothMessageDialog().show();
                }
                else{
                    Context context;
                    context = getApplicationContext();
                    Intent intent = new Intent(context,ChooseLevelClient.class);
                    startActivity(intent);
                }
            }
        });
        bUtworzNowaGre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!ba.isEnabled()){
                    createBluetoothMessageDialog().show();
                }
                else{
                    Intent intent;
                    Context context;
                    context = getApplicationContext();
                    intent= new Intent(context,ChooseLevelServer.class);
                    startActivity(intent);
                }
            }
        });
        bMario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context;
                context = getApplicationContext();
                Intent intent = new Intent(context,ChooseLevelSingle.class);
                startActivity(intent);
            }
        });
    }
    private Dialog createPlainAlertDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Łączenie");
        dialogBuilder.setMessage("Poczekaj na połączenie z drugim graczem");
        dialogBuilder.setNegativeButton("Anuluj", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                //reset
                Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName() );
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });
        dialogBuilder.setCancelable(false);
        return dialogBuilder.create();
    }
    private Dialog createBluetoothMessageDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Bluetooth nie działa");
        dialogBuilder.setMessage("Może najpierw włącz bluetooth, co?");
        dialogBuilder.setNegativeButton("OK", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                //w sumie to nic nie musi robić
            }
        });
        dialogBuilder.setCancelable(false);
        return dialogBuilder.create();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i){
        if(resultCode==Activity.RESULT_OK){
            BluetoothAdapter ba=BluetoothAdapter.getDefaultAdapter();
        }
    }
    void wlaczBluetooth(){
        Intent pokazSie=new Intent (BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        pokazSie.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(pokazSie);
    }

    private Dialog disconnectDialog() {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("AAAAAAAAA");
        dialogBuilder.setMessage("Rozłączyło cię z przeciwnikiem");
        dialogBuilder.setNegativeButton("To pszypau", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                //reset

            }
        });
        dialogBuilder.setCancelable(false);
        return dialogBuilder.create();
    }

}
