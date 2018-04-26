/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

package com.dsktp.sora.bakeme.Widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 26/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
public class ListViewWidgetService extends RemoteViewsService
{
    public String DEBUG_TAG = "#" + this.getClass().getSimpleName();
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {
        Log.e(DEBUG_TAG,"-----------Creating new AppWidgetListView object--------");
        return new AppWidgetListView (this.getApplicationContext(), WidgetDataModel.getDataFromSharedPrefs(getApplicationContext()));
    }
}

class AppWidgetListView implements RemoteViewsService.RemoteViewsFactory
{
    private  final String DEBUG_TAG = "#" + this.getClass().getSimpleName();
    private Context mContext;
   // private ArrayList<Recipe> mRecipesList; // todo uncomment this
    private ArrayList<WidgetDataModel> mRecipesList;
    public AppWidgetListView(Context applicationContext, ArrayList<WidgetDataModel> dataList)
    {
        mContext = applicationContext;
        mRecipesList = dataList;
        Log.e(DEBUG_TAG,"-----------------updateAppWidget inside Contructor-----------------");
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mRecipesList.size();
    }

    @Override
    public RemoteViews getViewAt(int position)
    {
        Log.e(DEBUG_TAG,"getViewAt is called....");
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredient_row);

//        views.setTextViewText(R.id.titleTextView, mRecipesList.get(position).getName()); // TODO CHANGE TO THIS
        views.setTextViewText(R.id.widget_ingredient_row_textview, mRecipesList.get(position).ingredientName);

        //fill the pending intent with data
        Intent fillInIntent = new Intent();
//        fillInIntent.putExtra("ItemTitle",mRecipesList.get(position).getName()); // todo change to this
        fillInIntent.putExtra("ItemTitle",mRecipesList.get(position).ingredientName);
        views.setOnClickFillInIntent(R.id.widget_ingredient_row_textview, fillInIntent);

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}