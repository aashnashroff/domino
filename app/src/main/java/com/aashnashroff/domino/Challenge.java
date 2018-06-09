package com.aashnashroff.domino;

/**
 * Created by aashnashroff on 6/8/18.
 */

public class Challenge {
    Applet challenge_solution;
    String challenge_description;

    public Challenge() {
        challenge_solution = new Applet();
        challenge_description = "";
    }

    /**
     * Editing Challenge functions
     */

    public void setDescription(String str) {
        challenge_description = str;
    }

    public void setChallengeSolution() {
    }
}