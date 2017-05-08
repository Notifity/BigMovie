package com.supercwn.player.siteapi;

import com.supercwn.player.model.SCFailLog;
//import com.express.movies.model.SCLiveStream;

/**
 * Created by fire3 on 15-3-9.
 */
public interface OnGetLiveStreamWeekDaysListener {
//    public void onGetLiveStreamWeekDaysSuccess(SCLiveStream stream);
    public void onGetLiveStreamWeekDaysFailed(SCFailLog failReason);
}
