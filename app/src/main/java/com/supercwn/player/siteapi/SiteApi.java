package com.supercwn.player.siteapi;

import android.content.Context;

import com.supercwn.player.R;
import com.supercwn.player.model.SCAlbum;
import com.supercwn.player.model.SCChannel;
import com.supercwn.player.model.SCChannelFilter;
import com.supercwn.player.model.SCSite;
import com.supercwn.player.model.SCVideo;
import com.supercwn.player.utils.HttpUtils;

/**
 * Created by fire3 on 14-12-30.
 */
public class SiteApi {


    public static void cancel() {
        HttpUtils.cancelAll();
    }

    public static void doSearch(int siteID, String key, OnGetAlbumsListener listener) {}

    public static void doSearchAll(String key, OnGetAlbumsListener listener) {}

    public static void doGetAlbumVideos(SCAlbum album, int pageNo, int pageSize,  OnGetVideosListener listener) {}

    public static void doGetVideoPlayUrl(SCVideo video, OnGetVideoPlayUrlListener listener) {}

    public static int getSupportSiteNumber() {return SCSite.count();}

    public static String getBigSiteName(int siteID, Context mContext) {return null;}

    public static void doGetChannelAlbumsByFilter(int siteID, int channelID, int pageNo, int pageSize, SCChannelFilter filter, OnGetAlbumsListener listener) {}

    public static void doGetChannelAlbums(int siteID, int channelID, int pageNo, int pageSize, OnGetAlbumsListener listener) {}

    public static void doGetChannelFilter(int siteID, int channelID, OnGetChannelFilterListener listener) {}

    public static String getSiteName(int siteID, Context mContext) {
        if(siteID == SCSite.B_HOT)
            return mContext.getResources().getString(R.string.site_hot);
        if(siteID == SCSite.B_TV)
            return mContext.getResources().getString(R.string.site_tv);
        if(siteID == SCSite.B_MOVIES)
            return mContext.getResources().getString(R.string.site_movies);
        return null;
    }

    public static void doGetAlbumDesc(SCAlbum album, OnGetAlbumDescListener listener) {
        if(album.getSite().getSiteID() == SCSite.B_HOT) //最热 hot
            new BigMovieApi().doGetAlbumDesc(album,listener);
        if(album.getSite().getSiteID() == SCSite.B_MOVIES) //电影 tvplay
            new BigMovieApi().doGetAlbumDesc(album,listener);
        if(album.getSite().getSiteID() == SCSite.B_TV) //电视剧 movie
            new BigMovieApi().doGetAlbumDesc(album,listener);
    }

    public static void doGetBigChannelAlbum(int siteID, int channelID, OnGetAlbumsListener listener) {
        if(siteID == SCSite.B_HOT) //最热 hot
            new BigMovieApi().doGetBigChannelAlbums(new SCChannel(channelID),"hot",siteID,listener);
        if(siteID == SCSite.B_MOVIES) //电影 tvplay
            new BigMovieApi().doGetBigChannelAlbums(new SCChannel(channelID),"movielist",siteID,listener);
        if(siteID == SCSite.B_TV) //电视剧 movie
            new BigMovieApi().doGetBigChannelAlbums(new SCChannel(channelID),"tvplaylist",siteID,listener);
    }

}
