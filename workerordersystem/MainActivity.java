package com.zohausman.workerordersystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static Object setOnClickLis;
    Button loginbtn, Regbtn;

    public static void setOnClickLis(View.OnClickListener onClickListener) {
    }

    protected void onCreate(Bundle savedInstanceState, Intent packageContext) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbtn = (Button) findViewById(R.id.loginbtn);
        Regbtn =  (Button) findViewById(R.id.Regbtn);

        //regesration form
        Regbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this,RegestrationActivity.class);
                startActivity(myintent);
            }



             });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(myintent);
            }
        });

        };
    }
