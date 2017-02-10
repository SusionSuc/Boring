package com.susion.boring.music.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.music.model.Lyric;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.http.Path;

/**
 * Created by susion on 17/2/6.
 *
 * the each line of lyric format must is
 *   [00:00.00] 作词 : 吴梦奇/胡海泉/胥文雅 (aka 文雅) \n
 */
public class LyricView2 extends LinearLayout{

    private String mLyrics;
    private Activity mContext;
    private BufferedReader mReader;
    private List<Lyric> mData = new ArrayList<>();

    private Map<String, Lyric> mLyricMap = new HashMap();

    private int mPaddingTop;
    private int mLyricHeight;

    private ListView mLyricContainer;

    public LyricView2(Context context) {
        super(context);
        init(context);
    }

    public LyricView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mPaddingTop = getHeight() / 3;
        mLyricContainer.layout(l, t + mPaddingTop, r, b);
    }

    private void init(Context context) {
        mContext = (Activity) context;

        mLyricContainer = new ListView(mContext);
        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mLyricContainer.setLayoutParams(containerParams);
        addView(mLyricContainer);
    }

    public void setLyrics(String lyrics) {
        loadLyrics(lyrics);
        initLyricView();
    }

    private void initLyricView() {

        mLyricContainer.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mData.size();
            }

            @Override
            public Object getItem(int i) {
                return mData.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int pos, View convertView, ViewGroup viewGroup) {
                TextView tv;
                if (convertView == null) {
                    tv  = getLyricTextView();
                    convertView = tv;
                } else {
                    tv = (TextView) convertView;
                }

                tv.setText(mData.get(pos).lyric);

                return convertView;
            }
        });

        post(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<mData.size(); i++) {
                    TextView tv = (TextView) mLyricContainer.getChildAt(i);
                    mData.get(i).height = tv.getHeight();
                }
            }
        });
    }

    @NonNull
    private TextView getLyricTextView() {
        TextView lyricView = new TextView(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lyricView.setLayoutParams(params);
        lyricView.setGravity(Gravity.CENTER_HORIZONTAL);
        lyricView.setTextColor(getResources().getColor(R.color.lyric_color));
        lyricView.setTextSize(15);
        return lyricView;
    }

    // time format 00:00
    public void setCurrentLyricByTime(String currentTime){
        Lyric lyric = mLyricMap.get(currentTime);
        if (lyric != null) {
            if (lyric.height != Lyric.NO_MEASURE) {
                if (mPaddingTop >= 0) {
                    mLyricContainer.layout(mLyricContainer.getLeft(), mLyricContainer.getTop()-lyric.height,
                            mLyricContainer.getRight(), mLyricContainer.getBottom());
                    mPaddingTop -= lyric.height;
                } else {
                    mLyricContainer.scrollBy(0, lyric.height);
                }
            }
        }
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
