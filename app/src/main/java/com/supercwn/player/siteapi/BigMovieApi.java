package com.supercwn.player.siteapi;
import android.util.Log;

import com.supercwn.player.SuperApplication;
import com.supercwn.player.model.SCAlbum;
import com.supercwn.player.model.SCAlbums;
import com.supercwn.player.model.SCChannel;
import com.supercwn.player.model.SCChannelFilter;
import com.supercwn.player.model.SCChannelFilterItem;
import com.supercwn.player.model.SCFailLog;
import com.supercwn.player.model.SCVideo;
import com.supercwn.player.model.SCVideos;
import com.supercwn.player.model.show.DetailInfo;
import com.supercwn.player.utils.HttpUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class BigMovieApi extends BaseSiteApi {
    private static final String BIG_MOVIES = "http://www.lajiaovod.com:22435/index.php"; //大电影
    private static final String HOME_API = "/api/index"; //首页
    private int CID_KEYS = 0;
    private static final String TAG = "BigMovies";
    private SCAlbums albums = new SCAlbums();

    private SCFailLog makeHttpFailLog(String url, String functionName) {
        SCFailLog err = new SCFailLog(CID_KEYS,SCFailLog.TYPE_HTTP_FAILURE);
        err.setFunctionName(functionName);
        err.setClassName("BIG_MOVIES");
        err.setTag(TAG);
        err.setUrl(url);
        return err;
    }

    private SCFailLog makeHttpFailLog(String url, String functionName, Exception e) {
        SCFailLog err = new SCFailLog(CID_KEYS,SCFailLog.TYPE_HTTP_FAILURE);
        err.setException(e);
        err.setFunctionName(functionName);
        err.setClassName("BIG_MOVIES");
        err.setTag(TAG);
        err.setUrl(url);
        return err;
    }

    private SCFailLog makeFatalFailLog(String url, String functionName, Exception e) {
        SCFailLog err = new SCFailLog(CID_KEYS,SCFailLog.TYPE_FATAL_ERR);
        err.setException(e);
        err.setFunctionName(functionName);
        err.setClassName("BIG_MOVIES");
        err.setTag(TAG);
        err.setUrl(url);
        return err;
    }

    private SCFailLog makeNoResultFailLog(String url, String functionName) {
        SCFailLog err = new SCFailLog(CID_KEYS,SCFailLog.TYPE_NO_RESULT);
        err.setFunctionName(functionName);
        err.setClassName("BIG_MOVIES");
        err.setTag(TAG);
        err.setUrl(url);
        return err;
    }

    private String getShowInfoUrl(String showID,int site) {
        String url,albumId,playType;
        if(showID == null || showID.equals("")){
            return null;
        }else {
            Log.d("ShowInfoUrl__showID:",showID);
            albumId = showID.split("/")[0];
            Log.d("ShowInfoUrl__albumId:",albumId);
            playType = showID.split("/")[1];
            Log.d("ShowInfoUrl__playType:",playType);
            if(site == 0) //最新详情
                if(playType.equals("tv"))
                    url = BIG_MOVIES + "/api/tvplayinfo/" + albumId;
                else
                    url = BIG_MOVIES + "/api/movieinfo/" + albumId;
            else if (site == 1) //电视剧详情
                url = BIG_MOVIES + "/api/tvplayinfo/" + albumId;
            else //电影详情
                url = BIG_MOVIES + "/api/movieinfo/" + albumId;
            Log.d("ShowInfoUrl__url:",url);
            return url;
        }
    }

    @Override
    public void doGetAlbumDesc(final SCAlbum album, final OnGetAlbumDescListener listener) {
        final String url = getShowInfoUrl(album.getAlbumId(),album.getSite().getSiteID());
        Log.i("doGetAlbumDesc_SiteID:",String.valueOf(album.getSite().getSiteID()));
        Log.i("doGetAlbumDesc_AlbumId:",String.valueOf(album.getAlbumId()));
        Log.i("doGetAlbumDesc_url:",url);
        if(url == null) {
            SCFailLog err = new SCFailLog(CID_KEYS,SCFailLog.TYPE_URL_ERR);
            err.setFunctionName("doGetAlbumDesc");
            err.setReason(album.toJson());
            if(listener != null)
                listener.onGetAlbumDescFailed(err);
        }
        HttpUtils.asyncGet(url,new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                SCFailLog err = makeHttpFailLog(url,"doGetAlbumDesc",e);
                if(listener != null)
                    listener.onGetAlbumDescFailed(err);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    SCFailLog err = makeHttpFailLog(url,"doGetAlbumDesc");
                    if(listener != null)
                        listener.onGetAlbumDescFailed(err);
                    return;
                }
                try {
                    String ret = response.body().string();
                    Log.d("AlbumDes_onResponse:",ret);
                    SCVideos  mVideos = new SCVideos();
                    if(ret!=null){
                        DetailInfo showInfo = SuperApplication.getGson().fromJson(ret,DetailInfo.class);
                        album.setTitle(showInfo.getTitle());
                        Log.d("AlbumDes__info:",showInfo.getInfo());
                        album.setDesc(showInfo.getInfo());
                        Log.d("AlbumDes__img:",showInfo.getImg());
                        album.setVerImageUrl(showInfo.getImg());
                        if(showInfo.getActor() != null) {
                            Log.d("AlbumDes__Actor:",showInfo.getActor());
                            String actors = showInfo.getActor();
                            album.setMainActor(actors);
                        }
                        if(showInfo.getDirector() != null) {
                            Log.d("AlbumDes__Director:",showInfo.getDirector());
                            String directors = showInfo.getDirector();
                            album.setDirector(directors);
                        }
                        if(showInfo.getArea() != null) {
                            Log.d("AlbumDes__Area:",showInfo.getArea());
                            String area = showInfo.getArea();
                            album.setSubTitle(area);
                        }
                        if(showInfo.getType() != null) {
                            Log.d("AlbumDes__Type:",showInfo.getType());
                            String type = showInfo.getType();
                            album.setTip(type);
                        }
                        if(showInfo.getList().size() > 0) {
                            Log.e("AlbumDes__List:",String.valueOf(showInfo.getList().size()));
                            album.setVideosTotal(showInfo.getList().size());
//                            album.setHorImageUrl(showInfo.getList().get(0).getUrl());
                            if (showInfo.getList().size() == 1){
                                SCVideo mVideo = new SCVideo();
                                mVideo.setVideoTitle(showInfo.getList().get(0).getTitle());
                                mVideo.setM3U8Nor(showInfo.getList().get(0).getUrl());
                                mVideos.add(mVideo);
//                                album.setHorImageUrl(showInfo.getList().get(0).getUrl());
                            }else if(showInfo.getList().size() > 1){
                                for (int i = 0; i < showInfo.getList().size(); i++){
                                    SCVideo mVideo = new SCVideo();
                                    mVideo.setVideoTitle(showInfo.getList().get(i).getTitle());
                                    mVideo.setM3U8Nor(showInfo.getList().get(i).getUrl());
                                    mVideos.add(mVideo);
                                    Log.e("tvPlays__title:",showInfo.getList().get(i).getTitle());
                                    Log.e("tvPlays__url:",showInfo.getList().get(i).getUrl());
                                }
                            }
                        }
                    }
                    if(album != null ) {
                        if(listener != null)
                            listener.onGetAlbumDescSuccess(album,mVideos);
                    }
                } catch (IOException e) {
                    SCFailLog err = makeHttpFailLog(url,"doGetAlbumDesc",e);
                    if(listener != null)
                        listener.onGetAlbumDescFailed(err);
                    e.printStackTrace();
                }
            }
        });
    }

    private  void doGetChannelAlbumsByUrl(final String url, final SCChannel channel, final String key, final OnGetAlbumsListener listener) {
        HttpUtils.asyncGet(url, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                SCFailLog err = makeHttpFailLog(url,"doGetChannelAlbumsByUrl",e);
                if(listener != null)
                    listener.onGetAlbumsFailed(err);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String ret = response.body().string();
                parseAlbumBigListResult(ret,key);
                if(albums != null && albums.size() > 0) {
                    if(listener != null)
                        listener.onGetAlbumsSuccess(albums);
                } else {
                    if(listener != null) {
                        SCFailLog err = makeNoResultFailLog(url, "doGetChannelAlbumsByUrl");
                        err.setReason(ret);
                        listener.onGetAlbumsFailed(err);
                    }
                }
            }
        });
    }

    @Override
    public void doGetBigChannelAlbums(SCChannel channel, String key, int keys, OnGetAlbumsListener listener) {
        String url;
        if (CID_KEYS == 0)
            CID_KEYS = keys;
        if (key.equals("hot"))
            url = BIG_MOVIES + HOME_API + "/" + key;
        else
            url = BIG_MOVIES + "/api/" + key;
        doGetChannelAlbumsByUrl(url,channel,key,listener);
    }

    private void AlbumBigList(JSONArray results,String keymark){
        String tmp;
        if (albums == null)
            albums = new SCAlbums();
        try {
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                SCAlbum album = new SCAlbum(CID_KEYS);
                tmp = result.optString("id");
                if (tmp != null && !tmp.isEmpty())
                    if(keymark.equals("all"))
                        if(CID_KEYS == 1)
                            tmp = tmp + "/tv";
                        else if(CID_KEYS == 2)
                            tmp = tmp + "/movie";
                album.setAlbumId(tmp);
                Log.e("AlbumBigList__id",tmp);

                tmp = result.optString("title");
                if (tmp != null && !tmp.isEmpty())
                    album.setTitle(tmp);
                Log.d("AlbumBigList__title",tmp);

                tmp = result.optString("img");
                if (tmp != null && !tmp.isEmpty()) {
                    album.setVerImageUrl(tmp);
                }

                albums.add(album);
            }
            Log.e("SCAlbum________toString",albums.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void parseAlbumBigListResult(String ret,String key){
        JSONArray results1 = new JSONArray();
        try {
            JSONObject retJson = new JSONObject(ret);
            if(key.equals("hot")){
                Iterator it = retJson.keys();
                while (it.hasNext()) {
                    String marks = (String) it.next();
                    JSONArray newArray = retJson.getJSONArray(marks);
                    for (int i=0;i < newArray.length();i++){
                        JSONObject json = newArray.getJSONObject(i);
                        if(marks.equals("movies")){
                            json.put("id",json.getString("id") + "/movie");
                        }else {
                            json.put("id",json.getString("id") + "/tv");
                        }
                        json.put("title",json.getString("title"));
                        json.put("img",json.getString("img"));
                        results1.put(json);
                    }
                }

            }else {
                results1 = retJson.optJSONArray("alllist");
            }
            Log.e("results1__",results1.toString());
            if(results1 != null){
                if(results1.length() == 0){
                    return;
                }
                if(CID_KEYS == 0)
                    AlbumBigList(results1,"hot_all");
                else
                    AlbumBigList(results1,"all");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGetChannelFilter(SCChannel channel, final OnGetChannelFilterListener listener) {
        final String url = "";
        if(url == null) {
            SCFailLog err = new SCFailLog(CID_KEYS,SCFailLog.TYPE_URL_ERR);
            err.setFunctionName("doGetChannelFilter");
            err.setReason(channel.getChannelName());
            if(listener != null)
                listener.onGetChannelFilterFailed(err);
            return;
        }
        HttpUtils.asyncGet(url, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if(listener != null) {
                    SCFailLog err = makeHttpFailLog(url,"doGetChannelFilter",e);
                    listener.onGetChannelFilterFailed(err);
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String ret = response.body().string();
                try {
                    JSONObject retJson = new JSONObject(ret);
                    JSONObject resultsJson = retJson.optJSONObject("results");
                    if(resultsJson != null) {
                        JSONArray filterJsonArray = resultsJson.optJSONArray("filter");
                        if(filterJsonArray != null && filterJsonArray.length() > 0) {
                            SCChannelFilter filter = new SCChannelFilter();
                            for (int i = 0; i < filterJsonArray.length(); i++) {
                                JSONObject filterJson = filterJsonArray.getJSONObject(i);
                                JSONArray filterItemsJson = filterJson.optJSONArray("items");
                                String key = filterJson.optString("cat");
                                ArrayList<SCChannelFilterItem> filterItems = new ArrayList<SCChannelFilterItem>();
                                for (int j = 0; j < filterItemsJson.length(); j++) {
                                    JSONObject filterItemJson = filterItemsJson.getJSONObject(j);
                                    String searchVal = filterItemJson.optString("value");
                                    String displayName = filterItemJson.optString("title");
                                    if(searchVal == null)
                                        searchVal = "";
                                    SCChannelFilterItem filterItem = new SCChannelFilterItem(searchVal,displayName);
                                    filterItem.setParentKey(key);
                                    filterItem.setSearchKey(key);
                                    filterItems.add(filterItem);
                                }
                                filter.addFilter(key,filterItems);
                            }
                            if(listener != null)
                                listener.onGetChannelFilterSuccess(filter);
                        }
                    }
                } catch (Exception e) {
                    if(listener != null) {
                        SCFailLog err = makeFatalFailLog(url, "doGetChannelFilter", e);
                        listener.onGetChannelFilterFailed(err);
                    }
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void doGetAlbumVideos(final SCAlbum album, int pageNo, int pageSize, final OnGetVideosListener listener) {}

    @Override
    public void doSearch(String key, final OnGetAlbumsListener listener) {}

    @Override
    public void doGetVideoPlayUrl(final SCVideo video, final OnGetVideoPlayUrlListener listener) {}

    @Override
    public void doGetChannelAlbums(SCChannel channel, int pageNo, int pageSize, final OnGetAlbumsListener listener) {}

    @Override
    public void doGetChannelAlbumsByFilter(SCChannel channel, int pageNo, int pageSize, SCChannelFilter filter, OnGetAlbumsListener listener) {}

}
