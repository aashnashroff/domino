package com.aashnashroff.domino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user touches the play button. */
    public void startPlay(View view) {
        Log.d("STATE", "Play button pressed");
        Intent intent = new Intent(this, LightInfoActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_light_info);
    }

    //TODO: Move this function to be called by the appropriate menu button
    public void startBuild(View view) {
        Log.d("STATE", "Build button pressed");
        Intent intent = new Intent(this, BuildActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_build);
    }
}
