/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.dsktp.sora.bakeme.Repository.Local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dsktp.sora.bakeme.Model.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 22/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
@Dao
public interface DaoAccess
{
    @Insert
    void insert(Recipe recipe);

    @Delete
    int deleteAll(Recipe[] recipes);

    @Update
    int update(Recipe recipe);


//    @Query("SELECT * FROM recipe")
//    LiveData<List<Recipe>> getRecipes();

    @Query("SELECT * FROM recipe")
    List<Recipe> getRecipes();
}
