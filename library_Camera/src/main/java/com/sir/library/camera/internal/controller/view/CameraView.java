package com.sir.library.camera.internal.controller.view;

import android.support.annotation.Nullable;
import android.view.View;

import com.sir.library.camera.configuration.Configuration;
import com.sir.library.camera.internal.utils.Size;
import com.sir.library.camera.listeners.CameraFragmentResultListener;

public interface CameraView {

    void updateCameraPreview(Size size, View cameraPreview);

    void updateUiForMediaAction(@Configuration.MediaAction int mediaAction);

    void updateCameraSwitcher(int numberOfCameras);

    void onPhotoTaken(byte[] bytes, @Nullable CameraFragmentResultListener callback);

    void onVideoRecordStart(int width, int height);

    void onVideoRecordStop(@Nullable CameraFragmentResultListener callback);

    void releaseCameraPreview();

}
