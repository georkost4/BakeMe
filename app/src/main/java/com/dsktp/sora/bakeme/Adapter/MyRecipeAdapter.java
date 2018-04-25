/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

package com.dsktp.sora.bakeme.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsktp.sora.bakeme.Model.Recipe;
import com.dsktp.sora.bakeme.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class is used to bind data to the recyclerView that hold's
 * a recipe list
 */
public class MyRecipeAdapter extends RecyclerView.Adapter<MyRecipeAdapter.MyRecipeViewHolder> {

    private ArrayList<Recipe> mRecipeList;
    private recipeClickListener mListener;

    /**
     * Custom interface to
     * delegate the click handling
     * to the fragment/activity
     */
    public interface recipeClickListener
    {
        void handleRecipeClicked(int position,ArrayList<Recipe> recipes);
    }

    /**
     * Contructor for this class . It set's the mListener
     * field from the parameter recipeClickListener listener . So
     * we can call mListener.handleRecipeClicked method. See more inside
     * ViewHolder class
     * @param listener The listener who handle's the click event
     */
    public MyRecipeAdapter(recipeClickListener listener)
    {
        mListener = listener;
    }

    /**
     *This is setter method for the field mRecipeList which
     * hold's an ArrayList of recipe's . After the value set it call's
     * the notifyDataSetChanged method to re bind the new data (recipes) to the UI
     * @param recipes The recipeList
     */
    public void setRecipeList(ArrayList<Recipe> recipes)
    {
        this.mRecipeList = recipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the View containing our custom layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card,parent,false);
        //create a ViewHolder object with this inflated View
        MyRecipeViewHolder viewHolder = new MyRecipeViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecipeViewHolder holder, int position)
    {
        Recipe currentRecipe = mRecipeList.get(position);
        holder.mRecipeServingsTextView.setText(String.valueOf(currentRecipe.getServings()));
        holder.mRecipeNameTextView.setText(currentRecipe.getName());
        //TODO add placeholder for no image
        //If the URL of the image link us null
        //meaning there is no image then set the imageView
        //with a default placeholder
        if(currentRecipe.getImageURL().equals(""))
        {
           //load default placeholder
           holder.mRecipeImageImageView.setImageResource(R.drawable.ic_launcher_background);
        }
        else
        {
            //load the recipe image using Picasso library
            Picasso.get()
                    .load(currentRecipe.getImageURL())
//                    .error(R.drawable.ic_launcher_background)
                    .into(holder.mRecipeImageImageView);
        }

    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }


    /**
     * This class is used to create custom ViewHolder object's for our custom layout
     * containing two TextView's with the recipe name and recipe serving's and one ImageView
     * which display's the image of the recipe
     */
    public class MyRecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mRecipeNameTextView;
        private TextView mRecipeServingsTextView;
        private ImageView mRecipeImageImageView;

        /**
         * Default constructor for the ViewHolder object
         * @param itemView
         */
        public MyRecipeViewHolder(View itemView)
        {
            super(itemView);
            //Get a reference to the desired views
            mRecipeImageImageView = itemView.findViewById(R.id.iv_recipe_image);
            mRecipeNameTextView = itemView.findViewById(R.id.tv_recipe_name_value);
            mRecipeServingsTextView = itemView.findViewById(R.id.tv_recipe_servings_value);

            //set a click Listener for the ViewHolder item
            itemView.setOnClickListener(this);
        }

        /**
         * This method is called when a click
         * on a ViewHolder object happen's
         * @param v The ViewHolder which was clicked
         */
        @Override
        public void onClick(View v)
        {
            //Delegate the click handling to whoever listen's for this event
            mListener.handleRecipeClicked(getAdapterPosition(),mRecipeList);
        }
    }
}
