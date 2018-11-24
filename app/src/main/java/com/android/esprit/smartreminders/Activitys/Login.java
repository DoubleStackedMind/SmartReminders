package com.android.esprit.smartreminders.Activitys;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import br.com.simplepass.loading_button_lib.customViews.CircularProgressImageButton;


public class Login extends AppCompatActivity {


    private RelativeLayout rellay1, rellay2;
    private CircularProgressButton LoginButton;
    private EditText passwordTextView;
    private EditText usernameTextView;
    //private Button


    private Handler handler;
    private Runnable runnable;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        runDelayedStarup();
        defineBehaviour();
    }

    private void defineBehaviour() {
        this.LoginButton = (CircularProgressButton) findViewById(R.id.LoginBtn);
        this.passwordTextView = (EditText) findViewById(R.id.txpwd);
        this.usernameTextView = (EditText) findViewById(R.id.txusername);
        LoginButton.setOnClickListener((view) ->{if(checkInputs()) login();});
    }

    private void runDelayedStarup() {
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

        private void login () {

            LoginButton.startAnimation();
            WebServiceUser ws = new WebServiceUser(Login.this, new CallBackWSConsumer<User>() {
                @Override
                public void OnResultPresent(List<User> Users) {

                    setProgressBarIndeterminateVisibility(false);

                    LoginButton.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp));
                    CharSequence text = getString(R.string.sucessful_login);

                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getApplicationContext(), text, duration);

                    toast.show();
                    new Handler().postDelayed(
                            () -> startActivity(new Intent(Login.this, Profile.class)),
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
                    new Handler().postDelayed(() -> {
                        LoginButton.revertAnimation();
                    }, 2000);

                }
            });
            Map<String, String> myMap = new HashMap<>();
            myMap.put("email", usernameTextView.getText().toString());
            myMap.put("password", passwordTextView.getText().toString());
            ws.findBy(myMap);


        }
    }
