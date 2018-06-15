package com.aashnashroff.domino;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

public class Applet {

    ArrayList<Chain> chains;

    public Applet() {
        chains = new ArrayList<>();
        Chain startChain = new Chain();
        chains.add(startChain);
    }

    /**
     * Edit Applet functions
     */

    public void editApplet() {

    }

    public ArrayList<Chain> getChains() {
        return chains;
    }

    public boolean isEqualTo(Applet other) {
        // Fill me in
        return false;
    }

    /**
     * Applet running functions
     */

    public boolean isOn() { //FIXME: Maybe change return type to void
        return false;
    }

    //TODO: Need a lock if the domino is on/you're editing the domino

}
