package com.supercwn.player.model;

import com.supercwn.player.SuperApplication;
import com.supercwn.player.R;

public class SCSite {
    public static int UNKNOWN = -1;
    public static int B_HOT = 0;
    public static int B_TV = 1;
    public static int B_MOVIES = 2;

    private String mSiteName;

    private int mSiteID;

    public SCSite(int mSiteID) {
        this.mSiteID = mSiteID;
        if(mSiteID == B_HOT)
            mSiteName = SuperApplication.getResource().getString(R.string.site_hot);
        if(mSiteID == B_TV)
            mSiteName = SuperApplication.getResource().getString(R.string.site_tv);
        if(mSiteID == B_MOVIES)
            mSiteName = SuperApplication.getResource().getString(R.string.site_movies);
        if(mSiteID == UNKNOWN)
            mSiteName = "Unknown";
    }

    public int getSiteID() {
        return mSiteID;
    }
    @Override
    public String toString() {
        return mSiteName;
    }

    public String getSiteName() {
        return mSiteName;
    }

    //增加网站时需要增加该count
    public static int count() {
        return 3;
    }
}
