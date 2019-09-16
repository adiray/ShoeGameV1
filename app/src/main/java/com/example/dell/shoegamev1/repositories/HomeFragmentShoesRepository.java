package com.example.dell.shoegamev1.repositories;

import com.example.dell.shoegamev1.responseobjects.ShoeObject;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class HomeFragmentShoesRepository {


    private static HomeFragmentShoesRepository instance;
    private ArrayList<ShoeObject> dataSet = new ArrayList<>();


    public static HomeFragmentShoesRepository getInstance() {
        if (instance == null) {
            instance = new HomeFragmentShoesRepository();
        }
        return instance;
    }

    public MutableLiveData<List<ShoeObject>> getBestDeals(){
        requestBestDeals();
        MutableLiveData<List<ShoeObject>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }



   private void requestBestDeals (){








    }


}
