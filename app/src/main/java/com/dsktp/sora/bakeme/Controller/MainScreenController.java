/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

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

package com.dsktp.sora.bakeme.Controller;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.dsktp.sora.bakeme.Adapter.MyRecipeAdapter;
import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.Repository.MyDatabase;
import com.dsktp.sora.bakeme.Repository.RecipeDao;
import com.dsktp.sora.bakeme.Rest.WebService;
import com.dsktp.sora.bakeme.Utils.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

    private String DEBUG_TAG = "MainScreenController";
    private MyRecipeAdapter mAdapter = null;
    private static MainScreenController instance = null;
    private ArrayList<Recipe> mRecipeList = null;
    private RecipeDao mDao = null;
    private MyDatabase mDb = null;

    private MainScreenController(){}

    public static MainScreenController getController()
    {
        if(instance == null)
        {
           instance =  new MainScreenController();
        }
        return instance;
    }

    public ArrayList<Recipe> getRecipeList() {
        return mRecipeList;
    }

    public void setRecipeList(ArrayList<Recipe> mRecipeList) {
        this.mRecipeList = mRecipeList;
    }

    @Override
    public Call<List<Recipe>> getRecipes()
    {
        if(getRecipeList() == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.RECIPE_LIST_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            WebService service = retrofit.create(WebService.class);


            service.getRecipes().enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    if(response.isSuccessful())
                    {
                        //We got a response from the api
                        Log.d(DEBUG_TAG,"We have the recipe list from the web service");
                        ArrayList<Recipe> recipeList = (ArrayList<Recipe>) response.body();
                        updateTheUi(recipeList);

//                        setRecipeList(recipeList); //todo unnesesary statement

//                        saveTheListToDatabase(recipeList);


                        for(Recipe recipe:recipeList)
                        {
                            Log.d(DEBUG_TAG,"Recipe's id's:"+recipe.getId());
                        }

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
        }
        else
        {
            updateTheUi(getRecipeList());
        }
        return null;
    }


    public void updateTheUi(ArrayList<Recipe> list)
    {
       if(mAdapter!=null)
       {
           // if there is an adapter update the UI
           mAdapter.setRecipeList(list);

       }
       else
       {
           //just log an error
           Log.e(DEBUG_TAG,"There was no adapter set  to update the UI ");
       }

    }


    public void setAdapter(MyRecipeAdapter adapter)
    {
      mAdapter = adapter;
    }

    private void saveTheListToDatabase(final ArrayList<Recipe> recipes)
    {
        if(mDb != null)
        {
            mDao = mDb.recipeDao();

            AsyncTask anonym = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects)
                {
                    for(Recipe recipe : recipes)
                    {
                        mDao.insertAll(recipes.);
                    }
                    return null;
                }
            }.execute();

        }
    }

    public ArrayList<Recipe> getRecipesFromDatabase()
    {
        if(mDb != null)
        {
            mDao = mDb.recipeDao();

            final ArrayList<Recipe>[] recipes = new ArrayList[0];
            @SuppressLint("StaticFieldLeak") AsyncTask anonym = new AsyncTask() {
                @Override
                protected ArrayList<Recipe> doInBackground(Object[] objects)
                {
                    return (ArrayList<Recipe>) mDao.getAllRecipes(); // todo also set this field variable to the updated list
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    mRecipeList = (ArrayList<Recipe>) o;
                }
            }.execute();

            return getRecipeList();
        }
        return null;
    }

    public void setDb(MyDatabase mDb) {
        this.mDb = mDb;
    }
}
