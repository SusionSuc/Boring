package com.susion.boring.music.mvp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.annotation.Unique;
import com.litesuits.orm.db.enums.AssignType;
import com.susion.boring.base.entity.FavoriteMark;

import java.util.ArrayList;
import java.util.List;

/**
 * store in local database
 */
@Table("simple_song")
public class SimpleSong extends FavoriteMark implements Parcelable, PlayQueueSong {

    @PrimaryKey(AssignType.BY_MYSELF)
    public String id;
    private String title;
    private String displayName;
    private String artist;
    private String album;

    @Unique
    private String path;

    private int duration;
    private int size;
    private boolean hasDown;
    private String picPath;  //url for :  no store in play, play online

    private boolean fromPlayList;

    public SimpleSong() {
        // Empty
    }

    public Song translateToSong() {
        Song song = new Song();
        song.audio = path;
        song.name = displayName;
        song.id = id;
        List<Singer> artist = new ArrayList<>();
        artist.add(new Singer(getArtist()));
        song.artists = artist;
        song.favorite = favorite;
        song.hasDown = hasDown;

        song.album = new Album(album, picPath);

        return song;
    }


    public boolean isFromPlayList() {
        return fromPlayList;
    }

    public void setFromPlayList(boolean fromPlayList) {
        this.fromPlayList = fromPlayList;
    }

    public SimpleSong(Parcel in) {
        readFromParcel(in);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isHasDown() {
        return hasDown;
    }

    public void setHasDown(boolean hasDown) {
        this.hasDown = hasDown;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.displayName);
        dest.writeString(this.artist);
        dest.writeString(this.album);
        dest.writeString(this.path);
        dest.writeInt(this.duration);
        dest.writeInt(this.size);
        dest.writeInt(this.favorite ? 1 : 0);
    }

    public void readFromParcel(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.displayName = in.readString();
        this.artist = in.readString();
        this.album = in.readString();
        this.path = in.readString();
        this.duration = in.readInt();
        this.size = in.readInt();
        this.favorite = in.readInt() == 1;
    }

    public static final Creator<SimpleSong> CREATOR = new Creator<SimpleSong>() {
        @Override
        public SimpleSong createFromParcel(Parcel source) {
            return new SimpleSong(source);
        }

        @Override
        public SimpleSong[] newArray(int size) {
            return new SimpleSong[size];
        }
    };
}
