package com.supercwn.player.siteapi;

import com.supercwn.player.model.SCFailLog;
//import com.express.movies.model.SCLiveStream;

/**
 * Created by fire3 on 14-12-29.
 */
public interface OnGetLiveStreamPlayUrlListener {
//    void onGetLiveStreamPlayUrlNormal(SCLiveStream v, String urlNormal);
//    void onGetLiveStreamPlayUrlHigh(SCLiveStream v, String urlHigh);
//    void onGetLiveStreamPlayUrlSuper(SCLiveStream v, String urlSuper);
    void onGetLiveStreamPlayUrlFailed(SCFailLog reason);
}
