/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.dsktp.sora.bakeme;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.Repository.Local.RecipeDatabase;

import java.util.ArrayList;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
class MainScreenController
{
    public MainScreenController()
    {

    }



    public void updateDatabaseAsync(final ArrayList<Recipe> recipes, final RecipeDatabase db)
    {
       AsyncTask asyncTask = new AsyncTask() {
           @Override
           protected Object doInBackground(Object[] objects)
           {
               for(int i=0;i<recipes.size();i++) {
                   db.daoAccess().insert(recipes.get(i));
               }
               return null;
           }
       }.execute();
    }

    public ArrayList<Recipe> fetchFromDatabaseAsync(final RecipeDatabase db)
    {
       ArrayList<Recipe> returnVal = new ArrayList<>();
       @SuppressLint("StaticFieldLeak") AsyncTask asyncTask = new AsyncTask()
        {
            @Override
            protected ArrayList<Recipe> doInBackground(Object[] objects)
            {
                return (ArrayList<Recipe>) db.daoAccess().getRecipes();

            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);

            }
        }.
    }




}
