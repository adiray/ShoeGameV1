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



    public LiveData<Boolean> getSignUpResult(){
        return mUserRepository.getSignUpResult();

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





