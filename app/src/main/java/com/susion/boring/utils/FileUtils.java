package com.susion.boring.utils;

import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.text.TextUtils;

import com.susion.boring.music.mvp.model.SimpleSong;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by susion on 17/2/18.
 */
public class FileUtils {

    public static final String SD_ROOT_DIR = Environment.getExternalStorageDirectory() + "/Boring/";
    public static final String SD_MUSIC_DIR = SD_ROOT_DIR + "music/";
    public static final String SD_CACHE_DIR = SD_ROOT_DIR + "cache/";
    public static final String SD_INTERESTING_DIR = SD_ROOT_DIR + "interesting/";
    public static final String SD_INTERESTING_DAILY_NEWS = SD_INTERESTING_DIR + "daily_news/";
    public static final String SD_INTERESTING_PICTURE = SD_INTERESTING_DIR + "picture/";

    private static final String UNKNOWN = "unknown";
    public static final String SD_TEMP_CSS = SD_INTERESTING_DAILY_NEWS + "css/";
    public static final String TEMP_CSS_FILE = SD_TEMP_CSS + "temp_css.css";

    public static void initAppDir() {
        if (!Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return;
        }

        String[] initPath = new String[]{SD_MUSIC_DIR, SD_INTERESTING_DIR, SD_INTERESTING_DAILY_NEWS, TEMP_CSS_FILE, SD_INTERESTING_PICTURE};
        for (String path : initPath) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
    }

    public static boolean saveFile(File file, String destDir) {
        try {
            FileInputStream in = new FileInputStream(file);
            FileOutputStream out = new FileOutputStream(destDir + file.getName());

            byte[] buff = new byte[1024];
            int len = 0;

            while ((len = in.read(buff)) != -1) {
                out.write(buff, 0, len);
            }

            in.close();
            out.close();
            return true;

        } catch (IOException e) {
            e.printStackTrace();

        }
        return false;
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

    public static boolean saveFile(InputStream in, File destFile) {
        try {

            if (!destFile.exists()) {
                destFile.createNewFile();
            }

            FileOutputStream out = new FileOutputStream(destFile);
            byte[] buff = new byte[1024];
            int len;

            while ((len = in.read(buff)) != -1) {
                out.write(buff, 0, len);
            }

            in.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static File getCacheDir() {
        return createFile(SD_CACHE_DIR, "");
    }

    public static File createFile(String folderPath, String fileName) {
        File destDir = new File(folderPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return new File(folderPath, fileName);
    }

    public static boolean saveImage(InputStream inputStream, String imageIdentifier) {
        File image = new File(SD_INTERESTING_PICTURE + Md5Utils.md5(imageIdentifier) + ".png");
        return saveFile(inputStream, image);
    }

    public static String getTempCssString() {
        try {
            return getStringFromFile(TEMP_CSS_FILE);
        } catch (Exception e) {

        }
        return null;
    }

    private static String getStringFromFile(String tempCssFile) throws FileNotFoundException {
        FileInputStream in = new FileInputStream(new File(tempCssFile));
        return getStringFromInputStream(in);
    }

    public static String getStringFromInputStream(InputStream in) {
        ByteArrayOutputStream byteOS = null;
        try {

            byteOS = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int len;
            while ((len = in.read(buff)) != -1) {
                byteOS.write(buff, 0, len);
            }

        } catch (Exception e) {

        }

        if (byteOS == null) {
            return null;
        }

        return byteOS.toString();
    }
}
