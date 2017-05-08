package com.supercwn.player.siteapi;

import com.supercwn.player.model.SCAlbums;
import com.supercwn.player.model.SCFailLog;

/**
 * Created by fire3 on 2014/12/26.
 */
public interface OnGetAlbumsListener {
    public void onGetAlbumsSuccess(SCAlbums albums);
    public void onGetAlbumsFailed(SCFailLog failReason);
}
