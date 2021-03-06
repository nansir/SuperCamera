package com.sir.app.camera.sample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sir.library.camera.CameraFragment;
import com.sir.library.camera.CameraFragmentApi;
import com.sir.library.camera.PreviewActivity;
import com.sir.library.camera.configuration.Configuration;
import com.sir.library.camera.listeners.CameraFragmentResultListener;
import com.sir.library.camera.listeners.CameraFragmentStateListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("MissingPermission")
public class CameraFragmentMainActivityCustoms extends AppCompatActivity {

    public static final String FRAGMENT_TAG = "camera";
    private static final int REQUEST_CAMERA_PERMISSIONS = 931;
    private static final int REQUEST_PREVIEW_CODE = 1001;

    @BindView(R.id.settings_view)
    Button settingsView;
    @BindView(R.id.flash_switch_view)
    Button flashSwitchView;
    @BindView(R.id.front_back_camera_switcher)
    Button cameraSwitchView;
    @BindView(R.id.record_button)
    Button recordButton;
    @BindView(R.id.photo_video_camera_switcher)
    Button mediaActionSwitchView;

    @BindView(R.id.cameraLayout)
    View cameraLayout;
    @BindView(R.id.addCameraButton)
    View addCameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camerafragment_activity_main_customs);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.flash_switch_view)
    public void onFlashSwitcClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.toggleFlashMode();
        }
    }

    private CameraFragmentApi getCameraFragment() {
        return (CameraFragmentApi) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }

    @OnClick(R.id.front_back_camera_switcher)
    public void onSwitchCameraClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.switchCameraTypeFrontBack();
        }
    }

    @OnClick(R.id.record_button)
    public void onRecordButtonClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.takePhotoOrCaptureVideo(new CameraFragmentResultListener() {
                @Override
                public void onVideoRecorded(String filePath) {

                }

                @Override
                public void onPhotoTaken(byte[] bytes, String filePath) {

                }
            }, "/storage/self/primary", "photo0");
        }
    }

    @OnClick(R.id.settings_view)
    public void onSettingsClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.openSettingDialog();
        }
    }

    @OnClick(R.id.photo_video_camera_switcher)
    public void onMediaActionSwitchClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.switchActionPhotoVideo();
        }
    }

    @OnClick(R.id.addCameraButton)
    public void onAddCameraClicked() {
        if (Build.VERSION.SDK_INT > 15) {
            final String[] permissions = {
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE};

            final List<String> permissionsToRequest = new ArrayList<>();
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsToRequest.add(permission);
                }
            }
            if (!permissionsToRequest.isEmpty()) {
                ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[permissionsToRequest.size()]), REQUEST_CAMERA_PERMISSIONS);
            } else addCamera();
        } else {
            addCamera();
        }
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    public void addCamera() {
        addCameraButton.setVisibility(View.GONE);
        cameraLayout.setVisibility(View.VISIBLE);

        final Configuration.Builder builder = new Configuration.Builder();
        builder.setCamera(Configuration.CAMERA_FACE_FRONT)
                .setFlashMode(Configuration.FLASH_MODE_ON)
                .setMediaAction(Configuration.MEDIA_ACTION_VIDEO);

        final CameraFragment cameraFragment = CameraFragment.newInstance(builder.build());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, cameraFragment, FRAGMENT_TAG)
                .commit();

        if (cameraFragment != null) {
            cameraFragment.setResultListener(new CameraFragmentResultListener() {
                @Override
                public void onVideoRecorded(String filePath) {
                    Intent intent = PreviewActivity.newIntentVideo(CameraFragmentMainActivityCustoms.this, filePath);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }

                @Override
                public void onPhotoTaken(byte[] bytes, String filePath) {
                    Intent intent = PreviewActivity.newIntentPhoto(CameraFragmentMainActivityCustoms.this, filePath);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            });

            cameraFragment.setStateListener(new CameraFragmentStateListener() {

                @Override
                public void onCurrentCameraBack() {
                    cameraSwitchView.setText("back");
                }

                @Override
                public void onCurrentCameraFront() {
                    cameraSwitchView.setText("front");
                }

                @Override
                public void onFlashAuto() {
                    flashSwitchView.setText("auto");
                }

                @Override
                public void onFlashOn() {
                    flashSwitchView.setText("on");
                }

                @Override
                public void onFlashOff() {
                    flashSwitchView.setText("off");
                }

                @Override
                public void onCameraSetupForPhoto() {
                    mediaActionSwitchView.setText("photo");
                    recordButton.setText("take photo");
                    flashSwitchView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onCameraSetupForVideo() {
                    mediaActionSwitchView.setText("video");
                    recordButton.setText("capture video");
                    flashSwitchView.setVisibility(View.GONE);
                }

                @Override
                public void onRecordStateVideoReadyForRecord() {
                    recordButton.setText("take video");
                }

                @Override
                public void onRecordStateVideoInProgress() {
                    recordButton.setText("stop");
                }

                @Override
                public void onRecordStatePhoto() {
                    recordButton.setText("take photo");
                }

                @Override
                public void shouldRotateControls(int degrees) {
                    ViewCompat.setRotation(cameraSwitchView, degrees);
                    ViewCompat.setRotation(mediaActionSwitchView, degrees);
                    ViewCompat.setRotation(flashSwitchView, degrees);
                }

                @Override
                public void onStartVideoRecord(File outputFile) {
                }

                @Override
                public void onStopVideoRecord() {
                    settingsView.setVisibility(View.VISIBLE);
                }
            });

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length != 0) {
            addCamera();
        }
    }
}
