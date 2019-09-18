package com.example.dell.shoegamev1.viewmodels;

import com.example.dell.shoegamev1.repositories.ShoesRepository;

import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragmentShoesViewModel extends ViewModel {


    private ShoesRepository shoesRepository;

    public void init() {

        shoesRepository = ShoesRepository.getInstance();
    }







    // ***********************************************************
    //  TAGS METHODS : get all the tags with these
    // ***********************************************************





    public LiveData<Boolean> getTagsRequestResult() {

        return shoesRepository.tagsRequestResult;

    }

    public LiveData<List<Map>> getTagsResponse() {

        return shoesRepository.tagsResponseMutableData;

    }

    public void requestTags() {

        shoesRepository.requestTags();

    }








    // ***********************************************************
    // SPECIFIED TAGS METHODS : Search the tags with these
    // ***********************************************************


    public LiveData<Boolean> getSpecificTagsRequestResult() {

        return shoesRepository.specificTagsRequestResult;

    }


    public LiveData<List<Map>> getSpecificTagsResponse() {

        return shoesRepository.specificTagsResponseMutableData;

    }


    public void requestSpecificTags(String whereClause) {

        shoesRepository.requestSpecifiedTag(whereClause);

    }





    // ***********************************************************
    // BEST DEALS METHODS : use these to get the best deals
    // ***********************************************************





    public void requestBestDeals(String whereClause, String sortBy) {

        shoesRepository.requestBestDeals(whereClause,sortBy);
    }

    public LiveData<Boolean> getBestDealsRequestResult() {

        return shoesRepository.bestDealsRequestResult;
    }

    public LiveData<List<Map>> getBestDealsResponse() {

        return shoesRepository.bestDealsShoesResponse;
    }


}
