/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

package com.dsktp.sora.bakeme.UI.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsktp.sora.bakeme.Adapter.MyRecipeAdapter;
import com.dsktp.sora.bakeme.Controller.MainScreenController;
import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.R;

import java.util.ArrayList;


/**
 * Class for inflating a Fragment , specifically a Recipe List Fragment containing a RecycleView of the
 * Recipes from the network
 */
public class RecipeListFragment extends Fragment {


    private MyRecipeAdapter mAdapter;
    private RecyclerView mRecipeListRV;
    private String DEBUG_TAG = "#" + this.getClass().getSimpleName();
    private View inflatedView = null;
    private ArrayList<Recipe> mRecipeList;
    private MainScreenController mController = MainScreenController.getController();

    public RecipeListFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(DEBUG_TAG,"------------ON CREATE VIEW---------------");
        // Inflate the layout for this fragment
        if(inflatedView == null)
        {
            inflatedView = inflater.inflate(R.layout.fragment_recipe_list, container, false);



            setUpRecyclerView(inflatedView);
        }

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.action_bar_text_recipe_list_fragment);
        return inflatedView;
    }

    /**
     * Method for setting up the RecyclerView ocntaining the Recipe list
     * @param inflatedView
     */
    private void setUpRecyclerView(View inflatedView)
    {
        mAdapter = new MyRecipeAdapter((MyRecipeAdapter.recipeClickListener) getContext());
        mRecipeListRV = inflatedView.findViewById(R.id.rv_recipes);
        mRecipeListRV.setAdapter(mAdapter);
        mRecipeListRV.setLayoutManager(new LinearLayoutManager(getContext()));
        //set the adapter to the controller
        //to update the UI
        mController.setAdapter(mAdapter);
        //get the recipe list form repository
        mRecipeList = mController.fetchRecipes();
        //the mRecipeList will be null if we don't have a cached list
        if(mRecipeList!=null)
        {
            //update the UI
            mController.updateTheUi(mRecipeList);
        }
    }

}
