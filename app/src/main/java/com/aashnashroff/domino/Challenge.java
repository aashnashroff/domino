package com.aashnashroff.domino;

/**
 * Created by aashnashroff on 6/8/18.
 */

public class Challenge {
    Applet solution;
    String name;
    String description;

    public Challenge() {
        solution = new Applet();
        name = "";
        description = "";
    }

    /**
     * Editing Challenge functions
     */

    public void setDescription(String str) {
        description = str;
    }

    public void setChallengeSolution() {
    }
}