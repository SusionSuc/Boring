package com.susion.boring.utils;

import android.text.TextUtils;

import okhttp3.Response;

/**
 * Created by susion on 17/2/16.
 */
public class HttpUtils {

    public static final String HEAD_KEY_CONTENT_DISPOSITION = "Content-Disposition";

    /** 解析文件头 Content-Disposition:attachment;filename=FileName.txt */
    private static String getHeaderFileName(Response response) {
        String dispositionHeader = response.header(HEAD_KEY_CONTENT_DISPOSITION);
        if (dispositionHeader != null) {
            String split = "filename=";
            int indexOf = dispositionHeader.indexOf(split);
            if (indexOf != -1) {
                String fileName = dispositionHeader.substring(indexOf + split.length(), dispositionHeader.length());
                fileName = fileName.replaceAll("\"", "");   //文件名可能包含双引号,需要去除
                return fileName;
            }
        }
        return null;
    }

    /** 根据响应头或者url获取文件名 */
    public static String getNetFileName(Response response, String url) {
        String fileName = getHeaderFileName(response);
        if (TextUtils.isEmpty(fileName)) fileName = getUrlFileName(url);
        if (TextUtils.isEmpty(fileName)) fileName = "nofilename";
        return fileName;
    }


    /** 通过 ‘？’ 和 ‘/’ 判断文件名 */
    private static String getUrlFileName(String url) {
        int index = url.lastIndexOf('?');
        String filename;
        if (index > 1) {
            filename = url.substring(url.lastIndexOf('/') + 1, index);
        } else {
            filename = url.substring(url.lastIndexOf('/') + 1);
        }
        return filename;
    }

}
