package com.supercwn.player.siteapi;

import com.supercwn.player.model.SCFailLog;
//import com.express.movies.model.SCLiveStreams;

/**
 * Created by fire3 on 15-3-9.
 */
public interface OnGetLiveStreamsDescListener {
//    public void onGetLiveStreamsDescSuccess(SCLiveStreams streams);
    public void onGetLiveStreamsDescFailed(SCFailLog failReason);
}
