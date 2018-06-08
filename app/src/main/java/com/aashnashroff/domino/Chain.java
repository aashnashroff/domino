package com.aashnashroff.domino;

import java.util.ArrayList;

public class Chain {

    ArrayList<CauseEffect> ces;
    CauseEffect currCE;
    ArrayList<Boolean> currCEStates;

    public Chain() {
        currCE = new CauseEffect();
        ces.add(currCE);
    }

    /**
     * Editing Applet functions
     */

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

    public void start() {
        // TODO: Initialize currCEStates to currCE.getInputs().length() 0s;
    }

    public void receiveSensorSignal() {

    }

    public void signalNextInput() {

    }

    //TODO: Consider creating functions for putting and removing ces (for BuildActivity to call)
    //TODO: Need a lock if the domino is on/you're editing the domino
}
