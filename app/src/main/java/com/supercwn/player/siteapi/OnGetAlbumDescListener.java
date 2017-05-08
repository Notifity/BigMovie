package com.supercwn.player.siteapi;

import com.supercwn.player.model.SCAlbum;
import com.supercwn.player.model.SCFailLog;
import com.supercwn.player.model.SCVideos;

/**
 * Created by fire3 on 2014/12/27.
 */
public interface OnGetAlbumDescListener {
    public void onGetAlbumDescSuccess(SCAlbum album,SCVideos scVideos);
    public void onGetAlbumDescFailed(SCFailLog failReason);
}
