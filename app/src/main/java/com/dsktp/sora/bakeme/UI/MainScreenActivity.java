
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

import android.content.Intent;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dsktp.sora.bakeme.Adapter.MyRecipeAdapter;
import com.dsktp.sora.bakeme.Adapter.MyStepAdapter;
import com.dsktp.sora.bakeme.Controller.MainScreenController;
import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.Model.Step;
import com.dsktp.sora.bakeme.R;
import com.dsktp.sora.bakeme.Repository.LocalRepository;
import com.dsktp.sora.bakeme.UI.Fragment.RecipeListFragment;
import com.dsktp.sora.bakeme.UI.Fragment.DetailFragment;
import com.dsktp.sora.bakeme.UI.Fragment.NavBarFragment;
import com.dsktp.sora.bakeme.UI.Fragment.StepDetailFragment;

import java.util.ArrayList;


/**
 * This class is the activity hosting the rest of the fragment classes . It handles the user interaction to the fragments
 * with the help of MainScreenController
 */
public class MainScreenActivity extends AppCompatActivity implements MyRecipeAdapter.recipeClickListener,MyStepAdapter.StepClickListener {

    private MainScreenController mController;
    private FragmentManager mManager;
    private boolean mTwoPane = false;
    private MyRecipeAdapter mAdapter;
    private RecyclerView mRecipeListRV;
    private final String DEBUG_TAG = "#" + getClass().getSimpleName();
    private LocalRepository mLocalRepository;

    private int mCurrentStepIndex;
    private ArrayList<Step> mCurrentStepList;
    private int mCurrentStepListSize;

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.d(DEBUG_TAG,"----------ON RESUME--------------");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(DEBUG_TAG,"-----------ON START-------------");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Log.d(DEBUG_TAG,"-------ON CREATE-----------");

        Intent widgetClickIntent = getIntent(); // get the intent from the widget

        setUpVariables(); // set up the variable's



        String action = widgetClickIntent.getAction(); //get the action from the intent
        if(action.equals("SHOW_RECIPE_DETAILS")) // todo extract string
        {
            Log.d(DEBUG_TAG,"Handling the user clicked on recipe");
            //get the recipe the user clicked from widget config activity
            int recipeIDclicked = PreferenceManager.getDefaultSharedPreferences(this).getInt("widget_chosen_recipe_id",-1);
            mLocalRepository.getRecipeById(recipeIDclicked);


            //Make this fragment transaction to create the proper back stack for the widget
            RecipeListFragment recipeListFragment = new RecipeListFragment(); //TODO RECONSIDER RE-ALLOCATING NEW OBJECT
            mManager.beginTransaction()
                    .replace(R.id.fragment_placeholder_recipe_list,recipeListFragment)
                    .commit();


            ArrayList<Recipe> recipes = mController.fetchRecipes();
            if(recipes==null) Log.e(DEBUG_TAG,"recipes list are empty"); // todo remove this
            //mock the recipe clicked event
            handleRecipeClicked(recipeIDclicked,recipes);
        }
        else
        {
            Log.d(DEBUG_TAG,"Starting the activity the normal way");
            //the activity started normally not from the widget
            //show the recipe list fragment
            showRecipeListFragment(savedInstanceState);
        }

    }

    /**
     * This method set's up some eseential variables we will
     * need later.
     */
    private void setUpVariables()
    {
        //get the fragment manager to make transaction with fragments
        mManager = getSupportFragmentManager();
        //instantiate the controller
        mController = MainScreenController.getController();
        //create the database
        LocalRepository.getRoomDatabase(this);

//        //get the local repository
//        mLocalRepository = LocalRepository.getLocalRepository(); //todo TO BE REMOVED UNNECESSARY
//        //set the local repo to the controller
//        mController.setLocalRepo(mLocalRepository);

        //get this variable so we can know if we are in tablet mode or phone mode
        mTwoPane = getResources().getBoolean(R.bool.twoPane);
    }

    /**
     * This method is used to show the Fragment containing the Recipe List . See the comment's
     * on this method to understand the logic.
     * @param savedInstanceState The savedInstance Bundle
     */
    private void showRecipeListFragment(Bundle savedInstanceState)
    {
        //We have saved the number of fragment's that are in the backStack inside this bundle
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
                if (mManager.findFragmentByTag("recipe_list_fragment_tag") == null)
                {
                    // if we dont have already created the fragment create it
                    RecipeListFragment recipeListFragment = new RecipeListFragment(); //TODO RECONSIDER RE-ALLOCATING NEW OBJECT
                mManager.beginTransaction()
                        .replace(R.id.fragment_placeholder_recipe_list, recipeListFragment,"recipe_list_fragment_tag")
                        .commit();
            }
            }
        }
        else
        {
            if(mManager.findFragmentByTag("recipe_list_fragment_tag") == null) // if we dont have already created the fragment create it
            {
                //The first time the app launches has savedInstanceState == null
                // so "add" the fragment
                RecipeListFragment recipeListFragment = new RecipeListFragment(); //TODO RECONSIDER RE-ALLOCATING NEW OBJECT
                mManager.beginTransaction()
                        .replace(R.id.fragment_placeholder_recipe_list, recipeListFragment, "recipe_list_fragment_tag")
                        .commit();
            }
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
        if(mManager.findFragmentByTag("recipe_fragment_tag") == null)
        {
            Log.d(DEBUG_TAG,"We dont have a recipe fragment so create it");
            DetailFragment detailFragment = new DetailFragment(recipes.get(position));

            mManager.beginTransaction().replace(R.id.fragment_placeholder_recipe_list, detailFragment, "recipe_fragment_tag").addToBackStack("").commit();
        }

    }

    /**
     * This method handle's the click event on a step from the list containing the step's of executing a recipe .
     * It create's a stepDetailFragment and show's it on the UI . This stepDetailFragment contain's detail's about
     * how to execute this particular step with a video and a helpful text.
     * @param stepClicked The Step object which was clicked
     * @param stepList The full list containing all the steps of Executions
     * @param stepIndex the index inside the stepList
     */
    @Override
    public void onStepClicked(Step stepClicked,ArrayList<Step> stepList,int stepIndex)
    {
        // Show recipe detail fragment
        StepDetailFragment stepDetailFragment = new StepDetailFragment(stepClicked);
        NavBarFragment navBarFragment = new NavBarFragment();

       mCurrentStepIndex = stepIndex;
       mCurrentStepList = stepList;
       mCurrentStepListSize = mCurrentStepList.size();


        //show navigation  bar (previous/next) step  if  in phone mode to
        if(!mTwoPane)
        {
            if(mManager.findFragmentByTag("detail_fragment_tag") == null) // create the fragments
            {
                mManager.beginTransaction()
                        .replace(R.id.fragment_placeholder_recipe_list, stepDetailFragment, "detail_fragment_tag")
                        .add(R.id.fragment_placeholder_nav_bar, navBarFragment, "nav_bar_fragment_tag")
                        .addToBackStack("").commit();
            }
        }
        else
        {
            //In tablet mode
            // change the step
            mManager.beginTransaction().replace(R.id.right_part_placeholder, stepDetailFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }


    public void ShowPreviousStep(View view)
    {
        if(mCurrentStepIndex>0) // if there is a previous step navigate to it
        {
            mCurrentStepIndex = mCurrentStepIndex - 1; // the new index is -1
            StepDetailFragment stepDetailFragment = new StepDetailFragment(mCurrentStepList.get(mCurrentStepIndex)); // pass the previous step to the constructor
            mManager.beginTransaction().replace(R.id.fragment_placeholder_recipe_list,stepDetailFragment).addToBackStack("").commit();
        }
        else
        {
            Toast.makeText(this,"There is no previous step to navigate to.",Toast.LENGTH_LONG).show();
        }

    }

    public void ShowNextStep(View view)
    {
        if(mCurrentStepIndex+1<=mCurrentStepListSize-1)
        {
            mCurrentStepIndex = mCurrentStepIndex + 1; // the new index is +1
            StepDetailFragment stepDetailFragment = new StepDetailFragment(mCurrentStepList.get(mCurrentStepIndex));
            mManager.beginTransaction().replace(R.id.fragment_placeholder_recipe_list,stepDetailFragment).addToBackStack("").commit();

        }
        else
        {
            Toast.makeText(this,"There is no next step to navigate to.",Toast.LENGTH_LONG).show();
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
        outState.putInt("fragments_number",numberOfFragments); // todo extract string resource
    }
}
