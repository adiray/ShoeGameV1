package com.example.dell.shoegamev1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.backendless.Backendless;
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    //views
    ViewPager mainViewPager;
    BubbleNavigationConstraintView mBubbleNav;
    BubbleNavigationLinearView mBubbleNavBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initialize backendless
        Backendless.initApp( getApplicationContext(),
               "05DBC061-3DE1-0252-FF3C-FBCECC684700",
               "EB559093-3624-CE96-FFEF-AECE72F44100");

        initializeViews();



    }


    void initializeViews() {




        //get references to the views
        mainViewPager = findViewById(R.id.mainActivityViewPager);
        mBubbleNav = findViewById(R.id.mainActivityTopTabLayout);
        //mBubbleNavBottom = findViewById(R.id.main_activity_bottom_navigation_view_linear);

        MainActivityViewPagerAdapter mainActivityViewPagerAdapter = new MainActivityViewPagerAdapter(getSupportFragmentManager());
        mainActivityViewPagerAdapter.addFragment(new HomeFragment(), "Home");
        mainActivityViewPagerAdapter.addFragment(new BrowseFragment(), "Browse");
        mainActivityViewPagerAdapter.addFragment(new ProfileFragment(), "Profile");
        mainViewPager.setAdapter(mainActivityViewPagerAdapter);
        //topTabLayout.setupWithViewPager(mainViewPager);


        /*
         Add an OnPageChangeListener so that you can change the active item on the bubble tab layout when the user swipes instead of clicking on the layout
          When the user clicks on the layout add an mBubbleNav.setNavigationChangeListener to change the fragment in the viewpager to the position corresponding
          with the click*/

        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mBubbleNav.setCurrentActiveItem(position);
                //mBubbleNavBottom.setCurrentActiveItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBubbleNav.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {

                mainViewPager.setCurrentItem(position,true);
            }
        });



      /*

        mBubbleNavBottom.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {

                mainViewPager.setCurrentItem(position,true);

            }
        });
*/





    }

}






/*<color name="colorPrimary">#008577</color>
    <color name="colorPrimaryDark">#00574B</color>
    <color name="colorAccent">#D81B60</color>
    <color name="myColorAccent">#06d6a0</color>
    <color name="myColorPrimary">#770000</color>
    <color name="myColorSecondary">#118ab2</color>
    <color name="myColorSecondaryRed">#cc334c</color>
    <color name="myColorText">#fff</color>
    <color name="myColorLightRed">#ef476f</color>
    <color name="myColorLightYellow">#ffd166</color>
    <color name="myColorDarkYellow">#ff9000</color>
    <color name="myColorTextDark">#000</color>
    <color name="myColorPrimaryDark">#660000</color>
    <color name="myColorAccentGreenDark">#06a371</color>





    */






/* <com.google.android.material.tabs.TabLayout
        android:id="@+id/mainActivityTopTabLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/myColorPrimary"
        app:tabGravity="fill"
        app:tabIndicatorColor="@color/myColorDarkYellow"
        app:tabIndicator="@color/myColorAccent"
        app:tabTextColor="@color/myColorText"
        />

*/






/*  <com.gauravk.bubblenavigation.BubbleNavigationConstraintView
        android:id="@+id/floating_top_bar_navigation"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginStart="18dp"
        android:layout_marginLeft="18dp"
        android:layout_marginTop="18dp"
        android:layout_marginEnd="18dp"
        android:layout_marginRight="18dp"
        android:background="@drawable/rounded_rect_white"
        android:elevation="8dp"
        android:padding="16dp"
        app:bnc_mode="packed"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <com.gauravk.bubblenavigation.BubbleToggleView
            android:id="@+id/l_item_home"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:bt_active="true"
            app:bt_colorActive="@color/red_active"
            app:bt_colorInactive="@color/red_inactive"
            app:bt_icon="@drawable/ic_home"
            app:bt_shape="@drawable/rounded_rect"
            app:bt_shapeColor="@color/red_bg_light"
            app:bt_title="@string/home" />

        <com.gauravk.bubblenavigation.BubbleToggleView
            android:id="@+id/l_item_search"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:bt_colorActive="@color/blue_active"
            app:bt_colorInactive="@color/blue_inactive"
            app:bt_icon="@drawable/ic_search"
            app:bt_shape="@drawable/rounded_rect"
            app:bt_shapeColor="@color/blue_bg_light"
            app:bt_title="@string/search" />

        <com.gauravk.bubblenavigation.BubbleToggleView
            android:id="@+id/l_item_profile_list"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:bt_colorActive="@color/blue_grey_active"
            app:bt_colorInactive="@color/blue_grey_inactive"
            app:bt_icon="@drawable/ic_like"
            app:bt_shape="@drawable/rounded_rect"
            app:bt_shapeColor="@color/blue_grey_bg_light"
            app:bt_title="@string/likes" />

        <com.gauravk.bubblenavigation.BubbleToggleView
            android:id="@+id/l_item_notification"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:bt_colorActive="@color/green_active"
            app:bt_colorInactive="@color/green_inactive"
            app:bt_icon="@drawable/ic_playlist"
            app:bt_shape="@drawable/rounded_rect"
            app:bt_shapeColor="@color/green_bg_light"
            app:bt_title="@string/notification" />

    </com.gauravk.bubblenavigation.BubbleNavigationConstraintView>*/




/*<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">


    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">


        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">


            <ImageView
                android:id="@+id/homeFragmentFeaturedImageView"
                android:layout_width="0dp"
                android:layout_height="0dp"
                android:background="@color/myColorPrimary"
                app:layout_constraintDimensionRatio="H,16:16"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />

            <TextView
                android:id="@+id/homeFragmentFeaturedImageTitle"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="24dp"
                android:layout_marginEnd="24dp"
                android:layout_marginBottom="40dp"
                android:text="High Top Og Kendrick Off White Yeezy 700 Throwback Jordans"
                android:textAlignment="center"
                android:textColor="@color/myColorText"
                android:textSize="20sp"
                android:textStyle="bold"
                app:layout_constraintBottom_toBottomOf="@id/homeFragmentFeaturedImageView"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent" />


            <TextView
                android:id="@+id/homeFragmentFeaturedHeadLine"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="24dp"
                android:layout_marginEnd="24dp"
                android:layout_marginBottom="16dp"
                android:text="@string/featured"
                android:textAlignment="center"
                android:textColor="@color/myColorText"
                android:textSize="30sp"
                android:textStyle="bold"
                app:layout_constraintBottom_toTopOf="@+id/homeFragmentFeaturedImageTitle"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent" />


            <TextView
                android:id="@+id/homeFragmentCollectionsHeadLine"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:layout_marginTop="16dp"
                android:layout_marginEnd="16dp"
                android:text="@string/collections"
                android:textAlignment="center"
                android:textColor="@color/myColorTextDark"
                android:textSize="20sp"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentFeaturedImageView"
                tools:layout_editor_absoluteX="16dp" />


            <ImageView
                android:id="@+id/homeFragmentCollectionsMenIcon"
                android:layout_width="50dp"
                android:layout_height="50dp"
                android:layout_marginStart="16dp"
                android:layout_marginTop="24dp"
                android:src="@drawable/male_profile_colored"
                app:layout_constraintEnd_toStartOf="@+id/homeFragmentCollectionsWomenIcon"
                app:layout_constraintHorizontal_bias="0.5"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentCollectionsHeadLine" />


            <ImageView
                android:id="@+id/homeFragmentCollectionsWomenIcon"
                android:layout_width="50dp"
                android:layout_height="50dp"
                android:layout_marginStart="16dp"
                android:layout_marginTop="24dp"
                android:src="@drawable/female_profile_colored"
                app:layout_constraintEnd_toStartOf="@+id/homeFragmentCollectionsSportsIcon"
                app:layout_constraintHorizontal_bias="0.5"
                app:layout_constraintStart_toEndOf="@+id/homeFragmentCollectionsMenIcon"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentCollectionsHeadLine" />


            <ImageView
                android:id="@+id/homeFragmentCollectionsSportsIcon"
                android:layout_width="50dp"
                android:layout_height="50dp"
                android:layout_marginStart="16dp"
                android:layout_marginTop="18dp"
                android:src="@drawable/sneakers_colored_filled"
                app:layout_constraintEnd_toStartOf="@+id/homeFragmentCollectionsMoreIcon"
                app:layout_constraintHorizontal_bias="0.5"
                app:layout_constraintStart_toEndOf="@+id/homeFragmentCollectionsWomenIcon"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentCollectionsHeadLine" />


            <ImageView
                android:id="@+id/homeFragmentCollectionsMoreIcon"
                android:layout_width="50dp"
                android:layout_height="50dp"
                android:layout_marginStart="16dp"
                android:layout_marginTop="24dp"
                android:layout_marginEnd="16dp"
                android:src="@drawable/more_circled_colored_filled"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.5"
                app:layout_constraintStart_toEndOf="@+id/homeFragmentCollectionsSportsIcon"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentCollectionsHeadLine" />


            <TextView
                android:id="@+id/homeFragmentBestDealsHeadLine"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="32dp"
                android:layout_marginTop="24dp"
                android:text="@string/best_deals"
                android:textColor="@color/myColorTextDark"
                android:textSize="20sp"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentCollectionsMenIcon"
                tools:layout_editor_absoluteX="16dp" />


            <ImageView
                android:id="@+id/homeFragmentBestDealsMoreIcon"
                android:layout_width="35dp"
                android:layout_height="35dp"
                android:layout_marginTop="16dp"
                android:layout_marginEnd="16dp"
                android:src="@drawable/right_arrow_black_simple"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentCollectionsMenIcon" />


            <androidx.cardview.widget.CardView
                android:id="@+id/homeFragmentBestDealsImgView1Card"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:layout_marginTop="24dp"
                app:layout_constraintEnd_toStartOf="@+id/homeFragmentBestDealsImgView2Card"
                app:layout_constraintHorizontal_bias="0.5"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentBestDealsHeadLine">


                <ImageView
                    android:id="@+id/homeFragmentBestDealsImgView1"
                    android:layout_width="150dp"
                    android:layout_height="150dp"
                    android:background="@color/myColorAccent"
                    />


            </androidx.cardview.widget.CardView>


            <TextView
                android:id="@+id/homeFragmentBestDealsPriceTV1"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp"
                android:text="70$"
                android:textColor="@color/myColorTextDark"
                android:textSize="15sp"
                app:layout_constraintEnd_toEndOf="@id/homeFragmentBestDealsImgView1Card"
                app:layout_constraintStart_toStartOf="@id/homeFragmentBestDealsImgView1Card"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentBestDealsImgView1Card"

                />


            <androidx.cardview.widget.CardView
                android:id="@+id/homeFragmentBestDealsImgView2Card"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:layout_marginTop="24dp"
                android:layout_marginEnd="16dp"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.5"
                app:layout_constraintStart_toEndOf="@+id/homeFragmentBestDealsImgView1Card"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentBestDealsHeadLine">


                <ImageView
                    android:id="@+id/homeFragmentBestDealsImgView2"
                    android:layout_width="150dp"
                    android:layout_height="150dp"
                    android:background="@color/myColorAccent"
                 />


            </androidx.cardview.widget.CardView>


            <TextView
                android:id="@+id/homeFragmentBestDealsPriceTV2"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp"
                android:text="30$"
                android:textColor="@color/myColorTextDark"
                android:textSize="15sp"
                app:layout_constraintEnd_toEndOf="@id/homeFragmentBestDealsImgView2Card"
                app:layout_constraintStart_toStartOf="@id/homeFragmentBestDealsImgView2Card"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentBestDealsImgView2Card"

                />


            <androidx.cardview.widget.CardView
                android:id="@+id/homeFragmentBestDealsImgView3Card"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:layout_marginTop="8dp"
                app:layout_constraintEnd_toStartOf="@+id/homeFragmentBestDealsImgView4Card"
                app:layout_constraintHorizontal_bias="0.5"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentBestDealsPriceTV1">


                <ImageView
                    android:id="@+id/homeFragmentBestDealsImgView3"
                    android:layout_width="150dp"
                    android:layout_height="150dp"
                    android:background="@color/myColorAccent"
                  />


            </androidx.cardview.widget.CardView>


            <TextView
                android:id="@+id/homeFragmentBestDealsPriceTV3"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp"
                android:text="45$"
                android:textColor="@color/myColorTextDark"
                android:textSize="15sp"
                app:layout_constraintEnd_toEndOf="@id/homeFragmentBestDealsImgView3Card"
                app:layout_constraintStart_toStartOf="@id/homeFragmentBestDealsImgView3Card"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentBestDealsImgView3Card"

                />


            <androidx.cardview.widget.CardView
                android:id="@+id/homeFragmentBestDealsImgView4Card"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="16dp"
                android:layout_marginTop="8dp"
                android:layout_marginEnd="16dp"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintHorizontal_bias="0.5"
                app:layout_constraintStart_toEndOf="@+id/homeFragmentBestDealsImgView3Card"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentBestDealsPriceTV2">


                <ImageView
                    android:id="@+id/homeFragmentBestDealsImgView4"
                    android:layout_width="150dp"
                    android:layout_height="150dp"
                    android:background="@color/myColorAccent"
                   />


            </androidx.cardview.widget.CardView>


            <TextView
                android:id="@+id/homeFragmentBestDealsPriceTV4"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="8dp"
                android:text="37$"
                android:textColor="@color/myColorTextDark"
                android:textSize="15sp"
                app:layout_constraintEnd_toEndOf="@id/homeFragmentBestDealsImgView4Card"
                app:layout_constraintStart_toStartOf="@id/homeFragmentBestDealsImgView4Card"
                app:layout_constraintTop_toBottomOf="@+id/homeFragmentBestDealsImgView4Card"

                />


        </androidx.constraintlayout.widget.ConstraintLayout>


    </LinearLayout>

</ScrollView>*/



















/* <com.gauravk.bubblenavigation.BubbleNavigationLinearView
        android:id="@+id/bottom_navigation_view_linear"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/white"
        android:elevation="8dp"
        android:orientation="horizontal"
        android:padding="12dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent">

        <com.gauravk.bubblenavigation.BubbleToggleView
            android:id="@+id/l_item_home"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:bt_active="true"
            app:bt_colorActive="@color/red_active"
            app:bt_colorInactive="@color/red_inactive"
            app:bt_icon="@drawable/ic_home"
            app:bt_shape="@drawable/transition_background_drawable_home"
            app:bt_title="@string/home" />

        <com.gauravk.bubblenavigation.BubbleToggleView
            android:id="@+id/l_item_search"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:bt_badgeBackgroundColor="@color/green_active"
            app:bt_colorActive="@color/blue_active"
            app:bt_colorInactive="@color/blue_inactive"
            app:bt_icon="@drawable/ic_search"
            app:bt_shape="@drawable/transition_background_drawable_search"
            app:bt_title="@string/search" />

        <com.gauravk.bubblenavigation.BubbleToggleView
            android:id="@+id/l_item_profile_list"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:bt_colorActive="@color/blue_grey_active"
            app:bt_colorInactive="@color/blue_grey_inactive"
            app:bt_icon="@drawable/ic_like"
            app:bt_shape="@drawable/transition_background_drawable_like"
            app:bt_title="@string/likes" />

        <com.gauravk.bubblenavigation.BubbleToggleView
            android:id="@+id/l_item_notification"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:bt_colorActive="@color/green_active"
            app:bt_colorInactive="@color/green_inactive"
            app:bt_icon="@drawable/ic_playlist"
            app:bt_shape="@drawable/transition_background_drawable_list"
            app:bt_title="@string/notification" />

        <com.gauravk.bubblenavigation.BubbleToggleView
            android:id="@+id/l_item_profile"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:bt_colorActive="@color/purple_active"
            app:bt_colorInactive="@color/purple_inactive"
            app:bt_icon="@drawable/ic_person"
            app:bt_shape="@drawable/transition_background_drawable_person"
            app:bt_title="@string/profile" />
    </com.gauravk.bubblenavigation.BubbleNavigationLinearView>*/





/*
        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">










            <com.gauravk.bubblenavigation.BubbleNavigationLinearView
                android:id="@+id/main_activity_bottom_navigation_view_linear"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@color/white"
                android:elevation="8dp"
                android:orientation="horizontal"
                android:padding="12dp"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent">


                <com.gauravk.bubblenavigation.BubbleToggleView
                    android:id="@+id/l_item_home"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    app:bt_active="true"
                    app:bt_colorActive="@color/red_active"
                    app:bt_colorInactive="@color/red_inactive"
                    app:bt_icon="@drawable/ic_home"
                    app:bt_shape="@drawable/transition_background_drawable_home"
                    app:bt_title="home" />


                <com.gauravk.bubblenavigation.BubbleToggleView
                    android:id="@+id/l_item_profile"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    app:bt_colorActive="@color/purple_active"
                    app:bt_colorInactive="@color/purple_inactive"
                    app:bt_icon="@drawable/ic_person"
                    app:bt_shape="@drawable/transition_background_drawable_person"
                    app:bt_title="profile" />


            </com.gauravk.bubblenavigation.BubbleNavigationLinearView>


        </androidx.constraintlayout.widget.ConstraintLayout>
*/