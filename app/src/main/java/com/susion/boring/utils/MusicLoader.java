package com.susion.boring.utils;

/**
 * Created by susion on 17/2/11.
 */

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Audio.Media;

import java.util.ArrayList;
import java.util.List;

public class MusicLoader {

    private static List<MusicInfo> musicList = new ArrayList<>();
    private static MusicLoader musicLoader;
    private static ContentResolver contentResolver;

    //Uri，指向external的database
    private Uri contentUri = Media.EXTERNAL_CONTENT_URI;
    private String[] projection = {
            Media._ID,
            Media.DISPLAY_NAME,
            Media.DATA,
            Media.ALBUM,
            Media.ARTIST,
            Media.DURATION,
            Media.SIZE
    };
    private String where = "mime_type in ('audio/mpeg','audio/x-ms-wma') and bucket_display_name <> 'audio' and is_music > 0 " ;
    private String sortOrder = Media.DATA;

    public static MusicLoader getInstance(ContentResolver pContentResolver){
        if(musicLoader == null){
            contentResolver = pContentResolver;
            musicLoader = new MusicLoader();
        }
        return musicLoader;
    }

    private MusicLoader(){

    }

    private void loadMusic(){                                                                                                             //利用ContentResolver的query函数来查询数据，然后将得到的结果放到MusicInfo对象中，最后放到数组中
        Cursor cursor = contentResolver.query(contentUri, null, null, null, null);
        if(cursor == null){

        }else if(!cursor.moveToFirst()){

        }else{
            int displayNameCol = cursor.getColumnIndex(Media.DISPLAY_NAME);
            int albumCol = cursor.getColumnIndex(Media.ALBUM);
            int idCol = cursor.getColumnIndex(Media._ID);
            int durationCol = cursor.getColumnIndex(Media.DURATION);
            int sizeCol = cursor.getColumnIndex(Media.SIZE);
            int artistCol = cursor.getColumnIndex(Media.ARTIST);
            int urlCol = cursor.getColumnIndex(Media.DATA);
            do{
                String title = cursor.getString(displayNameCol);
                String album = cursor.getString(albumCol);
                long id = cursor.getLong(idCol);
                int duration = cursor.getInt(durationCol);
                long size = cursor.getLong(sizeCol);
                String artist = cursor.getString(artistCol);
                String url = cursor.getString(urlCol);

                MusicInfo musicInfo = new MusicInfo(id, title);
                musicInfo.album = album;
                musicInfo.duration = duration;
                musicInfo.size = size;
                musicInfo.artist = artist;
                musicInfo.url = url;
                musicList.add(musicInfo);
            }while(cursor.moveToNext());
        }
    }

    public List<MusicInfo> getMusicList(){
        if (musicList.isEmpty()) {
            loadMusic();
        }
        return musicList;
    }

    public Uri getMusicUriById(long id){
        Uri uri = ContentUris.withAppendedId(contentUri, id);
        return uri;
    }

    public static class MusicInfo{
        public long id;
        public String title;
        public String album;
        public int duration;
        public long size;
        public String artist;
        public String url;

        public MusicInfo(){

        }

        public MusicInfo(long pId, String pTitle){
            id = pId;
            title = pTitle;
        }

    }
}
