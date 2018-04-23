/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.dsktp.sora.bakeme;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.Model.RecipeViewModel;
import com.dsktp.sora.bakeme.Repository.Local.RecipeDatabase;

import java.util.ArrayList;

import static com.dsktp.sora.bakeme.Utils.Constants.DATABASE_NAME;

public class MainScreenActivity extends AppCompatActivity {

    private RecipeViewModel mViewModel;
    private RecipeDatabase mDatabase;
    private MainScreenController mController;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        mController = new MainScreenController();

        mDatabase =  Room.databaseBuilder(getApplicationContext(),
                RecipeDatabase.class, DATABASE_NAME).build();

        mViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        final Observer<ArrayList<Recipe>> recipeListObserver = new Observer<ArrayList<Recipe>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Recipe> recipes)
            {
                mController.updateDatabaseAsync(recipes,mDatabase);
            }
        };
        mViewModel.init().observe(this,recipeListObserver);
    }
}
