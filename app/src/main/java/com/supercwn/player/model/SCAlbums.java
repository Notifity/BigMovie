package com.supercwn.player.model;

import android.util.Log;

import java.util.ArrayList;

public class SCAlbums extends ArrayList<SCAlbum> {
    private static final String TAG = "SCAlbums";
    public void debugLog() {
        for (SCAlbum a : this) {
            Log.d(TAG, a.toString());
        }
    }
}
