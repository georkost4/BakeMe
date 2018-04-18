/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.dsktp.sora.bakeme.Rest;

import android.util.Log;

import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.Utils.Constants;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 18/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
public class RecipeClient {

    public interface RecipeClientRetrofit
    {
        @GET("baking.json")
        Call<List<Recipe>> getRecipes();
    }


    public static ArrayList<Recipe> makeRequest()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.RECIPE_LIST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RecipeClientRetrofit service = retrofit.create(RecipeClientRetrofit.class);

        Type typeCollection = new TypeToken<Collection<Recipe>>(){}.getType();


        Call<List<Recipe>> response = service.getRecipes();

        response.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.isSuccessful())
                {
                    //successful response
                    Log.d("dEBUG",response.toString());
                    String resp = String.valueOf(response.body().size());
                    Log.d("DEBUG",resp);
                }
                else
                {
                    Log.e("DEBUG",response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("DEBIG","eeror:");
                t.printStackTrace();
            }
        });
        return new ArrayList<Recipe>();
    }



}
