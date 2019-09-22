package com.example.dell.shoegamev1.viewmodels;

import com.backendless.BackendlessUser;
import com.example.dell.shoegamev1.models.SubmittedUserObject;
import com.example.dell.shoegamev1.repositories.UserRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SignUpActivityViewModel extends ViewModel {



    /*Activities that use this view model
    * Sign up activity
    * log in activity
    * main activity
    * profile
    * */



    private UserRepository mUserRepository;

/*
    public void init(){
        if(mNicePlaces != null){
            return;
        }
        mRepo = NicePlaceRepository.getInstance();
        mNicePlaces = mRepo.getNicePlaces();
    }
*/


    public void init() {

       mUserRepository = UserRepository.getInstance();

    }



    //GETTERS
    public LiveData<Boolean> getSignUpResult(){
        return mUserRepository.getSignUpResult();

    }

    public LiveData<Boolean> getIsValidLoginCheckResult(){

        return mUserRepository.getIsValidLoginCheckResult();
    }

    public LiveData<Boolean> getIsValidLoginCheckResponse(){
        return mUserRepository.getIsValidLoginCheckResponse();
    }

    public LiveData<Boolean> getRetrieveCurrentUserFromTheInternetResult(){
        return mUserRepository.getRetrieveCurrentUserFromTheInternetResult();
    }

    public LiveData<BackendlessUser> getRetrieveCurrentUserFromTheInternetResponse(){
        return mUserRepository.getRetrieveCurrentLoggedInUserResponse();
    }


    public void checkIfUserLoginIsValid(){
        mUserRepository.checkIfUserLoginIsValid();
    }

    public void retrieveCurrentUserFromTheInternet(){
        mUserRepository.retrieveCurrentUserFromTheInternet();
    }

    public void registerNewUser(BackendlessUser userObject, SubmittedUserObject thisSubmittedUserObject) {

        mUserRepository.createNewUser(userObject,thisSubmittedUserObject);

    }


    public LiveData<BackendlessUser> getNewlyRegisteredUser(){

        return mUserRepository.getSignUpUserResponse();

    }


    public void logUserIn(SubmittedUserObject thisSubmittedUserObject){

        mUserRepository.logUserIn(thisSubmittedUserObject);

    }


    public LiveData<BackendlessUser> getNewlySignedInUser(){

        return mUserRepository.getSignInUserResponse();
    }


    public LiveData<Boolean> getNewSignInResult(){

        return mUserRepository.getSignInResult();

    }





}





