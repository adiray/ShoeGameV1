package com.example.dell.shoegamev1.repositories;

import android.content.Context;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.dell.shoegamev1.models.SubmittedUserObject;
import com.gdacciaro.iOSDialog.iOSDialog;
import com.gdacciaro.iOSDialog.iOSDialogBuilder;
import com.gdacciaro.iOSDialog.iOSDialogClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import br.vince.easysave.EasySave;
import br.vince.easysave.SaveAsyncCallback;

public class UserRepository {


    private static UserRepository instance;
    private Boolean staySignedIn = false;
    private SubmittedUserObject mSubmittedUserObject;
    private ArrayList<BackendlessUser> dataSet = new ArrayList<>();
    private String signUpErrorMessage;
    private MutableLiveData<Boolean> signUpResult = new MutableLiveData<>();
    private MutableLiveData<BackendlessUser> userResponse = new MutableLiveData<>();

    public MutableLiveData<BackendlessUser> getUserResponse() {
        return userResponse;
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
                        userResponse.setValue(response);
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
