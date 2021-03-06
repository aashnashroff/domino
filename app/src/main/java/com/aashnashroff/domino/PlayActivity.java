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
        Intent intent = new Intent(this, LightInfoActivity.class);

        /* this will be useful when we don't hard code front end... sad.
        //passing sensor information to the Challenges Activity page
        intent.putExtra("sensor", "light");
        String[] challenges_names = getResources().getStringArray(R.array.light_challenges_names);
        intent.putExtra("challenges_names", challenges_names);
        String[] challenges_desc = getResources().getStringArray(R.array.light_challenges_descriptions);
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
