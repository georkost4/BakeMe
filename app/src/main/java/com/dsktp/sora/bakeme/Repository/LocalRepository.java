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


/**
 * This class is contain's helper method's to communicate with the Local database created with Room.
 * All the method's use AsnyncTask's for doing the transaction in a background thread.
 */
public class LocalRepository  {

    private static final String DEBUG_TAG = "#LocalRepository.java";
    private static MyDatabase mDb = null;
    private static LocalRepository instance = null;

    /**
     * Default private constructor
     */
    private LocalRepository() {    }


    /**
     * This method return's a SINLGLE instance of this class
     * to ensure we have only one Database object across the application
     * @param context The context to create the database
     * @return a object of this class
     */
    public static MyDatabase getRoomDatabase(Context context)
    {
        if (mDb == null)
        {
            mDb =  Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class,"recipe.db").allowMainThreadQueries().build(); // TODO CREATE CONSTANT FOR DB NAME and remove the main thread query
        }
        return mDb;
    }

    /**
     * This method return's a SINLGLE instance of this class
     * to ensure we have only one Database object across the application
     * @return a object of this class
     */
    public static MyDatabase getRoomDatabase()
    {
        if (mDb != null) return mDb;
        return null;
    }

    public static LocalRepository getLocalRepository()
    {
        if(instance==null) return new LocalRepository();
        else return instance;
    }


    /**
     * Method for saving a single Recipe to the database
     * @param recipe
     */
    public void saveRecipe(Recipe recipe)  //todo asynctask background query
    {
        mDb.recipeDao().insertRecipe(recipe);
    }

    /**
     * This method return's a Recipe object from the database
     * using the recipeID.
     * @param recipeID The id of the recipe to return
     * @return The Recipe object
     */
    public Recipe getRecipeById(int recipeID) //todo asynctask background query
    {
        return mDb.recipeDao().getRecipeById(recipeID);
    }


    /**
     * This method return's all the Recipe object's stored in the database
     * @return ArrayList<Recipe> with all recipe object's from the database
     */
    public ArrayList<Recipe> getAllRecipes() //todo asynctask background query
    {
        Log.d(DEBUG_TAG,"Returning cached list from local repo...");
        return (ArrayList<Recipe>) mDb.recipeDao().getAllRecipes();
    }

    /**
     * This method is used to save all the Recipe  from the list to the local
     * database
     * @param recipes The list with the Recipe object's
     */
    public void saveAllRecipe(ArrayList<Recipe> recipes) //todo asynctask background query
    {
        mDb.recipeDao().insertAllRecipes(recipes);
    }

    /**
     * This method check's to determine if we have a local cache of the recipe list
     * available to the database
     * @return
     */
    public boolean checkIfThereIsCachedList() //todo asynctask background query
    {
        int rows = mDb.recipeDao().checkIfEmpty();
        Log.d(DEBUG_TAG,"rows returned from check method = " + rows);
        if(rows>0) return true; // the table exists so there is cached version of the list
        return false; // there are no rows returned so no cached version of the list
    }


}
