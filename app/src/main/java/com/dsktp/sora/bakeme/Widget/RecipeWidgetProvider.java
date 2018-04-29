/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

package com.dsktp.sora.bakeme.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.dsktp.sora.bakeme.R;
import com.dsktp.sora.bakeme.UI.MainScreenActivity;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 26/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
public class RecipeWidgetProvider extends AppWidgetProvider
{
    private static String DEBUG_TAG = "#WidgetProvider.java";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //update all the widget's on our homescreen
        Log.e(DEBUG_TAG,"-----------------onUpdate called-----------------");
        WidgetUpdateService.startActionUpdateAppWidgets(context);
    }

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appId)
    {
        Log.e(DEBUG_TAG,"-----------------updateAppWidget called-----------------");
        // Create an Intent to launch MainActivity when clicked
        Intent fillListViewDataIntent = new Intent(context, ListViewRemoteViewsService.class);


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_layout);

        views.setRemoteAdapter(R.id.widget_recipe_listview, fillListViewDataIntent);

        //Handle click on the listView
        Intent appIntent = new Intent(context, MainScreenActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_recipe_listview, appPendingIntent);

        //set empty view if no data are available
        views.setEmptyView(R.id.widget_recipe_listview,R.id.empty_view);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appId, views);
        Log.e(DEBUG_TAG,"-----------------This line is executed-----------------");
    }

    public static void updateAllAppWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds)
        {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}