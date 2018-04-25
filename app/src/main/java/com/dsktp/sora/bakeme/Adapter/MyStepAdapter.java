

/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

package com.dsktp.sora.bakeme.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsktp.sora.bakeme.Model.Step;
import com.dsktp.sora.bakeme.R;

import java.util.ArrayList;

import javax.security.auth.callback.UnsupportedCallbackException;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class is used to bind data to the UI . It's an adapter for a recyclerView which has
 * a list of step's for a chosen recipe.
 */
public class MyStepAdapter extends RecyclerView.Adapter<MyStepAdapter.MyStepViewHolder> {

    private ArrayList<Step> mStepList;
    private StepClickListener mListener;

    /**
     * Custom interface which define's a click Handling method
     * Whoever implement's this interface should handle the click to
     * a particular Step
     */
    public interface StepClickListener
    {
        void onStepClicked(Step stepClicked);
    }

    /**
     * Contructor for this class.Initialize's the mStepList
     * ArrayList
     */
    public MyStepAdapter() {
        this.mStepList = new ArrayList<>();
    }

    /**
     * Setter method for the StepClickListener
     * @param listener the listener object
     */
    public void setClickListener(StepClickListener listener)
    {
        mListener = listener;
    }

    /**
     * Setter method for the Step list. It call's notifyDataSetChanged
     * to re-bind the new Data to the UI
     * @param list the list containing the Step ArrayList with the new data
     */
    public void setData(ArrayList<Step> list)
    {
        mStepList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //Inflate the custom layout and create a new ViewHolder object with this view
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_row_item,parent,false);
        return new MyStepViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyStepViewHolder holder, int position) {
        Step currentStep = mStepList.get(position); // Get a reference to the current Step object from the list
        holder.mStepDescriptionTextView.setText(currentStep.getShortDescription()); // set The value
    }

    @Override
    public int getItemCount() {
        if(mStepList.isEmpty()) return 0;
        else return mStepList.size();
    }


    /**
     * Class extending the ViewHolder class to get a reference
     * to the TextView we bind a value to to the onBindViewHolder method
     */
    public class MyStepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mStepDescriptionTextView;

        /**
         * Default constructor
         * @param itemView the inflateView which contain's this viewHolder
         */
        public MyStepViewHolder(View itemView) {
            super(itemView);
            //Get a reference
            mStepDescriptionTextView = itemView.findViewById(R.id.tv_step_description_value);
            //Set the ViewHOlder clickListener
            itemView.setOnClickListener(this);
        }

        /**
         * This method is called when a click event is triggered . We then
         * call the mListener.handleStepClickListener method
         * @param v The view which was clicked
         */
        @Override
        public void onClick(View v)
        {
            //Check to see if we have a listener for this event
            if(mListener!=null)
            {
                //Call the onStepClicked method to delegate the handling
                //to the Activity/Fragment who is listening
                mListener.onStepClicked(mStepList.get(getAdapterPosition()));
            }
            //if there is no listener for this event throw a exception
            else throw new UnsupportedOperationException("No callback for this method");
        }
    }
}
