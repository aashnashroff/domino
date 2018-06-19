package com.aashnashroff.domino;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class BuildActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Applet currApp;
    private Applet challengeApp;
    LightSensorService lightService;
    boolean mBound = false;
    boolean appIsOn = false;
    LightSensorReceiver receiver;

    // LightSensor instance vars
    // TODO: Should be moved to a separate fragment/component via popup
    private EditText editLux;
    String operand;
    String chosenVal;

    //Flashlight instance vars
    private EditText editDuration;
    String duration;
    boolean forever;

//    private static final int CHALLENGE_CODE = 0;
//
//    @Override
//    /***
//     * Get challenge app to compare to
//     */
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CHALLENGE_CODE) {
//            Log.d("STATE", "RECEIVED CHALLENGE");
//            challengeApp = (Applet) data.getSerializableExtra("challenge_app");
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        challengeApp = (Applet) getIntent().getSerializableExtra("challenge_app");
        if (challengeApp != null) {
            findViewById(R.id.completeChallenge).setVisibility(View.VISIBLE);
        }
        //TODO: if challengeApp is not null, show Check button

        setContentView(R.layout.activity_build);

        currApp = new Applet();

        Switch turnOn = findViewById(R.id.active_toggle);
        //TODO: Show this only if input and output tiles have been set
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

        Spinner spinner = findViewById(R.id.operand_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.operand_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        addLightKeyListener();

        Spinner flashlightSpinner = findViewById(R.id.flashlight_spinner);
        ArrayAdapter<CharSequence> flashlightAdapter = ArrayAdapter.createFromResource(this,
                R.array.flashlight_spinner, android.R.layout.simple_spinner_item);
        flashlightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flashlightSpinner.setAdapter(flashlightAdapter);
        flashlightSpinner.setOnItemSelectedListener(this);

        addFlashlightKeyListener();

//        //get challenge description from intent params
//        Intent intent = getIntent();
//        String challenge_desc = intent.getStringExtra("challenge_desc");
//        Log.d("oncreate for build", "description: "+ challenge_desc);
//
//        //set textview to contain description
//        TextView desc_textbox = findViewById(R.id.challenge_description);
//        desc_textbox.setText(challenge_desc);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        switch (parent.getId()) {
            case R.id.operand_spinner:
                operand = parent.getItemAtPosition(pos).toString();
                Log.d("STATE", "Set operand to: " + parent.getItemAtPosition(pos).toString());
                break;
            case R.id.flashlight_spinner:
                String selectedFlash = parent.getItemAtPosition(pos).toString();
                if (selectedFlash.equals("turns on")) {
                    forever = true;
                    duration = "0"; // default value
                    findViewById(R.id.durationTextBox).setVisibility(View.INVISIBLE);
                } else if (selectedFlash.equals("blinks")) {
                    forever = false; // default value
                    findViewById(R.id.durationTextBox).setVisibility(View.VISIBLE);
                }
        }
    }

    public void addLightKeyListener() {
        // Get text box component
        editLux = findViewById(R.id.luxTextBox);

        // Add a keylistener to keep track user input
        editLux.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
            // if keydown and "enter" is pressed
            if ((event.getAction() == KeyEvent.ACTION_DOWN)
                    && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                chosenVal = editLux.getText().toString();
                Log.d("STATE", "Set chosenVal to: " + editLux.getText().toString());
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editLux.getWindowToken(), 0);
                return true;
            }
            return false;
            }
        });
    }

    public void addFlashlightKeyListener() {
        // Get text box component
        editDuration = findViewById(R.id.durationTextBox);

        // Add a keylistener to keep track user input
        editDuration.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // if keydown and "enter" is pressed
                if ((event.getAction() == KeyEvent.ACTION_DOWN)
                        && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    duration = editDuration.getText().toString();
                    Log.d("STATE", "Set chosenVal to: " + editDuration.getText().toString());
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editDuration.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        operand = "=";
        duration = "0";
        forever = false;
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

    public void showDefaultButtons(View view) {
        // Go back to default screen
        findViewById(R.id.actionsButton).setEnabled(true);
        findViewById(R.id.sensorsButton).setEnabled(true);
        findViewById(R.id.actionsButton).setVisibility(View.VISIBLE);
        findViewById(R.id.sensorsButton).setVisibility(View.VISIBLE);

        // Hide everything else
        findViewById(R.id.flashlightButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.flashlight_spinner).setVisibility(View.INVISIBLE);
        findViewById(R.id.durationTextBox).setVisibility(View.INVISIBLE);
        findViewById(R.id.flashlightSave).setVisibility(View.INVISIBLE);
        findViewById(R.id.lightSensorButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.lightButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.luxTextBox).setVisibility(View.INVISIBLE);
        findViewById(R.id.operand_spinner).setVisibility(View.INVISIBLE);
    }

    // Could use this as a condition in the below two functions if useful
    // Depends on how we want button flow to work
    public boolean showingActionAndSensorButton(View view) {
        return (findViewById(R.id.actionsButton).isClickable() &&
                findViewById(R.id.sensorsButton).isClickable());
    }

    public void showInputToolbar(View view) {
        // Disable actions button
        if (findViewById(R.id.actionsButton).isEnabled()) {
            findViewById(R.id.actionsButton).setEnabled(false);
            findViewById(R.id.sensorsButton).setEnabled(true);
        } else {
            showDefaultButtons(view);
        }
    }

    public void showOutputToolbar(View view) {
        // Disable sensors button
        if (findViewById(R.id.sensorsButton).isEnabled()) {
            findViewById(R.id.actionsButton).setEnabled(true);
            findViewById(R.id.sensorsButton).setEnabled(false);
        } else {
            showDefaultButtons(view);
        }
    }

    public void showSensors(View view) {
        //TODO: Update toolbar to show sensor buttons
        ImageButton lightSensorButton = findViewById(R.id.lightSensorButton);
        if (lightSensorButton != null) {
            lightSensorButton.setVisibility(View.VISIBLE);
            findViewById(R.id.actionsButton).setVisibility(View.INVISIBLE);
            findViewById(R.id.sensorsButton).setVisibility(View.INVISIBLE);
            Log.d("STATE", "Showing light sensor");
        }
    }

    public void showActions(View view) {
        //TODO: Update toolbar to show action buttons
        ImageButton flashlightButton = findViewById(R.id.flashlightButton);
        if (flashlightButton != null) {
            flashlightButton.setVisibility(View.VISIBLE);
            findViewById(R.id.actionsButton).setVisibility(View.INVISIBLE);
            findViewById(R.id.sensorsButton).setVisibility(View.INVISIBLE);
            Log.d("STATE", "Showing flashlight action");
        }
    }

    public void checkApp(View view) {
        String message = currApp.isEqualTo(challengeApp);
        if (message.equals("Congratulations!")) {
            // TODO: SHOW POSITIVE MESSAGE with "Congratulations!"
            // TODO: Send back completed notification to Challenges page
        } else {
            // TODO: SHOW message string
        }
    }

    public void displayLightSensorPopup(View view) {
        currApp.getChains().get(0).getCEs().get(0).getInputs().get(0).updateSensor(Sensor.TYPE_LIGHT);
        findViewById(R.id.lightButton).setVisibility(View.VISIBLE);
        findViewById(R.id.luxTextBox).setVisibility(View.VISIBLE);
        findViewById(R.id.operand_spinner).setVisibility(View.VISIBLE);
    }


    public void displayFlashlightPopup(View view) {
        // Set output to new FlashlightOutput with default vals
        // TODO: Figure out a way to get outputTile index rather than just using 0
        OutputTile output = new FlashlightOutput(0, true, false);
        if (currApp.getChains().get(0).getCEs().get(0).getOutputs().size() == 0) {
            currApp.getChains().get(0).getCEs().get(0).addOutput(output);
        } else {
            output = currApp.getChains().get(0).getCEs().get(0).getOutputs().get(0);
        }
        findViewById(R.id.flashlight_spinner).setVisibility(View.VISIBLE);
        findViewById(R.id.flashlightSave).setVisibility(View.VISIBLE);

        //TODO: Need to add a check to confirm the output is actually a flashlight output
        duration = Integer.toString(((FlashlightOutput)output).getDuration());
        if (Integer.parseInt(duration) != 0) {
            findViewById(R.id.durationTextBox).setVisibility(View.VISIBLE);
            ((EditText)findViewById(R.id.durationTextBox)).setText(duration);
        }
    }

    public void setFlashlightOutput(View view) {
        if (forever == false) {
            duration = editDuration.getText().toString();
        }

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editLux.getWindowToken(), 0);

        ((FlashlightOutput)currApp.getChains().get(0).getCEs().get(0).getOutputs().get(0)).setDuration(Integer.parseInt(duration));
        ((FlashlightOutput)currApp.getChains().get(0).getCEs().get(0).getOutputs().get(0)).setForever(forever);

        findViewById(R.id.flashlight_spinner).setVisibility(View.INVISIBLE);
        findViewById(R.id.durationTextBox).setVisibility(View.INVISIBLE);
        findViewById(R.id.flashlightSave).setVisibility(View.INVISIBLE);

        //FIXME: Should probably just show actions bar but this is necessary for testing without x button
        findViewById(R.id.flashlightButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.actionsButton).setVisibility(View.VISIBLE);
        findViewById(R.id.actionsButton).setEnabled(true);
        findViewById(R.id.sensorsButton).setVisibility(View.VISIBLE);
        findViewById(R.id.sensorsButton).setEnabled(true);


        //CHECKING
        FlashlightOutput output = (FlashlightOutput) currApp.getChains().get(0).getCEs().get(0).getOutputs().get(0);
        Log.d("STATE", "Created flashlight with duration: " + Integer.toString(output.getDuration()));
        Log.d("STATE", "Created flashlight with forever: " + Boolean.toString(output.getForever()));
    }


    public void setLightSensor(View view) {
        chosenVal = editLux.getText().toString();

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editLux.getWindowToken(), 0);

        currApp.getChains().get(0).getCEs().get(0).getInputs().get(0).updateChosenVal(editLux.getText().toString());
        currApp.getChains().get(0).getCEs().get(0).getInputs().get(0).updateOperand(operand);

        findViewById(R.id.lightButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.luxTextBox).setVisibility(View.INVISIBLE);
        findViewById(R.id.operand_spinner).setVisibility(View.INVISIBLE);

        //FIXME: Should probably just show sensors bar but this is necessary for testing without x button
        findViewById(R.id.lightSensorButton).setVisibility(View.INVISIBLE);
        findViewById(R.id.actionsButton).setVisibility(View.VISIBLE);
        findViewById(R.id.actionsButton).setEnabled(true);
        findViewById(R.id.sensorsButton).setVisibility(View.VISIBLE);
        findViewById(R.id.sensorsButton).setEnabled(true);

        //CHECKING
        InputTile input = currApp.getChains().get(0).getCEs().get(0).getInputs().get(0);
        Log.d("STATE", "Created Condition with op: " + input.getOp());
        Log.d("STATE", "Created Condition with chosenVal: " + input.getChosenValue());
        Log.d("STATE", "Created Condition with sensor: "+ Double.toString(input.getSensor()));
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
