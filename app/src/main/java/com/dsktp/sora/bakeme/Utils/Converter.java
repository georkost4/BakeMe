
/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.dsktp.sora.bakeme.Utils;

import android.arch.persistence.room.TypeConverter;

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

/**
 * This class contain's method's for converting a ArrayList<E> into a JSON string and vice versa. This
 * is done so we can store them to the local Room database.
 */
public class Converter
{
    /**
     * This method converts a JSON string into a ArrayList<Step>
     * @param value The JSON string
     * @return ArrayList<Step>
     */
    @TypeConverter
    public static ArrayList<Step> StepFromString(String value) {
        Type listType = new TypeToken<ArrayList<Step>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    /**
     * This method converts a ArrayList<Step> to a JSON string
     * @param list The ArrayList<Step> to convert
     * @return the JSON string from conversion
     */
    @TypeConverter
    public static String StepFromArrayList(ArrayList<Step> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    /**
     * This method converts a JSON string into a ArrayList<Ingredient>
     * @param value The json string
     * @return ArrayList<Ingredient>
     */
    @TypeConverter
    public static ArrayList<Ingredient> IngredientFromString(String value) {
        Type listType = new TypeToken<ArrayList<Ingredient>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    /**
     * This method converts a ArrayList<Ingredient> to a JSON string
     * @param list The ArrayList<Ingredient> to convert
     * @return the JSON string from conversion
     */
    @TypeConverter
    public static String IngredientFromArrayList(ArrayList<Ingredient> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}