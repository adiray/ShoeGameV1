package com.example.dell.shoegamev1.repositories;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.dell.shoegamev1.models.SubmittedUserObject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class UserRepository {

    //VARIABLES
    private static UserRepository instance;




    //LIVE DATA
    private MutableLiveData<Boolean> isValidLoginCheckResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> isValidLoginCheckResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> signUpResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> signInResult = new MutableLiveData<>();
    private MutableLiveData<Boolean> retrieveCurrentUserFromTheInternetResult = new MutableLiveData<>();
    private MutableLiveData<BackendlessUser> retrieveCurrentLoggedInUserResponse = new MutableLiveData<>();
    private MutableLiveData<BackendlessUser> signUpUserResponse = new MutableLiveData<>();
    private MutableLiveData<BackendlessUser> signInUserResponse = new MutableLiveData<>();


    //CONSTRUCTOR
    private UserRepository() {
    }

    //suggest that you eagerly create the instance to avoid synchronization issues since this class will definitely be accessed

    /**
     * Returns the single available instance of the user repository
     */
    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }

        return instance;
    }


    //Getters
    public MutableLiveData<BackendlessUser> getSignUpUserResponse() {
        return signUpUserResponse;
    }

    public MutableLiveData<Boolean> getSignInResult() {
        return signInResult;
    }

    public MutableLiveData<BackendlessUser> getSignInUserResponse() {
        return signInUserResponse;
    }

    public LiveData<Boolean> getSignUpResult() {

        return signUpResult;
    }

    public MutableLiveData<Boolean> getIsValidLoginCheckResult() {
        return isValidLoginCheckResult;
    }

    public MutableLiveData<Boolean> getIsValidLoginCheckResponse() {
        return isValidLoginCheckResponse;
    }

    public MutableLiveData<Boolean> getRetrieveCurrentUserFromTheInternetResult() {
        return retrieveCurrentUserFromTheInternetResult;
    }

    public MutableLiveData<BackendlessUser> getRetrieveCurrentLoggedInUserResponse() {
        return retrieveCurrentLoggedInUserResponse;
    }


    //USER OPERATIONS

    /**
     * this method logs a user in manually using details submitted by the user.
     * Extra details can be supported by adding them to the submitted user object model and passing them
     * in the constructor
     * call this when user is logging in using a form
     */
    public void logUserIn(SubmittedUserObject thisSubmittedUserObject) {

        Backendless.UserService.login(thisSubmittedUserObject.geteMail(), thisSubmittedUserObject.getPassword(), new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {

                if (response != null) {

                    signInUserResponse.setValue(response);
                    signInResult.setValue(true);
                    Log.d("MyLogsUser", "user signed in successfully. token: " + response.toString());

                }


            }

            @Override
            public void handleFault(BackendlessFault fault) {

                if (fault != null) {

                    Log.d("MyLogsUser", "user sign in failed. error: " + fault.toString());

                }

            }
        }, thisSubmittedUserObject.getStayLoggedIn());


    }


    /**
     * This method is used with the sign up form.
     * accepts both a backendless user object and a submittedUserObject model.
     * the backendless user object is sent to the server to be added to the users table while
     * the submitted user object can be used to store extra details collected from the user that may not be
     * accepted by the backendless table but may be useful during registration.
     * for example user preferences and settings
     * for example if the user wants to stay signed in or if they want to receive promotional emails
     */
    public void createNewUser(BackendlessUser userObject, SubmittedUserObject thisSubmittedUserObject) {

        //Todo sign user in


        if (userObject != null) {

            Backendless.UserService.register(userObject, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {

                    if (response != null) {
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
                        Log.d("MyLogsUser", "user registration failed. error: " + fault.toString());
                    }


                }
            });


        }


    }


    public void logUserInAutomatically() {


    }


    /**
     * Checks if the current users' session is still valid and if the user has a valid token.
     * If the session is valid, it will update the isValidLoginCheckResponse to 'true' otherwise false.
     * If the request is successful, will update isValidLoginCheckResult to true, if there is an error it will update
     * isValidLoginCheckResult to 'false'
     **/
    public void checkIfUserLoginIsValid() {

        Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response) {

                if (response != null) {

                    isValidLoginCheckResult.setValue(true);
                    isValidLoginCheckResponse.setValue(response);
                    Log.d("MyLogsUser", "login validity checked successfully. response: " + response.toString());

                }


            }

            @Override
            public void handleFault(BackendlessFault fault) {

                if (fault != null) {
                    Log.d("MyLogsUser", "failed to check current user login validity. error: " + fault.toString());
                }

                isValidLoginCheckResult.setValue(false);

            }
        });


    }


    /**
     * gets the current logged in user from the internet
     * Must only be called after checkIfUserLoginIsValid has been called and there is a valid login*/
    public void retrieveCurrentUserFromTheInternet() {

        String currentUserId = Backendless.UserService.loggedInUser();

        if (currentUserId != null) {
            Backendless.UserService.findById(currentUserId, new AsyncCallback<BackendlessUser>() {
                @Override
                public void handleResponse(BackendlessUser response) {

                    if (response != null) {

                        retrieveCurrentUserFromTheInternetResult.setValue(true);
                        retrieveCurrentLoggedInUserResponse.setValue(response);
                        Log.d("MyLogsUser", "current logged in user retrieved from internet. User details: " + response.toString());

                    }


                }

                @Override
                public void handleFault(BackendlessFault fault) {

                    retrieveCurrentUserFromTheInternetResult.setValue(false);
                    Log.d("MyLogsUser", "failed to retrieve current logged in user from internet. error: " + fault.toString());

                }
            });
        }

    }




}
