package com.example.dell.shoegamev1.repositories;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.dell.shoegamev1.models.SubmittedUserObject;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserRepository {


    private static UserRepository instance;
    private Boolean staySignedIn = false;
    private SubmittedUserObject mSubmittedUserObject;
    private ArrayList<BackendlessUser> dataSet = new ArrayList<>();
    private String signUpErrorMessage;
    private MutableLiveData<Boolean> signUpResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> signInResult = new MutableLiveData<>();
    private MutableLiveData<BackendlessUser> signUpUserResponse = new MutableLiveData<>();
    private MutableLiveData<BackendlessUser> signInUserResponse = new MutableLiveData<>();

    public MutableLiveData<BackendlessUser> getSignUpUserResponse() {
        return signUpUserResponse;
    }

    public MutableLiveData<Boolean> getSignInResult() {
        return signInResult;
    }

    public MutableLiveData<BackendlessUser> getSignInUserResponse() {
        return signInUserResponse;
    }

    private BackendlessUser currentBackendlessUser = new BackendlessUser();




    private UserRepository(){}


    //suggest that you eagerly create the instance to avoid synchronization issues since this class will definitely be accessed
    public  static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }

        return instance;
    }


    public void logUserIn(SubmittedUserObject thisSubmittedUserObject){

        Backendless.UserService.login(thisSubmittedUserObject.geteMail(), thisSubmittedUserObject.getPassword(), new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {

                if(response!=null){

                    signInUserResponse.setValue(response);
                    signInResult.setValue(true);
                    Log.d("MyLogsUser", "user signed in successfully. token: " + response.toString());

                }


            }

            @Override
            public void handleFault(BackendlessFault fault) {

                if(fault!=null){

                    Log.d("MyLogsUser", "user sign in failed. error: " + fault.toString());

                }

            }
        },thisSubmittedUserObject.getStayLoggedIn());



    }


    public void logUserInAutomatically() {




    }


    public void createNewUser(BackendlessUser userObject, SubmittedUserObject thisSubmittedUserObject) {


        mSubmittedUserObject = thisSubmittedUserObject;
        staySignedIn = mSubmittedUserObject.getStayLoggedIn();


        if (userObject != null) {

            Backendless.UserService.register(userObject, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {

                    if (response != null) {
                        currentBackendlessUser = response;
                        signUpResult.setValue(true);
                        signUpUserResponse.setValue(response);
                        Log.d("MyLogsUser", "user registered successfully. token: " + response.toString());

                    } else {

                        signUpResult.setValue(false);

                    }


                }

                @Override
                public void handleFault(BackendlessFault fault) {

                    signUpResult.setValue(false);

                    if (fault != null) {
                        signUpErrorMessage = fault.toString();
                        Log.d("MyLogsUser", "user registration failed. error: " + fault.toString());
                    }


                }
            });


        }


    }


    public LiveData<Boolean> getSignUpResult(){

        return signUpResult;
    }



}
