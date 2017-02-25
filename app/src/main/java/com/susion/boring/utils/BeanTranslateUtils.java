package com.susion.boring.utils;

import com.susion.boring.db.model.SimpleSong;
import com.susion.boring.music.model.PlayListDetail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by susion on 17/2/25.
 */
public class BeanTranslateUtils {
    public static List<SimpleSong> translateTracksToSimpleSong(List<PlayListDetail.PlaylistBean.TracksBean> tracks) {
        List<SimpleSong> datas = new ArrayList<>();
        for(PlayListDetail.PlaylistBean.TracksBean track : tracks) {
            datas.add(track.translateToSimpleSong());
        }
        return datas;
    }
}
