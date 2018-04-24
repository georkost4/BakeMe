
/*
 *
 *  * This file is subject to the terms and conditions defined in
 *  * file 'LICENSE.txt', which is part of this source code package.
 *
 *
 */

package com.dsktp.sora.bakeme;

import android.media.MediaDataSource;
import android.net.Uri;
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
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 24/4/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
public class StepDetailFragment extends Fragment {
    private final String DEBUG_TAG = this.getClass().getSimpleName();
    private Step mStepClicked;
    private SimpleExoPlayerView mPlayerView;
    private ExoPlayer mExoPlayer;

    public StepDetailFragment(Step mStepClicked) {
        this.mStepClicked = mStepClicked;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);

        TextView fullDescriptionTextView = inflatedView.findViewById(R.id.tv_step_full_description_value);
        fullDescriptionTextView.setText(mStepClicked.getDescription());

        //Set up exo player
        mPlayerView = inflatedView.findViewById(R.id.simpleExoPlayerView);

        setUpPlayer();

        return inflatedView;
    }

    private void setUpPlayer() {
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl mLoadControl = new DefaultLoadControl();
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

        mPlayerView.setPlayer(mExoPlayer);

        mExoPlayer.setPlayWhenReady(true);

        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), getActivity().getApplication().getPackageName()), new DefaultBandwidthMeter());
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(mStepClicked.getVideoURL()),
                dataSourceFactory, extractorsFactory, null, null);
        mExoPlayer.prepare(mediaSource);
    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mExoPlayer.getCurrentPosition();
        mStepClicked
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }
}



