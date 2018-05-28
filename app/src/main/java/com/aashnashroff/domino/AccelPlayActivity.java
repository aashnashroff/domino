package com.aashnashroff.domino;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AccelPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accel_play);
    }

    /** Called when the user selects the Accelerometer from the sensor playground. */
    public void StartAccel(View view) {
        Intent intent = new Intent(this, PlayActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_accel_play);
    }
}

