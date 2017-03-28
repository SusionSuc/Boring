package com.susion.boring.base.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.susion.boring.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SettingActivity.class);
        context.startActivity(intent);
    }
}
