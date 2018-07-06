package com.Menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.brentaureli.mariobros.android.R;

/**
 * Created by kat on 05.07.2018.
 */


public class Informations extends Activity {
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informations);
        back =  (Button) findViewById(R.id.btn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context;
                context = getApplicationContext();
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}