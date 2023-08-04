package com.example.mediaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button play;
    private Button pause;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        seekBar = findViewById(R.id.seekBar);

        // Media player using local source
        mediaPlayer = MediaPlayer.create(this,R.raw.niagara);
        //mediaPlayer.start(); // if we are not using play and pause button then we can use this directly

//  ---------------------------------------------------------------------------------------------------------------------

        // Media player using remote(online) source
        // STEP 1
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("http://bankbiztonsag.muhaha.hu/tarkan/Kavinsky-Nightcall.mp3");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // STEP 2
        // after doing this add android.permission.INTERNET to MANIFEST
        // then after add  android:usesCleartextTraffic="true" in MANIFEST under application section

        // STEP 3
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Toast.makeText(MainActivity.this, "Playing...", Toast.LENGTH_SHORT).show();
                mp.start();

                // setting seekbar controls
                seekBar.setMax(mediaPlayer.getDuration());
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                        if (fromUser) {
                            mediaPlayer.seekTo(i);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
            }
        });

        // STEP 4
        mediaPlayer.prepareAsync();

//  ----------------------------------------------------------------------------------------------------------------------------


    // setting play and pause buttons
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });


    }
}



// to add mp3 file...go to res then right click and go to new then click on Android Resource Directory and rename both the things to raw then ok.
// after all this simply paste mp3 files to raw
// mp3 files name should be in all small letters