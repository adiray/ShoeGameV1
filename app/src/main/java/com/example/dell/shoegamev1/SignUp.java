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

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SignUp extends AppCompatActivity {


    //VIEWS
    CustomEditText firstNameET, lastNameET, eMailET, phoneET, passwordET;
    Button submitDetailsBtn;
    CheckBox staySignedInCheckBox, termsAndConditionsCheckBox;
    NiceSpinner genderSpinner;

    //VARIABLES
    String firstName, lastName, eMail, phone, password;
    Boolean staySignedIn = false, agreeToTermsAndConditions = false;
    Integer gender = 1;  //none selected:1, male:2, female:3
    Map<String, Object> userDetailsMap = new HashMap<>();  //used to create a backendless user object


    //VIEW MODEL
    SignUpActivityViewModel signUpActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        //get references to views
        firstNameET = findViewById(R.id.activitySignUpFirstNameEditText);
        lastNameET = findViewById(R.id.activitySignUpLastNameEditText);
        eMailET = findViewById(R.id.activitySignUpEmailEditText);
        phoneET = findViewById(R.id.activitySignUpPhoneEditText);
        passwordET = findViewById(R.id.activitySignUpPasswordEditText);
        genderSpinner = findViewById(R.id.activitySignUpGenderSpinner);
        submitDetailsBtn = findViewById(R.id.activitySignUpSubmitButton);
        staySignedInCheckBox = findViewById(R.id.activitySignUpStaySignedInCheckBox);
        termsAndConditionsCheckBox = findViewById(R.id.activitySignUpTermsCheckBox);


        //set up gender options spinner
        List<String> dataset = new LinkedList<>(Arrays.asList("Male", "Female"));
        genderSpinner.attachDataSource(dataset);
        genderSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {

                String genderString = (String) parent.getItemAtPosition(position);

                if (genderString.equals("Male")) {

                    gender = 2;

                } else if (genderString.equals("Female")) {

                    gender = 3;

                }


            }
        });


        signUpActivityViewModel = ViewModelProviders.of(this).get(SignUpActivityViewModel.class);
        signUpActivityViewModel.init();

        signUpActivityViewModel.getSignUpResult().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {

                    saveUserToCache(signUpActivityViewModel.getNewlyRegisteredUser().getValue());
                }


            }
        });


        //set up on click listeners
        submitDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createSubmittedUserObject();
            }
        });


    }


    private void createSubmittedUserObject() {

        if (!TextUtils.isEmpty(firstNameET.getText())) {

            if (!TextUtils.isEmpty(lastNameET.getText())) {


                if (!TextUtils.isEmpty(eMailET.getText())) {


                    if (!TextUtils.isEmpty(phoneET.getText())) {


                        if (!TextUtils.isEmpty(passwordET.getText())) {


                            if (termsAndConditionsCheckBox.isChecked()) {

                                firstName = firstNameET.getText().toString();
                                lastName = lastNameET.getText().toString();
                                eMail = eMailET.getText().toString();
                                phone = phoneET.getText().toString();
                                password = passwordET.getText().toString();

                                if (staySignedInCheckBox.isChecked()) {

                                    staySignedIn = true;

                                }

                                SubmittedUserObject thisSubmittedUserObject = new SubmittedUserObject(firstName, lastName, eMail, phone,
                                        password, staySignedIn, agreeToTermsAndConditions, gender);


                                userDetailsMap.put("gender", gender);
                                userDetailsMap.put("email", eMail);
                                userDetailsMap.put("first_name", firstName);
                                userDetailsMap.put("last_name", lastName);
                                userDetailsMap.put("password", password);
                                userDetailsMap.put("phone_number", phone);

                                BackendlessUser thisBackendlessUser = new BackendlessUser();
                                thisBackendlessUser.putProperties(userDetailsMap);


                                signUpActivityViewModel.registerNewUser(thisBackendlessUser, thisSubmittedUserObject);


                            } else {


                                new iOSDialogBuilder(this)
                                        .setTitle("Error")
                                        .setSubtitle("Please submit all required details")
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
                                    .setSubtitle("Please choose a password")
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
                                .setSubtitle("Please enter a valid phone number")
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
                            .setSubtitle("Please enter your E-mail")
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
                        .setSubtitle("Please enter your last name")
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
                    .setSubtitle("Please enter your first name")
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


    private void saveUserToCache(BackendlessUser user) {

        new EasySave(this).saveModelAsync("current_saved_user", user, new SaveAsyncCallback<BackendlessUser>() {
            @Override
            public void onComplete(BackendlessUser user) {


                if (user != null) {
                    Log.d("myLogsUserCacheSx", "Signed up user saved to cache: " + user.toString());
                }

            }

            @Override
            public void onError(String s) {

                Log.d("myLogsUserCacheFail", "Signed up user not saved to cache. Error : " + s);

            }
        });

    }


}



/* new iOSDialogBuilder(HousesDetails.this)
                .setTitle(titleString)
                .setSubtitle(subTitleString)
                .setBoldPositiveLabel(true)
                .setCancelable(true)
                .setPositiveListener(getString(R.string.okay), new iOSDialogClickListener() {
                    @Override
                    public void onClick(iOSDialog dialog) {

                        dialog.dismiss();
                    }
                })
                .build().show();*/






/*
                <TextView
                    android:id="@+id/activitySignUpGenderMaleTV"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginStart="16dp"
                    android:layout_marginTop="24dp"
                    android:background="@drawable/rounded_rect_8dp"
                    android:fontFamily="@font/quicksand_bold"
                    android:paddingStart="10dp"
                    android:paddingTop="8dp"
                    android:paddingEnd="10dp"
                    android:paddingBottom="8dp"
                    android:text="Male"
                    android:textColor="#7E7A7A"
                    android:textSize="12sp"
                    app:layout_constraintStart_toEndOf="@+id/activitySignUpGenderLabelTV"
                    app:layout_constraintTop_toBottomOf="@+id/activitySignUpPasswordEditText" />





                <TextView
                    android:id="@+id/activitySignUpGenderFemaleTV"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginStart="16dp"
                    android:layout_marginTop="24dp"
                    android:background="@drawable/rounded_rect_8dp"
                    android:fontFamily="@font/quicksand_bold"
                    android:paddingStart="10dp"
                    android:paddingTop="8dp"
                    android:paddingEnd="10dp"
                    android:paddingBottom="8dp"
                    android:text="Female"
                    android:textColor="#7E7A7A"
                    android:textSize="12sp"
                    app:layout_constraintStart_toEndOf="@+id/activitySignUpGenderMaleTV"
                    app:layout_constraintTop_toBottomOf="@+id/activitySignUpPasswordEditText" />



*/