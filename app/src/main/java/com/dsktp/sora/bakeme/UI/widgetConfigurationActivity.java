/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

package com.dsktp.sora.bakeme.UI;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.R;
import com.dsktp.sora.bakeme.Repository.LocalRepository;
import com.dsktp.sora.bakeme.Utils.Constants;

import java.util.ArrayList;

public class widgetConfigurationActivity extends AppCompatActivity {


    int mWidgetId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Set the result to CANCELED. This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.activity_widget_configuration);

        showTheAvailableRecipes();

        Intent intentFromWidget = getIntent();

        mWidgetId = intentFromWidget.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        RemoteViews views = new RemoteViews(getPackageName(),R.layout.recipe_widget_layout);

        appWidgetManager.updateAppWidget(mWidgetId, views);



    }

    private void showTheAvailableRecipes()
    {
        ArrayList<Recipe> recipes = getRecipeFromDb();
        if (recipes != null) // if there is no database the above statement will return NULL so check first
        {
            //we have a database and we have recipes available
            //so setup the listview
            setUpListView(recipes);
        }

    }

    private void setUpListView(final ArrayList<Recipe> recipes) {
        ListView listView = findViewById(R.id.configuration_listview_recipes);

        ArrayList<String> values = new ArrayList<>();

        for(Recipe recipe : recipes)
        {
            values.add(recipe.getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,values);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String toastMessage = recipes.get(position).getName() + " Clicked";
                Toast.makeText(getApplicationContext(), toastMessage,Toast.LENGTH_LONG).show();

                saveTheRecipeToTheUserPreferences(recipes.get(position).getId());

            }
        });
    }

    private void saveTheRecipeToTheUserPreferences(int id) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        Log.d("DEBIG","------------------------------Saving to user pref id with " + id + "------------------------");

        sharedPreferences.edit().putInt(Constants.WIDGET_CHOSEN_RECIPE_KEY,id).apply();

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }


    private ArrayList<Recipe> getRecipeFromDb()
    {
        //check first if there is a cached list
        boolean weHaveCachedList = LocalRepository.getLocalRepository().checkIfThereIsCachedList();
        if(weHaveCachedList)
        {
            //get the recipe list from the repository
            ArrayList<Recipe> recipesFromDatabase = LocalRepository.getLocalRepository().getAllRecipes();
            return recipesFromDatabase;
        }
        else
        {
            //show a toast to the user
            Toast.makeText(this,"Run the app first to download the available recipes",Toast.LENGTH_LONG).show();
            setResult(RESULT_CANCELED);
            finish();
        }
        return null;

    }

}
