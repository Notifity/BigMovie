package com.supercwn.player.siteapi;

import com.supercwn.player.model.SCBanners;
import com.supercwn.player.model.SCFailLog;

/**
 * Created by fire3 on 15-1-28.
 */
public interface OnGetBannersListener {
    public void onGetBannersSuccess(SCBanners banners);
    public void onGetBannersFailed(SCFailLog failReason);
}
