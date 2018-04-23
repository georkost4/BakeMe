/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.dsktp.sora.bakeme;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dsktp.sora.bakeme.Adapter.MyRecipeAdapter;
import com.dsktp.sora.bakeme.Model.Recipe;

import java.util.ArrayList;


public class MainScreenActivity extends AppCompatActivity implements MyRecipeAdapter.recipeClickListener {

    private MainScreenController mController;
    private FragmentManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        mController = new MainScreenController(this);

        mController.getRecipes();

        FragmentMasterView fragmentMasterView = new FragmentMasterView();

        mManager = getSupportFragmentManager();

        mManager.beginTransaction().add(R.id.master_view_fragment,fragmentMasterView).commit();
    }

    @Override
    public void handleRecipeClicked(int position, ArrayList<Recipe> recipes)
    {
        // Show recipe detail fragment
        DetailFragment detailFragment = new DetailFragment(recipes);

        mManager.beginTransaction().replace(R.id.master_view_fragment,detailFragment).addToBackStack("").commit();

    }
}
