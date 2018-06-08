package com.aashnashroff.domino;

import android.content.Context;
import java.io.Serializable;

public interface OutputTile extends Serializable {
    void onTrigger(Context context);
    boolean isEqualTo(OutputTile other);
}
