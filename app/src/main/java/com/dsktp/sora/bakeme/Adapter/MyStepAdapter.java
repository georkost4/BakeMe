/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 */

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

import com.dsktp.sora.bakeme.Model.Step;
import com.dsktp.sora.bakeme.R;

import java.util.ArrayList;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
public class MyStepAdapter extends RecyclerView.Adapter<MyStepAdapter.MyStepViewHolder> {

    private ArrayList<Step> mStepList;

    public MyStepAdapter() {
        this.mStepList = new ArrayList<>();
    }

    public void setData(ArrayList<Step> list)
    {
        mStepList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyStepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_row_item,parent,false);
        return new MyStepViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyStepViewHolder holder, int position) {
        Step currentStep = mStepList.get(position);
        holder.mStepDescriptionTextView.setText(currentStep.getShortDescription());
    }

    @Override
    public int getItemCount() {
        if(mStepList.isEmpty()) return 0;
        else return mStepList.size();
    }



    public class MyStepViewHolder extends RecyclerView.ViewHolder {

        private TextView mStepDescriptionTextView;

        public MyStepViewHolder(View itemView) {
            super(itemView);
            mStepDescriptionTextView = itemView.findViewById(R.id.tv_step_description_value);
        }
    }
}
