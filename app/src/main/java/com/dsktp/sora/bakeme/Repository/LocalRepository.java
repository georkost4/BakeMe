/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

package com.dsktp.sora.bakeme.Repository;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.dsktp.sora.bakeme.Model.Recipe;

import java.util.ArrayList;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 30/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
public class LocalRepository  {

    private static final String DEBUG_TAG = "#LocalRepository.java";
    private static MyDatabase mDb = null;

    public static MyDatabase getInstance(Context context)
    {
        if (mDb == null)
        {
            mDb =  Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class,"recipe.db").allowMainThreadQueries().build(); // TODO CREATE CONSTANT FOR DB NAME and remove the main thread query
        }
        return mDb;
    }
    public static MyDatabase getInstance()
    {
        if (mDb != null) return mDb;
        return null;
    }


    public void saveRecipe(Recipe recipe)
    {
        mDb.recipeDao().insertRecipe(recipe);
    }


    public Recipe getRecipeById(int recipeID)
    {
        return mDb.recipeDao().getRecipeById(recipeID);
    }


    public ArrayList<Recipe> getAllRecipes()
    {
        Log.d(DEBUG_TAG,"Returning cached list from local repo...");
        return (ArrayList<Recipe>) mDb.recipeDao().getAllRecipes();
    }


    public void saveAllRecipe(ArrayList<Recipe> recipes)
    {
        mDb.recipeDao().insertAllRecipes(recipes);
    }

    public boolean checkIfThereIsCachedList()
    {
        int rows = mDb.recipeDao().checkIfEmpty();
        Log.d(DEBUG_TAG,"rows returned from check method = " + rows);
        if(rows>0) return true; // the table exists so there is cached version of the list
        return false; // there are no rows returned so no cached version of the list
    }


}
