package com.supercwn.player.siteapi;

import com.supercwn.player.model.SCFailLog;
//import com.express.movies.model.SCLiveStream;
//import com.express.movies.model.SCLiveStreamPrograms;

/**
 * Created by fire3 on 15-3-11.
 */
public interface OnGetLiveStreamProgramsListener {
//    void onGetLiveStreamProgramsSuccess(SCLiveStream stream, SCLiveStreamPrograms programs);
    void onGetLiveStreamProgramsFailed(SCFailLog reason);
}
