package com.susion.boring.music.mvp.model;

import java.util.List;

/**
 * Created by susion on 17/1/20.
 */
public class MusicSearchResult{

    public int code;
    public SearchResult result;

    public class SearchResult{
        public int songCount;
        public List<Song> songs;
    }

}
