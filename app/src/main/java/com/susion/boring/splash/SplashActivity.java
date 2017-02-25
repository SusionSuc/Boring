package com.susion.boring.splash;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.mainui.MainActivity;
import com.susion.boring.utils.FileUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;


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

}
