
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

package com.dsktp.sora.bakeme.UI;

import android.arch.persistence.room.Room;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dsktp.sora.bakeme.Adapter.MyRecipeAdapter;
import com.dsktp.sora.bakeme.Adapter.MyStepAdapter;
import com.dsktp.sora.bakeme.Controller.MainScreenController;
import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.Model.Step;
import com.dsktp.sora.bakeme.R;
import com.dsktp.sora.bakeme.Repository.MyDatabase;
import com.dsktp.sora.bakeme.UI.Fragment.RecipeListFragment;
import com.dsktp.sora.bakeme.UI.Fragment.DetailFragment;
import com.dsktp.sora.bakeme.UI.Fragment.NavBarFragment;
import com.dsktp.sora.bakeme.UI.Fragment.StepDetailFragment;

import java.util.ArrayList;


public class MainScreenActivity extends AppCompatActivity implements MyRecipeAdapter.recipeClickListener,MyStepAdapter.StepClickListener {

    private MainScreenController mController;
    private FragmentManager mManager;
    private boolean mTwoPane = false;
    private MyRecipeAdapter mAdapter;
    private RecyclerView mRecipeListRV;
    private final String DEBUG_TAG = "#" + getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Log.d(DEBUG_TAG,"-------ON CREATE-----------");

        setUpVariables();


        showRecipeListFragment(savedInstanceState);


        mController.getRecipesFromDatabase();

    }

    private void setUpVariables() {
        mManager = getSupportFragmentManager();

        mController = MainScreenController.getController();

        MyDatabase db = Room.databaseBuilder(getApplicationContext(),MyDatabase.class,"recipe.db").build();


        mController.setDb(db);


        mTwoPane = getResources().getBoolean(R.bool.twoPane);
    }

    /**
     * This method is used to show the Fragment containing the Recipe List . See the comment's
     * on this method to understand the logic.
     * @param savedInstanceState The savedInstance Bundle
     */
    private void showRecipeListFragment(Bundle savedInstanceState)
    {
        //We have saved the number of fragment's that are in the backStack inside
        //this bundle
        if(savedInstanceState!=null)
        {
            int number = savedInstanceState.getInt("fragments_number");
            Log.d(DEBUG_TAG,"--------------------" + number + "-------------"); // TODO REMOVE THIS LINE ON RELEASE
            if(number>0) // If the number of the fragment's on the backStack are greater than zero don't do anything
            {
                //don't re-create the fragment
            }
            else
            // if there aren't any fragment's on the backstack meaning the user navigated back to the first fragment
            // Recreate it
            {
                RecipeListFragment recipeListFragment = new RecipeListFragment(); //TODO RECONSIDER RE-ALLOCATING NEW OBJECT
                mManager.beginTransaction()
                        .replace(R.id.fragment_placeholder_recipe_list,recipeListFragment)
                        .commit();
            }
        }
        else
        {
            //The first time the app launches has savedInstanceState == null
            // so "add" the fragment
            RecipeListFragment recipeListFragment = new RecipeListFragment(); //TODO RECONSIDER RE-ALLOCATING NEW OBJECT
            mManager.beginTransaction()
                    .replace(R.id.fragment_placeholder_recipe_list,recipeListFragment)
                    .commit();
        }
    }


    /**
     * This method handle's the click even't on a Recipe from the list . It replace's the current
     * fragment to the detail Fragment which contain's detail's about the ingredient's and the step's
     * of execution about the clicked Recipe
     * @param position The position of the RecyclerView which was clicked
     * @param recipes The  ArrayList with the objects // TODO TO BE REMOVED
     */
    @Override
    public void handleRecipeClicked(int position, ArrayList<Recipe> recipes) //TODO remove the arrayList parameter it's useless
    {
        // Show recipe detail fragment
        DetailFragment detailFragment = new DetailFragment(recipes.get(position));

        mManager.beginTransaction().replace(R.id.fragment_placeholder_recipe_list,detailFragment).addToBackStack("").commit();

    }

    /**
     * This method handle's the click event on a step from the list containing the step's of executing a recipe .
     * It create's a stepDetailFragment and show's it on the UI . This stepDetailFragment contain's detail's about
     * how to execute this particular step with a video and a helpful text.
     * @param stepClicked The Step object which was clicked
     */
    @Override
    public void onStepClicked(Step stepClicked)
    {
        // Show recipe detail fragment
        StepDetailFragment detailFragment = new StepDetailFragment(stepClicked);

        //show navigation  bar (previous/next) step  if  in phone mode to
        if(!mTwoPane)
        {
            findViewById(R.id.fragment_placeholder_nav_bar).setVisibility(View.VISIBLE);
            mManager.beginTransaction()
                    .replace(R.id.fragment_placeholder_recipe_list,detailFragment)
                    .add(R.id.fragment_placeholder_nav_bar,new NavBarFragment())
                    .addToBackStack("").commit();
        }
        else
        {
            //In tablet mode
            // change the step
            mManager.beginTransaction().replace(R.id.right_part_placeholder,detailFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(DEBUG_TAG,"------------ON PAUSE-----------");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Get the number of the fragment's who are in the backStack
        int numberOfFragments = mManager.getBackStackEntryCount();
        //put the number to the savedInstance bundle
        outState.putInt("fragments_number",numberOfFragments);
    }
}
