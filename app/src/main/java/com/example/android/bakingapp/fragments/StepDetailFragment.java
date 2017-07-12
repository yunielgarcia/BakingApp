package com.example.android.bakingapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Step;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by ygarcia on 7/10/2017.
 */

public class StepDetailFragment extends Fragment {

    private Step mStepSelected;
    private SimpleExoPlayerView mPlayerView;
    private Context mContext;
    private Uri mVideoUri;
    private SimpleExoPlayer player;


    public StepDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        mContext = rootView.getContext();

        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);

        if ( mStepSelected.getVideoURL().isEmpty()){
            mPlayerView.setVisibility(View.GONE);
        }

        TextView tv = (TextView) rootView.findViewById(R.id.step_detail_tv);
        if (mStepSelected != null) {
            tv.setText(mStepSelected.getDescription());
            mVideoUri = Uri.parse(mStepSelected.getVideoURL());
        }


        //FROM THE DOCS
        // 1. Create a default TrackSelector
        TrackSelector trackSelector = new DefaultTrackSelector();

        // 2. Create the player
        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        mPlayerView.setPlayer(player);

        //3. Prepare the MediaSource
        // Produces DataSource instances through which media data is loaded.
        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, "yourApplicationName"), bandwidthMeter);
        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(mVideoUri,
                dataSourceFactory, extractorsFactory, null, null);
        player.prepare(videoSource);
        player.setPlayWhenReady(true);



//        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, "ExoPlayer"));
//        Uri uri = Uri.parse(mStepSelected.getVideoURL());
//        DashMediaSource dashMediaSource = new DashMediaSource(uri, dataSourceFactory,
//                new DefaultDashChunkSource.Factory(dataSourceFactory), null, null);
//
//        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
//
//        SimpleExoPlayer simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);
//
//        mPlayerView.setPlayer(simpleExoPlayer);
//        simpleExoPlayer.prepare(dashMediaSource);
//
//        simpleExoPlayer.setPlayWhenReady(true);


        return rootView;
    }

    public void setmStepSelected(Step mStepSelected) {
        this.mStepSelected = mStepSelected;
    }

    private void releasePlayer(){
        player.stop();
        player.release();
        player = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }
}
