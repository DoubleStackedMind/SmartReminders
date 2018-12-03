package com.android.esprit.smartreminders.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class Singup extends AppCompatActivity {

    private Button SignBtn;
    private  EditText email;
    private EditText password;

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

        SignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkInputs()) Signup();
            }
        });
    }

    public boolean checkInputs() {
        return !EditTextUtils.isInValid(
                new EditTextEmailValidator(this.email),
                new EditTextRequiredInputValidator(this.password)
        );
    }

    public void Signup() {
        WebServiceUser ws = new WebServiceUser(Singup.this, new CallBackWSConsumer<User>() {
            @Override
            public void OnResultPresent(List<User> result) {

            }

            @Override
            public void OnResultNull() {

            }

            @Override
            public void OnHostUnreachable() {

            }
        });
        User usr = new User();
        usr.setEmail(this.email.getText().toString());
        usr.setPassword(this.password.getText().toString());
        ws.insert(usr);
    }
}
