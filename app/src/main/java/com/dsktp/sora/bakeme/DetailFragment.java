/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.dsktp.sora.bakeme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsktp.sora.bakeme.Adapter.MyIngredientsAdapter;
import com.dsktp.sora.bakeme.Adapter.MyStepAdapter;
import com.dsktp.sora.bakeme.Model.Recipe;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
public class DetailFragment extends Fragment
{
    private Recipe  mRecipeClicked;

    public DetailFragment(Recipe recipe) {
        mRecipeClicked = recipe;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_recipe_step_list,container,false);

        MyStepAdapter adapter = new MyStepAdapter();

        RecyclerView recyclerView = inflatedView.findViewById(R.id.rv_recipe_step_description);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setData(mRecipeClicked.getSteps());


        MyIngredientsAdapter ingredientsAdapter = new MyIngredientsAdapter();

        RecyclerView rvIngredients = inflatedView.findViewById(R.id.rv_ingredients_list);
        rvIngredients.setAdapter(ingredientsAdapter);
        rvIngredients.setLayoutManager(new GridLayoutManager(inflatedView.getContext(),2));

        ingredientsAdapter.setData(mRecipeClicked.getIngredients());

        return inflatedView;
    }
}
