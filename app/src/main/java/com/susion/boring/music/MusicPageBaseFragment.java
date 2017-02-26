package com.susion.boring.music;

import com.susion.boring.base.BaseFragment;
import com.susion.boring.music.model.Song;
import com.susion.boring.music.presenter.itf.MediaPlayerContract;

/**
 * Created by susion on 17/2/26.
 *
 * a decorator class for music page fragment
 */
public abstract class  MusicPageBaseFragment  extends BaseFragment implements MediaPlayerContract.PlayControlView{

    @Override
    public void setPlayDuration(int duration) {

    }

    @Override
    public void updatePlayProgressForSetMax(int curPos, int duration) {

    }

    @Override
    public void refreshPlayMode(int playmode) {

    }

    @Override
    public void preparedPlay(int duration) {

    }

    @Override
    public void completionPlay() {

    }

    @Override
    public void updateBufferedProgress(int percent) {

    }

    @Override
    public void updatePlayProgress(int curPos, int duration) {

    }

    @Override
    public void tryToChangeMusicByCurrentCondition(boolean playStatus, boolean needLoadMusic) {

    }

    @Override
    public void refreshSong(Song song) {

    }


}
