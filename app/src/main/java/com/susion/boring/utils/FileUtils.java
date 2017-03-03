package com.susion.boring.utils;

import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.text.TextUtils;

import com.susion.boring.db.model.SimpleSong;
import com.susion.boring.music.model.Album;
import com.susion.boring.music.model.Song;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by susion on 17/2/18.
 */
public class FileUtils {

    public static final String SD_ROOT_DIR = Environment.getExternalStorageDirectory() + "/Boring/";
    public static final String SD_MUSIC_DIR = SD_ROOT_DIR + "music/";
    private static final String UNKNOWN = "unknown";

    public static void initAppDir() {
        // 不存在SD卡
        if (!Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return;
        }

        String path = SD_MUSIC_DIR;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

//    public static boolean saveFile(File file, String destDir) {
//        try {
//            FileInputStream in = new FileInputStream(file);
//            FileOutputStream out = new FileOutputStream(destDir+file.getName());
//
//            byte[] buff = new byte[1024];
//            int len = 0;
//
//            while ( (len = in.read(buff))!= -1) {
//                out.write(buff, 0, len);
//            }
//
//            in.close();
//            out.close();
//            return true;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//
//        }
//
//        return false;
//    }


    public static List<Song> scanLocalMusic() {
        return null;
    }

    public static SimpleSong fileToMusic(File file) {
        if (file.length() == 0) return null;

        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(file.getAbsolutePath());

        final int duration;

        String keyDuration = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        // ensure the duration is a digit, otherwise return null song
        if (keyDuration == null || !keyDuration.matches("\\d+")) return null;
        duration = Integer.parseInt(keyDuration);

        final String title = extractMetadata(metadataRetriever, MediaMetadataRetriever.METADATA_KEY_TITLE, file.getName());
        final String displayName = extractMetadata(metadataRetriever, MediaMetadataRetriever.METADATA_KEY_TITLE, file.getName());
        final String artist = extractMetadata(metadataRetriever, MediaMetadataRetriever.METADATA_KEY_ARTIST, UNKNOWN);
        final String album = extractMetadata(metadataRetriever, MediaMetadataRetriever.METADATA_KEY_ALBUM, UNKNOWN);

        final SimpleSong song = new SimpleSong();
        song.setTitle(title);
        song.setDisplayName(displayName);
        song.setArtist(artist);
        song.setAlbum(album);
        song.setDuration(duration);
        song.setPath(file.getPath());
        return song;
    }

    private static String extractMetadata(MediaMetadataRetriever retriever, int key, String defaultValue) {
        String value = retriever.extractMetadata(key);
        if (TextUtils.isEmpty(value)) {
            value = defaultValue;
        }
        return value;
    }
}
