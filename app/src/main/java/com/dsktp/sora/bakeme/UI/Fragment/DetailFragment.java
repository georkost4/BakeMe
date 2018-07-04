/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.dsktp.sora.bakeme.UI.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsktp.sora.bakeme.Adapter.MyIngredientsAdapter;
import com.dsktp.sora.bakeme.Adapter.MyStepAdapter;
import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.R;
import com.dsktp.sora.bakeme.Utils.Constants;

import static com.dsktp.sora.bakeme.Utils.Constants.RECIPE_CLICKED_KEY;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */

/**
 * Class for inflating a Fragment , specifically a Detail Fragment containing a RecycleView of ingredients
 * from selected Recipe and a RecyclerView with the the steps of execution for this recipe.
 */
public class DetailFragment extends Fragment
{
    private Recipe  mRecipeClicked;
    private  String DEBUG_TAG = "#" +this.getClass().getSimpleName();

    public DetailFragment(){}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle fragmentArgsBundle = getArguments();
        mRecipeClicked = fragmentArgsBundle.getParcelable(Constants.DETAIL_FRAGMENT_ARGS_KEY);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(DEBUG_TAG,"------------ON CREATE VIEW---------------");
        //restore the list on screen - orientation change
        if(savedInstanceState != null)
        {
            //retrieve the clicked recipe from the bundle
            mRecipeClicked = savedInstanceState.getParcelable(RECIPE_CLICKED_KEY);
        }

        //inflate the layout
        View inflatedView = inflater.inflate(R.layout.fragment_recipe_step_list,container,false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mRecipeClicked.getName() + " Ingredients"); // change the title bar name to the recipe name

        //set up step rv
        setUpStepRecyclerView(inflatedView);
        //set up ingredients rv
        setUpIngredientsRecyclerView(inflatedView);

        //check to see if we are in tablet mode
        boolean twoPane = getResources().getBoolean(R.bool.twoPane);
        if(twoPane)
        {
            //we are in tablet mode so add the step detail fragment to the RIGHT portion of the screen
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            StepDetailFragment fragment = new StepDetailFragment(mRecipeClicked.getSteps().get(0));
            ft.add(R.id.right_part_placeholder,fragment).commit();
        }

        return inflatedView;
    }

    /**
     * This method sets up the RecyclerView showing the Ingedients of the recipe selected
     */
    private void setUpIngredientsRecyclerView(View inflatedView) {
        //create the ingredients adapter
        //get a reference to the rv
        //set the adapter to the rv
        MyIngredientsAdapter ingredientsAdapter = new MyIngredientsAdapter();
        RecyclerView rvIngredients = inflatedView.findViewById(R.id.rv_ingredients_list);
        rvIngredients.setAdapter(ingredientsAdapter);
        rvIngredients.setLayoutManager(new LinearLayoutManager(inflatedView.getContext())); // use Linear to display the ingredients of a list as a grid
        ingredientsAdapter.setData(mRecipeClicked.getIngredients());
    }

    /**
     * This method sets up the RecyclerView showing the Step of the recipe selected
     */
    private void setUpStepRecyclerView(View inflatedView) {
        //create a adapter for the Step RecyclerView
        MyStepAdapter stepAdapter = new MyStepAdapter();
        // set the click listener to
        stepAdapter.setClickListener((MyStepAdapter.StepClickListener) getContext());
        //get a reference to the step RecyclerView
        RecyclerView stepRecyclerView = inflatedView.findViewById(R.id.rv_recipe_step_description);
        //set the adapter to the recyclerview
        stepRecyclerView.setAdapter(stepAdapter);
        //set the layout manager to the rv
        stepRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stepAdapter.setData(mRecipeClicked.getSteps()); // set the data to the adapter to update the UI
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        //save the recipe clicked to the bundle
        outState.putParcelable(RECIPE_CLICKED_KEY,mRecipeClicked);
    }
}
