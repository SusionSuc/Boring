package com.susion.boring.music.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.susion.boring.R;
import com.susion.boring.base.adapter.BaseRVAdapter;
import com.susion.boring.base.ui.ItemHandler;
import com.susion.boring.base.ui.ItemHandlerFactory;
import com.susion.boring.event.ChangeSongEvent;
import com.susion.boring.event.SongDeleteFromPlayQueueEvent;
import com.susion.boring.music.itemhandler.DialogMusicIH;
import com.susion.boring.music.mvp.model.Song;
import com.susion.boring.utils.RVUtils;
import com.susion.boring.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by susion on 17/3/3.
 */
public class PlayControlDialog extends Dialog {

    private RecyclerView mRV;
    private List<Object> mDialogData = new ArrayList<>();
    private ImageView mIvLoading;
    private Context mContext;

    public PlayControlDialog(Context context) {
        super(context);
        mContext = context;
        initView();
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_play_control);
        mRV = (RecyclerView) findViewById(R.id.list_view);
        mIvLoading = (ImageView) findViewById(R.id.loading);

        Window dialogWindow = getWindow();
        dialogWindow.setBackgroundDrawableResource(R.drawable.bg_dialog);
        dialogWindow.setGravity(Gravity.BOTTOM);
        this.setCancelable(true);
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = UIUtils.getScreenHeight() * 4 / 7;
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(p);
        dialogWindow.setWindowAnimations(R.style.dialog_animation_frombottom);

        initData();
        initRv();
    }

    private void initRv() {
        mRV.setLayoutManager(RVUtils.getLayoutManager(mContext, LinearLayoutManager.VERTICAL));
        mRV.addItemDecoration(new RVUtils.NoLastDividerDecoration(getContext(), R.color.divider, 1, new Rect(UIUtils.dp2Px(15), 0, 0, 0)));
        mRV.setAdapter(new BaseRVAdapter((Activity) mContext, mDialogData) {
            final int TYPE_MUSIC = 1;

            @Override
            protected void initHandlers() {
                registerItemHandler(TYPE_MUSIC, new ItemHandlerFactory() {
                    @Override
                    public ItemHandler newInstant(int viewType) {
                        return new DialogMusicIH();
                    }
                });

            }

            @Override
            protected int getViewType(int position) {
                Object o = mData.get(position);
                if (o instanceof Song) {
                    return TYPE_MUSIC;
                }
                return 0;
            }
        });
    }

    private void initData() {

    }

    public void addMusicQueue(List<Song> songs) {
        mDialogData.addAll(songs);
        mRV.getAdapter().notifyDataSetChanged();
    }


    public void startLoadingAnimation() {
        mIvLoading.setVisibility(View.VISIBLE);
        mRV.setVisibility(View.INVISIBLE);
        UIUtils.startSimpleRotateAnimation(mIvLoading);
    }

    public void stopLoadingAnimation() {
        mIvLoading.setVisibility(View.INVISIBLE);
        mRV.setVisibility(View.VISIBLE);
        mIvLoading.clearAnimation();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SongDeleteFromPlayQueueEvent event) {
        mDialogData.remove(event.song);
        mRV.getAdapter().notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ChangeSongEvent event) {
        dismiss();
        Song song = event.song;
        song.isPlaying = true;
        mRV.getAdapter().notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
