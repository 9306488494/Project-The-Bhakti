package com.thebhakti;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.provider.ContactsContract.CommonDataKinds.Website.URL;
import static com.google.android.youtube.player.YouTubePlayer.*;

/**
 * Created by Yeshveer on 8/27/2018.
 */

public class YoutubeAPi extends YouTubeBaseActivity implements OnInitializedListener{
public static final String API_KEY="AIzaSyCbX0uRsGBXC5x8PrCLv79PIJhFgvzy-d0";
public  static final String Playlist_ID="PLaGRbVkdtMWcGWFLl3kWdXtdm3Sk6MWj8";
    private YouTubePlayerView tubePlayer;
    private ListView videoList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_lay);

        tubePlayer = (YouTubePlayerView) findViewById(R.id.tubePlayer);
        tubePlayer.initialize(API_KEY,this);
        videoList = (ListView) findViewById(R.id.videoList);

    }



    @Override
    public void onInitializationSuccess(Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);

        // Start Buffering
        if(!b)
        {
            /*youTubePlayer.cuePlaylist(Playlist_ID);*/
            /*youTubePlayer.loadPlaylist(Playlist_ID);*/
           youTubePlayer.cueVideo("3Z-cXmUr-VE");
            // many other function such as load etc
        }
    }

    @Override
    public void onInitializationFailure(Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Fail to Initilize ", Toast.LENGTH_SHORT).show();
    }

private PlaybackEventListener playbackEventListener=new PlaybackEventListener() {
    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }
};
    private PlayerStateChangeListener playerStateChangeListener=new PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(ErrorReason errorReason) {

        }
    };
}
