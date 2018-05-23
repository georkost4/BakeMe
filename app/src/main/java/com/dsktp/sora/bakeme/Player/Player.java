package com.dsktp.sora.bakeme.Player;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dsktp.sora.bakeme.R;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.DynamicConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/5/2018.
 * The name of the project is BakeMe and it was created as part of
 * UDACITY ND programm.
 */
public class Player implements com.google.android.exoplayer2.Player.EventListener {

    private Context context;
    private DynamicConcatenatingMediaSource dynamicConcatenatingMediaSource;
    private SimpleExoPlayerView playerView;
    private SimpleExoPlayer mExoPlayer;

    private RelativeLayout state_holder;
    private TextView state_text;

    private static String DEBUG_TAG ="#Player.java";

    public Player(Context context, SimpleExoPlayerView playerView,String recipe_video_url) {
        this.context = context;
        this.dynamicConcatenatingMediaSource = new DynamicConcatenatingMediaSource();
        this.playerView = playerView;
        init_player();

        addMedia(recipe_video_url);

    }

    private void init_player() {

        /**GET the reqired view to show in you player
         * In our case as we know the the simpleExoplayerView is a FrameLayout
         * Thus we have made a relative layout underneath it
         * By this we can get the relative layout from the simpleExoplayerView
         */
        state_holder = (RelativeLayout) playerView.findViewById(R.id.state_holder);
        state_text = (TextView) playerView.findViewById(R.id.state_text);


        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        mExoPlayer.addListener(this);//initiate the event listener

        playerView.setPlayer(mExoPlayer);

    }


    /*
        add media urls dynamically at run time
        Here we have added urls that are compatible with ExtractorMediaSource
        If you need other media source then pass media source as arguement not the url
     */
    public void addMedia(String url) {
        DefaultBandwidthMeter bandwidthMeterA = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, "NepTechSansaar"), bandwidthMeterA);

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url),
                dataSourceFactory, extractorsFactory, null, null);
        if (dynamicConcatenatingMediaSource.getSize() == 0)
        {
            dynamicConcatenatingMediaSource.addMediaSource(mediaSource);
            mExoPlayer.prepare(dynamicConcatenatingMediaSource);
            mExoPlayer.setPlayWhenReady(true);

        } else {
            dynamicConcatenatingMediaSource.addMediaSource(mediaSource);

        }

    }

    private void show_buffering() {
        state_holder.setVisibility(View.VISIBLE);
        state_text.setTextColor(Color.YELLOW);
        state_text.setText("BUFFERING...");
    }

    private void show_error() {
        state_holder.setVisibility(View.VISIBLE);
        state_text.setTextColor(Color.RED);
        state_text.setText("CANNOT PLAY....");
    }

    private void show_ready() {
        state_holder.setVisibility(View.GONE);
    }


    public void releasePlayer() {
        if(mExoPlayer!=null)
        {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }


    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady,
                                     int playbackState) {
        switch (playbackState) {
            case com.google.android.exoplayer2.Player.STATE_IDLE:
                Log.e(DEBUG_TAG,"IDLE state");
                break;
            case com.google.android.exoplayer2.Player.STATE_BUFFERING:
                Log.e(DEBUG_TAG,"BUFFERING state");
                show_buffering();
                break;
            case com.google.android.exoplayer2.Player.STATE_READY:
                Log.e(DEBUG_TAG,"READY state");
                show_ready();
                break;
            case com.google.android.exoplayer2.Player.STATE_ENDED:
                Log.e(DEBUG_TAG,"ENDED state");
                break;
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        Log.e(DEBUG_TAG,"ERROR state");
        show_error();
    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }



    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }

    public long getCurrentPosition()
    {
       return mExoPlayer.getCurrentPosition();
    }

    public void pausePlayer()
    {
        mExoPlayer.setPlayWhenReady(false);
        mExoPlayer.getPlaybackState();
    }
    public void startPlayer(){
        mExoPlayer.setPlayWhenReady(true);
        mExoPlayer.getPlaybackState();
    }
}