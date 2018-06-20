package com.aashnashroff.domino;

import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Challenge2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge2);
    }

    /** Called when the user touches the play button. */
    public void startPlay(View view) {
        Intent intent = new Intent(this, WheresTheSensorActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_light_wheresthesensor);
    }

    /** Called when the user touches the Where's the Sensor button. */
    public void wheresTheSensorButton(View view) {
        Intent intent = new Intent(this, WheresTheSensorActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_light_wheresthesensor);
    }

    /** Called when the user touches the Next Challenge (Lights Out) button. */
    public void lightsOutButton(View view) {
        Intent intent = new Intent(this, BuildActivity.class);

        // Build solution app
        CauseEffect ce = new CauseEffect();
        ce.addGivenInput(new InputTile(Sensor.TYPE_LIGHT, "=", "0"));
        ce.addOutput(new FlashlightOutput(0, true, true));

        intent.putExtra("challenge_app", new Applet(new Chain(ce)));
        startActivity(intent);
        setContentView(R.layout.activity_build);
    }
}
