package com.android.esprit.smartreminders;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.esprit.smartreminders.Entities.User;
import com.android.esprit.smartreminders.Services.WebServiceUser;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {


    RelativeLayout rellay1, rellay2;


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//test
        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
        handler.postDelayed(runnable, 2000); //2000 is the timeout for the splash

        final Button LoginBtn = (Button) findViewById(R.id.LoginBtn);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebServiceUser ws = new WebServiceUser(getApplicationContext());
                User usr = new User();
                Map<String,String> myMap = new HashMap<>();
                myMap.put("email",((TextView)findViewById(R.id.txusername)).getText().toString());
                myMap.put("password",((TextView)findViewById(R.id.txpwd)).getText().toString());
                try {
                    usr = ws.findBy(myMap);
                    if(usr != null) {
                        startActivity(new Intent(Login.this, Profile.class));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
