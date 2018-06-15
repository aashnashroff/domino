package com.aashnashroff.domino;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.util.Log;

public class FlashlightOutput extends OutputTile {

    private int duration;
    private boolean hasCameraFlash;

    public FlashlightOutput(int duration, boolean hasCameraFlash ) {
        this.duration = duration;
        this.hasCameraFlash = hasCameraFlash;
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
            //TODO
//            Toast.makeText(this.context, "No flash available on your device",
//                    Toast.LENGTH_SHORT).show();
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
            while((System.currentTimeMillis() - startTime) < this.duration){
                if(!currentBlinkStatus) {
                    cameraManager.setTorchMode(cameraId, true);
                    currentBlinkStatus = true;
                }
                else {
                    cameraManager.setTorchMode(cameraId, false);
                    currentBlinkStatus = false;
                }
                try {
                    Thread.sleep(blinkDelay);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        } catch (CameraAccessException e) {

        }
    }

    private void flashLightOff(Context context) {
        CameraManager cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);

        Log.d("STATE", "FLASHLIGHT OFF");
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {

        }
    }

}
