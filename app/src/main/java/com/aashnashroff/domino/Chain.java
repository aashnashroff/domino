package com.aashnashroff.domino;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Chain implements Serializable{

    ArrayList<CauseEffect> ces;
    int currCEIndex = 0;
    ArrayList<Boolean> currCEStates;
    boolean isFinished = false;

    public Chain() {
        ces = new ArrayList<CauseEffect>();
        CauseEffect firstCE = new CauseEffect();
        ces.add(firstCE);
    }

    public Chain(CauseEffect firstCE) {
        ces = new ArrayList<CauseEffect>();
        ces.add(firstCE);
    }


    /**
     * Editing Applet functions
     */

    public void addCauseEffect(CauseEffect ce) {
        ces.add(ce);
    }

    public void addCauseTile() {

    }

    public void addEffectTile() {

    }

    public void removeTile() {

    }

    public void editTile() {

    }

    public boolean isEqualTo(Chain other) {
        // Fill me in
        return false;
    }

    /**
     * Applet running functions
     */

    public InputTile start() {
        currCEStates = new ArrayList<>(Collections.nCopies(ces.get(currCEIndex).getInputs().size(), false));
        return ces.get(currCEIndex).getInputs().get(0);
    }

    public InputTile receivedSensorSignal(InputTile condition) {
        Log.d("STATE", Integer.toString(currCEIndex));
        // FIXME: indexOf is not finding the condition
        //int inputIndex = ces.get(currCEIndex).getInputs().indexOf(condition);
        int inputIndex = 0;
        currCEStates.set(inputIndex, true);

        Log.d("STATE", "currCEStates: " + Integer.toString(currCEStates.size()));
        if (inputIndex == (currCEStates.size() - 1)) {
            return null;
        }
        return ces.get(currCEIndex).getInputs().get(inputIndex+1);

        // TODO: Consider how to do this when we start allowing and/or conditions
    }

    public ArrayList<OutputTile> getOutputs() {
        ArrayList<OutputTile> outputs = ces.get(currCEIndex).getOutputs();
        currCEIndex++;
        if (currCEIndex > ces.size()-1) {
            isFinished = true;
        } else {
            currCEStates = new ArrayList<>(Collections.nCopies(ces.get(currCEIndex).getInputs().size(), false));
        }
        return outputs;
    }

    public boolean checkFinished() {
        return isFinished;
    }

    public InputTile startNextCE() {
        return ces.get(currCEIndex).getInputs().get(0);
    }

    public ArrayList<CauseEffect> getCEs() {
        return ces;
    }

    //TODO: Consider creating functions for putting and removing ces (for BuildActivity to call)
    //TODO: Need a lock if the domino is on/you're editing the domino
}
