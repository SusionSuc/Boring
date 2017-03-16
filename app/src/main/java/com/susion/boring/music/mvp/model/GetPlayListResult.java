package com.susion.boring.music.mvp.model;

import java.util.List;

/**
 * Created by susion on 17/2/23.
 */
public class GetPlayListResult {
    private List<PlayList> playlists;
    private String code;
    private boolean more;
    private int total;

    public List<PlayList> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<PlayList> playlists) {
        this.playlists = playlists;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
