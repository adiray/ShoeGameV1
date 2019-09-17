package com.example.dell.shoegamev1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import br.vince.easysave.EasySave;
import br.vince.easysave.SaveAsyncCallback;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.backendless.BackendlessUser;
import com.example.dell.shoegamev1.models.SubmittedUserObject;
import com.example.dell.shoegamev1.viewmodels.SignUpActivityViewModel;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;
import com.libizo.CustomEditText;

public class LogIn extends AppCompatActivity {


    //views
    CustomEditText eMailET, passwordET;
    Button submitDetailsBtn;
    CheckBox staySignedInCheckBox;

    //Strings
    String email, password;

    //booleans
    Boolean staySignedIn = false;

    //VIEW MODEL
    SignUpActivityViewModel signUpActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //get references to views

        eMailET = findViewById(R.id.activitySignInEmailEditText);
        passwordET = findViewById(R.id.activitySignInPasswordEditText);
        submitDetailsBtn = findViewById(R.id.activitySignInSubmitButton);
        staySignedInCheckBox = findViewById(R.id.activitySignInStaySignedInCheckBox);

        signUpActivityViewModel = ViewModelProviders.of(this).get(SignUpActivityViewModel.class);
        signUpActivityViewModel.init();


        submitDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logUserIn();
            }
        });



        signUpActivityViewModel.getNewSignInResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean){



                    if(signUpActivityViewModel.getNewlyRegisteredUser().getValue()!=null){

                        saveSignedInUserToCache(signUpActivityViewModel.getNewlySignedInUser().getValue());
                    }




                    //TODO Display success dialog and send user to profile.


                    new iOSDialogBuilder(LogIn.this)
                            .setTitle("Success")
                            .setSubtitle("You have logged in successfully!")
                            .setBoldPositiveLabel(true)
                            .setCancelable(true)
                            .setPositiveListener("okay", new iOSDialogClickListener() {
                                @Override
                                public void onClick(iOSDialog dialog) {

                                    dialog.dismiss();
                                }
                            })
                            .build().show();






                }

            }
        });

    }


    void logUserIn() {

        if (!TextUtils.isEmpty(eMailET.getText())) {

            if (!TextUtils.isEmpty(passwordET.getText())) {

                email = eMailET.getText().toString();
                password = passwordET.getText().toString();

                if (staySignedInCheckBox.isChecked()) {

                    staySignedIn = true;

                }

                SubmittedUserObject newlySubmitedUserObject = new SubmittedUserObject(email, password, staySignedIn);
                signUpActivityViewModel.logUserIn(newlySubmitedUserObject);


            } else {

                new iOSDialogBuilder(this)
                        .setTitle("Error")
                        .setSubtitle("Please enter your password")
                        .setBoldPositiveLabel(true)
                        .setCancelable(true)
                        .setPositiveListener("okay", new iOSDialogClickListener() {
                            @Override
                            public void onClick(iOSDialog dialog) {

                                dialog.dismiss();
                            }
                        })
                        .build().show();


            }

        } else {


            new iOSDialogBuilder(this)
                    .setTitle("Error")
                    .setSubtitle("Please make sure that all the details are entered")
                    .setBoldPositiveLabel(true)
                    .setCancelable(true)
                    .setPositiveListener("okay", new iOSDialogClickListener() {
                        @Override
                        public void onClick(iOSDialog dialog) {

                            dialog.dismiss();
                        }
                    })
                    .build().show();


        }


    }


    private void saveSignedInUserToCache(BackendlessUser user) {


        new EasySave(this).saveModelAsync("current signed in user", user, new SaveAsyncCallback<BackendlessUser>() {
            @Override
            public void onComplete(BackendlessUser user) {

                Log.d("MyLogsUser", "signed in user saved to cache. token: " + user.toString());

            }

            @Override
            public void onError(String s) {
                if (s != null) {
                    Log.d("MyLogsUser", "signed in user NOT saved to cache. error: " + s);
                }
            }
        });


    }


}



