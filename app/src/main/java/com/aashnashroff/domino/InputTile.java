package com.aashnashroff.domino;

import java.io.Serializable;
import android.util.Log;


public class InputTile implements Serializable {
    private int sensor;
    private String operand;
    private String chosenValue;

    public InputTile() {
        sensor = -1;
        operand = "";
        chosenValue = "";
    }

    public InputTile(int sense, String op, String chosenVal) {
        sensor = sense;
        operand = op;
        chosenValue = chosenVal;
    }

    // The following methods will be called from the InTile page
    public void updateSensor(int newSensor) {
        sensor = newSensor;
    }

    public void updateOperand(String op) {
        operand = op;
    }

    public void updateChosenVal(String chosen) {
        chosenValue = chosen;
    }

    public String getOp() {
        return operand;
    }

    public String getChosenValue() {
        return chosenValue;
    }

    public int getSensor() {
        return sensor;
    }

    // TODO: Restore when(if) Conditions/SensorInputs are Parcelable
    public boolean evaluate(float sensorVal) {
        double chosenVal = Double.parseDouble(chosenValue);
        switch (operand) {
            case "<":
                if (sensorVal < chosenVal) return true;
                return false;
            case ">":
                if (sensorVal > chosenVal) return true;
                return false;
            case "=":
                if (sensorVal == chosenVal) return true;
                return false;
            default:
                Log.d("STATE", "Invalid operand");
                return false;
        }
    }

    public boolean isEqualTo(InputTile other) {
        if ((other.getSensor() == this.sensor) &&
            (other.getChosenValue().equals(this.chosenValue)) &&
            (other.getOp().equals(this.operand))) {
            return true;
        }
        return false;
    }
}
