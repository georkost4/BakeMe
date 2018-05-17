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
import android.util.Log;

import com.dsktp.sora.bakeme.Controller.MainScreenController;
import com.dsktp.sora.bakeme.Model.Ingredient;
import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.Repository.MyDatabase;
import com.dsktp.sora.bakeme.Repository.LocalRepository;

import java.util.ArrayList;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 26/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
public class DataModel
{

    public String ingredientName = "";


    public static ArrayList<Ingredient> getDataFromDatabase(Context context)
    {
        ArrayList<Recipe> list = null;

        MainScreenController controller = MainScreenController.getController();
        MyDatabase db = LocalRepository.getInstance(context);

        int recipeChosen = userRecipePreference(context);

        if(db!=null)
        {
            list = controller.fetchRecipes();
        }
        else
        {
            db = Room.databaseBuilder(context,MyDatabase.class,"recipe.db").build();
            list = (ArrayList<Recipe>) db.recipeDao().getAllRecipes();

        }
        return list.get(recipeChosen).getIngredients();
    }



    public static int userRecipePreference(Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        int userPrefRecipeId =  sharedPreferences.getInt("widget_chosen_recipe_id",0);
        Log.d("DEBUG","/////////////----------RETURNING ID === " + userPrefRecipeId + "-------///////");
        return  userPrefRecipeId;
    }

}