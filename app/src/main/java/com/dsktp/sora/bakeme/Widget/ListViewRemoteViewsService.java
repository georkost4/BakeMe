/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

package com.dsktp.sora.bakeme.Widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.dsktp.sora.bakeme.R;

import java.util.ArrayList;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 26/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
public class ListViewRemoteViewsService extends RemoteViewsService
{
    public String DEBUG_TAG = "#" + this.getClass().getSimpleName();
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {
        Log.e(DEBUG_TAG,"-----------Creating new AppWidgetListView object--------");
        return new AppWidgetListView (this.getApplicationContext(), DataModel.getDataFromSharedPrefs(getApplicationContext()));
    }
}

class AppWidgetListView implements RemoteViewsService.RemoteViewsFactory
{
    private  final String DEBUG_TAG = "#" + this.getClass().getSimpleName();
    private Context mContext;
   // private ArrayList<Recipe> mRecipesList; // todo uncomment this
    private ArrayList<DataModel> mRecipesList;
    public AppWidgetListView(Context applicationContext, ArrayList<DataModel> dataList)
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
    public int getCount()
    {
        Log.e(DEBUG_TAG,"RecipeList count = " + mRecipesList.size());
        return mRecipesList.size();
    }

    @Override
    public RemoteViews getViewAt(int position)
    {
        Log.e(DEBUG_TAG,"getViewAt is called....");
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredient_row);

//        views.setTextViewText(R.id.titleTextView, mRecipesList.get(position).getName()); // TODO CHANGE TO THIS
        views.setTextViewText(R.id.widget_ingredient_row_textview, mRecipesList.get(position).ingredientName);

        Log.e(DEBUG_TAG,mRecipesList.get(position).ingredientName);

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
        return 1;
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