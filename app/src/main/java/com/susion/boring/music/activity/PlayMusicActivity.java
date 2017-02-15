package com.susion.boring.music.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.susion.boring.R;
import com.susion.boring.base.BaseActivity;
import com.susion.boring.API.APIHelper;
import com.susion.boring.music.model.LyricResult;
import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.IPlayMusicPresenter;
import com.susion.boring.music.presenter.PlayMusicPresenter;

import com.susion.boring.music.service.MusicInstruction;
import com.susion.boring.music.service.MusicPlayerService;
import com.susion.boring.music.view.IMediaPlayView;
import com.susion.boring.music.view.LyricView;
import com.susion.boring.music.view.MediaSeekBar;
import com.susion.boring.music.view.MusicPlayControlView;
import com.susion.boring.utils.BroadcastUtils;
import com.susion.boring.utils.ImageUtils;
import com.susion.boring.utils.MediaUtils;
import com.susion.boring.view.SToolBar;

import rx.Observer;

public class PlayMusicActivity extends BaseActivity implements IMediaPlayView{
    private static final String TO_PLAY_MUSIC_INFO = "played_music";
    private static final String IS_PLAY = "is_play";

    private SToolBar mToolBar;
    private MediaSeekBar mSeekBar;
    private MusicPlayControlView mPlayControlView;
    private LinearLayout mLl;
    private IPlayMusicPresenter mPresenter;

    private Song mSong;
    private TextView mTvPlayedTime;
    private TextView mTvLeftTime;
    private LyricView mLyricView;
    private ClientMusicReceiver mReceiver;

    public static void start(Context context, Song song) {
        Intent intent = new Intent();
        intent.putExtra(TO_PLAY_MUSIC_INFO, song);
        intent.setClass(context, PlayMusicActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_play_music;
    }

    @Override
    public void findView(){
        mToolBar = (SToolBar) findViewById(R.id.toolbar);
        mSeekBar = (MediaSeekBar) findViewById(R.id.seek_bar);
        mPlayControlView = (MusicPlayControlView) findViewById(R.id.control_view);
        mLl = (LinearLayout) findViewById(R.id.ll);
        mTvPlayedTime = (TextView) findViewById(R.id.tv_has_play_time);
        mTvLeftTime = (TextView) findViewById(R.id.tv_left_time);
        mLyricView = (LyricView) findViewById(R.id.lyric_view);
    }

    @Override
    public void initView() {
        mReceiver = new ClientMusicReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, mReceiver.getIntentFilter());
        mSong = (Song) getIntent().getSerializableExtra(TO_PLAY_MUSIC_INFO);

        mPresenter = new PlayMusicPresenter(this);
        mToolBar.setMainPage(false);
        mToolBar.setTitle(mSong.name);
        mToolBar.setLeftIcon(R.mipmap.tool_bar_back);
        mToolBar.setBackgroundColor(getResources().getColor(R.color.transparent));

        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setBlurBackground();
        initListener();

        mPlayControlView.setIsPlay(false);
    }

    @Override
    public void initListener() {
        mPlayControlView.setOnControlItemClickListener(new MusicPlayControlView.MusicPlayerControlViewItemClickListener() {
            @Override
            public void onMoreItemClick() {

            }

            @Override
            public void onNextItemClick() {

            }

            @Override
            public void onPreItemClick() {

            }

            @Override
            public void onPatternItemClick() {

            }

            @Override
            public void onStartOrStartItemClick(boolean isPlay) {
                if (isPlay) {
                    BroadcastUtils.sendIntentAction(PlayMusicActivity.this, MusicInstruction.SERVICE_RECEIVER_PLAY_MUSIC);
                } else {
                    BroadcastUtils.sendIntentAction(PlayMusicActivity.this, MusicInstruction.SERVICE_RECEIVER_PAUSE_MUSIC);
                }
            }
        });


        mSeekBar.setMediaSeekBarListener(new MediaSeekBar.MediaSeekBarListener() {
            @Override
            public void onStartDragThumb(int currentProgress) {
                mPlayControlView.setIsPlay(false);
            }

            @Override
            public void onDraggingThumb(int currentProgress) {
            }

            @Override
            public void onStopDragThumb(int currentProgress) {
                Intent intent = new Intent(MusicInstruction.SERVICE_RECEIVER_SEEK_TO);
                intent.putExtra(MusicInstruction.SERVICE_PARAM_SEEK_TO_POS, currentProgress);
                LocalBroadcastManager.getInstance(PlayMusicActivity.this).sendBroadcast(intent);
                if (!mPlayControlView.ismIsPlay()) {
                    mPlayControlView.setIsPlay(true);
                }
            }

            @Override
            public void onProgressChange(int currentProgress) {
                Intent intent = new Intent(MusicInstruction.SERVICE_RECEIVER_SEEK_TO);
                intent.putExtra(MusicInstruction.SERVICE_PARAM_SEEK_TO_POS, currentProgress);
                LocalBroadcastManager.getInstance(PlayMusicActivity.this).sendBroadcast(intent);
            }
        });
    }

    @Override
    public void initData() {
        loadLyric();
        BroadcastUtils.sendIntentAction(PlayMusicActivity.this, MusicInstruction.SERVICE_RECEIVER_QUERY_IS_PLAYING);  //查询当前的播放状态
    }

    private void loadMusic() {
        Intent intent = new Intent(MusicInstruction.SERVICE_LOAD_MUSIC_INFO);
        intent.putExtra(MusicInstruction.SERVICE_PARAM_PLAY_SONG, mSong);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void loadLyric() {
        APIHelper.subscribeSimpleRequest(APIHelper.getMusicServices().getMusicLyric(mSong.id), new Observer<LyricResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(LyricResult s) {
                mLyricView.setLyrics(s.lrc.lyric);
            }
        });

    }

    private void setBlurBackground() {
        ImageUtils.LoadImage(this, mSong.album.picUrl, new ImageUtils.OnLoadFinishLoadImage() {
            @Override
            public void loadImageFinish(String imageUri, View view, Bitmap loadedImage) {
                mLl.setBackground(mPresenter.getBackgroundBlurImage(loadedImage));
            }
        });
    }

    private void tryToChangeMusic() {
        Intent intent = new Intent(MusicInstruction.SERVER_RECEIVER_CHANGE_MUSIC);
        intent.putExtra(MusicInstruction.SERVICE_PARAM_CHANGE_MUSIC, mSong);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void preparedPlay(int duration) {
        mSeekBar.setCurrentProgress(0);
        mSeekBar.setMaxProgress(duration);
    }

    @Override
    public void completionPlay() {
        mPlayControlView.setIsPlay(false);
        mSeekBar.setCurrentProgress(mSeekBar.getMaxProgress());
    }

    @Override
    public void updateBufferedProgress(int percent) {
        mSeekBar.setHasBufferProgress( (int) (percent * 1.0f / 100 * mSeekBar.getMaxProgress()));
    }

    @Override
    public void updatePlayProgress(int curPos, int left) {
        mTvPlayedTime.setText(MediaUtils.getDurationString(curPos, false));
        mTvLeftTime.setText(MediaUtils.getDurationString(left, true));
        mSeekBar.setCurrentProgress(curPos);
        mLyricView.setCurrentLyricByTime(MediaUtils.getDurationString(curPos, false));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
    }

    public class ClientMusicReceiver extends BroadcastReceiver{

        public IntentFilter getIntentFilter(){
            IntentFilter filter = new IntentFilter();
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_PLAYER_PREPARED);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_UPDATE_BUFFERED_PROGRESS);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_UPDATE_PLAY_PROGRESS);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_CURRENT_SERVER_STATE);
            filter.addAction(MusicInstruction.CLIENT_RECEIVER_SET_DURATION);
            return filter;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case MusicInstruction.CLIENT_RECEIVER_PLAYER_PREPARED:
                    preparedPlay(intent.getIntExtra(MusicInstruction.CLIENT_PARAM_PREPARED_TOTAL_DURATION, 0));
                    BroadcastUtils.sendIntentAction(PlayMusicActivity.this, MusicInstruction.SERVICE_RECEIVER_PLAY_MUSIC);
                    mPlayControlView.setIsPlay(true);
                    break;
                case MusicInstruction.CLIENT_RECEIVER_UPDATE_BUFFERED_PROGRESS:
                    updateBufferedProgress(intent.getIntExtra(MusicInstruction.CLIENT_PARAM_BUFFERED_PROGRESS, 0));
                    break;
                case MusicInstruction.CLIENT_RECEIVER_UPDATE_PLAY_PROGRESS:
                    updatePlayProgress(intent.getIntExtra(MusicInstruction.CLIENT_PARAM_PLAY_PROGRESS_CUR_POS, 0),
                            intent.getIntExtra(MusicInstruction.CLIENT_PARAM_PLAY_PROGRESS_DURATION, 0));
                    break;
                case MusicInstruction.CLIENT_RECEIVER_CURRENT_SERVER_STATE:
                    boolean serverState = intent.getBooleanExtra(MusicInstruction.CLIENT_PARAM_SERVER_STATE, false);
                    if (serverState) {  //繁忙状态
                        tryToChangeMusic();
                        mPlayControlView.setIsPlay(true);
                    } else {  //空闲状态
                        loadMusic();
                    }
                    break;
                case MusicInstruction.CLIENT_RECEIVER_SET_DURATION:
                    mSeekBar.setMaxProgress(intent.getIntExtra(MusicInstruction.CLIENT_PARAM_MEDIA_DURATION, 0));
                    break;
                case MusicInstruction.CLIENT_RECEIVER_CURRENT_IS_PALING:  //查询当前是否在播放
                    mPlayControlView.setIsPlay(intent.getBooleanExtra(MusicInstruction.CLIENT_PARAM_IS_PLAYING, false));
                    break;
            }
        }
    }

}
