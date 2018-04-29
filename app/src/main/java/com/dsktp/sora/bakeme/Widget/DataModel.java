/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

package com.dsktp.sora.bakeme.Widget;

import android.content.Context;
import android.content.SharedPreferences;

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


    public static void createSampleDataForWidget(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE);


        sharedPref.edit().putString("title","Cake").apply(); //todo change "title" to ingredient-Name
        sharedPref.edit().putString("title1","Muffin").apply(); //todo change "title" to ingredient-Name

    }

    public static ArrayList<DataModel> getDataFromSharedPrefs(Context context) {
        ArrayList<DataModel> list=new ArrayList<>();
        if(list.isEmpty())
        {
            SharedPreferences sharedPref = context.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE);

            DataModel model = new DataModel();
            DataModel model1 = new DataModel();
            model.ingredientName = sharedPref.getString("title","nope");
            model1.ingredientName = sharedPref.getString("title1","nope");

            list.add(model);
            list.add(model1);

        }
        return list;
    }

}