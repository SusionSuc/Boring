package com.susion.boring.base.ui;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.susion.boring.R;
import com.susion.boring.base.ui.mainui.MainActivity;
import com.yanzhenjie.permission.AndPermission;


public class SplashActivity extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);     //no status bar
        setContentView(R.layout.activity_splash);

        skipToMainActivity(this, getWindow().getDecorView());
        requestPermission(this);
    }



    public void skipToMainActivity(final Activity context, View view) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.start(context);
                context.finish();
            }
        }, 3000);
    }


    public void requestPermission(Activity activity) {
        AndPermission.with(activity)
                .requestCode(100)
                .permission(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET, Manifest.permission.MEDIA_CONTENT_CONTROL, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)
                .send();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
