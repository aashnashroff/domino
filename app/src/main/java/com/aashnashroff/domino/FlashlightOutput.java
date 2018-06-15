package com.aashnashroff.domino;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.util.Log;

public class FlashlightOutput extends OutputTile {

    private int duration;
    private boolean hasCameraFlash;
    private boolean forever;

    public FlashlightOutput(int duration, boolean hasCameraFlash, boolean forever) {
        this.duration = duration;
        this.hasCameraFlash = hasCameraFlash;
        this.forever = forever;
    }


    public int getDuration() {
        return duration;
    }

    public boolean getHasCameraFlash() {
        return hasCameraFlash;
    }

    @Override
    public void onTrigger(Context context) {
        if (this.hasCameraFlash) {
            flashLightOn(context);
        } else {
            //TODO: Flashlight not available on your phone
        }
    }

    @Override
    public boolean isEqualTo(OutputTile other) {
        //TODO
        return super.isEqualTo(other);
    }

    @Override
    public void turnOff(Context context) {
        flashLightOff(context);
    }

    private void flashLightOn(Context context) {
        long blinkDelay = 500; //Delay in ms
        long startTime = System.currentTimeMillis(); //fetch starting time
        boolean currentBlinkStatus = false;
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            if (this.forever) {
                cameraManager.setTorchMode(cameraId, true);
            } else {
                while ((System.currentTimeMillis() - startTime) < (this.duration * 1000)) {
                    if (!currentBlinkStatus) {
                        cameraManager.setTorchMode(cameraId, true);
                        currentBlinkStatus = true;
                    } else {
                        cameraManager.setTorchMode(cameraId, false);
                        currentBlinkStatus = false;
                    }
                    try {
                        Thread.sleep(blinkDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (CameraAccessException e) {
            //TODO
        }
    }

    private void flashLightOff(Context context) {
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

        //Log.d("STATE", "FLASHLIGHT OFF");
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
            //TODO
        }
    }

}
