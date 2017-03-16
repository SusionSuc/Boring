package com.susion.boring.music.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.susion.boring.music.mvp.model.Lyric;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by susion on 17/2/6.
 *
 * the each line of lyric format must is
 *   [00:00.00] 作词 : 吴梦奇/胡海泉/胥文雅 (aka 文雅) \n
 */
public class LyricView extends TextView{

    private String mLyrics;
    private Context mContext;
    private BufferedReader mReader;
    private List<Lyric> mData = new ArrayList<>();
    private Map<String, Lyric> mLyricMap = new HashMap();


    public LyricView(Context context) {
        super(context);
        init(context);
    }

    public LyricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LyricView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    public LyricView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = getContext();
    }


    public void setLyrics(String lyrics) {
        loadLyrics(lyrics);
    }

    // time format 00:00
    public void setCurrentLyricByTime(String currentTime){
        Lyric lyric = mLyricMap.get(currentTime);
        setText(lyric.lyric);
    }


    private void loadLyrics(String lyrics){
        if (!mData.isEmpty()) {
            mData.clear();
            mLyricMap.clear();
        }
        mLyrics = lyrics;
        mReader = new BufferedReader(new StringReader(mLyrics));
        String lyricStr;
        int pos = 0;
        try{
            while ((lyricStr = mReader.readLine()) != null) {
                translateStringToLyric(lyricStr, pos);
                pos++;
            }
            mReader.close();
        }catch(Exception e){

        }
    }

    private void translateStringToLyric(String lyricStr, int pos) {
        String time = lyricStr.substring(lyricStr.indexOf("[")+1, lyricStr.indexOf("]"));
        String l = lyricStr.substring(lyricStr.indexOf("]")+1);
        Lyric lyric = new Lyric(time, l, pos);
        mData.add(lyric);
        mLyricMap.put(time.substring(0, time.indexOf(".")), lyric);
    }

}
