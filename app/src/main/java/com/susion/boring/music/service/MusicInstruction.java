package com.susion.boring.music.service;

/**
 * Created by susion on 17/2/13.
 */
public class MusicInstruction {

    /*后台播放操作指令*/
    public static final String BR_ACTION_MUSIC_PLAY = "BR_ACTION_MUSIC_PLAY";
    public static final String BR_ACTION_MUSIC_PAUSE = "BR_ACTION_MUSIC_PAUSE";
    public static final String BR_ACTION_MUSIC_NEXT = "BR_ACTION_MUSIC_NEXT";
    public static final String BR_ACTION_MUSIC_LAST = "BR_ACTION_MUSIC_LAST";
    public static final String BR_ACTION_MUSIC_SEEK_TO = "BR_ACTION_MUSIC_SEEK_TO";


    /*后台当前状态*/
    public static final String SERVICE_BR_STATUS_PLAY = "SERVICE_BR_STATUS_PLAY";
    public static final String SERVICE_BR_STATUS_PAUSE = "SERVICE_BR_STATUS_PAUSE";
    public static final String SERVICE_BR_STATUS_COMPLETE = "SERVICE_BR_STATUS_COMPLETE";
    public static final String SERVICE_BR_STATUS_DURATION = "SERVICE_BR_STATUS_DURATION";


    /*前后台的状体同步*/
    public static final String SYN_PARAM_DURATION = "SYN_PARAM_DURATION";
    public static final String SYN_PARAM_SEEK_TO = "SYN_PARAM_SEEK_TO";
    public static final String SYN_PARAM_CURRENT_POSITION = "SYN_PARAM_CURRENT_POSITION";
    public static final String SYN_PARAM_IS_OVER = "SYN_PARAM_IS_OVER";


}
