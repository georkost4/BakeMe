/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

package com.dsktp.sora.bakeme.Widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dsktp.sora.bakeme.R;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 26/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
public class WidgetUpdateService extends IntentService
{
    public static final String ACTION_UPDATE_LIST_VIEW = "com.dsktp.sora.bakeme.widget.widgetupdateservice.update_app_widget_list";

    private static String DEBUG_TAG = "#WidgetUpdateService.java";

    public WidgetUpdateService() {
        super("WidgetUpdateService");
        Log.e(DEBUG_TAG,"-----------------super constructor executed-----------------");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent)
    {
        if (intent != null)
        {
            final String action = intent.getAction();

            if(ACTION_UPDATE_LIST_VIEW.equals(action))
            {
                Log.e(DEBUG_TAG,"-----------------handleActionUpdateListView-----------------");
                handleActionUpdateListView();
            }
        }

    }

    private void handleActionUpdateListView()
    {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));

        RecipeWidgetProvider.updateAllAppWidget(this, appWidgetManager,appWidgetIds);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_recipe_listview);

    }


    public static void startActionUpdateAppWidgets(Context context) {
        Log.e(DEBUG_TAG,"-----------------startActionUpdateAppWidgets-----------------");
        Intent intent = new Intent(context, WidgetUpdateService.class);

        intent.setAction(ACTION_UPDATE_LIST_VIEW);

        context.startService(intent);
    }
}
