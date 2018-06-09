package com.aashnashroff.domino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }

    public void startLight(View view) {
        Intent intent = new Intent(this, ChallengeActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_challenge);
    }

    public void startAccelerometer(View view) {
        Intent intent = new Intent(this, AccelPlayActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_accel_play);
    }
}
