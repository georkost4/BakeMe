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

/**
 * This class is used to bind the Ingredient's list to the corresponding recyclerView
 */
public class MyIngredientsAdapter extends RecyclerView.Adapter<MyIngredientsAdapter.MyIngredientVH> {

    //The list with the Ingredient's
    private ArrayList<Ingredient> mIngredientList;

    /**
     * Default constructor just initializing
     * the ArrayList
     */
    public MyIngredientsAdapter()
    {
        this.mIngredientList = new ArrayList<>();
    }

    /**
     * This method set's the list of Ingredient's
     * and notifies the adapter to invalidate and
     * rebind the data to the layout
     * @param ingredients The ArrayList containing the Ingredient list
     */
    public void setData(ArrayList<Ingredient> ingredients)
    {
        this.mIngredientList = ingredients;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyIngredientVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate our custom ingredient row layout and create a new MyIngredientVH viewholder object
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_row_list,parent,false);
        return new MyIngredientVH(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyIngredientVH holder, int position)
    {
        //Get a reference to the currentIngredient being binded to the UI
        Ingredient currentIngredient = mIngredientList.get(position);
        String bulletPoint = "•";
        String ingredient = bulletPoint + currentIngredient.getIngredient();
        //Get a reference to the view inside the ViewHolder and set the
        //appropriate value
        holder.mIngredientTextView.setText(ingredient);
    }

    @Override
    public int getItemCount() {
        return mIngredientList.size();
    }

    /**
     * This class extend's the ViewHolder class that wrap's the Ingredient TextView
     * of our custom layout . Every item on the recyclerView hold's a viewHolder object ,
     * which in turn hold's a view in this case a TextView.
     */
    public  class MyIngredientVH extends RecyclerView.ViewHolder {

        private TextView mIngredientTextView;

        /**
         * Default Contructor
         * @param itemView The inflated View containing the ViewHolder
         */
        public MyIngredientVH(View itemView)
        {
            super(itemView);
            //Get a reference to the TextView
            mIngredientTextView = itemView.findViewById(R.id.tv_ingredient_item_value);
        }
    }
}
