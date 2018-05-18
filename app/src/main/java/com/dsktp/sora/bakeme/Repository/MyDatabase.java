/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

package com.dsktp.sora.bakeme.Repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.Utils.Converter;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 29/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class describes the Room Database
 */
@Database(entities = {Recipe.class},version = 1,exportSchema = false)
@TypeConverters({Converter.class})
public abstract class MyDatabase  extends RoomDatabase
{
    public abstract RecipeDao recipeDao();
}
