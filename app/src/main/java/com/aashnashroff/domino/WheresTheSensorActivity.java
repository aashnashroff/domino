package com.aashnashroff.domino;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class WheresTheSensorActivity extends AppCompatActivity {

    public enum AudioMode {FLOWING, MUTE, INTERVAL};

    public static final float BACKGROUND_HUE = 210;
    public static final float BACKGROUND_SATURATION = 0.5f;

    double luxValue = 0;
    double maxLux = 50000; /* TODO: change default value here. */

    AudioMode audioMode;

    LatoTextView textReading;
    LinearLayout layout;
    LatoButton audioController;

    /* Flowing audio state. */
    MediaPlayer mediaPlayer;

    /* Interval audio state. */
    SoundPool sp;
    int sp_id;
    int sp_play_id;
    float volumeFactor = 0.3f;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_play);

        textReading = findViewById(R.id.lux_value);
        layout = (LinearLayout) findViewById(R.id.play_light_background);
        audioController = (LatoButton) findViewById(R.id.light_play_audio_controller);

        /* Set up audio resources. */
        mediaPlayer = mediaPlayer.create(this, R.raw.a4_10min);
        mediaPlayer.setLooping(true);
        sp = new SoundPool.Builder().build();
        sp_id = sp.load(this, R.raw.a2, 0);
        sp_play_id = -1;

        /* Default to starting on */
        audioMode = AudioMode.FLOWING;
        useCorrectAudioBySetting();

        /* Register sensor event listener for light to this activity. */
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

        /* TODO: test if this works somehow.
         * Display sensor not available error. */
        Toast.makeText(WheresTheSensorActivity.this,
                "Sensor not available.",
                Toast.LENGTH_LONG).show();
    }

    private void useCorrectAudioBySetting() {
        if (audioMode == AudioMode.MUTE) {
            mediaPlayer.pause();
//            if (sp_play_id != -1) sp.pause(sp_play_id);
        } else if (audioMode == AudioMode.INTERVAL) {
            mediaPlayer.pause();
            sp_play_id = sp.play(sp_id, volumeFactor, volumeFactor,0,-1, (float)(luxToEV(luxValue)));
        } else if (audioMode == AudioMode.FLOWING) {
            sp.pause(sp_play_id);
            mediaPlayer.start();
        }
    }

    /**Changes audio settings after button clicks. */
    public void changeAudioSettings(View view) {
        if (audioMode == AudioMode.FLOWING) {
            audioMode = AudioMode.MUTE;
            audioController.setText("Interval Audio");
        } else if (audioMode == AudioMode.MUTE) {
            audioMode = AudioMode.INTERVAL;
            audioController.setText("Flowing Audio");
        } else if (audioMode == AudioMode.INTERVAL) {
            audioMode = AudioMode.FLOWING;
            audioController.setText("Mute Audio");
        }

        useCorrectAudioBySetting();
    }

    /**
     * Converts lux to EV based on the log scale.
     * Formula run in reverse: lux = 2.5 * 2^EV
     *
     * @param lux
     * @return EV
     */
    private double luxToEV(double lux) {
        return Math.log(lux / 2.5) / (Math.log(2));
    }

    SensorEventListener lightSensorEventListener = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            /* Does nothing. */
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                /* Get sensor value. */
                luxValue = (double) event.values[0];

                /* Update display with new lux value. */
                textReading.setText(String.valueOf(luxValue));

                /* Set background color. */
                layout.setBackgroundColor(getBackgroundColorFromLux(luxValue));

                /* Set pitch value. */
                setPitch(luxValue);

            }
        }
    };

    /**
     * Gets background color based on lux value -- ideally this based on EV values and changes
     * colors of the background based on the EV log scale.
     *
     * @param lux
     */
    private int getBackgroundColorFromLux(double lux) {
        double EV = luxToEV(lux);
        double maxEV = luxToEV(maxLux);

        float brightness = EV > maxEV ? 1 : (float) (EV / maxEV);
        return ColorUtils.HSLToColor(new float[] {BACKGROUND_HUE, BACKGROUND_SATURATION, brightness});
    }

    /**
     * Sets pitch based on lux value -- ideally this based on EV values and changes
     * colors of the background based on the EV log scale.
     *
     * Will set the pitch differently based on the sound mode.
     *
     * @param lux
     */
    private void setPitch(double lux) {
        double EV = luxToEV(lux);
        double maxEV = luxToEV(maxLux);

        if (audioMode == AudioMode.FLOWING) {
            float pitchFactor = (float) Math.max(0.5, Math.min(4, EV / 5));
            PlaybackParams pm = mediaPlayer.getPlaybackParams();
            pm.setPitch(pitchFactor);
            mediaPlayer.setPlaybackParams(pm);
        } else if (audioMode == AudioMode.INTERVAL) {
            float pitchFactor = (float) EV;
            sp_play_id = sp.play(sp_id, volumeFactor, volumeFactor,0,-1, pitchFactor);
        }
    }

    /** Called when the user touches the back button. */
    public void onClickBack(View view) {
        Intent intent = new Intent(this, LightInfoActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_light_info);
    }

    /** Called when the user touches the forward (next) button. */
    public void onClickForward(View view) {
        Intent intent = new Intent(this, ChallengeActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_challenge);
    }
}