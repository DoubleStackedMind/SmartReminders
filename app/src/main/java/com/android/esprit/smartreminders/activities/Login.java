package com.android.esprit.smartreminders.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.esprit.smartreminders.Entities.User;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.Services.CallBackWSConsumer;
import com.android.esprit.smartreminders.Services.WebServiceUser;
import com.android.esprit.smartreminders.appcommons.utils.EditTextUtils;
import com.android.esprit.smartreminders.appcommons.validator.EditTextEmailValidator;
import com.android.esprit.smartreminders.appcommons.validator.EditTextRequiredInputValidator;
import com.android.esprit.smartreminders.permissionHandlers.PermissionHandler;
import com.android.esprit.smartreminders.sessions.Session;

import org.json.JSONException;

import java.security.Permission;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class Login extends AppCompatActivity {


    private RelativeLayout rellay1, rellay2;
    private CircularProgressButton LoginButton;
    private EditText passwordTextView;
    private EditText usernameTextView;
    private Button SignupButton;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private Handler handler;
    private Runnable runnable;
    private User sessionUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        runDelayedStartup();
        if(!isThisDeviceAlreadyLoggedIn())
        defineBehaviour();
    }

    private boolean isThisDeviceAlreadyLoggedIn() {
        sharedPref = getSharedPreferences("Myprefs", MODE_PRIVATE);
        sessionUser=Session.getSession(this).getSessionUser();
        return sharedPref.getAll().size() != 0;

    }

    private void defineBehaviour() {

        this.LoginButton = (CircularProgressButton) findViewById(R.id.LoginBtn);
        this.passwordTextView = (EditText) findViewById(R.id.txpwd);
        this.usernameTextView = (EditText) findViewById(R.id.txusername);
        this.SignupButton = (Button) findViewById(R.id.Signup);

        LoginButton.setOnClickListener((view) -> {
            if (checkInputs()) login();
        });
        SignupButton.setOnClickListener(view -> startActivity(new Intent(Login.this,Singup.class)));
        PermissionHandler p = new PermissionHandler(this);

    }

    private void runDelayedStartup() {
        handler = new Handler();
        runnable = () -> {
            if(isThisDeviceAlreadyLoggedIn()) {
                startActivity(new Intent(Login.this,MainFrame.class));
            }
            else {
                rellay1.setVisibility(View.VISIBLE);
                rellay2.setVisibility(View.VISIBLE);
            }
        };
        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
        handler.postDelayed(runnable, 2000); //2000 is the timeout for the splash
    }
    public boolean checkInputs() {
        return !EditTextUtils.isInValid(
                new EditTextRequiredInputValidator(this.usernameTextView),
                new EditTextRequiredInputValidator(this.passwordTextView),
                new EditTextEmailValidator(this.usernameTextView)
        );
    }

    private void login() {

        LoginButton.startAnimation();
        WebServiceUser ws = new WebServiceUser(Login.this, new CallBackWSConsumer<User>() {
            @Override
            public void OnResultPresent(List<User> Users) {
                LoginButton.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp));
                CharSequence text = getString(R.string.sucessful_login);

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getApplicationContext(), text, duration);

                toast.show();

                new Handler().postDelayed(
                        () -> {
                            sharedPref = getSharedPreferences("Myprefs", MODE_PRIVATE);
                            editor = sharedPref.edit();
                            try {
                                editor.putString("Logged_user_data", Users.get(0).ToJsonObject().toString());
                               } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            editor.apply();
                            sessionUser=Users.get(0);
                            Session.getSession(Login.this).setSessionUser(Users.get(0));
                            System.out.println(Users.get(0));
                            startActivity(new Intent(Login.this, MainFrame.class));
                        },
                        2000
                );
            }

            @Override
            public void OnResultNull() {
                CharSequence text = getString(R.string.wrong_credentials);
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
                passwordTextView.getText().clear();
                LoginButton.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_error_button));
                new Handler().postDelayed(
                        () -> LoginButton.revertAnimation(),
                        2000
                );
            }

            @Override
            public void OnHostUnreachable() {
                CharSequence text = getString(R.string.hostunreachable);

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getApplicationContext(), text, duration);

                toast.show();
                passwordTextView.getText().clear();
                LoginButton.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_error_button));
                new Handler().postDelayed(
                        () -> LoginButton.revertAnimation(),
                        2000
                );
            }
        });
        Map<String, String> myMap = new HashMap<>();
        myMap.put("email", usernameTextView.getText().toString());
        myMap.put("password", passwordTextView.getText().toString());

            ws.findBy(myMap);



    }
}
