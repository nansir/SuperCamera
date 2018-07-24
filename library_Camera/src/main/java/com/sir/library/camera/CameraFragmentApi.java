package com.sir.library.camera;

import android.support.annotation.Nullable;

import com.sir.library.camera.internal.ui.model.PhotoQualityOption;
import com.sir.library.camera.listeners.CameraFragmentControlsListener;
import com.sir.library.camera.listeners.CameraFragmentResultListener;
import com.sir.library.camera.listeners.CameraFragmentStateListener;
import com.sir.library.camera.listeners.CameraFragmentVideoRecordTextListener;

public interface CameraFragmentApi {

    void takePhotoOrCaptureVideo(CameraFragmentResultListener resultListener, @Nullable String directoryPath, @Nullable String fileName);

    void openSettingDialog();

    PhotoQualityOption[] getPhotoQualities();

    void switchCameraTypeFrontBack();

    void switchActionPhotoVideo();

    void toggleFlashMode();

    void setStateListener(CameraFragmentStateListener cameraFragmentStateListener);

    void setTextListener(CameraFragmentVideoRecordTextListener cameraFragmentVideoRecordTextListener);

    void setControlsListener(CameraFragmentControlsListener cameraFragmentControlsListener);

    void setResultListener(CameraFragmentResultListener cameraFragmentResultListener);

}
