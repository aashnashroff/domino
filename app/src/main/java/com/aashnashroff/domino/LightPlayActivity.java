package com.aashnashroff.domino;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LightPlayActivity extends AppCompatActivity {

    ProgressBar lightMeter;
    TextView textMax, textReading;
    float counter;
    Button read;
    TextView display;



    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        counter = 0;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_play);

        display = (TextView) findViewById(R.id.tvDisplay);
        lightMeter = (ProgressBar)findViewById(R.id.lightmeter);
        textMax = (TextView)findViewById(R.id.max);
        textReading = (TextView)findViewById(R.id.reading);

        SensorManager sensorManager
                = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor
                = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor == null){
            Toast.makeText(LightPlayActivity.this,
                    "Sensor not available.",
                    Toast.LENGTH_LONG).show();
        }else{
            float max =  lightSensor.getMaximumRange();
            lightMeter.setMax((int)max);
            textMax.setText("Max Reading(Lux): " + String.valueOf(max));

            sensorManager.registerListener(lightSensorEventListener,
                    lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        }
    }

    SensorEventListener lightSensorEventListener
            = new SensorEventListener(){

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // TODO Auto-generated method stub
            if(event.sensor.getType()==Sensor.TYPE_LIGHT){
                final float currentReading = event.values[0];
                lightMeter.setProgress((int)currentReading);
                textReading.setText("Current Reading(Lux): " + String.valueOf(currentReading));

            }
        }

    };
}