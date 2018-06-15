package com.aashnashroff.domino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.util.Arrays;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }

    public void startLight(View view) {
        Intent intent = new Intent(this, ChallengeActivity.class);

        /* This will eventually be used when the front end is not hard coded like it is right now ... sad."

        //passing sensor information to the Challenges Activity page
        intent.putExtra("sensor", "light");
        String[] challenges_names = getResources().getStringArray(R.array.light_challenges_names);
        intent.putExtra("challenges_names", challenges_names);
        String[] challenges_desc = getResources().getStringArray(R.array.light_challenges_descriptions);
        intent.putExtra("challenges_desc", challenges_desc);
        */

        startActivity(intent);
        setContentView(R.layout.activity_light_info);
    }

    public void startAccelerometer(View view) {
        Intent intent = new Intent(this, AccelPlayActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_accel_play);
    }
}
