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

        inputs.add(firstInput);
//        OutputTile firstOutput = new OutputTile();
//        outputs.add(firstOutput);
    }

    /**
     * Editing Applet functions
     */

    public void setOutput(int index, OutputTile tile) {
        // TODO: Add check for inbounds index
        outputs.set(index, tile);
    }

    public void addOutput(OutputTile tile) {
        outputs.add(tile);
    }

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
