
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

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dsktp.sora.bakeme.Adapter.MyRecipeAdapter;
import com.dsktp.sora.bakeme.Adapter.MyStepAdapter;
import com.dsktp.sora.bakeme.Controller.MainScreenController;
import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.Model.Step;
import com.dsktp.sora.bakeme.R;
import com.dsktp.sora.bakeme.UI.Fragment.DetailFragment;
import com.dsktp.sora.bakeme.UI.Fragment.MasterRecipeListFragment;
import com.dsktp.sora.bakeme.UI.Fragment.NavBarFragment;
import com.dsktp.sora.bakeme.UI.Fragment.StepDetailFragment;

import java.util.ArrayList;


public class MainScreenActivity extends AppCompatActivity implements MyRecipeAdapter.recipeClickListener,MyStepAdapter.StepClickListener {

    private MainScreenController mController;
    private FragmentManager mManager;
    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        mController = new MainScreenController(this);
        mManager = getSupportFragmentManager();
        mTwoPane = getResources().getBoolean(R.bool.twoPane);

        if(savedInstanceState==null)
        {
            mController.getRecipes();

            MasterRecipeListFragment fragmentMasterView = new MasterRecipeListFragment();


            mManager.beginTransaction().add(R.id.fragment_placeholder_recipe_list, fragmentMasterView).commit();
        }
    }

    @Override
    public void handleRecipeClicked(int position, ArrayList<Recipe> recipes)
    {
        // Show recipe detail fragment
        DetailFragment detailFragment = new DetailFragment(recipes.get(position));

        mManager.beginTransaction().replace(R.id.fragment_placeholder_recipe_list,detailFragment).addToBackStack("").commit();

    }

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

}
