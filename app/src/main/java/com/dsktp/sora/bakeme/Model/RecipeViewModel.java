/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.dsktp.sora.bakeme.Model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dsktp.sora.bakeme.Repository.Local.RecipeDatabase;
import com.dsktp.sora.bakeme.Repository.Remote.WebService;
import com.dsktp.sora.bakeme.Utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.dsktp.sora.bakeme.Utils.Constants.DATABASE_NAME;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 19/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
public class RecipeViewModel extends AndroidViewModel
{

    private MutableLiveData<ArrayList<Recipe>> mRecipesList;
    private WebService webService;

    public RecipeViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<ArrayList<Recipe>> init()
    {
        if(mRecipesList == null)
        {
            mRecipesList = new MutableLiveData<>();
            makeRequest();
        }
        return mRecipesList;
    }


    public LiveData<ArrayList<Recipe>> getRecipeList() {
        return this.mRecipesList;
    }

    public void setmRecipesList(ArrayList<Recipe> mRecipesList) {
        this.mRecipesList.setValue(mRecipesList);
    }

    private void  makeRequest()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.RECIPE_LIST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebService service = retrofit.create(WebService.class);



        Call<List<Recipe>> response = service.getRecipes();

        response.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.isSuccessful())
                {
                    //successful response
                    Log.d("dEBUG",response.toString());
                    String resp = String.valueOf(response.body().size());

                    mRecipesList.setValue((ArrayList<Recipe>) response.body());
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
    }
}
