/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

package com.dsktp.sora.bakeme.Widget;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.util.Log;

import com.dsktp.sora.bakeme.Controller.MainScreenController;
import com.dsktp.sora.bakeme.Model.Ingredient;
import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.Repository.MyDatabase;
import com.dsktp.sora.bakeme.Repository.LocalRepository;
import com.dsktp.sora.bakeme.Utils.Constants;

import java.util.ArrayList;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 26/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class provides the widget Remote View Service with two helper static methods.
 */
public class DataModel
{

    /**
     * This method return's the ingredients list by querying the database by the recipe id of the chosen recipe
     * from the shared preferences, saved there when the user clicked a recipe
     * @param context The context to access the shared preferences
     * @return ArrayList<Ingredient> list of the chosen recipe
     */
    public static ArrayList<Ingredient> getDataFromDatabase(Context context)
    {
        int recipeChosen = userRecipePreference(context); // get the recipe id from the shared preferences

        //query the database by id and get the ingredients list of that recipe
        ArrayList<Ingredient> ingredients = LocalRepository.getRoomDatabase().recipeDao().getRecipeById(recipeChosen).getIngredients();

        return ingredients;
    }


    /**
     * This method return's the recipe id of the chosen by the user in the widget configuration activity
     * @param context The context object
     * @return The id of the recipe chosen at the widget configuration screen
     */
    public static int userRecipePreference(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        int userPrefRecipeId =  sharedPreferences.getInt(Constants.WIDGET_CHOSEN_RECIPE_KEY,0);
        Log.d("DEBUG","/////////////----------RETURNING ID === " + userPrefRecipeId + "-------///////");
        return  userPrefRecipeId;
    }

}