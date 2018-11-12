package com.android.esprit.smartreminders.Activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.esprit.smartreminders.Entities.User;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.Services.CallBackWSConsumer;
import com.android.esprit.smartreminders.Services.WebServiceUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Login extends AppCompatActivity {


    private RelativeLayout rellay1, rellay2;
    private Button LoginButton;


    private Handler handler ;
    private Runnable runnable ;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        runDelayedStarup();
        defineBehaviour();
    }
    private void defineBehaviour()
    {
        this.LoginButton = (Button) findViewById(R.id.LoginBtn);
        LoginButton.setOnClickListener((view) -> login());
    }
    private void runDelayedStarup()
    {
        handler= new Handler();
        runnable= () -> {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        };
        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
        handler.postDelayed(runnable, 2000); //2000 is the timeout for the splash
    }
    private void login()
    {
        WebServiceUser ws = new WebServiceUser(Login.this, new CallBackWSConsumer<User>() {
            @Override
            public void OnResultPresent(List<User> Users) {
                startActivity(new Intent(Login.this, Profile.class));
            }
            @Override
            public void OnResultNull() {

            }
        });
        Map<String,String> myMap = new HashMap<>();
        myMap.put("email",((TextView)findViewById(R.id.txusername)).getText().toString());
        myMap.put("password",((TextView)findViewById(R.id.txpwd)).getText().toString());
        ws.findBy(myMap);
    }
}
