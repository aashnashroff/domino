package com.aashnashroff.domino;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.util.Output;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.HashMap;

public class LightSensorService extends Service implements SensorEventListener {

    SensorManager sensorManager;
    Sensor sensor;
    HashMap<InputTile, Chain> conditions;
    public LightSensorService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float currSensorValue = sensorEvent.values[0];
        for (InputTile condition : conditions.keySet()) {
            if (condition.evaluate(currSensorValue)) {
                conditions.get(condition).receiveSensorSignal(condition);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void receiveInputCondition(InputTile condition, Chain chain) {
        conditions.put(condition, chain);
    }
}
