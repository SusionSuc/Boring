package com.susion.boring.music.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.base.BaseRVAdapter;
import com.susion.boring.base.ItemHandler;
import com.susion.boring.base.ItemHandlerFactory;
import com.susion.boring.base.SimpleItemHandler;
import com.susion.boring.music.model.Lyric;
import com.susion.boring.utils.RVUtils;

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
public class LyricView extends LinearLayout{

    private String mLyrics;
    private Activity mContext;
    private BufferedReader mReader;
    private List<Lyric> mData = new ArrayList<>();

    private Map<String, Lyric> mLyricMap = new HashMap();
    private RecyclerView mRecycleView;
    private BaseRVAdapter mAdapter;
    private int mPaddingTop;
    private int mLyricHeight;


    public LyricView(Context context) {
        super(context);
        init(context);
    }

    public LyricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = (Activity) context;
        mRecycleView = new RecyclerView(mContext);
        addView(mRecycleView);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mRecycleView.layout(l, getHeight()/2+l, r, b);
    }

    public void setLyrics(String mLyrics) {

        if (!mData.isEmpty()) {
            mData.clear();
            mLyricMap.clear();
        }

        this.mLyrics = mLyrics;
        mReader = new BufferedReader(new StringReader(mLyrics));
        initData();
        mRecycleView.setLayoutManager(RVUtils.getLayoutManager(mContext, LinearLayoutManager.VERTICAL));
        mAdapter = new BaseRVAdapter(mContext, mData) {
            @Override
            protected void initHandlers() {
                registerItemHandler(0, new ItemHandlerFactory() {
                    @Override
                    public ItemHandler newInstant(int viewType) {
                        return new LyricItemHandler();
                    }
                });
            }

            @Override
            protected int getViewType(int position) {
                return 0;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
                if(!payloads.isEmpty()) {
                    if (payloads.get(0) instanceof Integer) {
                        LyricItemHandler itemHandler = (LyricItemHandler) ((com.susion.boring.base.ViewHolder) holder.itemView.getTag(R.id.item_tag_id)).itemHandler;
                        itemHandler.setLyricColor((Integer) payloads.get(0));
                    }
                }else {
                    super.onBindViewHolder(holder,position, payloads);
                }
            }
        };
        mRecycleView.setAdapter(mAdapter);

    }
    
    // time format 00:00
    public void setCurrentLyricByTime(String currentTime){
        Lyric lyric = mLyricMap.get(currentTime);
        if (lyric != null) {
            mRecycleView.smoothScrollToPosition(lyric.pos);
            mAdapter.notifyItemChanged(lyric.pos, new Integer(R.color.lyric_color));
        }
    }

    private void initData(){
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

    private class LyricItemHandler extends SimpleItemHandler<Lyric> {
        private TextView mTv;
        @Override
        public void onBindDataView(com.susion.boring.base.ViewHolder vh, Lyric data, int position) {
            mTv = vh.getTextView(R.id.tv);
            mTv.setText(data.lyric);
        }

        @Override
        public int getLayoutResId() {
            return R.layout.item_lyric;
        }

        @Override
        public void onClick(View view) {

        }

        public void setLyricColor(int colRes){
            mTv.setTextColor(getResources().getColor(colRes));
        }
    }
}
