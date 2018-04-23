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
public class MyRecipeAdapter extends RecyclerView.Adapter<MyRecipeAdapter.MyRecipeViewHolder> {

    private ArrayList<Recipe> mRecipeList;
    private Context mContext;
    private recipeClickListener mListener;

    public interface recipeClickListener
    {
        void handleRecipeClicked(int position,ArrayList<Recipe> recipes);
    }

    public MyRecipeAdapter(Context mContext,recipeClickListener listener)
    {
        this.mContext = mContext;
        mListener = listener;
    }

    public void setRecipeList(ArrayList<Recipe> recipes)
    {
        this.mRecipeList = recipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_card,parent,false);
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
        if(currentRecipe.getImageURL().equals(""))
        {
           //load default placeholder
           holder.mRecipeImageImageView.setImageResource(R.drawable.ic_launcher_background);
        }
        else
        {
            //load normal image
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




    public class MyRecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mRecipeNameTextView;
        private TextView mRecipeServingsTextView;
        private ImageView mRecipeImageImageView;

        public MyRecipeViewHolder(View itemView)
        {
            super(itemView);
            mRecipeImageImageView = itemView.findViewById(R.id.iv_recipe_image);
            mRecipeNameTextView = itemView.findViewById(R.id.tv_recipe_name_value);
            mRecipeServingsTextView = itemView.findViewById(R.id.tv_recipe_servings_value);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.handleRecipeClicked(getAdapterPosition(),mRecipeList);
        }
    }
}
