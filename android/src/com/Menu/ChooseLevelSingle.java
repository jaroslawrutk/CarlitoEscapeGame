package com.Menu;

import android.app.Activity;
import android.content.Context;
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

public class ChooseLevelSingle extends Activity {
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
        Context context;
        context = getApplicationContext();
        Intent intent= new Intent(context,AndroidLauncher.class);
        MyCallbackListener.sinlgePlay=1;

        class AsyncSinglePlayer extends AsyncTask<String,Void, Void> {
            @Override
            protected Void doInBackground(String... strings) {

                while(true){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(MyCallbackListener.end==1){
                        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName() );
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                        break;
                    }
                }
                return null;
            }
        }
        AsyncSinglePlayer asp = new AsyncSinglePlayer();
        asp.execute();
        startActivity(intent);
    }
}
