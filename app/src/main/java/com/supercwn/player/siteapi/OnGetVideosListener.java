package com.supercwn.player.siteapi;

import com.supercwn.player.model.SCFailLog;
import com.supercwn.player.model.SCVideos;

/**
 * Created by fire3 on 2014/12/26.
 */
public interface OnGetVideosListener {
    public void onGetVideosSuccess(SCVideos videos);
    public void onGetVideosFailed(SCFailLog failReason);
}
