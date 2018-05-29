/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

package com.dsktp.sora.bakeme.Repository;

import android.support.test.espresso.idling.CountingIdlingResource;
import android.util.Log;

import com.dsktp.sora.bakeme.Controller.MainScreenController;
import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.Rest.WebService;
import com.dsktp.sora.bakeme.UI.MainScreenActivity;
import com.dsktp.sora.bakeme.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 17/5/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class contains helper method for fetching data from the web.
 */
public class RemoteRepository implements WebService
{

    private String DEBUG_TAG = "#RemoteRepository.java";
    private MainScreenController mController;

    private CountingIdlingResource mIdlingResource;


    /**
     * Constructor
     * @param mainScreenController The MainScreenController object
     */
    public RemoteRepository(MainScreenController mainScreenController)
    {

       mController = mainScreenController;

       mIdlingResource = MainScreenActivity.getIdlingResource();
    }



    @Override
    public Call<List<Recipe>> getRecipes()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.RECIPE_LIST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebService service = retrofit.create(WebService.class);



        mIdlingResource.increment(); // the asynchronous task beging so increment the counter
        service.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    //We got a response from the api
                    Log.d(DEBUG_TAG, "We have the recipe list from the web service");
                    ArrayList<Recipe> recipeList = (ArrayList<Recipe>) response.body();

                    //inform back the main screen controller to update the ui
                    mController.updateTheUi(recipeList);
                    //cache the result to the local repo for later use
                    mController.saveToLocalRepository(recipeList);


                    mIdlingResource.decrement(); //decrement the idle resource to indicate that the activity is now idle


                }
                else
                {
                    String errorMessage = response.message();
                    String erroCode = String.valueOf(response.code());
                    Log.e(DEBUG_TAG, errorMessage + " " + erroCode);
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(DEBUG_TAG, t.getMessage());
                t.printStackTrace();

                mIdlingResource.decrement(); //decrement the idle resource to indicate that the activity is now idle
            }
        });
        return null;
    }

}


