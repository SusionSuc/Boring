package com.susion.boring.music.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pair;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susion.boring.R;
import com.susion.boring.base.BaseActivity;
import com.susion.boring.http.APIHelper;
import com.susion.boring.music.model.LyricResult;

import com.susion.boring.music.model.PlayList;
import com.susion.boring.music.model.PlayListSong;
import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.PlayMusicCommunicatePresenter;
import com.susion.boring.music.presenter.itf.MediaPlayerContract;
import com.susion.boring.music.service.MusicInstruction;
import com.susion.boring.music.view.MediaSeekBar;
import com.susion.boring.music.view.MusicPlayControlView;
import com.susion.boring.music.view.PlayOperatorView;
import com.susion.boring.utils.AlbumUtils;
import com.susion.boring.utils.BroadcastUtils;
import com.susion.boring.utils.Md5Utils;
import com.susion.boring.utils.TimeUtils;
import com.susion.boring.utils.TransitionHelper;
import com.susion.boring.view.SToolBar;

import java.io.File;

import rx.Observer;

public class PlayMusicActivity extends BaseActivity implements MediaPlayerContract.CommunicateView {
    private static final String TO_PLAY_MUSIC_INFO = "played_music";
    private static final String FROM_LITTLE_PANEL = "from_little_panel";
    private static final String FROM_PLAY_LIST = "from_play_list";
    private static final String PLAY_LIST_ID = "play_list_song_id";

    private SToolBar mToolBar;
    private MediaSeekBar mSeekBar;
    private MusicPlayControlView mPlayControlView;
    private TextView mTvPlayedTime;
    private TextView mTvLeftTime;
    private SimpleDraweeView mSdvAlbym;
    private TextView mTvMusicName;
    private PlayOperatorView mPovMusicPlayControl;

    private Song mSong;
    private boolean mIsFromLittlePanel;

    private MediaPlayerContract.PlayMusicCommunicatePresenter mCommunicatePresenter;
    private boolean mIsFromPlayList;

    public static void start(Context context, Song song, boolean isFromPlayList) {
        Intent intent = new Intent();
        intent.putExtra(TO_PLAY_MUSIC_INFO, song);
        intent.putExtra(FROM_PLAY_LIST, isFromPlayList);
        intent.setClass(context, PlayMusicActivity.class);
        context.startActivity(intent);
    }

    public static void startFromLittlePanel(Activity activity, Song song) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
        Intent intent = new Intent();
        intent.putExtra(TO_PLAY_MUSIC_INFO, song);
        intent.putExtra(FROM_LITTLE_PANEL, true);
        intent.setClass(activity, PlayMusicActivity.class);
        activity.startActivity(intent, transitionActivityOptions.toBundle());
    }

    @Override
    public void initTransitionAnim() {
        if (getIntent().getBooleanExtra(FROM_LITTLE_PANEL, false)) {
            Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_from_bottom);
            getWindow().setEnterTransition(transition);
        }
    }

    @Override
    protected void setStatusBar() {
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_play_music;
    }

    @Override
    public void findView() {
        mCommunicatePresenter = new PlayMusicCommunicatePresenter(this);

        mToolBar = (SToolBar) findViewById(R.id.toolbar);
        mSeekBar = (MediaSeekBar) findViewById(R.id.seek_bar);
        mPlayControlView = (MusicPlayControlView) findViewById(R.id.control_view);
        mTvPlayedTime = (TextView) findViewById(R.id.tv_has_play_time);
        mTvLeftTime = (TextView) findViewById(R.id.tv_left_time);
        mSdvAlbym = (SimpleDraweeView) findViewById(R.id.ac_play_music_sdv_album);
        mTvMusicName = (TextView) findViewById(R.id.ac_play_tv_music_name);
        mPovMusicPlayControl = (PlayOperatorView) findViewById(R.id.ac_play_music_pov);
    }

    @Override
    public void initView() {
        getParamAndInitReceiver();
        mToolBar.setMainPage(false);
        mToolBar.setTitle("");
        mToolBar.setLeftIcon(R.mipmap.tool_bar_back);
        mToolBar.setBackgroundColor(getResources().getColor(R.color.transparent));

        refreshSong(mSong);
        initListener();

        mPlayControlView.setIsPlay(false);
        mPovMusicPlayControl
                .setSong(mSong);
        mPovMusicPlayControl.setPresenter(mCommunicatePresenter);
    }

    private void getParamAndInitReceiver() {
        mIsFromLittlePanel = getIntent().getBooleanExtra(FROM_LITTLE_PANEL, false);
        mIsFromPlayList = getIntent().getBooleanExtra(FROM_PLAY_LIST, false);
        mSong = (Song) getIntent().getSerializableExtra(TO_PLAY_MUSIC_INFO);

        if (mIsFromPlayList) {
            APIHelper.subscribeSimpleRequest(APIHelper.getMusicServices().getSongDetail(Integer.valueOf(mSong.id)), new Observer<PlayListSong>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    int b = 0;
                }

                @Override
                public void onNext(PlayListSong songs) {
                    if (songs != null && !songs.getData().isEmpty()) {
                        mSong.audio = songs.getData().get(0).getUrl();
                        mCommunicatePresenter.queryServiceIsPlaying();
                    }
                }
            });
        } else {
            mCommunicatePresenter.queryServiceIsPlaying();
        }
    }

    @Override
    public void initListener() {
        mPlayControlView.setOnControlItemClickListener(new MusicPlayControlView.MusicPlayerControlViewItemClickListener() {
            @Override
            public void onNextItemClick() {
                mCommunicatePresenter.changeToNextMusic();
            }

            @Override
            public void onPreItemClick() {
                mCommunicatePresenter.changeToPreMusic();
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

            }
        });

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
    public void setPlayDuration(int duration) {
        mSeekBar.setMaxProgress(duration);
    }

    @Override
    public void updatePlayProgressForSetMax(int curPos, int left) {
        mSeekBar.setMaxProgress(left);
        mSeekBar.setCurrentProgress(curPos);
        mTvPlayedTime.setText(TimeUtils.getDurationString(curPos, false));
        mTvLeftTime.setText(TimeUtils.getDurationString(left, true));
    }

    @Override
    public void tryToChangeMusicByCurrentCondition(boolean playStatus) {
        if (!mIsFromLittlePanel) {
            mCommunicatePresenter.tryToChangePlayingMusic(mSong);
            mPlayControlView.setIsPlay(true);
            return;
        }
        mPlayControlView.setIsPlay(playStatus);
    }

    @Override
    public void refreshSong(Song song) {
        mSong = song;
        if (mSong.hasDown) {
            mSdvAlbym.setImageBitmap(AlbumUtils.parseAlbum(new File(mSong.audio)));
        } else {
            if (mSong.album.picUrl != null) {
                mSdvAlbym.setImageURI(mSong.album.picUrl);
            }
        }
        mTvMusicName.setText(mSong.name);
    }

    @Override
    public void refreshPlayMode(int mode) {
        mPovMusicPlayControl.refreshPlayMode(mode);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void updateBufferedProgress(int percent) {
        mSeekBar.setHasBufferProgress((int) (percent * 1.0f / 100 * mSeekBar.getMaxProgress()));
    }

    @Override
    public void updatePlayProgress(int curPos, int left) {
        mTvPlayedTime.setText(TimeUtils.getDurationString(curPos, false));
        mTvLeftTime.setText(TimeUtils.getDurationString(left, true));
        mSeekBar.setCurrentProgress(curPos);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCommunicatePresenter.releaseResource();
    }
}
