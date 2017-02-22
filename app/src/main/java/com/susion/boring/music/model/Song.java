package com.susion.boring.music.model;

import com.susion.boring.db.model.SimpleSong;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by susion on 17/1/20.
 * model for request accept wang yi music API
 */
public class Song implements Serializable{
    public String id;
    public String name;
    public List<Singer> artists;
    public Album album;
    public String audio;
    public String djProgramId;
    public String page;

    public boolean fromLocalMusic;
    public boolean favorite;


//    Song song = new Song();
//    song.audio = mSong.getPath();
//    song.fromLocalMusic = true;
//    song.name = mSong.getDisplayName();
//    song.id = mSong.getId();
//    List<Singer> artist = new ArrayList<>();
//    artist.add(new Singer(mSong.getArtist()));
//    song.artists = artist;
//    song.favorite = mSong.isFavorite();
    public SimpleSong translateToSimpleSong(){
        SimpleSong simpleSong = new SimpleSong();
        simpleSong.setPath(audio);
        simpleSong.setDisplayName(name);
        simpleSong.setId(id);
        simpleSong.setArtist(artists.get(0).name);
        simpleSong.setFavorite(favorite);
        return simpleSong;
    }
}
