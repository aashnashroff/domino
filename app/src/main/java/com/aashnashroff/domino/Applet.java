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

    public String isEqualTo(Applet ideal) {
        // TODO: Only works for app with one CauseEffect for now
        CauseEffect idealCE = ideal.getChains().get(0).getCEs().get(0);
        CauseEffect userCE = chains.get(0).getCEs().get(0);
        // Check if length of chain is the same (same number of inputs and outputs)
        if (idealCE.getInputs().size() != userCE.getInputs().size()) {
            return "It looks like you don't have the correct number of input tiles.";
        }
        if (idealCE.getOutputs().size() != userCE.getOutputs().size()) {
            return "It looks like you don't have the correct number of output tiles.";
        }

        for (int i = 0; i < idealCE.getInputs().size(); i++) {
            if (!idealCE.getInputs().get(i).isEqualTo(userCE.getInputs().get(i))) {
                return "Input tile " + Integer.toString(i+1) + " is incorrect.";
            }
        }

        for (int i = 0; i < idealCE.getOutputs().size(); i++) {
            if (!idealCE.getOutputs().get(i).isEqualTo(userCE.getOutputs().get(i))) {
                return "Output tile " + Integer.toString(i+1) + " is incorrect.";
            }
        }

        return "Congratulations!";
    }

    /**
     * Applet running functions
     */

    public boolean isOn() { //FIXME: Maybe change return type to void
        return false;
    }

    //TODO: Need a lock if the domino is on/you're editing the domino

}
