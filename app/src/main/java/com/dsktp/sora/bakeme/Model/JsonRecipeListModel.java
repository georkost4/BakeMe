package com.dsktp.sora.bakeme.Model;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 18/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * This class represents the response from
 * the recipe list json response
 */
public class JsonRecipeListModel
{
    private ArrayList<Recipe> recipeArrayList;

    /**
     * Getter method for the recipeArrayList field
     * @return
     */
    @SerializedName("")
    public ArrayList<Recipe> getRecipeArrayList()
    {
        return recipeArrayList;
    }
}
