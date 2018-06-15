package com.aashnashroff.domino;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.ArrayList;

public class BuildActivity extends AppCompatActivity {

    private Applet currApp;
    LightSensorService lightService;
    boolean mBound = false;
    boolean appIsOn = false;
    LightSensorReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        currApp = new Applet();

        Switch turnOn = findViewById(R.id.active_toggle);
        turnOn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (appIsOn) {
//                    Log.d("STATE", "TURNING OFF");
                    lightService.removeAllConditions();
                    for (Chain chain : currApp.getChains()) {
                        for (CauseEffect ce : chain.getCEs()) {
                            for (OutputTile output : ce.getOutputs()) {
                                output.turnOff(getBaseContext());
                            }
                        }
                    }
                } else {
                    for (Chain chain : currApp.getChains()) { // For now, we only allow one chain per app
                        InputTile condition = chain.start();
                        sendCondition(chain, condition);
                    }
                }
                appIsOn = !appIsOn;
            }
        });

        //FIXME?
        IntentFilter filter = new IntentFilter();
        filter.addAction("done");
        receiver = new LightSensorReceiver();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(receiver); //FIXME
        super.onStop();
    }

    private void sendCondition(Chain chain, InputTile condition) {
        // TODO: Create intent based on sensor type in the condition
        Intent intent = new Intent(getBaseContext(), LightSensorService.class);
        intent.putExtra("condition", condition);
        intent.putExtra("chain", chain);
        bindService(intent, lightConnection, Context.BIND_AUTO_CREATE);
    }

    private class LightSensorReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Chain chain = (Chain) intent.getSerializableExtra("chain");
            InputTile fulfilledCondition = (InputTile) intent.getSerializableExtra("condition");
            InputTile nextCondition = chain.receivedSensorSignal(fulfilledCondition);
//            Log.d("STATE", "Received sensor signal");
            if (nextCondition != null) {
                sendCondition(chain, nextCondition);
            } else {
//                Log.d("STATE", "Executing outputs");
                ArrayList<OutputTile> outputs = chain.getOutputs();
                for (OutputTile output : outputs) {
                    output.onTrigger(getBaseContext());
                }
                if (chain.checkFinished()) {
                    if (mBound) {
                        unbindService(lightConnection);
                        mBound = false;
                    }
                } else {
                    nextCondition = chain.startNextCE();
                    sendCondition(chain, nextCondition);
                }
            }
        }
    }

    public void openInTilePage(View view) {
        //TODO: Open appropriate build toolbox menu
    }

    public void openOutTilePage(View view) {
        //TODO: Open appropriate build toolbox menu
    }

    private ServiceConnection lightConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LightSensorService.LightSensorBinder binder = (LightSensorService.LightSensorBinder) service;
            lightService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}
