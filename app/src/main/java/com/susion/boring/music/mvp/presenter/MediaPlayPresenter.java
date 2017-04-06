package com.susion.boring.music.mvp.presenter;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.susion.boring.music.mvp.contract.MediaPlayerContract;

import java.io.IOException;

/**
 * Created by susion on 17/1/25.
 * control MediaPlayer
 */
public class MediaPlayPresenter implements MediaPlayerContract.Presenter, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    private static final String TAG = MediaPlayPresenter.class.getSimpleName();

    private MediaPlayerContract.MediaPlayerRefreshView mView;
    private Context mContext;
    private MediaPlayer mPlayer;
    private Uri mSource;

    private Handler mUpdateProgressHandler;
    private static final int UPDATE_INTERVAL = 500;

    private boolean mIsPrepared;


    private final Runnable mProgressUpdateRun = new Runnable() {
        @Override
        public void run() {
            if (mUpdateProgressHandler == null || mPlayer == null || !mIsPrepared)
                return;

            int pos = mPlayer.getCurrentPosition();
            final int dur = mPlayer.getDuration();
            if (pos > dur) pos = dur;

            mView.updatePlayProgress(pos, dur - pos, dur);
            if (mUpdateProgressHandler != null)
                mUpdateProgressHandler.postDelayed(this, UPDATE_INTERVAL);
        }
    };


    public MediaPlayPresenter(MediaPlayerContract.MediaPlayerRefreshView mView, Context mContext) {
        this.mView = mView;
        this.mContext = mContext;
    }

    @Override
    public void initMediaPlayer(String mediaUri) throws Exception {
        if (TextUtils.isEmpty(mediaUri)) {
            return;
        }

        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnPreparedListener(this);
            mPlayer.setOnBufferingUpdateListener(this);
            mPlayer.setOnCompletionListener(this);
            mPlayer.setOnErrorListener(this);
            mPlayer.setLooping(false);  // after play finish not remain at start state, and will call OnCompletionListener
        }

        mPlayer.reset(); // reset for change music

        mSource = Uri.parse(mediaUri);
        setSourceForMultiType();
        mIsPrepared = false;
        mPlayer.prepareAsync();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.e(TAG, "onPrepared()");
        mIsPrepared = true;
        mView.preparedPlay(mPlayer.getDuration());
    }

    @Override
    public boolean startPlay() {
        if (!mIsPrepared) {
            return false;
        }

        if (mUpdateProgressHandler == null) {
            mUpdateProgressHandler = new Handler();
        }


        mPlayer.start();

        mUpdateProgressHandler.post(mProgressUpdateRun);
        return true;
    }

    @Override
    public void pausePlay() {
        if (mPlayer == null || !mPlayer.isPlaying()) {
            return;
        }

        mPlayer.pause();
        mUpdateProgressHandler.removeCallbacks(mProgressUpdateRun);
    }

    @Override
    public void stopPlay() {
        mIsPrepared = false;
        if (mPlayer == null) return;
        try {
            mPlayer.stop();
        } catch (Throwable ignored) {
        }
        if (mUpdateProgressHandler == null) return;
        mUpdateProgressHandler.removeCallbacks(mProgressUpdateRun);
    }

    @Override
    public void releaseResource() {
        mIsPrepared = false;
        if (mPlayer != null) {
            try {
                mPlayer.release();
            } catch (Throwable ignored) {
            }
            mPlayer = null;
        }
        if (mUpdateProgressHandler != null) {
            mUpdateProgressHandler.removeCallbacks(mProgressUpdateRun);
            mUpdateProgressHandler = null;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.e(TAG, "onCompletion()");
        if (mUpdateProgressHandler != null)
            mUpdateProgressHandler.removeCallbacks(mProgressUpdateRun);

        mView.completionPlay();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e(TAG, "onError() what=" + what + ", extra=" + extra);
        return false;
    }

    @Override
    public void seekTo(int pos) {
        if (mPlayer == null) return;
        mPlayer.seekTo(pos);
    }

    @Override
    public boolean isPrepared() {
        return mIsPrepared;
    }

    @Override
    public int getDuration() {
        if (mIsPrepared) {
            return mPlayer.getDuration();
        }
        return 0;
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public int getCurrentProgress() {
        return mPlayer.getCurrentPosition();
    }


    private void setSourceForMultiType() throws IOException {
        if (mSource.getScheme() != null &&
                (mSource.getScheme().equals("http") || mSource.getScheme().equals("https"))) {
            mPlayer.setDataSource(mSource.toString());
        } else if (mSource.getScheme() != null && (mSource.getScheme().equals("file") && mSource.getPath().contains("/android_assets/"))) {
            AssetFileDescriptor afd;
            afd = mContext.getAssets().openFd(mSource.toString().replace("file:///android_assets/", ""));
            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
        } else if (mSource.getScheme() != null && mSource.getScheme().equals("asset")) {
            AssetFileDescriptor afd;
            afd = mContext.getAssets().openFd(mSource.toString().replace("asset://", ""));
            mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
        } else {
            mPlayer.setDataSource(mContext, mSource);
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        mView.updateBufferedProgress(percent);
    }

}
