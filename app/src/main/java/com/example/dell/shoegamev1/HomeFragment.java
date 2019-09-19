package com.example.dell.shoegamev1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.backendless.BackendlessUser;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dell.shoegamev1.repositories.ShoesRepository;
import com.example.dell.shoegamev1.responseobjects.ShoeObject;
import com.example.dell.shoegamev1.viewmodels.HomeFragmentShoesViewModel;
import com.example.dell.shoegamev1.viewmodels.SignUpActivityViewModel;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.novoda.merlin.MerlinsBeard;
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class HomeFragment extends Fragment {


    View view;

    //views
    private IndefinitePagerIndicator bestDealsRecyclerViewIndicator;
    private RecyclerView bestDealsRecyclerView;
    private LottieAnimationView bestDealsRecyclerViewLoadingView;
    ImageView bestDealImageView1, bestDealImageView3, bestDealImageView2, bestDealImageView4, featuredItemImageView;
    ImageView bestDealCartImage1, bestDealLikeImage1;
    ImageView womenCollectionImageView, menCollectionImageView, moreCollectionImageView, sportsShoesCollectionImageView;

    //merlin
    private MerlinsBeard merlinsBeard;

    //create our FastAdapter which will manage everything
    private ItemAdapter<ShoeObject> bestDealsItemAdapter;
    private FastAdapter bestDealsFastAdapter;


    //ViewModel
    HomeFragmentShoesViewModel homeFragmentShoesViewModel;

    //Current User
    BackendlessUser currentGlobalUser = new BackendlessUser();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);


        //initialize the views
        bestDealsRecyclerViewLoadingView = view.findViewById(R.id.homeFragmentBestDealsRecyclerViewLoadingView);
        bestDealsRecyclerViewIndicator = view.findViewById(R.id.homeFragmentBestDealsRecyclerViewIndicator);

        //create merlin
        //library used to monitor internet connectivity
        merlinsBeard = MerlinsBeard.from(getActivity());

        //initialize view model
        homeFragmentShoesViewModel = ViewModelProviders.of(this).get(HomeFragmentShoesViewModel.class);
        homeFragmentShoesViewModel.init();

        // //delay the display of soft keyboard until the user ties to input details into the filters
        //        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //initialize recycler view
        bestDealsRecyclerView = view.findViewById(R.id.homeFragmentBestDealsRecyclerView);
        bestDealsRecyclerView.setHasFixedSize(true);
        bestDealsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        bestDealsRecyclerViewIndicator.attachToRecyclerView(bestDealsRecyclerView);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(bestDealsRecyclerView);
        bestDealsRecyclerView.setVisibility(View.GONE);
        bestDealsRecyclerViewIndicator.setVisibility(View.GONE);

        //initialize our FastAdapter which will manage everything
        bestDealsItemAdapter = new ItemAdapter<>();
        bestDealsFastAdapter = FastAdapter.with(bestDealsItemAdapter);


        //TODO SAVE TAGS IN CACHE


        requestBestDeals();


        return view;
    }


    private void requestBestDeals() {


        currentGlobalUser = MainActivity.currentLoggedInBackendlessUser;


        Integer genderInt = 1;

        if (currentGlobalUser != null) {
            genderInt = (Integer) currentGlobalUser.getProperty("gender");
            //2 = male, 3= female, 1= none
        } else {

            Log.d("MyLogsHomeFrag", "failed to retrieve gender int, step one");

        }

        if (genderInt != null) {

            if (genderInt == 1) {
                //no gender
                String whereClause = "name = 'Best deals'";

                getBestDealItems(whereClause);


            } else if (genderInt == 2) {

                //men
                String whereClause = "name = 'Men'";
                getBestDealItems(whereClause);


            } else if (genderInt == 3) {

                //women
                String whereClause = "name = 'Women'";
                getBestDealItems(whereClause);


            }


        } else {

            Log.d("MyLogsHomeFrag", "failed to retrieve gender int, step two");


        }


    }


    private List<ShoeObject> createShoeObjects(List<Map> shoeObjectMaps) {

        List<ShoeObject> shoeObjectData = new ArrayList<>();

        if (shoeObjectMaps != null) {

            Integer count = shoeObjectMaps.size() - 1;

            for (int i = 0; i <= count; i++) {

                ShoeObject holder = new ShoeObject();
                holder.setMainImage((String) shoeObjectMaps.get(i).get("mainImage"));
                holder.setPrice((String) shoeObjectMaps.get(i).get("price"));
                holder.setTitle((String) shoeObjectMaps.get(i).get("title"));

                shoeObjectData.add(holder);


            }


        } else {

            Log.d("MyLogsHomeFrag", "createShoeObjects method. passed in shoeObjectMap = null");

        }


        return shoeObjectData;

    }

    private void getBestDealItems(String whereClause) {


        homeFragmentShoesViewModel.requestSpecificTags(whereClause);

        homeFragmentShoesViewModel.getSpecificTagsRequestResult().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {

                    if (homeFragmentShoesViewModel.getSpecificTagsResponse().getValue() != null) {
                        String tagId = (String) homeFragmentShoesViewModel.getSpecificTagsResponse().getValue().get(0).get("objectId");

                        if (tagId != null) {

                            String thisWhereClause = "tags='" + tagId + "'";
                            String thisSortBy = "price%20asc";
                            homeFragmentShoesViewModel.requestBestDeals(thisWhereClause, thisSortBy);

                            homeFragmentShoesViewModel.getBestDealsRequestResult().observe(getActivity(), new Observer<Boolean>() {
                                @Override
                                public void onChanged(Boolean aBoolean) {

                                    homeFragmentShoesViewModel.getBestDealsResponse().observe(getActivity(), new Observer<List<Map>>() {
                                        @Override
                                        public void onChanged(List<Map> maps) {

                                            if (maps != null) {
                                                bestDealsRecyclerView.setAdapter(bestDealsFastAdapter);
                                                bestDealsItemAdapter.add(createShoeObjects(maps));
                                                bestDealsRecyclerViewLoadingView.pauseAnimation();
                                                bestDealsRecyclerViewLoadingView.setVisibility(View.GONE);
                                                bestDealsRecyclerView.setVisibility(View.VISIBLE);
                                                bestDealsRecyclerViewIndicator.setVisibility(View.VISIBLE);
                                            }

                                        }
                                    });


                                }
                            });


                        } else {
                            Log.d("MyLogsHomeFrag", "Tag id = null");
                        }


                    }


                }

            }
        });


    }


}







































/*homeFragmentShoesViewModel.getTagsRequestResult().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                if (aBoolean) {

                    if (homeFragmentShoesViewModel.getTagsResponse().getValue() != null) {

                        List<Map> tagsRequestResult = homeFragmentShoesViewModel.getTagsResponse().getValue();

                    }

                }

            }
        });*/












/*    homeFragmentShoesViewModel.requestSpecificTags(whereClause);

                homeFragmentShoesViewModel.getSpecificTagsRequestResult().observe(getActivity(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {

                        if (aBoolean) {

                            if (homeFragmentShoesViewModel.getSpecificTagsResponse().getValue() != null) {
                                String tagId = (String) homeFragmentShoesViewModel.getSpecificTagsResponse().getValue().get(0).get("objectId");

                                if (tagId != null) {

                                    String thisWhereClause = "tags='" + tagId + "'";
                                    String thisSortBy = "price%20asc";
                                    homeFragmentShoesViewModel.requestBestDeals(thisWhereClause, thisSortBy);

                                    homeFragmentShoesViewModel.getBestDealsRequestResult().observe(getActivity(), new Observer<Boolean>() {
                                        @Override
                                        public void onChanged(Boolean aBoolean) {

                                            bestDealsRecyclerView.setAdapter(bestDealsFastAdapter);
                                            bestDealsItemAdapter.add(createShoeObjects(homeFragmentShoesViewModel.getBestDealsResponse().getValue()));

                                        }
                                    });


                                } else {
                                    Log.d("MyLogsHomeFrag", "Tag id = null");
                                }


                            }


                        }

                    }
                });*/











  /*

        bestDealImageView1 = view.findViewById(R.id.homeFragmentBestDealsImgView1);
        bestDealCartImage1 = view.findViewById(R.id.homeFragmentBestDealsCartImg1);
        bestDealLikeImage1 = view.findViewById(R.id.homeFragmentBestDealsLikeImg1);

        Glide.with(this).load(R.drawable.heart_96px_gray).into(bestDealLikeImage1);
        Glide.with(this).load(R.drawable.shopping_bag_100px_inactive).into(bestDealCartImage1);


        Glide.with(this).load(R.drawable.test_img_shoes)
                .apply(RequestOptions
                        .bitmapTransform(new RoundedCornersTransformation(45,0,RoundedCornersTransformation.CornerType.LEFT))).into(bestDealImageView1);

*/


    /*

                apply(bitmapTransform(RoundedCornersTransformation(45, 0,
                        RoundedCornersTransformation.CornerType.BOTTOM)))
                .into(holder.image)
*/


/*
        featuredItemImageView =view.findViewById(R.id.homeFragmentFeaturedImageView);

        bestDealImageView1 = view.findViewById(R.id.homeFragmentBestDealsImgView1);
        bestDealImageView2 = view.findViewById(R.id.homeFragmentBestDealsImgView2);
        bestDealImageView3 = view.findViewById(R.id.homeFragmentBestDealsImgView3);
        bestDealImageView4 = view.findViewById(R.id.homeFragmentBestDealsImgView4);

        womenCollectionImageView =view.findViewById(R.id.homeFragmentCollectionsWomenIcon);
        menCollectionImageView = view.findViewById(R.id.homeFragmentCollectionsMenIcon);
        moreCollectionImageView = view.findViewById(R.id.homeFragmentCollectionsMoreIcon);
        sportsShoesCollectionImageView = view.findViewById(R.id.homeFragmentCollectionsSportsIcon);



        Glide.with(this).load(R.drawable.test_img_shoes).into(featuredItemImageView);
        Glide.with(this).load(R.drawable.test_img_shoes_2).into(bestDealImageView1);
        Glide.with(this).load(R.drawable.test_img_shoes_3).into(bestDealImageView2);
        Glide.with(this).load(R.drawable.test_img_shoes_4).into(bestDealImageView3);
        Glide.with(this).load(R.drawable.test_img_shoes_5).into(bestDealImageView4);

        Glide.with(this).load(R.drawable.sneakers_colored_filled).into(sportsShoesCollectionImageView);
        Glide.with(this).load(R.drawable.male_profile_colored).into(menCollectionImageView);
        Glide.with(this).load(R.drawable.female_profile_colored).into(womenCollectionImageView);
        Glide.with(this).load(R.drawable.more_circled_colored_filled).into(moreCollectionImageView);

*/




















/* <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"


        >

        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_margin="0dp"
            android:layout_marginTop="16dp"
            android:background="@drawable/rounded_rect_white"
            android:padding="24dp">


            <TextView
                android:id="@+id/textViewCollectionsHeader"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="8dp"
                android:layout_marginTop="8dp"
                android:layout_marginBottom="8dp"
                android:fontFamily="@font/quicksand_bold"
                android:text="@string/collections"
                android:textColor="#000"
                android:textSize="40sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />


            <TextView
                android:id="@+id/textViewWomen"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:layout_marginTop="16dp"
                android:layout_marginBottom="8dp"
                android:fontFamily="@font/quicksand_bold"
                android:text="Women"
                android:textColor="#7E7A7A"
                android:textSize="35sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@id/textViewCollectionsHeader" />


            <TextView
                android:id="@+id/textViewMen"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:layout_marginTop="8dp"
                android:layout_marginBottom="8dp"
                android:fontFamily="@font/quicksand_bold"
                android:text="Men"
                android:textColor="#7E7A7A"
                android:textSize="35sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@id/textViewWomen" />


            <TextView
                android:id="@+id/textViewSneakers"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:layout_marginTop="8dp"
                android:layout_marginBottom="8dp"
                android:fontFamily="@font/quicksand_bold"
                android:text="Sneakers"
                android:textColor="#7E7A7A"
                android:textSize="35sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@id/textViewMen" />


            <TextView
                android:id="@+id/textViewPopularHeader"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="8dp"
                android:layout_marginTop="32dp"
                android:layout_marginBottom="8dp"
                android:fontFamily="@font/quicksand_bold"
                android:text="@string/popular"
                android:textColor="#000"
                android:textSize="40sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/textViewSneakers" />


            <androidx.constraintlayout.widget.ConstraintLayout
                android:layout_width="match_parent"
                android:layout_height="200dp"
                android:layout_marginStart="8dp"
                android:layout_marginTop="24dp"
                android:layout_marginEnd="8dp"
                android:background="@drawable/rounded_rect_8dp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/textViewPopularHeader">


                <ImageView
                    android:id="@+id/homeFragmentBestDealsImgView1"
                    android:layout_width="200dp"
                    android:layout_height="200dp"
                    app:layout_constraintStart_toStartOf="parent"
                    app:layout_constraintTop_toTopOf="parent"

                    />


                <TextView
                    android:layout_marginBottom="12dp"
                    android:layout_marginEnd="12dp"
                    android:id="@+id/homeFragmentBestDealsPriceTV1"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:fontFamily="@font/quicksand_bold"
                    android:text="$87"
                    android:textSize="35sp"
                    app:layout_constraintBottom_toBottomOf="parent"
                    app:layout_constraintEnd_toEndOf="parent" />



                <ImageView
                    android:id="@+id/homeFragmentBestDealsCartImg1"
                    android:layout_marginTop="12dp"
                    android:layout_marginEnd="12dp"
                    app:layout_constraintTop_toTopOf="parent"
                    app:layout_constraintEnd_toEndOf="parent"
                    android:layout_width="30dp"
                    android:layout_height="30dp" />



                <ImageView
                    android:id="@+id/homeFragmentBestDealsLikeImg1"
                    android:layout_marginTop="12dp"
                    android:layout_marginEnd="12dp"
                    app:layout_constraintTop_toTopOf="parent"
                    app:layout_constraintEnd_toStartOf="@id/homeFragmentBestDealsCartImg1"
                    android:layout_width="32dp"
                    android:layout_height="32dp" />




            </androidx.constraintlayout.widget.ConstraintLayout>


        </androidx.constraintlayout.widget.ConstraintLayout>


    </LinearLayout>
*/








/*<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">




        <TextView
            android:id="@+id/textViewCollectionsHeader"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="16dp"
            android:layout_marginTop="24dp"
            android:layout_marginBottom="8dp"
            android:fontFamily="@font/quicksand_bold"
            android:text="@string/collections"
            android:textColor="#000"
            android:textSize="25sp"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />


        <ImageView
            android:id="@+id/homeFragmentCollectionsMenIcon"
            android:layout_width="90dp"
            android:layout_height="90dp"
            android:layout_marginStart="16dp"
            android:layout_marginTop="24dp"
            android:background="@drawable/rounded_rect_8dp"
            android:backgroundTint="@color/myColorAccent"
            app:layout_constraintEnd_toStartOf="@+id/homeFragmentCollectionsWomenIcon"
            app:layout_constraintHorizontal_bias="0.5"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/textViewCollectionsHeader" />


        <TextView
            android:id="@+id/homeFragmentCollectionsMenTv"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:fontFamily="@font/quicksand_bold"
            android:text="Men"
            android:textColor="#7E7A7A"
            android:textSize="15sp"
            app:layout_constraintEnd_toEndOf="@id/homeFragmentCollectionsMenIcon"
            app:layout_constraintStart_toStartOf="@id/homeFragmentCollectionsMenIcon"
            app:layout_constraintTop_toBottomOf="@id/homeFragmentCollectionsMenIcon" />


        <ImageView
            android:id="@+id/homeFragmentCollectionsWomenIcon"
            android:layout_width="90dp"
            android:layout_height="90dp"
            android:layout_marginStart="16dp"
            android:layout_marginTop="24dp"
            android:background="@drawable/rounded_rect_8dp"
            android:backgroundTint="@color/myColorDarkYellow"
            app:layout_constraintEnd_toStartOf="@+id/homeFragmentCollectionsSneakersIcon"
            app:layout_constraintHorizontal_bias="0.5"
            app:layout_constraintStart_toEndOf="@+id/homeFragmentCollectionsMenIcon"
            app:layout_constraintTop_toBottomOf="@+id/textViewCollectionsHeader" />


        <TextView
            android:id="@+id/homeFragmentCollectionsWomenTv"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:fontFamily="@font/quicksand_bold"
            android:text="Women"
            android:textColor="#7E7A7A"
            android:textSize="15sp"
            app:layout_constraintEnd_toEndOf="@id/homeFragmentCollectionsWomenIcon"
            app:layout_constraintStart_toStartOf="@id/homeFragmentCollectionsWomenIcon"
            app:layout_constraintTop_toBottomOf="@id/homeFragmentCollectionsWomenIcon" />


        <ImageView
            android:id="@+id/homeFragmentCollectionsSneakersIcon"
            android:layout_width="90dp"
            android:layout_height="90dp"
            android:layout_marginStart="16dp"
            android:layout_marginTop="24dp"
            android:layout_marginEnd="16dp"
            android:background="@drawable/rounded_rect_8dp"
            android:backgroundTint="@color/purple_active"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.5"
            app:layout_constraintStart_toEndOf="@+id/homeFragmentCollectionsWomenIcon"
            app:layout_constraintTop_toBottomOf="@+id/textViewCollectionsHeader" />


        <TextView
            android:id="@+id/homeFragmentCollectionsSneakersTv"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:fontFamily="@font/quicksand_bold"
            android:text="Sports"
            android:textColor="#7E7A7A"
            android:textSize="15sp"
            app:layout_constraintEnd_toEndOf="@id/homeFragmentCollectionsSneakersIcon"
            app:layout_constraintStart_toStartOf="@id/homeFragmentCollectionsSneakersIcon"
            app:layout_constraintTop_toBottomOf="@id/homeFragmentCollectionsSneakersIcon" />


        <TextView
            android:id="@+id/textViewBestDealsHeader"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="32dp"
            android:layout_marginTop="32dp"
            android:layout_marginBottom="8dp"
            android:fontFamily="@font/quicksand_bold"
            android:text="@string/best_deals"
            android:textColor="#7E7A7A"
            android:textSize="17sp"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/homeFragmentCollectionsSneakersTv" />


        <ImageView
            android:id="@+id/homeFragmentBestDealsMoreIcon"
            android:layout_width="18dp"
            android:layout_height="20dp"
            android:layout_marginTop="32dp"
            android:layout_marginEnd="24dp"
            android:src="@drawable/right_arrow_black_simple"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/homeFragmentCollectionsSneakersTv" />


        <ImageView
            android:id="@+id/homeFragmentBestDealsImgView1"
            android:layout_width="120dp"
            android:layout_height="120dp"
            android:layout_marginStart="32dp"
            android:layout_marginTop="28dp"
            android:background="@drawable/rounded_rect_8dp"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/textViewBestDealsHeader" />


        <TextView
            android:id="@+id/homeFragmentBestDealsPriceTV1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="12dp"
            android:layout_marginTop="12dp"
            android:fontFamily="@font/quicksand_bold"
            android:text="$86"
            android:textColor="#7E7A7A"
            android:textSize="17sp"
            app:layout_constraintStart_toStartOf="@+id/homeFragmentBestDealsImgView1"
            app:layout_constraintTop_toBottomOf="@+id/homeFragmentBestDealsImgView1" />


        <ImageView
            android:layout_width="17dp"
            android:layout_height="17dp"
            android:layout_marginTop="12dp"
            android:layout_marginEnd="12dp"
            android:src="@drawable/heart_96px_gray"
            app:layout_constraintEnd_toEndOf="@+id/homeFragmentBestDealsImgView1"
            app:layout_constraintTop_toBottomOf="@+id/homeFragmentBestDealsImgView1" />


        <TextView

            android:id="@+id/homeFragmentRecommendationsHeadlineTV"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginStart="32dp"
            android:layout_marginTop="32dp"
            android:layout_marginEnd="32dp"
            android:layout_marginBottom="8dp"
            android:fontFamily="@font/quicksand_bold"
            android:text="Popular"
            android:textColor="#7E7A7A"
            android:textSize="17sp"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/homeFragmentBestDealsPriceTV1" />




        <ImageView
            android:id="@+id/homeFragmentRecommendationsMoreIcon"
            android:layout_width="18dp"
            android:layout_height="20dp"
            android:layout_marginTop="32dp"
            android:layout_marginEnd="24dp"
            android:src="@drawable/right_arrow_black_simple"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/homeFragmentBestDealsPriceTV1" />


        <TextView
            android:id="@+id/homeFragmentRecommendationsDataPrompt"
            android:layout_width="300dp"
            android:layout_height="wrap_content"
            android:layout_marginStart="32dp"
            android:layout_marginTop="32dp"
            android:layout_marginEnd="32dp"
            android:layout_marginBottom="8dp"
            android:fontFamily="@font/quicksand_bold"
            android:scrollHorizontally="false"
            android:text="We care about your data bill. Load these recommendations by clicking the button"
            android:textAlignment="center"
            android:textColor="#7E7A7A"
            android:textSize="15sp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/homeFragmentRecommendationsHeadlineTV" />



        <ImageView

            android:background="@drawable/rounded_rect_8dp"
            app:layout_constraintEnd_toEndOf="@id/homeFragmentRecommendationsDataPrompt"
            android:layout_marginTop="16dp"
            app:layout_constraintTop_toBottomOf="@+id/homeFragmentRecommendationsDataPrompt"
            app:layout_constraintStart_toStartOf="parent"
            android:id="@+id/homeFragmentRecommendationsDataPromptWomenImg"
            android:layout_width="30dp"
            android:layout_height="30dp" />


        <ImageView


            android:layout_marginStart="8dp"
            android:background="@drawable/rounded_rect_8dp"
            app:layout_constraintEnd_toEndOf="parent"
            android:layout_marginTop="16dp"
            app:layout_constraintTop_toBottomOf="@+id/homeFragmentRecommendationsDataPrompt"
            app:layout_constraintStart_toStartOf="@+id/homeFragmentRecommendationsDataPrompt"
            android:id="@+id/homeFragmentRecommendationsDataPromptMenImg"
            android:layout_width="30dp"
            android:layout_height="30dp" />


    </androidx.constraintlayout.widget.ConstraintLayout>


*/











/*<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical">


    <LinearLayout

        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">


        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <TextView
                android:id="@+id/textViewCollectionsHeader"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:layout_marginTop="24dp"
                android:layout_marginBottom="8dp"
                android:fontFamily="@font/quicksand_bold"
                android:text="@string/collections"
                android:textColor="#000"
                android:textSize="25sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />


            <ImageView
                android:id="@+id/homeFragmentCollectionsMenIcon"
                android:layout_width="90dp"
                android:layout_height="90dp"
                android:layout_marginStart="16dp"
                android:layout_marginTop="24dp"
                android:background="@drawable/rounded_rect_8dp"
                android:backgroundTint="@color/myColorAccent"
                app:layout_constraintEnd_toStartOf="@+id/homeFragmentCollectionsWomenIcon"
                app:layout_constraintHorizontal_bias="0.5"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/textViewCollectionsHeader" />


            <TextView
                android:id="@+id/homeFragmentCollectionsMenTv"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp"
                android:fontFamily="@font/quicksand_bold"
                android:text="Men"
                android:textColor="#7E7A7A"
                android:textSize="15sp"
                app:layout_constraintEnd_toEndOf="@id/homeFragmentCollectionsMenIcon"
                app:layout_constraintStart_toStartOf="@id/homeFragmentCollectionsMenIcon"
                app:layout_constraintTop_toBottomOf="@id/homeFragmentCollectionsMenIcon" />


            <ImageView
                android:id="@+id/homeFragmentCollectionsWomenIcon"
                android:layout_width="90dp"
                android:layout_height="90dp"
                android:layout_marginStart="16dp"
                android:layout_marginTop="24dp"
                android:background="@drawable/rounded_rect_8dp"
                android:backgroundTint="@color/myColorDarkYellow"
                app:layout_constraintEnd_toStartOf="@+id/homeFragmentCollectionsSneakersIcon"
                app:layout_constraintHorizontal_bias="0.5"
                app:layout_constraintStart_toEndOf="@+id/homeFragmentCollectionsMenIcon"
                app:layout_constraintTop_toBottomOf="@+id/textViewCollectionsHeader" />


            <TextView
                android:id="@+id/homeFragmentCollectionsWomenTv"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp"
                android:fontFamily="@font/quicksand_bold"
                android:text="Women"
                android:textColor="#7E7A7A"
                android:textSize="15sp"
                app:layout_constraintEnd_toEndOf="@id/homeFragmentCollectionsWomenIcon"
                app:layout_constraintStart_toStartOf="@id/homeFragmentCollectionsWomenIcon"
                app:layout_constraintTop_toBottomOf="@id/homeFragmentCollectionsWomenIcon" />


            <ImageView
                android:id="@+id/homeFragmentCollectionsSneakersIcon"
                android:layout_width="90dp"
                android:layout_height="90dp"
                android:layout_marginStart="16dp"
                android:layout_marginTop="24dp"
                android:layout_marginEnd="16dp"
                android:background="@drawable/rounded_rect_8dp"
                android:backgroundTint="@color/purple_active"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.5"
                app:layout_constraintStart_toEndOf="@+id/homeFragmentCollectionsWomenIcon"
                app:layout_constraintTop_toBottomOf="@+id/textViewCollectionsHeader" />


            <TextView
                android:id="@+id/homeFragmentCollectionsSneakersTv"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp"
                android:fontFamily="@font/quicksand_bold"
                android:text="Sports"
                android:textColor="#7E7A7A"
                android:textSize="15sp"
                app:layout_constraintEnd_toEndOf="@id/homeFragmentCollectionsSneakersIcon"
                app:layout_constraintStart_toStartOf="@id/homeFragmentCollectionsSneakersIcon"
                app:layout_constraintTop_toBottomOf="@id/homeFragmentCollectionsSneakersIcon" />


        </androidx.constraintlayout.widget.ConstraintLayout>

        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">


            <TextView
                android:id="@+id/mainActivityBestDealsHeader"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="32dp"
                android:layout_marginTop="32dp"
                android:layout_marginBottom="8dp"
                android:fontFamily="@font/quicksand_bold"
                android:text="@string/best_deals"
                android:textColor="#7E7A7A"
                android:textSize="17sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />


            <ImageView
                android:id="@+id/homeFragmentBestDealsMoreIcon"
                android:layout_width="18dp"
                android:layout_height="20dp"
                android:layout_marginTop="32dp"
                android:layout_marginEnd="24dp"
                android:src="@drawable/right_arrow_black_simple"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintTop_toTopOf="parent" />


            <ImageView
                android:id="@+id/homeFragmentBestDealsImgView1"
                android:layout_width="120dp"
                android:layout_height="120dp"
                android:layout_marginStart="32dp"
                android:layout_marginTop="28dp"
                android:background="@drawable/rounded_rect_8dp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/mainActivityBestDealsHeader" />


            <TextView
                android:id="@+id/homeFragmentBestDealsPriceTV1"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="12dp"
                android:layout_marginTop="12dp"
                android:fontFamily="@font/quicksand_bold"
                android:text="$86"
                android:textColor="#7E7A7A"
                android:textSize="17sp"
                app:layout_constraintStart_toStartOf="@+id/homeFragmentBestDealsImgView1"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentBestDealsImgView1" />


            <ImageView
                android:layout_width="17dp"
                android:layout_height="17dp"
                android:layout_marginTop="12dp"
                android:layout_marginEnd="12dp"
                android:src="@drawable/heart_96px_gray"
                app:layout_constraintEnd_toEndOf="@+id/homeFragmentBestDealsImgView1"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentBestDealsImgView1" />


        </androidx.constraintlayout.widget.ConstraintLayout>




        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">



            <TextView

                android:id="@+id/homeFragmentRecommendationsHeadlineTV"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="32dp"
                android:layout_marginTop="32dp"
                android:layout_marginEnd="32dp"
                android:layout_marginBottom="8dp"
                android:fontFamily="@font/quicksand_bold"
                android:text="Popular"
                android:textColor="#7E7A7A"
                android:textSize="17sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />




            <ImageView
                android:id="@+id/homeFragmentRecommendationsMoreIcon"
                android:layout_width="18dp"
                android:layout_height="20dp"
                android:layout_marginTop="32dp"
                android:layout_marginEnd="24dp"
                android:src="@drawable/right_arrow_black_simple"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintTop_toTopOf="parent" />


            <TextView
                android:id="@+id/homeFragmentRecommendationsDataPrompt"
                android:layout_width="300dp"
                android:layout_height="wrap_content"
                android:layout_marginStart="32dp"
                android:layout_marginTop="32dp"
                android:layout_marginEnd="32dp"
                android:layout_marginBottom="8dp"
                android:fontFamily="@font/quicksand_bold"
                android:scrollHorizontally="false"
                android:text="We care about your data bill. Load these recommendations by clicking the button"
                android:textAlignment="center"
                android:textColor="#7E7A7A"
                android:textSize="15sp"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentRecommendationsHeadlineTV" />



            <ImageView

                android:background="@drawable/rounded_rect_8dp"
                app:layout_constraintEnd_toEndOf="@id/homeFragmentRecommendationsDataPrompt"
                android:layout_marginTop="16dp"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentRecommendationsDataPrompt"
                app:layout_constraintStart_toStartOf="parent"
                android:id="@+id/homeFragmentRecommendationsDataPromptWomenImg"
                android:layout_width="30dp"
                android:layout_height="30dp" />


            <ImageView


                android:layout_marginStart="8dp"
                android:background="@drawable/rounded_rect_8dp"
                app:layout_constraintEnd_toEndOf="parent"
                android:layout_marginTop="16dp"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentRecommendationsDataPrompt"
                app:layout_constraintStart_toStartOf="@+id/homeFragmentRecommendationsDataPrompt"
                android:id="@+id/homeFragmentRecommendationsDataPromptMenImg"
                android:layout_width="30dp"
                android:layout_height="30dp" />




        </androidx.constraintlayout.widget.ConstraintLayout>




        <ImageView
            android:background="@drawable/rounded_rect_top_radius"
            android:layout_width="match_parent"
            android:layout_height="60dp" />









    </LinearLayout>


</ScrollView>


*/