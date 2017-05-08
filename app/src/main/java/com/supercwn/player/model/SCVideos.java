package com.supercwn.player.model;

import android.util.Log;

import java.util.ArrayList;

public class SCVideos extends ArrayList<SCVideo> {

    private static final String TAG = "SCVideos";

    public void debugLog() {
        for (SCVideo a : this) {
            Log.d(TAG, a.toString());
        }
    }

}
