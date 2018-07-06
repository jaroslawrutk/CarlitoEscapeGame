package com.Menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Tools.MyCallbackListener;
import com.android.AndroidLauncher;
import com.brentaureli.mariobros.android.R;

/**
 * Created by romek95a on 05.07.2018.
 */

public class ChooseLevelServer extends Activity {

    Button level1;
    Button level2;
    ServerBluetooth serwer;
    boolean czy=false;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_level_activity);
        level1=(Button) findViewById(R.id.level1);
        level2=(Button) findViewById(R.id.level2);

        level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCallbackListener.level="level1.tmx";
                startGame();
            }
        });
        level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCallbackListener.level="level2.tmx";
                startGame();
            }
        });
    }
    public void startGame(){
        //startuje wątek z serwerem
        serwer=new ServerBluetooth();
        if(!serwer.isAlive()){
            serwer.start();
        }
        createPlainAlertDialog().show();
        //jeśli połączono-startuje aktywnosc z gra, dopoki nie-wyswietla sie dialog z laczeniem i anuluj
        class AsyncSerwer extends AsyncTask<String,Void, Void> {
            @Override
            protected Void doInBackground(String... strings) {
                Context context;
                Intent intent;
                while(true){
                    if (serwer.polaczono.equals("Połączono")){
                        context = getApplicationContext();
                        intent= new Intent(context,AndroidLauncher.class);
                        System.out.println("serwer polaczony");
                        break;
                    }
                }
                intent.putExtra("skin", "KARLITO");
                startActivity(intent);

                //polaczenie:
                while(true){
                    //serwer wysyla
                    serwer.write(Float.toString(MyCallbackListener.sendWsp));
                    //serwer odbiera
                    MyCallbackListener.receiveWsp=Float.parseFloat(serwer.wiadPrzych);
                    //
                    if(ClientBluetooth.disconnect || ServerBluetooth.disconnect){
                        Intent intent2 = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName() );
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent2);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                        czy=true;
                        disconnectDialog().show();
                    }

                    if(MyCallbackListener.end==1){
                        Intent intent2 = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName() );
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent2);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                        break;
                    }
                }
                return null;
            }
        }
        AsyncSerwer as = new AsyncSerwer();
        as.execute();
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
}
