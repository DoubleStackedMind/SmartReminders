package com.android.esprit.smartreminders.activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Singup extends AppCompatActivity {

    private CircularProgressButton SignBtn;
    private  EditText email;
    private EditText password;
    private Button LoginButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        defineBehaviour();
    }

    public void defineBehaviour() {
        this.SignBtn = findViewById(R.id.btn_signup);
        this.email = findViewById(R.id.input_email);
        this.password = findViewById(R.id.input_password);
        this.LoginButton =  findViewById(R.id.link_login);
        LoginButton.setOnClickListener(view->{   startActivity(new Intent(Singup.this,Login.class));});
        SignBtn.setOnClickListener(view -> {
            if(checkInputs()) register();
        });
    }

    public boolean checkInputs() {
        return !EditTextUtils.isInValid(
                new EditTextEmailValidator(this.email),
                new EditTextRequiredInputValidator(this.password)
        );
    }


    public void register() {// changed because it refers to the Name of the class (constructor call)
        SignBtn.startAnimation();
        WebServiceUser ws = new WebServiceUser(Singup.this, new CallBackWSConsumer<User>() {
            @Override
            public void OnResultPresent(List<User> result) {
                SignBtn.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp));
                System.out.println("problem nop ended");
                CharSequence text = "Account created !";

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getApplicationContext(), text, duration);

                toast.show();
              // will return with auto login and add to shared preferences after debugging
            }



            @Override
            public void OnHostUnreachable() {
                CharSequence text = getString(R.string.hostunreachable);

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getApplicationContext(), text, duration);

                toast.show();

                SignBtn.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_error_button));
                new Handler().postDelayed(
                        () -> SignBtn.revertAnimation(),
                        2000
                );
            }
        });
        User usr = new User();
        usr.setEmail(this.email.getText().toString());
        usr.setPassword(this.password.getText().toString());
        ws.insert(usr);
    }
}
