package com.sir.library.camera.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.View;

import com.sir.library.camera.R;
import com.sir.library.camera.internal.utils.Utils;

/*
 * Created by memfis on 6/24/16.
 * Updated by amadeu01 on 17/04/17
 */
public class MediaActionSwitchView extends android.support.v7.widget.AppCompatImageButton {

    @Nullable
    private OnMediaActionStateChangeListener onMediaActionStateChangeListener;
    private Drawable photoDrawable;
    private Drawable videoDrawable;
    private int padding = 5;

    public MediaActionSwitchView(Context context) {
        this(context, null);
    }

    public MediaActionSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    private void initializeView() {
        Context context = getContext();

        photoDrawable = ContextCompat.getDrawable(context, R.drawable.ic_photo_camera_white_24dp);
        photoDrawable = DrawableCompat.wrap(photoDrawable);
        DrawableCompat.setTintList(photoDrawable.mutate(), ContextCompat.getColorStateList(context, R.color.switch_camera_mode_selector));

        videoDrawable = ContextCompat.getDrawable(context, R.drawable.ic_videocam_white_24dp);
        videoDrawable = DrawableCompat.wrap(videoDrawable);
        DrawableCompat.setTintList(videoDrawable.mutate(), ContextCompat.getColorStateList(context, R.color.switch_camera_mode_selector));

        setBackgroundResource(R.drawable.circle_frame_background_dark);
//        setBackgroundResource(R.drawable.circle_frame_background);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMediaActionStateChangeListener != null) {
                    onMediaActionStateChangeListener.switchAction();
                }
            }
        });

        padding = Utils.convertDipToPixels(context, padding);
        setPadding(padding, padding, padding, padding);

        displayActionWillSwitchVideo();
    }

    public void displayActionWillSwitchVideo() {
        setImageDrawable(videoDrawable);
    }

    public MediaActionSwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public void displayActionWillSwitchPhoto() {
        setImageDrawable(photoDrawable);
    }

    public void setOnMediaActionStateChangeListener(@Nullable OnMediaActionStateChangeListener onMediaActionStateChangeListener) {
        this.onMediaActionStateChangeListener = onMediaActionStateChangeListener;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (Build.VERSION.SDK_INT > 10) {
            if (enabled) {
                setAlpha(1f);
            } else {
                setAlpha(0.5f);
            }
        }
    }

    public interface OnMediaActionStateChangeListener {
        void switchAction();
    }
}
