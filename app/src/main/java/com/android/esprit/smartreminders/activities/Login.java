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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;


public class Login extends AppCompatActivity {


    private RelativeLayout rellay1, rellay2;
    private CircularProgressButton LoginButton;
    private EditText passwordTextView;
    private EditText usernameTextView;
    //private Button
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private Handler handler;
    private Runnable runnable;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        runDelayedStartup();
        defineBehaviour();
    }

    private boolean isThisDeviceAlreadyLoggedIn() {
        sharedPref = getSharedPreferences("Myprefs", MODE_PRIVATE);
        return sharedPref.getAll().size() != 0;

    }

    private void defineBehaviour() {

        this.LoginButton = (CircularProgressButton) findViewById(R.id.LoginBtn);
        this.passwordTextView = (EditText) findViewById(R.id.txpwd);
        this.usernameTextView = (EditText) findViewById(R.id.txusername);
        LoginButton.setOnClickListener((view) -> {
            if (checkInputs()) login();
        });
        if (isThisDeviceAlreadyLoggedIn()) {
            sharedPref = getSharedPreferences("Myprefs", MODE_PRIVATE);
            String data = sharedPref.getString("Logged_user_data", "User name or data missing");
            this.usernameTextView.setText(data.substring(0, data.indexOf("\n")));
            this.passwordTextView.setText(data.substring(data.indexOf("\n") + 1, data.length()));
            System.out.println("ppppppppppppppppppppppppppppp +" + data.substring(data.indexOf("\n") + 1, data.length()));
            new Handler().postDelayed(()->{
            if (checkInputs()) login();},
        2030);
        }
    }

    private void runDelayedStartup() {
        handler = new Handler();
        runnable = () -> {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
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

                setProgressBarIndeterminateVisibility(false);
                System.out.println("problem nop");
                LoginButton.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp));
                System.out.println("problem nop ended");
                CharSequence text = getString(R.string.sucessful_login);

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getApplicationContext(), text, duration);

                toast.show();
                System.out.println("problem nop ended");
                new Handler().postDelayed(

                        () -> {
                            System.out.println("start of handler");
                            sharedPref = getSharedPreferences("Myprefs", MODE_PRIVATE);
                            editor = sharedPref.edit();
                            editor.putString("Logged_user_data", Login.this.usernameTextView.getText() + "\n" + Login.this.passwordTextView.getText());
                            editor.apply();
                            startActivity(new Intent(Login.this, MainFrame.class));
                            System.out.println("end of handler");
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
