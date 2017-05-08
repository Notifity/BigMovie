package com.supercwn.player.model;

import com.supercwn.player.SuperApplication;
import com.supercwn.player.R;

public class SCChannel {

    public static final int   UNKNOWN = -1;  //未知
    private static final int  MIN_CHANNEL_ID = 1;
//    public static final int   SHOW = 1;  //电视剧
//    public static final int   MOVIE = 2;  //电影
//    public static final int   COMIC = 3;  //动漫
//    public static final int   DOCUMENTARY = 4;  //纪录片
//    public static final int   MUSIC = 5;  //音乐
//    public static final int   VARIETY = 6;  //综艺
    public static final int   LOCAL_BOOKMARK = 2;
    public static final int   LOCAL_HISTORY = 3;
    public static final int   BIG_MOVIE = 1;
    private static final int  MAX_CHANNEL_ID = 3;  //如果增加频道，注意修改该值

    private String mChannelName = "unknown";

    private int mChannelID = UNKNOWN;

    public SCChannel(int mChannelID) {
        if(mChannelID >= BIG_MOVIE && mChannelID <= MAX_CHANNEL_ID)
            this.mChannelID = mChannelID;
        if(mChannelID == LOCAL_BOOKMARK)
            mChannelName = SuperApplication.getResource().getString(R.string.channel_bookmark);
        else if(mChannelID == LOCAL_HISTORY)
            mChannelName = SuperApplication.getResource().getString(R.string.channel_history);
        else if(mChannelID == BIG_MOVIE)
            mChannelName = SuperApplication.getResource().getString(R.string.channel_big_movies);
        else
            mChannelName = "unknown";

    }

    public String getChannelName() {
        return mChannelName;
    }

    public int getChannelID() {
        return mChannelID;
    }

    public void setChannelID(int mChannelID) {
        this.mChannelID = mChannelID;
    }

    @Override
    public String toString() {
        return mChannelName;
    }

    public static int getChannelCount () {
        return MAX_CHANNEL_ID;
    }

    public boolean isLocalChannel() {
        if(mChannelID == LOCAL_BOOKMARK
                || mChannelID == LOCAL_HISTORY
                )
            return true;
        else
            return false;
    }
}
