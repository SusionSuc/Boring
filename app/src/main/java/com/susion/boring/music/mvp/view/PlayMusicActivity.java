package com.susion.boring.music.mvp.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pair;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susion.boring.R;
import com.susion.boring.base.ui.BaseActivity;
import com.susion.boring.http.APIHelper;
import com.susion.boring.http.CommonObserver;
import com.susion.boring.music.event.ChangeSongEvent;
import com.susion.boring.music.event.SongDeleteFromPlayQueueEvent;
import com.susion.boring.music.mvp.model.PlayListSong;
import com.susion.boring.music.mvp.model.Song;
import com.susion.boring.music.mvp.presenter.ClientReceiverPresenter;
import com.susion.boring.music.mvp.presenter.PlayMusicPagePresenter;
import com.susion.boring.music.service.action.ClientPlayControlCommand;
import com.susion.boring.music.service.action.ClientPlayModeCommand;
import com.susion.boring.music.service.action.ClientPlayQueueControlCommand;
import com.susion.boring.music.mvp.contract.MediaPlayerContract;
import com.susion.boring.music.mvp.contract.MusicServiceContract;
import com.susion.boring.music.mvp.contract.PlayMusicPageContract;
import com.susion.boring.music.service.MusicServiceInstruction;
import com.susion.boring.music.view.LyricView;
import com.susion.boring.music.view.MediaSeekBar;
import com.susion.boring.music.view.MusicPlayControlView;
import com.susion.boring.music.view.PlayControlDialog;
import com.susion.boring.music.view.PlayOperatorView;
import com.susion.boring.utils.AlbumUtils;
import com.susion.boring.utils.BroadcastUtils;
import com.susion.boring.utils.TimeUtils;
import com.susion.boring.utils.TransitionHelper;
import com.susion.boring.base.view.SToolBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import rx.Observer;

public class PlayMusicActivity extends BaseActivity implements MediaPlayerContract.PlayView, PlayMusicPageContract.View {
    private static final String TO_PLAY_MUSIC_INFO = "played_music";
    private static final String FROM_LITTLE_PANEL = "from_little_panel";
    private static final String NEED_LOAD = "needLoad";

    private SToolBar mToolBar;
    private MediaSeekBar mSeekBar;
    private MusicPlayControlView mPlayControlView;
    private TextView mTvPlayedTime;
    private TextView mTvLeftTime;
    private SimpleDraweeView mSdvAlbym;
    private LyricView mTvLyric;
    private PlayOperatorView mPovMusicPlayControl;

    private Song mSong;
    private boolean mIsFromLittlePanel;
    private boolean isLoading;

    private PlayMusicPageContract.Presenter mPresenter;
    private MediaPlayerContract.ClientPlayControlCommand mPlayControlCommand;
    private MediaPlayerContract.ClientPlayModeCommand mPlayModeCommand;
    private MediaPlayerContract.ClientReceiverPresenter mClientReceiver;
    private MediaPlayerContract.ClientPlayQueueControlCommand mPlayQueueCommand;
    private PlayControlDialog mDialog;
    private boolean needLoadFromInt = false;

    public static void start(Context context, Song song, boolean needLoad) {
        Intent intent = new Intent();
        intent.putExtra(TO_PLAY_MUSIC_INFO, song);
        intent.putExtra(NEED_LOAD, needLoad);
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
        EventBus.getDefault().register(this);
        mPlayControlCommand = new ClientPlayControlCommand(this);
        mPlayModeCommand = new ClientPlayModeCommand(this);
        mPlayQueueCommand = new ClientPlayQueueControlCommand(this);
        mClientReceiver = new ClientReceiverPresenter(this);
        mPresenter = new PlayMusicPagePresenter(this);
        mClientReceiver.setPlayView(this);


        mToolBar = (SToolBar) findViewById(R.id.toolbar);
        mSeekBar = (MediaSeekBar) findViewById(R.id.seek_bar);
        mPlayControlView = (MusicPlayControlView) findViewById(R.id.control_view);
        mTvPlayedTime = (TextView) findViewById(R.id.tv_has_play_time);
        mTvLeftTime = (TextView) findViewById(R.id.tv_left_time);
        mSdvAlbym = (SimpleDraweeView) findViewById(R.id.ac_play_music_sdv_album);
        mTvLyric = (LyricView) findViewById(R.id.ac_play_tv_music_name);
        mPovMusicPlayControl = (PlayOperatorView) findViewById(R.id.ac_play_music_pov);
    }

    @Override
    public void initView() {
        getParamAndInitReceiver();
        mToolBar.setMainPage(false);
        mToolBar.setTitle("");
        mToolBar.setLeftIcon(R.mipmap.tool_bar_back);
        mToolBar.setBackgroundColor(getResources().getColor(R.color.transparent));
        initListener();
        refreshSong(mSong);
    }

    private void getParamAndInitReceiver() {
        mIsFromLittlePanel = getIntent().getBooleanExtra(FROM_LITTLE_PANEL, false);
        needLoadFromInt = getIntent().getBooleanExtra(NEED_LOAD, false);
        mSong = (Song) getIntent().getSerializableExtra(TO_PLAY_MUSIC_INFO);

        if (needLoadFromInt) {
            APIHelper.subscribeSimpleRequest(APIHelper.getMusicServices().getSongDetail(Integer.valueOf(mSong.id)), new CommonObserver<PlayListSong>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onNext(PlayListSong songs) {
                    if (songs != null && !songs.getData().isEmpty()) {
                        mSong.audio = songs.getData().get(0).getUrl();
                        mPlayControlCommand.queryServiceIsPlaying();
                    }
                }
            });
        } else {
            mPlayControlCommand.queryServiceIsPlaying();
        }
    }

    @Override
    public void initListener() {
        mPlayControlView.setOnControlItemClickListener(new MusicPlayControlView.MusicPlayerControlViewItemClickListener() {
            @Override
            public void onNextItemClick() {
                loadNewMusic();
                mPlayControlCommand.changeToNextMusic();
            }

            @Override
            public void onPreItemClick() {
                loadNewMusic();
                mPlayControlCommand.changeToPreMusic();
            }

            @Override
            public void onStartOrStartItemClick(boolean isPlay) {
                if (isPlay) {
                    BroadcastUtils.sendIntentAction(PlayMusicActivity.this, MusicServiceInstruction.SERVICE_RECEIVER_PLAY_MUSIC);
                } else {
                    BroadcastUtils.sendIntentAction(PlayMusicActivity.this, MusicServiceInstruction.SERVICE_RECEIVER_PAUSE_MUSIC);
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
                Intent intent = new Intent(MusicServiceInstruction.SERVICE_RECEIVER_SEEK_TO);
                intent.putExtra(MusicServiceInstruction.SERVICE_PARAM_SEEK_TO_POS, currentProgress);
                LocalBroadcastManager.getInstance(PlayMusicActivity.this).sendBroadcast(intent);
                if (!mPlayControlView.ismIsPlay()) {
                    mPlayControlView.setIsPlay(true);
                }
            }

            @Override
            public void onProgressChange(int currentProgress) {
                Intent intent = new Intent(MusicServiceInstruction.SERVICE_RECEIVER_SEEK_TO);
                intent.putExtra(MusicServiceInstruction.SERVICE_PARAM_SEEK_TO_POS, currentProgress);
                LocalBroadcastManager.getInstance(PlayMusicActivity.this).sendBroadcast(intent);
            }
        });

        mPovMusicPlayControl.setItemClickListener(new PlayOperatorView.OnItemClickListener() {
            @Override
            public void onCirclePlayItemClick(int mode) {
                if (mode == MusicServiceContract.PlayQueueControlPresenter.CIRCLE_MODE) {
                    mPlayModeCommand.startCirclePlayMode();
                } else {
                    mPlayModeCommand.startQueuePlayMode();
                }
            }

            @Override
            public void onRandomPlayItemClick(int mode) {
                if (mode == MusicServiceContract.PlayQueueControlPresenter.RANDOM_MODE) {
                    mPlayModeCommand.startRandomPlayMode();
                } else {
                    mPlayModeCommand.startQueuePlayMode();
                }
            }

            @Override
            public void onLikeItemClick(boolean like) {
                mSong.favorite = like;
                mPresenter.doLikeOrDisLikeMusic(mSong);
            }

            @Override
            public void onMusicListClick() {
                mDialog = new PlayControlDialog(PlayMusicActivity.this);
                mDialog.show();
                mPlayQueueCommand.getPlayQueue();
                mDialog.startLoadingAnimation();
            }
        });
    }

    @Override
    public void initData() {
//        loadLyric();
    }

    @Override
    public void preparedPlay(int duration) {
        if (isLoading) {
            mPlayControlView.endLoadingAnimationAndPlay();
            mPlayControlView.setIsPlay(true);
            isLoading = false;
        }

        mSeekBar.setCurrentProgress(0);
        mSeekBar.setHasBufferProgress(0);
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
        if (!isLoading) {
            mSeekBar.setMaxProgress(left);
            mSeekBar.setCurrentProgress(curPos);
            mTvPlayedTime.setText(TimeUtils.getDurationString(curPos, false));
            mTvLeftTime.setText(TimeUtils.getDurationString(left, true));
        }
    }

    @Override
    public void tryToChangeMusicByCurrentCondition(boolean playStatus, boolean needLoadMusic) {
        if (!mIsFromLittlePanel) {
            mPlayControlCommand.tryToChangePlayingMusic(mSong);             //ignore needLoadMusic --> must load
            return;
        }
        mPlayControlView.setIsPlay(playStatus);
    }

    @Override
    public void refreshSong(Song song) {
        mSong = song;
        mPovMusicPlayControl.refreshLikeStatus(mSong.favorite);
        if (!mIsFromLittlePanel) {   //may be change music
            loadNewMusic();
        }

        if (mSong.hasDown) {
            mSdvAlbym.setImageBitmap(AlbumUtils.parseAlbum(song.audio));
        } else {
            if (mSong.album.picUrl != null) {
                mSdvAlbym.setImageURI(mSong.album.picUrl);
            }
        }
        mTvLyric.setText(mSong.name);

        mPlayModeCommand.queryCurrentPlayMode();
    }

    @Override
    public void loadNewMusic() {
        mPlayControlCommand.pausePlay();
        mSeekBar.setCurrentProgress(0);
        isLoading = true;
        mPlayControlView.startLoadingAnimation();
    }

    @NonNull
    @Override
    public void setPlayQueue(List<Song> playQueue) {
        if (mDialog != null) {
            mDialog.addMusicQueue(playQueue);
            mDialog.stopLoadingAnimation();
        }
    }

    @Override
    public void refreshPlayMode(int mode) {
        mPovMusicPlayControl.refreshPlayMode(mode);
    }

    @Override
    public Context getViewContext() {
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
    public void refreshLikeStatus(boolean like) {
        mPovMusicPlayControl.refreshLikeStatus(like);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mClientReceiver.releaseResource();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SongDeleteFromPlayQueueEvent event) {
        Song song = event.song;
        mPlayQueueCommand.removeSongFromQueue(song);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ChangeSongEvent event) {
        Song song = event.song;
        mPlayQueueCommand.changeMusic(song);
    }

}
