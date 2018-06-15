package com.aashnashroff.domino;

import android.content.Context;
import java.io.Serializable;

/***
 * This is a template class for output tiles.
 * No outputTile should ever be defined as only an OutputTile;
 * all types of outputs should extend this class. This class is
 * necessary to create generic OutputTiles before the user has
 * specified a type.
 */
public class OutputTile implements Serializable {

    public OutputTile() {
    }

    // This should be overwritten
    public void onTrigger(Context context) {
   }

    // This should be overwritten
    public boolean isEqualTo(OutputTile other) {
        return false;
    }

    // This should be overridden
    public void turnOff(Context context) {
    }
}
