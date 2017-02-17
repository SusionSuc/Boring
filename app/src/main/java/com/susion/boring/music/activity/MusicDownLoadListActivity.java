package com.susion.boring.music.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.susion.boring.R;

public class MusicDownLoadListActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, MusicDownLoadListActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_down_load_list);
    }


}
