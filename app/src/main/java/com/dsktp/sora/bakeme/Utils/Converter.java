/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.dsktp.sora.bakeme.Utils;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.dsktp.sora.bakeme.Model.Ingredient;
import com.dsktp.sora.bakeme.Model.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 22/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
public class Converter
{
    @TypeConverter
    public static ArrayList<Step> StepFromString(String value) {
        Type listType = new TypeToken<ArrayList<Step>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String StepFromArrayList(ArrayList<Step> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static ArrayList<Ingredient> IngredientFromString(String value) {
        Type listType = new TypeToken<ArrayList<Ingredient>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
    @TypeConverter
    public static String IngredientFromArrayList(ArrayList<Ingredient> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
