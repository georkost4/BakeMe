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

import com.dsktp.sora.bakeme.Model.Ingredient;
import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.R;
import com.dsktp.sora.bakeme.Utils.Constants;

import java.util.ArrayList;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 26/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class is creating the list view item by creating a AppWidgetListView object which implements a RemoteView factory class
 */
public class ListViewRemoteViewsService extends RemoteViewsService
{
    public String DEBUG_TAG = "#" + this.getClass().getSimpleName();
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent)
    {
        Log.e(DEBUG_TAG,"-----------Creating new AppWidgetListView object--------");

        return new AppWidgetListView (this.getApplicationContext(), DataModel.getDataFromDatabase(getApplicationContext()));
    }
}

class AppWidgetListView implements RemoteViewsService.RemoteViewsFactory
{
    private  final String DEBUG_TAG = "#" + this.getClass().getSimpleName();
    private Context mContext;
    private ArrayList<Ingredient> mIngredientList;


    public AppWidgetListView(Context applicationContext, ArrayList<Ingredient> dataList)
    {
        mContext = applicationContext;
        mIngredientList = dataList;
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
        Log.e(DEBUG_TAG,"Ingredients list count = " + mIngredientList.size());
        return mIngredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position)
    {
        Log.e(DEBUG_TAG,"getViewAt is called....");
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredient_row);

        views.setTextViewText(R.id.widget_ingredient_row_ingredient_name, mIngredientList.get(position).getIngredient());
        String quantityMeasuere = mIngredientList.get(position).getQuantity() + " " + mIngredientList.get(position).getMeasure();
        views.setTextViewText(R.id.widget_ingredient_row_ingredient_detail,quantityMeasuere);

        //fill the pending intent with data
        Intent fillInIntent = new Intent();

        //set action to the intent so the activity can handle the action properly
        fillInIntent.setAction(Constants.SHOW_RECIPE_DETAILS_ACTION);

        //set the click listener
        views.setOnClickFillInIntent(R.id.widget_ingredient_row_ingredient_name, fillInIntent);

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