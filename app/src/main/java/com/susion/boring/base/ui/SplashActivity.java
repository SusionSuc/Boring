package com.susion.boring.base.ui;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;

import com.susion.boring.R;
import com.susion.boring.base.ui.mainui.MainActivity;
import com.susion.boring.utils.SystemOperationUtils;
import com.susion.boring.utils.UIUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SplashActivity extends Activity {

    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 200;
    private boolean mHasSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);     //no status bar
        setContentView(R.layout.activity_splash);
        requestPermission();
    }

    public void skipToMainActivity() {
        if (mHasSkip) {
            return;
        }
        installShortcut();
        mHasSkip = true;
        getWindow().getDecorView().findViewById(android.R.id.content).postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.start(SplashActivity.this);
                SplashActivity.this.finish();
            }
        }, 3000);
    }

    private void installShortcut() {
        String shortCutName = getString(R.string.app_name);
        if (!SystemOperationUtils.hasShortcut(this, shortCutName)) {
            SystemOperationUtils.createShortCut(this, shortCutName, R.mipmap.ic_logo);
        }
    }

    public void requestPermission() {
        String[] needPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        final List<String> requestPermissions = new ArrayList<>();

        for (int i = 0; i < needPermissions.length; i++) {
            if (ActivityCompat.checkSelfPermission(this, needPermissions[i]) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions.add(needPermissions[i]);
            }
        }

        if (!requestPermissions.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    skipToMainActivity();
                }
            });
            builder.setTitle("你需要开启以下权限")
                    .setMessage("访问存储空间, 读取媒体信息")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(SplashActivity.this, requestPermissions.toArray(new String[requestPermissions.size()]),
                                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                        }
                    });
            builder.show();
        } else {
            skipToMainActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions == null || permissions.length == 0) {
            skipToMainActivity();
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                Map<String, Integer> perms = new HashMap<>();
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);

                if (perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    skipToMainActivity();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
                    builder.setTitle("拒绝这些权限, 你将不能很好的使用")
                            .setMessage("访问存储空间, 读取媒体信息")
                            .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                                    startActivity(intent);
                                }
                            }).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
