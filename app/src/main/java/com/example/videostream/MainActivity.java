package com.example.videostream;

import android.os.Bundle;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;
import android.os.Build;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity  {

    ProgressDialog pDialog;
    VideoView videoview;

    // Insert your Video URL
    String VideoURL;
    String[] videoURIs = {"http://www.androidbegin.com/tutorial/AndroidCommercial.3gp"};
    int pos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("videoURIslength", String.valueOf(videoURIs.length));
        Log.d("onCreate", String.valueOf(Build.VERSION.SDK_INT));

        if (Build.VERSION.SDK_INT >= 19) {
            Window window = getWindow();
            View view = window.getDecorView();
            view.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
        videoview = (VideoView) findViewById(R.id.VideoView);

        viewDialog();
        setVideo();

        videoview.setOnPreparedListener(new OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                videoview.start();
            }
        });

        // video finish listener
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                setVideo();
                videoview.start();
            }
        });
    }

    public void viewDialog() {
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setTitle("Video Streaming");
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void setVideoURI() {
        VideoURL = videoURIs[pos];
    }

    public void setVideo(){
        Log.d("setPos", String.valueOf(pos));

        setPos();
        setVideoURI();

        try {
            MediaController mediacontroller = new MediaController(
                    MainActivity.this);
            mediacontroller.setAnchorView(videoview);
            Uri video = Uri.parse(VideoURL);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        videoview.requestFocus();
    }

    public void setPos() {
        if (videoURIs.length <= pos + 1) {
            pos = 0;
        } else {
            pos++;
        }
    }
}
