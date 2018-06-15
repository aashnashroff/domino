package com.aashnashroff.domino;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;

public class LightSensorService extends Service implements SensorEventListener {

    SensorManager sensorManager;
    Sensor sensor;
    HashMap<InputTile, Chain> conditions;
    private final IBinder lightBinder = new LightSensorBinder();

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LightSensorBinder extends Binder {
        LightSensorService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LightSensorService.this;
        }
    }


    public LightSensorService() {
//        super("LightSensorService");
        conditions = new HashMap<InputTile, Chain>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Check if this will get called with every activity that binds to it or just the first
        // TODO: If this doesn't get called, create public function to add condition to conditions
        InputTile condition = (InputTile) intent.getSerializableExtra("condition");
        Chain chain = (Chain) intent.getSerializableExtra("chain");
        conditions.put(condition, chain);
        return lightBinder;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float currSensorValue = sensorEvent.values[0];
        for (InputTile condition : conditions.keySet()) {
            if (condition.evaluate(currSensorValue)) {
                Intent done = new Intent();
                done.setAction("done");
                done.putExtra("chain", conditions.get(condition));
                done.putExtra("condition", condition);
                sendBroadcast(done);
                Log.d("STATE", Float.toString(currSensorValue));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
