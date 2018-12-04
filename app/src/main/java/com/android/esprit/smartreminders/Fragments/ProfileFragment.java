package com.android.esprit.smartreminders.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.esprit.smartreminders.Entities.User;
import com.android.esprit.smartreminders.R;
import com.android.esprit.smartreminders.Services.CallBackWSConsumer;
import com.android.esprit.smartreminders.Services.CallBackWSConsumerSend;
import com.android.esprit.smartreminders.Services.WebServiceConsumer;
import com.android.esprit.smartreminders.Services.WebServiceUser;
import com.android.esprit.smartreminders.activities.Login;
import com.android.esprit.smartreminders.activities.MainFrame;
import com.android.esprit.smartreminders.activities.Profile;
import com.android.esprit.smartreminders.appcommons.utils.EditTextUtils;
import com.android.esprit.smartreminders.appcommons.validator.EditTextEmailValidator;
import com.android.esprit.smartreminders.appcommons.validator.EditTextRequiredInputValidator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends FragmentChild {

    private TextInputLayout passwordLayout;
    private EditText password;
    private EditText email;
    private EditText name;
    private CircularProgressButton updateProfile;
    private WebServiceUser WS;
    private User sessionUser;
    private SharedPreferences sharedPref;
    private boolean ReadOnlyMode;
    private String curr_password;
    private String curr_email;
    private String curr_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setBehaviour();
        ButtonBehaviour();


    }

    public boolean checkInputs() {
        return !EditTextUtils.isInValid(
                new EditTextRequiredInputValidator(this.password),
                new EditTextRequiredInputValidator(this.name),
                new EditTextEmailValidator(this.email)
        );
    }

    private void ButtonBehaviour() {
        ReadOnlyMode = true;
        updateProfile.setOnClickListener(v -> {
            if (!SomethingChanged()) {
                if (ReadOnlyMode) {
                    updateProfile.setText("Cancel");
                    setEnabledInputs(true);
                } else {
                    updateProfile.setText("Update Profile");
                    setEnabledInputs(false);
                }
                ReadOnlyMode = !ReadOnlyMode;
            } else {
                if (checkInputs()) {
                    updateLocalChanges();
                    updateDistantChanges();
                    this.updateProfile.startAnimation();
                }

            }

        });

    }

    private void enableSyncMode() {
        updateProfile.setText("Submit Changes");
        setEnabledInputs(true);
    }

    private void InputsBehaviour() {

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                System.out.println("changed focus!");

                    if (SomethingChanged())
                        enableSyncMode();


            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                System.out.println("changed focus!");

                    if (SomethingChanged())
                        enableSyncMode();


            }
        });
        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                System.out.println("changed focus!");

                    if (SomethingChanged())
                        enableSyncMode();


            }
        });
    }


    private boolean SomethingChanged() {
        System.out.println(email.getText()+"  "+name.getText()+"  "+password.getText());
        return !(curr_email.equals(email.getText().toString())&& curr_name.equals(name.getText().toString()) && curr_password.equals(password.getText().toString()));
    }

    private void setEnabledInputs(boolean mode) {
        email.setEnabled(mode);
        password.setEnabled(mode);
        name.setEnabled(mode);
    }

    private void setBehaviour() {
        email = this.getParentActivity().findViewById(R.id.Email_editText);
        password = this.getParentActivity().findViewById(R.id.Password_editText);
        name = this.getParentActivity().findViewById(R.id.Name_editText);
        updateProfile = this.getParentActivity().findViewById(R.id.updateProfile);
        setEnabledInputs(false);


        WS = new WebServiceUser(this.getParentActivity(), new CallBackWSConsumer<User>() {

            @Override
            public void OnResultPresent(List<User> result) {
                sessionUser = result.get(0);
                System.out.println(sessionUser);
                insertProfileData();
            }

            @Override
            public void OnHostUnreachable() {
                CharSequence text = "Server is Down Try Again Later";

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(ProfileFragment.this.getParentActivity().getApplicationContext(), text, duration);

                toast.show();
            }
        });

        sharedPref = this.getParentActivity().getSharedPreferences("Myprefs", MODE_PRIVATE);
        String data = sharedPref.getString("Logged_user_data", "User name or data missing");


        Map<String, String> myMap = new HashMap<>();
        myMap.put("email", data.substring(0, data.indexOf("\n")));
        myMap.put("password", data.substring(data.indexOf("\n") + 1, data.length()));
        WS.findBy(myMap);

    }

    private void insertProfileData() {

        this.curr_password = sessionUser.getPassword();
        this.curr_name = sessionUser.getName();
        this.curr_email = sessionUser.getEmail();
        this.password.setText(sessionUser.getPassword());
        this.name.setText(sessionUser.getName());
        this.email.setText(sessionUser.getEmail());
        InputsBehaviour();
    }

    private void updateLocalChanges() {
        this.curr_password = this.password.getText().toString();
        this.curr_email = this.email.getText().toString();
        this.curr_name = this.name.getText().toString();
        this.sessionUser.setEmail(curr_email);
        this.sessionUser.setName(curr_name);
        this.sessionUser.setPassword(curr_password);
    }

    private void updateDistantChanges() {

        WS.SetBehaviour(new CallBackWSConsumerSend<User>() {
            @Override
            public void OnResultPresent() {

                ProfileFragment.this.updateProfile.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp));
                CharSequence text ="Updated!";

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(ProfileFragment.this.getParentActivity().getApplicationContext(), text, duration);

                toast.show();
                new Handler().postDelayed(()->{
                    ProfileFragment.this.updateProfile.revertAnimation();
                } , 2000   );
            }

            @Override
            public void OnResultNull() {
                ProfileFragment.this.updateProfile.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_error_button));
                CharSequence text ="Server under maintenance";

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(ProfileFragment.this.getParentActivity().getApplicationContext(), text, duration);

                toast.show();
                new Handler().postDelayed(()->{
                    ProfileFragment.this.updateProfile.revertAnimation();
                } , 2000   );
            }

            @Override
            public void OnHostUnreachable() {
                ProfileFragment.this.updateProfile.doneLoadingAnimation(Color.parseColor("#333639"), BitmapFactory.decodeResource(getResources(), R.drawable.ic_error_button));
                CharSequence text ="Host Unreachable";

                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(ProfileFragment.this.getParentActivity().getApplicationContext(), text, duration);

                toast.show();
                new Handler().postDelayed(()->{
                    ProfileFragment.this.updateProfile.revertAnimation();
                } , 2000   );
            }




        });
        WS.update(sessionUser);

    }


}
