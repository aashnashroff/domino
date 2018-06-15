package com.aashnashroff.domino;

import android.content.Context;
import android.hardware.Sensor;

import java.io.Serializable;
import java.util.ArrayList;

public class CauseEffect implements Serializable {

    ArrayList<InputTile> inputs;
    ArrayList<OutputTile> outputs;

    public CauseEffect() {
        inputs = new ArrayList<>();
        outputs = new ArrayList<>();

        InputTile firstInput = new InputTile();
        //TESTING
        firstInput.updateChosenVal("10");
        firstInput.updateOperand(">");
        firstInput.updateSensor(Sensor.TYPE_LIGHT);

        inputs.add(firstInput);
        OutputTile firstOutput = new OutputTile();
        firstOutput = new FlashlightOutput(10, true);
        outputs.add(firstOutput);
    }

    /**
     * Editing Applet functions
     */

    //TODO: Consider functions for grouping tiles into CauseEffect groups from Chain

    public boolean isEqualTo(CauseEffect other) {
        // Fill me in
        return false;
    }

    public ArrayList<InputTile> getInputs() {
        return inputs;
    }

    public ArrayList<OutputTile> getOutputs() {
        return outputs;
    }
}
