package com.susion.boring.utils;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by susion on 17/2/18.
 */
public class FileUtils {

    public static final String SD_ROOT_DIR = Environment.getExternalStorageDirectory() + "/Boring/";
    public static final String SD_MUSIC_DIR = SD_ROOT_DIR + "music/";

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


    public static boolean saveFile(File file, String destDir) {
        try {
            FileInputStream in = new FileInputStream(file);
            FileOutputStream out = new FileOutputStream(destDir+file.getName());

            byte[] buff = new byte[1024];
            int len = 0;

            while ( (len = in.read(buff))!= -1) {
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



}
