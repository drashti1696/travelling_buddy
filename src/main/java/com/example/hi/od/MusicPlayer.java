package com.example.hi.od;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Random;

/**
 * Created by Om Mehta on 19-Sep-16.
 */
public class MusicPlayer extends Activity implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {

    private ImageButton btnPlay;
    private ImageButton btnForward;
    private ImageButton btnBackward;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private ImageButton btnPlaylist;
    private ImageButton btnRepeat;
    private ImageButton btnShuffle;
    private SeekBar songProgressBar;
    private TextView songTitleLabel;
    private TextView songCurrentDurationLabel;
    private TextView songTotalDurationLabel;
    private SurfaceView surfaceViewFrame;
    private SurfaceHolder holder1;


    VideoView mp;
   // public MediaPlayer mp;
    Handler mHandler =new Handler();

    private int seekForwardTime = 5000; // 5000 milliseconds
    private int seekBackwardTime = 5000; // 5000 milliseconds
    private int currentSongIndex = 0;
    private boolean isShuffle = false;
    private boolean isRepeat = false;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player);


        mp = (VideoView) findViewById(R.id.VideoView);

        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnForward = (ImageButton) findViewById(R.id.btnForward);
        btnBackward = (ImageButton) findViewById(R.id.btnBackward);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);
        btnPlaylist = (ImageButton) findViewById(R.id.btnPlaylist);
        btnRepeat = (ImageButton) findViewById(R.id.btnRepeat);
        btnShuffle = (ImageButton) findViewById(R.id.btnShuffle);
        songProgressBar = (SeekBar) findViewById(R.id.songProgressBar);
        songTitleLabel = (TextView) findViewById(R.id.songTitle);
        songCurrentDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);
        songTotalDurationLabel = (TextView) findViewById(R.id.songCurrentDurationLabel);

      //  mp=new MediaPlayer();

        songProgressBar.setOnSeekBarChangeListener(this); // Important
        mp.setOnCompletionListener(this);// Important
       // mp.setScreenOnWhilePlaying(true);
      //  mp.setDisplay(holder1);


        Bundle bundle = getIntent().getExtras();
        String stuff = bundle.getString("song");
        playSong(stuff);

        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // check for already playing
                if(mp.isPlaying()){
                    if(mp!=null){
                        mp.pause();
                        // Changing button image to play button
                        btnPlay.setImageResource(R.drawable.img_btn_play);
                    }
                }else{
                    // Resume song
                    if(mp!=null){
                        mp.start();
                        // Changing button image to pause button
                        btnPlay.setImageResource(R.drawable.img_btn_pause);
                    }
                }

            }
        });

        btnForward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // get current song position
                int currentPosition = mp.getCurrentPosition();
                // check if seekForward time is lesser than song duration
                if(currentPosition + seekForwardTime <= mp.getDuration()){
                    // forward song
                    mp.seekTo(currentPosition + seekForwardTime);
                }else{
                    // forward to end position
                    mp.seekTo(mp.getDuration());
                }
            }
        });

        btnBackward.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // get current song position
                int currentPosition = mp.getCurrentPosition();
                // check if seekBackward time is greater than 0 sec
                if(currentPosition - seekBackwardTime >= 0){
                    // forward song
                    mp.seekTo(currentPosition - seekBackwardTime);
                }else{
                    // backward to starting position
                    mp.seekTo(0);
                }

            }
        });

        btnRepeat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(isRepeat){
                    isRepeat = false;
                    Toast.makeText(getApplicationContext(), "Repeat is OFF", Toast.LENGTH_SHORT).show();
                    btnRepeat.setImageResource(R.drawable.img_btn_repeat);
                }else{
                    // make repeat to true
                    isRepeat = true;
                    Toast.makeText(getApplicationContext(), "Repeat is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isShuffle = false;
                    btnRepeat.setImageResource(R.drawable.img_btn_repeat_pressed);
                    btnShuffle.setImageResource(R.drawable.img_btn_shuffle);
                }
            }
        });

        btnShuffle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(isShuffle){
                    isShuffle = false;
                    Toast.makeText(getApplicationContext(), "Shuffle is OFF", Toast.LENGTH_SHORT).show();
                    btnShuffle.setImageResource(R.drawable.img_btn_shuffle);
                }else{
                    // make repeat to true
                    isShuffle= true;
                    Toast.makeText(getApplicationContext(), "Shuffle is ON", Toast.LENGTH_SHORT).show();
                    // make shuffle to false
                    isRepeat = false;
                    btnShuffle.setImageResource(R.drawable.img_btn_shuffle_pressed);
                    btnRepeat.setImageResource(R.drawable.img_btn_repeat);
                }
            }
        });

    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = getIntent().getExtras();
        String stuff = bundle.getString("song");

        playSong(stuff);
    }

    public void playSong(String stuff)
    {

        try
        {
           // mp.reset();

            Uri video = Uri.parse("http://192.168.179.1:9090/"+stuff);
           // videoview.setMediaController(mediacontroller);
            mp.setVideoURI(video);
            //mp.setDisplay();
          //  mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
          //  mp.setDataSource("http://192.168.179.1:9090/"+stuff);
           // mp.prepare();
            mp.start();

           // videoView.start();
            String songlist=stuff;
            songTitleLabel.setText(songlist);

            btnPlay.setImageResource(R.drawable.img_btn_pause);

            songProgressBar.setProgress(0);
            songProgressBar.setMax(100);

            updateProgressBar();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void updateProgressBar() {

        //mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

        // check for repeat is ON or OFF
        if(isRepeat){
            // repeat is on play same song again
            Bundle bundle = getIntent().getExtras();
            String stuff = bundle.getString("song");
            playSong(stuff);
        } else if(isShuffle){
            // shuffle is on - play a random song
            Random rand = new Random();
            //currentSongIndex = rand.nextInt((songsList.size() - 1) - 0 + 1) + 0;
            //playSong(currentSongIndex);
        } else{/*
            // no repeat or shuffle ON - play next song
            if(currentSongIndex < (songsList.size() - 1)){
                playSong(currentSongIndex + 1);
                currentSongIndex = currentSongIndex + 1;
            }else{
                // play first song
                playSong(0);
                currentSongIndex = 0;
            }*/
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void onDestroy(){
        super.onDestroy();
       mp.stopPlayback();
    }


}
