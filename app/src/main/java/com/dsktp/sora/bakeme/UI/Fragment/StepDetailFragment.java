
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
    private  Player mPlayer;
    private long mCurrentPosition = 0;
    private boolean mTwoPane = false;
    private View mInflatedView = null;
    private Boolean mVideoPlayState = true;
    private boolean mNeedsSetUp = true;

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
            mVideoPlayState = savedInstanceState.getBoolean("video_state");
        }

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
            else if(mTwoPane) // if we are in tablet mode show the full description tetView
            {
                //we are in phone mode and in orientation == portrait
                TextView fullDescriptionTextView = mInflatedView.findViewById(R.id.tv_step_full_description_value); //get a reference to the textview
                fullDescriptionTextView.setText(mStepClicked.getDescription());  //set the text

            }


            //Set up exo player
            mPlayerView = mInflatedView.findViewById(R.id.simpleExoPlayerView);

            if(mNeedsSetUp) setUpPlayer(mInflatedView); // setup the player only if the player instance is null
        }
        return mInflatedView;
    }

    /**
     * This method instantiates a Player object that takes care of creating a SimpleExoPlayer object , initiates
     * the MediaSource and implements the player's Callback methods
     * @param mInflatedView
     */
    private void setUpPlayer(View mInflatedView)
    {
        Log.d(DEBUG_TAG,"-----------Setting up player--------------");
        //instantiate a Player object
        if(mStepClicked.getVideoURL().equals("")) // if no video is available show a message
        {
            //there is no video available for this Step
            mInflatedView.findViewById(R.id.state_holder).setVisibility(View.VISIBLE);
            TextView textView = mInflatedView.findViewById(R.id.state_text);
            textView.setText(R.string.no_video_available_text);
        }
        else //show the video
        {
            //use the thumbnail url
            mPlayer = new Player(getContext(),mPlayerView,mStepClicked.getVideoURL());
            mNeedsSetUp = false; // set to false cause the player is already set up
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(DEBUG_TAG,"------------SAVING STATE INSIDE BUNDLE --------------");


        outState.putParcelable("step_clicked",mStepClicked);
        //Save the current position and the Step to the bundle
        if (mPlayer != null)
        {
            outState.putLong("current_pos", mPlayer.getCurrentPosition()); // save the
            outState.putBoolean("video_state", mPlayer.getPlayWhenReady()); // save the player state
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mNeedsSetUp && mPlayer != null) // check the boolean if the player needs to be set up
        {
            Log.d(DEBUG_TAG,"Setting up player....");
            setUpPlayer(mInflatedView);
            mPlayer.startPlayer(mCurrentPosition,mVideoPlayState);
        }
    }


    @Override
    public void onPause()
    {
        super.onPause();
        if (mPlayer !=null)
        {
            Log.d(DEBUG_TAG,"--------RELEASING  THE EXO PLAYER RESOURCES---------");
            //save the variables when the app goes to background
            //to restore the player state gracefully
            mCurrentPosition = mPlayer.getCurrentPosition();
            mVideoPlayState = mPlayer.getPlayWhenReady();
            mPlayer.stopPlayer();
            mPlayer.releasePlayer(); // release the exo player resources
            mNeedsSetUp = true; // in case the user went in background the player needs to bet resumed
        }
    }


}



