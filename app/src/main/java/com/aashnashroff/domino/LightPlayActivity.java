package com.aashnashroff.domino;

import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.media.PlaybackParams;


import android.support.v4.graphics.ColorUtils;

public class LightPlayActivity extends AppCompatActivity {

    public static final float BACKGROUND_HUE = 210;
    public static final float BACKGROUND_SATURATION = 0.5f;

    double luxValue = 0;
    double maxLux = 50000; /* TODO: change default value here. */

    TextView textReading;
    LinearLayout layout;

//    SoundPool sp;
//    int id;

    MediaPlayer mediaPlayer;


    int stupidCounter = 0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_play);

        textReading = findViewById(R.id.lux_value);
        layout = (LinearLayout) findViewById(R.id.play_light_background);

//        sp = new SoundPool.Builder().build();
//        id = sp.load(this, R.raw.a4_2, 0);
//        sp.play(id, 1,1,0,-1, 1.0f);
å
//        player = new AudioTrack.Builder().build();

        mediaPlayer = mediaPlayer.create(this, R.raw.a4_10min);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        SensorManager sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            if (lightSensor != null) {
                maxLux = (double) lightSensor.getMaximumRange();
                sensorManager.registerListener(lightSensorEventListener,
                        lightSensor,
                        SensorManager.SENSOR_DELAY_NORMAL);
                return;
            }
        }

        Toast.makeText(LightPlayActivity.this,
                "Sensor not available.",
                Toast.LENGTH_LONG).show();
    }

    private double luxToEV(double lux) {
        return Math.log(lux / 2.5) / (Math.log(2));
    }

    private int getBackgroundColorFromLux(double lux) {
        double EV = luxToEV(lux);
        double maxEV = luxToEV(maxLux);

        float brightness = EV > maxEV ? 1 : (float) (EV / maxEV);
        return ColorUtils.HSLToColor(new float[] {BACKGROUND_HUE, BACKGROUND_SATURATION, brightness});
    }

    SensorEventListener lightSensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            /* Does nothing. */
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                luxValue = (double) event.values[0];
                textReading.setText(String.valueOf(luxValue));
                layout.setBackgroundColor(getBackgroundColorFromLux(luxValue));
//                if (stupidCounter % 20 == 0)
//                sp.play(id, 1,1,0,-1, (float)(luxToEV(luxValue)));
//                stupidCounter++;
                PlaybackParams pm = mediaPlayer.getPlaybackParams();
                pm.setPitch(getPitch(luxValue));
                mediaPlayer.setPlaybackParams(pm);

            }
        }

        private float getPitch(double lux) {
            double EV = luxToEV(lux);
            double maxEV = luxToEV(maxLux);
            float pitch = (float) Math.max(0.5, Math.min(4, EV / 5));
            return pitch;
            // (float)(luxToEV(luxValue)/5) > 0 ? (float)(luxToEV(luxValue)/5) : 0.2f)
        }

    };
}