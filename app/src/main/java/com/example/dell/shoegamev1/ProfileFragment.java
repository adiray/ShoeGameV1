package com.example.dell.shoegamev1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.example.dell.shoegamev1.viewmodels.HomeFragmentShoesViewModel;
import com.example.dell.shoegamev1.viewmodels.SignUpActivityViewModel;
import com.novoda.merlin.MerlinsBeard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ProfileFragment extends Fragment {

    private View view;
    private TextView helloUserTextView;
    // private TextView signUpTextView, logInTextView, cartTV, faqTV, trackingTV, ordersTv, savedTv, editProfileTv, supportTv, aboutTv;
    private ConstraintLayout signUpLayout, logInLayout, cartLayout, faqLayout, trackingLayout, ordersLayout, savedLayout, editProfileLayout, supportLayout, aboutLayout;
    private ConstraintLayout logOutLayout, deleteAccountLayout, paymentMethodsLayout;
    private TextView helpHeaderTV,accountHeaderTV,personalHeaderTV;
    private LottieAnimationView loadingView;



    //ViewModel
    HomeFragmentShoesViewModel homeFragmentShoesViewModel;
    SignUpActivityViewModel signUpActivityViewModel;


    //Current User
    private BackendlessUser currentGlobalUser = new BackendlessUser();

    //merlin
    private MerlinsBeard merlinsBeard;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.profile_fragment, container, false);


        //initialize the views
        loadingView = view.findViewById(R.id.profileFragmentAnimationView);
        personalHeaderTV = view.findViewById(R.id.profileFragmentPersonalHeaderTV);
        helpHeaderTV = view.findViewById(R.id.profileFragmentHelpHeaderTV);
        accountHeaderTV = view.findViewById(R.id.profileFragmentAccountHeaderTV);
        helloUserTextView = view.findViewById(R.id.profileFragmentHelloUserTV);
        cartLayout = view.findViewById(R.id.profileFragmentCartLayout);
        trackingLayout = view.findViewById(R.id.profileFragmentTrackingLayout);
        ordersLayout = view.findViewById(R.id.profileFragmentOrdersLayout);
        savedLayout = view.findViewById(R.id.profileFragmentSavedLayout);
        editProfileLayout = view.findViewById(R.id.profileFragmentEditProfileLayout);
        paymentMethodsLayout = view.findViewById(R.id.profileFragmentPaymentMethodsLayout);
        supportLayout = view.findViewById(R.id.profileFragmentSupportLayout);
        aboutLayout = view.findViewById(R.id.profileFragmentAboutAppLayout);
        faqLayout = view.findViewById(R.id.profileFragmentFaqLayout);
        signUpLayout = view.findViewById(R.id.profileFragmentSignUpLayout);
        logInLayout = view.findViewById(R.id.profileFragmentLogInLayout);
        logOutLayout = view.findViewById(R.id.profileFragmentLogOutLayout);
        deleteAccountLayout = view.findViewById(R.id.profileFragmentDeleteAccountLayout);





        //create merlin. library used to monitor internet connectivity
        merlinsBeard = MerlinsBeard.from(getActivity());

        //initialize view models
        signUpActivityViewModel = ViewModelProviders.of(this).get(SignUpActivityViewModel.class);
        signUpActivityViewModel.init();









        //set whether views are visible depending on user logged in status
        if (MainActivity.IsValidLogin) {
            logInLayout.setVisibility(View.GONE);
            signUpLayout.setVisibility(View.GONE);

            if (MainActivity.currentLoggedInBackendlessUser != null) {

                //first_name
                String helloUserText = "Hello, " + MainActivity.currentLoggedInBackendlessUser.getProperty("first_name");
                helloUserTextView.setText(helloUserText);
                Log.d("MyLogsUser", "Profile fragment. Current user retrieved successfully. response: " + MainActivity.currentLoggedInBackendlessUser.toString());

            }


        } else {

            cartLayout.setVisibility(View.GONE);
            trackingLayout.setVisibility(View.GONE);
            ordersLayout.setVisibility(View.GONE);
            savedLayout.setVisibility(View.GONE);
            editProfileLayout.setVisibility(View.GONE);
            paymentMethodsLayout.setVisibility(View.GONE);
            logOutLayout.setVisibility(View.GONE);
            deleteAccountLayout.setVisibility(View.GONE);
        }


        logInLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LogIn.class);
                startActivity(intent);
            }
        });
        signUpLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignUp.class);
                startActivity(intent);
            }
        });


        return view;
    }







    private void getCurrentUser(){

        signUpActivityViewModel.checkIfUserLoginIsValid();
        signUpActivityViewModel.getIsValidLoginCheckResult().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if(aBoolean){

                    signUpActivityViewModel.getIsValidLoginCheckResponse().observe(getActivity(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {

                            //todo this is where you stopped


                        }
                    });

                }

            }
        });

    }










}
