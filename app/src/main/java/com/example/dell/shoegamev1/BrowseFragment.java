package com.example.dell.shoegamev1;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import net.cachapa.expandablelayout.ExpandableLayout;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class BrowseFragment extends Fragment {


    View view;
    ExpandableLayout filterExpandableLayout;
    TextView addFiltersTv;

    final int max = 45;
    final int min = 10;
    final int total = max - min;



    //colors
    //#7E7A7A



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.browse_fragment, container, false);




        filterExpandableLayout = view.findViewById(R.id.browseFragmentExpandableLayout);
        addFiltersTv = view.findViewById(R.id.browseFragmentAddFiltersTv);

        addFiltersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (filterExpandableLayout.isExpanded()){


                    filterExpandableLayout.collapse();
                    addFiltersTv.setTextColor(getResources().getColor(R.color.myColorInactiveTextBg));

                }else {

                    filterExpandableLayout.expand();
                    addFiltersTv.setTextColor(getResources().getColor(R.color.myColorAccentDark));


                }





            }
        });





/*


        if (slider != null){
            slider.setPositionListener(new Function1<Float, Unit>() {
                @Override
                public Unit invoke(Float aFloat) {

                    final String value = String.valueOf( (int)(min + total * aFloat) );
                    slider.setBubbleText(value);
                    return Unit.INSTANCE;


                }
            });}else{

            Log.d("My Logs slider", "Slider is null");

        }


        slider.setPosition(0.3f);

        slider.setStartText(String.valueOf(min));
        slider.setEndText(String.valueOf(max));



        */



        // Java 8 lambda
       /* slider.setPositionListener(pos -> {
            final String value = String.valueOf( (int)(min + total * pos) );
            slider.setBubbleText(value);
            return Unit.INSTANCE;
        });

*/
        return view;
    }
}









/* <com.ramotion.fluidslider.FluidSlider

            android:id="@+id/fluidSlider"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginStart="32dp"
            android:layout_marginEnd="32dp"
            android:elevation="2dp"
            app:bar_color="@color/myColorAccentDark"
            app:bar_text_color="@color/myColorAccent"
            app:duration="@android:integer/config_mediumAnimTime"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/browseFragmentPriceFilterTv"
            app:size="small"
            tools:targetApi="lollipop" />

*/






















/*<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tl="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/myColorAccent"
    android:orientation="vertical"
    tools:context=".MainActivity">


    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="120dp"
        android:padding="8dp">

        <com.gauravk.bubblenavigation.BubbleNavigationConstraintView
            android:id="@+id/mainActivityTopTabLayout"
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
            app:bnc_mode="spread"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent">


            <com.gauravk.bubblenavigation.BubbleToggleView
                android:id="@+id/item_home"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                app:bt_active="true"
                app:bt_colorActive="@color/myColorAccentDark"
                app:bt_colorInactive="@color/myColorAccent"
                app:bt_icon="@drawable/ic_home"
                app:bt_shape="@drawable/rounded_rect"
                app:bt_shapeColor="@color/red_bg_light"
                app:bt_title="home" />


            <com.gauravk.bubblenavigation.BubbleToggleView
                android:id="@+id/l_item_notification"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                app:bt_colorActive="@color/myColorAccentDark"
                app:bt_colorInactive="@color/myColorAccent"
                app:bt_icon="@drawable/ic_playlist"
                app:bt_shape="@drawable/rounded_rect"
                app:bt_shapeColor="@color/green_bg_light"
                app:bt_title="Browse" />


        </com.gauravk.bubblenavigation.BubbleNavigationConstraintView>


    </androidx.constraintlayout.widget.ConstraintLayout>





    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@drawable/rounded_rect_white"
        android:orientation="vertical">





        <androidx.viewpager.widget.ViewPager
            android:id="@+id/mainActivityViewPager"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_marginTop="0dp"
            android:layout_marginBottom="24dp" />








    </LinearLayout>






    <com.volcaniccoder.bottomify.BottomifyNavigationView
        android:id="@+id/bottomify_nav"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/bottomifyBackgroundColor"
        android:orientation="horizontal"
        app:menu="@menu/main_activity_bottomify_menu"
        app:active_color="@color/bottomifyActiveColor"
        app:passive_color="@color/bottomifyPassiveColor"
        app:pressed_color="@color/bottomifyPressedColor"
        app:item_text_size="10sp"
        app:item_padding="4dp"
        app:animation_duration="300"
        app:scale_percent="5" />










</LinearLayout>*/