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

/**
 * This helper class implements the Exoplayer callback methods and some other helper methods for
 * initiating the exoPlayer
 */
public class Player implements com.google.android.exoplayer2.Player.EventListener {

    private Context context;
    private SimpleExoPlayerView playerView;
    private SimpleExoPlayer mExoPlayer;

    private RelativeLayout state_holder;
    private TextView state_text;

    private MediaSource mMediaSource;

    private String mVideoURL;

    private static String DEBUG_TAG ="#Player.java";

    public Player(Context mContext, SimpleExoPlayerView mPlayerView, String recipe_video_url) {
        this.context = mContext;
        this.playerView = mPlayerView;
        this.mVideoURL = recipe_video_url;

        init_player();
        addMedia(mVideoURL);

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

    /**
     * This method dues the nessesary work to add the URL of the video, prepare the player
     * and start playing the video.
     * @param url The url of the video
     */
    public void addMedia(String url)
    {
        DefaultBandwidthMeter bandwidthMeterA = new DefaultBandwidthMeter();
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(context, Util.getUserAgent(context, context.getString(R.string.app_name)), bandwidthMeterA);

        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        mMediaSource = new ExtractorMediaSource(Uri.parse(url),
                dataSourceFactory, extractorsFactory, null, null);

        mExoPlayer.prepare(mMediaSource);
        mExoPlayer.setPlayWhenReady(true);

    }

    /**
     * This method displays a informative message about buffering state of the video
     */
    private void show_buffering() {
        state_holder.setVisibility(View.VISIBLE);
        state_text.setTextColor(Color.YELLOW);
        state_text.setText(R.string.player_buffering_message);
    }

    /**
     * This method displays a error message to the player view
     */
    private void show_error() {
        state_holder.setVisibility(View.VISIBLE);
        state_text.setTextColor(Color.RED);
        state_text.setText(R.string.player_error_play_message);
    }

    /**
     * This methods removes the grey screen from the player view indicating that the video
     * is ready to be played.
     */
    private void show_ready() {
        state_holder.setVisibility(View.GONE);
    }


    /**
     * This method releases the resources held by the exo player . Memory,codecs...
     */
    public void stopPlayer() {
        if(mExoPlayer!=null)
        {
            mExoPlayer.stop();
        }
    }

    /**
     * This method releases the resources and makes the Exo Plauer instance<b>UNSUSABLE</b>
     */
    public void releasePlayer()
    {
        if(mExoPlayer!=null)
        {
            mExoPlayer.release();
        }
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        Log.e(DEBUG_TAG,"ERROR state");
        show_error();
    }




    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        //act acording to the player state

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

    public long getCurrentPosition()
    {
        return mExoPlayer.getCurrentPosition();
    }

    /**
     * This method causes the player to stop playing the video
     */
    public void pausePlayer()
    {
        mExoPlayer.setPlayWhenReady(false);
        mExoPlayer.getPlaybackState();
    }

    /**
     * This method resumes the player when the fragment comes to foreground
     * from the home screen or another activity
     * @param lastPosition The last position the video was in milliseconds
     */
    public void startPlayer(final long lastPosition)
    {

        if (mExoPlayer!=null)
        {
            addMedia(mVideoURL);
            mExoPlayer.seekTo(lastPosition);
        }
    }

    /**
     * Simple getter method
     * @return
     */
    public boolean getPlayWhenReady()
    {
        return mExoPlayer.getPlayWhenReady();
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
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

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



}