package com.example.nuno.umusic;

import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.app.Activity;

public class HomeActivity extends AppCompatActivity  {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private Toolbar mToolbar;

    SeekBar seekBar;

    MediaPlayer mediaPlayer;

    Handler handler;
    Runnable runnable;

    TextView currentTim;
    TextView totalTim;

    ImageView playPause;

    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // music actions

        handler = new Handler();

        seekBar = findViewById(R.id.seekBar);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.watashinosekai);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mediaPlayer.getDuration());
                playCycle();
                mediaPlayer.start();
                currentTim = findViewById(R.id.currentTime);
                totalTim = findViewById(R.id.TotalTime);

                String endTime = createTime(mediaPlayer.getDuration());
                totalTim.setText(endTime);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {
                if(input) {
                    mediaPlayer.seekTo(progress);
                }
                currentTim = findViewById(R.id.currentTime);
                String time = createTime(mediaPlayer.getCurrentPosition());
                currentTim.setText(time);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Toolbar

        mToolbar = findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        playPause = findViewById(R.id.playPause);

        playPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                playPause.setImageResource(R.mipmap.baseline_pause_circle_outline_white_24dp);
            }
        });

    }

    public void playCycle() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        if(mediaPlayer.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCycle();
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        handler.removeCallbacks(runnable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String createTime(int time) {
        String timelevel = "";
        int min = time/1000/60;
        int sec = time/1000%60;

        timelevel = min + ":";

        if(sec < 10)
            timelevel += "0";
            timelevel += sec;

        return timelevel;
    }

}
