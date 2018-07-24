package com.sir.library.camera;

import android.Manifest;
import android.support.annotation.RequiresPermission;

import com.sir.library.camera.configuration.Configuration;
import com.sir.library.camera.internal.ui.BaseAnncaFragment;

public class CameraFragment extends BaseAnncaFragment {

    @RequiresPermission(Manifest.permission.CAMERA)
    public static CameraFragment newInstance(Configuration configuration) {
        return (CameraFragment) BaseAnncaFragment.newInstance(new CameraFragment(), configuration);
    }
}
