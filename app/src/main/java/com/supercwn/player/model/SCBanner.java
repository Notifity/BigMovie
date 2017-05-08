package com.supercwn.player.model;

public class SCBanner {
    int type;
    String title;
    SCAlbums albums;

    public final static int TYPE_SLIDER = 1;
    public final static int TYPE_TABLE = 2;

    public SCBanner(int type, String title, SCAlbums albums) {
        this.type = type;
        this.title = title;
        this.albums = albums;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public SCAlbums getAlbums() {
        return albums;
    }
}
