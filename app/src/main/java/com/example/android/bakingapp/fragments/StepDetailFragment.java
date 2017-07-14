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

import java.util.ArrayList;

/**
 * Created by ygarcia on 7/10/2017.
 */

public class StepDetailFragment extends Fragment {

    private Step mStepSelected;

    private ArrayList<Step> mSteps;
    private int mCurrentPos;

    private SimpleExoPlayerView mPlayerView;
    private Context mContext;
    private Uri mVideoUri;
    private SimpleExoPlayer player;

    public static final String STEP_SELECTED = "step_selected";
    public static final String STEPS = "steps";
    public static final String CURRENT_POS = "current_pos";

    DefaultBandwidthMeter bandwidthMeter;
    DataSource.Factory dataSourceFactory;
    ExtractorsFactory extractorsFactory;

    TextView tv;


    public StepDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Load the saved state (the list of images and list index) if there is one
        if(savedInstanceState != null) {
            mStepSelected = savedInstanceState.getParcelable(STEP_SELECTED);
            mSteps = savedInstanceState.getParcelableArrayList(STEPS);
            mCurrentPos = savedInstanceState.getInt(CURRENT_POS);
        }

        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        mContext = rootView.getContext();

        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.playerView);

        if ( mStepSelected.getVideoURL().isEmpty()){
            mPlayerView.setVisibility(View.GONE);
        }

        tv = (TextView) rootView.findViewById(R.id.step_detail_tv);
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
        bandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        dataSourceFactory = new DefaultDataSourceFactory(mContext,
                Util.getUserAgent(mContext, "bakingapp"), bandwidthMeter);
        // Produces Extractor instances for parsing the media data.
        extractorsFactory = new DefaultExtractorsFactory();
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(mVideoUri,
                dataSourceFactory, extractorsFactory, null, null);
        player.prepare(videoSource);
        player.setPlayWhenReady(true);

//        rootView.findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()){
//                    rootVi findViewById(R.id.previous_btn).setVisibility(View.VISIBLE);
//                    move(1);
//                }
//
//            }
//        });
//
//        rootView.findViewById(R.id.previous_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.findViewById(R.id.next_btn).setVisibility(View.VISIBLE);
//                move(-1);
//            }
//        });


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

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putParcelable(STEP_SELECTED, mStepSelected);
        currentState.putParcelableArrayList(STEPS, mSteps);
        currentState.putInt(CURRENT_POS, mCurrentPos);
    }

    public void setmSteps(ArrayList<Step> mSteps) {
        this.mSteps = mSteps;
    }

    public void setmCurrentPos(int mCurrentPos) {
        this.mCurrentPos = mCurrentPos;
    }

    private void move(int mov){
        int newIdx = mCurrentPos + mov;
          if ( newIdx <= mSteps.size()-1 && newIdx >= 0){
            mCurrentPos = mCurrentPos + mov;
            mStepSelected = mSteps.get(mCurrentPos);
            tv.setText(mStepSelected.getDescription());
        }
    }
}
