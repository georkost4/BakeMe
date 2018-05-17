/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

package com.dsktp.sora.bakeme.Repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.dsktp.sora.bakeme.Model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 29/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */

@Dao
public interface RecipeDao
{
    @Query("SELECT * FROM recipes")
    List<Recipe> getAllRecipes();

    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    Recipe getRecipeById(int recipeId);


    @Insert
    void insertAllRecipes(ArrayList<Recipe> recipes);

    @Insert
    void insertRecipe(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("SELECT id from recipes limit 2")
    int checkIfEmpty();
}
