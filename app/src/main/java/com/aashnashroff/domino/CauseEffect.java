package com.aashnashroff.domino;

import android.content.Context;

import java.util.ArrayList;

public class CauseEffect {

    ArrayList<InputTile> inputs;
    ArrayList<OutputTile> outputs;

    public CauseEffect() {
        InputTile firstInput = new InputTile();
        OutputTile firstOutput = new OutputTile();
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
