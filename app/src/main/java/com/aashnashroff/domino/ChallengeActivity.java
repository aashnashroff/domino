package com.aashnashroff.domino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChallengeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
    }

    /** Called when the user touches the play button. */
    public void startPlay(View view) {
        Intent intent = new Intent(this, LightPlayActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_light_play);
    }


}
