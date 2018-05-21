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


public class DataModel
{

    public static ArrayList<Ingredient> getDataFromDatabase(Context context)
    {
        int recipeChosen = userRecipePreference(context);

        ArrayList<Ingredient> ingredients = LocalRepository.getRoomDatabase().recipeDao().getRecipeById(recipeChosen).getIngredients();

        return ingredients;
    }



    public static int userRecipePreference(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        int userPrefRecipeId =  sharedPreferences.getInt(Constants.WIDGET_CHOSEN_RECIPE_KEY,0);
        Log.d("DEBUG","/////////////----------RETURNING ID === " + userPrefRecipeId + "-------///////");
        return  userPrefRecipeId;
    }

}