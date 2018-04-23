/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.dsktp.sora.bakeme.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsktp.sora.bakeme.Model.Ingredient;
import com.dsktp.sora.bakeme.R;

import java.util.ArrayList;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
public class MyIngredientsAdapter extends RecyclerView.Adapter<MyIngredientsAdapter.MyIngredientVH> {

    private ArrayList<Ingredient> mIngredientList;

    public MyIngredientsAdapter()
    {
        this.mIngredientList = new ArrayList<>();
    }


    public void setData(ArrayList<Ingredient> ingredients)
    {
        this.mIngredientList = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyIngredientVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_row_list,parent,false);
        return new MyIngredientVH(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyIngredientVH holder, int position)
    {
        Ingredient currentIngredient = mIngredientList.get(position);
        String bulletPoint = "•";
        String ingredient = bulletPoint + currentIngredient.getIngredient();
        holder.mIngredientTextView.setText(ingredient);
    }

    @Override
    public int getItemCount() {
        return mIngredientList.size();
    }

    public  class MyIngredientVH extends RecyclerView.ViewHolder {

        private TextView mIngredientTextView;

        public MyIngredientVH(View itemView)
        {
            super(itemView);
            mIngredientTextView = itemView.findViewById(R.id.tv_ingredient_item_value);
        }
    }
}
