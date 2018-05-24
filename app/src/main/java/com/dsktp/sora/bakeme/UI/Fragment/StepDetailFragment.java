
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
 *
 */

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
 *
 */

package com.dsktp.sora.bakeme.UI.Fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsktp.sora.bakeme.Model.Step;
import com.dsktp.sora.bakeme.Player.Player;
import com.dsktp.sora.bakeme.R;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 24/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */



/**
 * Class for inflating a Fragment , specifically a Step Detail Fragment containing a Simple Exo Player view of the
 * that displays a video about the step of execution clicked .
 */
public class StepDetailFragment extends Fragment {
    private final String DEBUG_TAG = "#" + this.getClass().getSimpleName();
    private Step mStepClicked;
    private SimpleExoPlayerView mPlayerView;
    private  Player mExoPlayer;
    private long mCurrentPosition = 0;
    private boolean mTwoPane = false;
    private View mInflatedView = null;

    public StepDetailFragment(){}
    public StepDetailFragment(Step mStepClicked) {
        this.mStepClicked = mStepClicked;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get the two pane variable from the resources
        mTwoPane = getResources().getBoolean(R.bool.twoPane);

        //restore the current position of the video and the Step object from the bundle
        if(savedInstanceState!=null)
        {
            Log.d(DEBUG_TAG,"------RESTORING SAVED INSTANCE VALUES FOR FRAGMENT-------------");
            mCurrentPosition = savedInstanceState.getLong("current_pos");
            mStepClicked = savedInstanceState.getParcelable("step_clicked");
        }

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //TODO CHECK TO SEE IF IT RE-CREATES THE VIEW OR IS IT UNNESSESERAY CHECK
        Log.d(DEBUG_TAG,"-------------ON CREATE VIEW------------------");
        if(mInflatedView == null)
        {
            mInflatedView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);


            //if the orientation is portrait and its on phone mode  display the fullDescription textView and also show the navigation bar
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && !mTwoPane)
            {
                //we are in phone mode and in orientation == portrait
                TextView fullDescriptionTextView = mInflatedView.findViewById(R.id.tv_step_full_description_value); //get a reference to the textview
                fullDescriptionTextView.setText(mStepClicked.getDescription());  //set the text

                getActivity().findViewById(R.id.fragment_placeholder_nav_bar).setVisibility(View.VISIBLE); // show the navigation bar
            }


            //Set up exo player
            mPlayerView = mInflatedView.findViewById(R.id.simpleExoPlayerView);

            setUpPlayer();
        }
        return mInflatedView;
    }

    /**
     * This method instantiates a Player object that takes care of creating a SimpleExoPlayer object , initiates
     * the MediaSource and implements the player's Callback methods
     */
    private void setUpPlayer()
    {
        //instantiate a Player object
        mExoPlayer = new Player(getContext(),mPlayerView,mStepClicked.getVideoURL()); //todo handle thumnail video url

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the current position and the Step to the bundle
        if (mExoPlayer != null)
        {
            outState.putLong("current_pos",mExoPlayer.getCurrentPosition());
            outState.putParcelable("step_clicked",mStepClicked);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(DEBUG_TAG,"Resuming player....");
        mExoPlayer.startPlayer(mCurrentPosition);
    }


    @Override
    public void onPause()
    {
        super.onPause();
        Log.d(DEBUG_TAG,"Pausing player...");
        mExoPlayer.pausePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mExoPlayer!=null)
        {
            Log.d(DEBUG_TAG,"--------STOPPING THE EXO PLAYER RESOURCES---------");
            mExoPlayer.stopPlayer();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(DEBUG_TAG,"--------RELEASING THE EXO PLAYER RESOURCES---------");
        mExoPlayer.releasePlayer();
    }
}



