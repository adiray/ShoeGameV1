package com.example.dell.shoegamev1.repositories;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.example.dell.shoegamev1.responseobjects.ShoeObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;

public class ShoesRepository {


    public MutableLiveData<List<Map>> bestDealsShoesResponse = new MutableLiveData<>();
    public MutableLiveData<Boolean> bestDealsRequestResult = new MutableLiveData<>();
    private List<Map> tagsResponse = new ArrayList<>();  //list holds the initial tag objects response
    public MutableLiveData<List<Map>> tagsResponseMutableData = new MutableLiveData<>();
    public MutableLiveData<Boolean> tagsRequestResult = new MutableLiveData<>();  //the result of the request for the tags
    public MutableLiveData<List<Map>> specificTagsResponseMutableData = new MutableLiveData<>();
    public MutableLiveData<Boolean> specificTagsRequestResult = new MutableLiveData<>();  //the result of the request for the specific tags
    private static ShoesRepository instance;
    private ArrayList<ShoeObject> dataSet = new ArrayList<>();

    private ShoesRepository() {}   //make the constructor private in accordance with singleton pattern


    public static ShoesRepository getInstance() {
        if (instance == null) {
            instance = new ShoesRepository();
        }
        return instance;
    }

    public void requestBestDeals(String whereClause, String sortBy ) {
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);
        queryBuilder.setPageSize(4);
        queryBuilder.setSortBy(sortBy);


        Backendless.Data.of("shoe").find(queryBuilder, new AsyncCallback<List<Map>>() {
            @Override
            public void handleResponse(List<Map> response) {

                if (response != null) {

                    bestDealsShoesResponse.setValue(response);
                    bestDealsRequestResult.setValue(true);
                    Log.d("MyLogsBestDeal", "best deals retrieved successfully. response: " + response.toString());

                }

//https://api.backendless.com/05DBC061-3DE1-0252-FF3C-FBCECC684700/25314E66-30EB-BF2D-FF4B-31B310A6FD00/data/
// shoe?where=tags%20%3D%20'EF76CBF7-09E0-75D3-FF15-1072F7C51E00'&sortBy=price%20asc

            }

            @Override
            public void handleFault(BackendlessFault fault) {

                if (fault != null) {

                    Log.d("MyLogsBestDeal", "best deals retrieval failed. error: " + fault.toString());

                }

                bestDealsRequestResult.setValue(false);

            }
        });


    }


    //get all the tags
    public void requestTags() {

        Backendless.Data.of("tags").find(new AsyncCallback<List<Map>>() {
            @Override
            public void handleResponse(List<Map> response) {

                if (response != null) {
                    tagsResponse = response;
                    tagsResponseMutableData.setValue(response);
                    tagsRequestResult.setValue(true);
                    Log.d("MyLogsTags", "tags retrieved successfully. response: " + response.toString());
                }


            }

            @Override
            public void handleFault(BackendlessFault fault) {

                tagsRequestResult.setValue(false);

                if (fault != null) {

                    Log.d("MyLogsTags", "tags NOT retrieved. error: " + fault.toString());

                }

            }
        });


    }


    public void requestSpecifiedTag(String whereClause ){

        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereClause);

        Backendless.Data.of("tags").find(queryBuilder, new AsyncCallback<List<Map>>() {
            @Override
            public void handleResponse(List<Map> response) {

                if (response != null) {
                    specificTagsResponseMutableData.setValue(response);
                    specificTagsRequestResult.setValue(true);
                    Log.d("MyLogsTags", "specific tags retrieved successfully. response: " + response.toString());
                }

            }

            @Override
            public void handleFault(BackendlessFault fault) {

                specificTagsRequestResult.setValue(false);

                if (fault != null) {

                    Log.d("MyLogsTags", "specific tags NOT retrieved. error: " + fault.toString());

                }


            }
        });


    }


}
