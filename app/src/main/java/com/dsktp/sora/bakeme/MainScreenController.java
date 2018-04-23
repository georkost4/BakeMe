/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.dsktp.sora.bakeme;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.dsktp.sora.bakeme.Adapter.MyRecipeAdapter;
import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.Rest.WebService;
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

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
public class MainScreenController implements WebService {
    private final MainScreenActivity MainScreenActivity;
    private String DEBUG_TAG = "MainScreenController";

    public MainScreenController(MainScreenActivity mainScreenActivity) {
        this.MainScreenActivity = mainScreenActivity;
    }

    @Override
    public Call<List<Recipe>> getRecipes()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.RECIPE_LIST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WebService service = retrofit.create(WebService.class);

        Type typeCollection = new TypeToken<Collection<Recipe>>(){}.getType();


        service.getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if(response.isSuccessful())
                {
                    //We got a response from the api
                    Log.d(DEBUG_TAG,"We have the recipe list from the web service");
                    ArrayList<Recipe> recipeList = (ArrayList<Recipe>) response.body();
                    updateTheUi(recipeList);

                }
                else
                {
                    String errorMessage = response.message();
                    String erroCode = String.valueOf(response.code());
                    Log.e(DEBUG_TAG,errorMessage + " " + erroCode);
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t)
            {
                Log.e(DEBUG_TAG,t.getMessage());
                t.printStackTrace();
            }
        });
        return null;
    }


    public void updateTheUi(ArrayList<Recipe> list)
    {
        MyRecipeAdapter adapter = new MyRecipeAdapter(MainScreenActivity.getApplicationContext(),MainScreenActivity);
        RecyclerView recyclerView = MainScreenActivity.findViewById(R.id.rv_recipes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainScreenActivity.getApplicationContext()));
        adapter.setRecipeList(list);

    }



}
