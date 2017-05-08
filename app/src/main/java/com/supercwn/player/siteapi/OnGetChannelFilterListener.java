package com.supercwn.player.siteapi;

import com.supercwn.player.model.SCChannelFilter;
import com.supercwn.player.model.SCFailLog;

/**
 * Created by fire3 on 2014/12/26.
 */
public interface OnGetChannelFilterListener {
    public void onGetChannelFilterSuccess(SCChannelFilter filter);
    public void onGetChannelFilterFailed(SCFailLog failReason);
}
