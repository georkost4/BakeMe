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
        if(intentFromWidget!=null)
        {
            mWidgetId = intentFromWidget.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

            RemoteViews views = new RemoteViews(getPackageName(),R.layout.recipe_widget_layout);

            appWidgetManager.updateAppWidget(mWidgetId, views);

        }

    }

    private void showTheAvailableRecipes()
    {
        ArrayList<Recipe> recipes = getRecipeFromDb();
        setUpListView(recipes);

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

        sharedPreferences.edit().putInt("widget_chosen_recipe_id",id).apply();

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }


    private ArrayList<Recipe> getRecipeFromDb()
    {
        ArrayList<Recipe> recipesFromDatabase = LocalRepository.getLocalRepository().getAllRecipes(); // todo check if there is database first

        return recipesFromDatabase;
    }

}
