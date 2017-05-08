package com.supercwn.player;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.supercwn.player.fragments.AlbumPlayGridFragment;
import com.supercwn.player.database.BookmarkDbHelper;
import com.supercwn.player.database.HistoryDbHelper;
import com.supercwn.player.model.SCAlbum;
import com.supercwn.player.model.SCFailLog;
import com.supercwn.player.model.SCVideo;
import com.supercwn.player.model.SCVideos;
import com.supercwn.player.siteapi.OnGetAlbumDescListener;
import com.supercwn.player.siteapi.SiteApi;
import com.supercwn.player.utils.ImageTools;
import com.superplayer.library.SuperPlayer;
import static com.supercwn.player.R.id.error_message;

public class AlbumDetailActivity extends AppCompatActivity implements View.OnClickListener,
        OnGetAlbumDescListener, AlbumPlayGridFragment.OnAlbumPlayGridListener,SuperPlayer.OnNetChangeListener {

    private SCAlbum mAlbum;
    private SCVideos mVideos;
    private int mVideoNo = -1;
    private int mInitialVideoNoInAlbum = 0;
    private boolean mIsShowTitle = false;
    private boolean mIsBackward = false;
    private BookmarkDbHelper mBookmarkDb;
    private HistoryDbHelper mHistoryDb;
    private boolean mIsFav;
    private AlbumPlayGridFragment mFragment;
    private SuperPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
//        fullScreen(true);
        setContentView(R.layout.activity_album_detail);
        mAlbum = getIntent().getParcelableExtra("album");
        Log.d("Detail___onCreate:",mAlbum.getTitle());
        mInitialVideoNoInAlbum = getIntent().getIntExtra("videoNo", 0);
        mIsShowTitle = getIntent().getBooleanExtra("showTitle", false);
        setTitle(mAlbum.getTitle());
        mBookmarkDb = new BookmarkDbHelper(this);
        mHistoryDb = new HistoryDbHelper(this);
        mIsFav = mBookmarkDb.getAlbumById(mAlbum.getAlbumId(), mAlbum.getSite().getSiteID()) != null;
        SiteApi.doGetAlbumDesc(mAlbum, this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

//    public void fullScreen(boolean enable){
//        if(enable){
//            WindowManager.LayoutParams lp = getWindow().getAttributes();
//            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
//            getWindow().setAttributes(lp);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }else {
//            WindowManager.LayoutParams attr = getWindow().getAttributes();
//            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
//            getWindow().setAttributes(attr);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }
//    }

    public static void launch(Activity activity, SCAlbum album) {
        Intent mpdIntent = new Intent(activity, AlbumDetailActivity.class)
                .putExtra("album", album);
        activity.startActivity(mpdIntent);
    }

    public static void launch(Activity activity, SCAlbum album,int videoNo) {
        Intent mpdIntent = new Intent(activity, AlbumDetailActivity.class)
                .putExtra("videoNo", videoNo)
                .putExtra("album", album);
        activity.startActivity(mpdIntent);
    }

    public static void launch(Activity activity, SCAlbum album, int videoNo, boolean mIsShowTitle) {
        Intent mpdIntent = new Intent(activity, AlbumDetailActivity.class)
                .putExtra("videoNo", videoNo)
                .putExtra("showTitle", mIsShowTitle)
                .putExtra("album", album);
        activity.startActivity(mpdIntent);
    }

    @Override
    public void onClick(View v) {}

    private void fillAlbumDescView(final SCAlbum album) {
        player = (SuperPlayer) findViewById(R.id.view_super_player);
        ImageView albumImage = (ImageView) findViewById(R.id.album_image);
        TextView albumTitle = (TextView) findViewById(R.id.album_title);
        TextView albumDirector = (TextView) findViewById(R.id.album_director);
        TextView albumActor = (TextView) findViewById(R.id.album_main_actor);
        TextView albumDesc = (TextView) findViewById(R.id.album_desc);
        TextView albumType = (TextView) findViewById(R.id.album_main_type);
        TextView albumArea = (TextView) findViewById(R.id.album_main_area);

        albumTitle.setText(album.getTitle());
        if (album.getDirector() != null && !album.getDirector().isEmpty()) {
            albumDirector.setText(getResources().getString(R.string.director) + album.getDirector());
            albumDirector.setVisibility(View.VISIBLE);
        } else
            albumDirector.setVisibility(View.GONE);

        if (album.getMainActor() != null && !album.getMainActor().isEmpty()) {
            albumActor.setText(getResources().getString(R.string.actor) + album.getMainActor());
            albumActor.setVisibility(View.VISIBLE);
        } else
            albumActor.setVisibility(View.GONE);

        if (album.getSubTitle() != null && !album.getSubTitle().isEmpty()) {
            albumArea.setText(getResources().getString(R.string.type) + album.getTip());
            albumArea.setVisibility(View.VISIBLE);
        } else
            albumArea.setVisibility(View.GONE);

        if (album.getTip() != null && !album.getTip().isEmpty()) {
            albumType.setText(getResources().getString(R.string.area) + album.getSubTitle());
            albumType.setVisibility(View.VISIBLE);
        } else
            albumType.setVisibility(View.GONE);

        if (album.getDesc() != null && !album.getDesc().isEmpty())
            albumDesc.setText(album.getDesc());

        albumImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlbumDesc();
            }
        });

        if (album.getVerImageUrl() != null) {
            ImageTools.displayImage(albumImage, album.getVerImageUrl());
        } else if (album.getHorImageUrl() != null) {
            ImageTools.displayImage(albumImage, album.getHorImageUrl());
        }
    }

    public void closeAlbumDesc(View view) {
        RelativeLayout albumDescContainer = (RelativeLayout) findViewById(R.id.album_desc_container);
        albumDescContainer.setVisibility(View.GONE);
    }

    public void openAlbumDesc() {
        RelativeLayout albumDescContainer = (RelativeLayout) findViewById(R.id.album_desc_container);
        albumDescContainer.setVisibility(View.VISIBLE);
    }

    private void openErrorMsg(String msg) {
        TextView textView = (TextView) findViewById(error_message);
        textView.setText(msg);
        textView.setVisibility(View.VISIBLE);
    }

    private void hideAlbumCloseButton() {
        Button b = (Button) findViewById(R.id.album_desc_close);
        b.setVisibility(View.GONE);
    }

    @Override
    public void onGetAlbumDescSuccess(final SCAlbum album,final SCVideos scVideos) {
        mAlbum = album;
        mVideos = scVideos;
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                fillAlbumDescView(album);
                invalidateOptionsMenu();
                try {
                    mFragment = AlbumPlayGridFragment.newInstance(mAlbum, mIsShowTitle, mIsBackward, mInitialVideoNoInAlbum,scVideos);
                    mFragment.setShowTitle(mIsShowTitle);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, mFragment);
                    ft.commit();
                    getFragmentManager().executePendingTransactions();
                } catch (Exception e) {
                    e.printStackTrace();
                    finish();
                }
                if (mAlbum.getVideosTotal() == 1) {
                    hideAlbumCloseButton();
                    openAlbumDesc();
                    if(scVideos.get(0).getM3U8Nor()!=null){
                        initPlayer(scVideos.get(0).getVideoTitle(),scVideos.get(0).getM3U8Nor(),scVideos.get(0));
                    }
                }

                if (mAlbum.getVideosTotal() == 0) {
                    hideAlbumCloseButton();
                    openAlbumDesc();
                    openErrorMsg(getResources().getString(R.string.album_no_videos));
                }
            }
        });
    }

    @Override
    public void onGetAlbumDescFailed(SCFailLog err) {
    }

    /**
     * 下面的这几个Activity的生命状态很重要
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (player != null) {
            player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onAlbumPlayVideoSelected(SCVideo v, int videoNoInAlbum) {
        Log.e("PlayVideo_Url:",v.getM3U8Nor());
        mVideoNo = videoNoInAlbum;
        String url = v.getM3U8Nor();
        String title = v.getVideoTitle() + " " + String.valueOf(mVideoNo + 1);
        if(url!=null){
            initPlayer(title,url,v);
//            mHistoryDb.addHistory(mAlbum, v, 0);
        }
    }

    @Override
    public void onWifi() {
        Toast.makeText(this,"当前网络环境是WIFI",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMobile() {
        Toast.makeText(this,"当前网络环境是手机网络",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisConnect() {
        Toast.makeText(this,"网络链接断开",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoAvailable() {
        Toast.makeText(this,"无网络链接",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(player.getScreenOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                player.toggleFullScreen();
            }else {
                this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 初始化播放器
     */
    private void initPlayer(String title,String url,final SCVideo v){
        player.setNetChangeListener(true)//设置监听手机网络的变化
                .setOnNetChangeListener(this)//实现网络变化的回调
                .onPrepared(new SuperPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared() {
                        /**
                         * 监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）
                         */
                    }
                }).onComplete(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * 监听视频是否已经播放完成了。（可以在这里处理视频播放完成进行的操作）
                         */
                        String switch_url;
                        String switch_title;
                        if(mVideoNo >= 0){
                            mVideoNo++;
                            Log.e("__onComplete__:",String.valueOf(mVideoNo));
                            if((mVideoNo+1) < mVideos.size()){
                                switch_url = mVideos.get(mVideoNo).getM3U8Nor();
                                switch_title = mVideos.get(mVideoNo).getVideoTitle()+ " " + String.valueOf(mVideoNo+1);
                                player.setTitle(switch_title);
                                player.playSwitch(switch_url);
                            }else if((mVideoNo+1) == mVideos.size()){
                                switch_url = mVideos.get(mVideos.size()-1).getM3U8Nor();
                                switch_title = mVideos.get(mVideoNo).getVideoTitle()+ " " + String.valueOf(mVideoNo+1);
                                player.setTitle(switch_title);
                                player.playSwitch(switch_url);
                            }else{
//                                mHistoryDb.addHistory(mAlbum, v, 0);
                                player.stopPlayVideo();
                            }
                        }else {
                            player.stop();
                        }
                    }
                }).onInfo(new SuperPlayer.OnInfoListener() {
                    @Override
                    public void onInfo(int what, int extra) {
                        /**
                         * 监听视频的相关信息。
                         */

                    }
                }).onError(new SuperPlayer.OnErrorListener() {
                    @Override
                    public void onError(int what, int extra) {
                        /**
                         * 监听视频播放失败的回调
                         */
                        player.stop();
                    }
                }).setTitle(title) //设置视频的titleName
                .play(url); //开始播放视频
        player.setScaleType(SuperPlayer.SCALETYPE_FILLPARENT);
        player.setPlayerWH(0,player.getMeasuredHeight());//设置竖屏的时候屏幕的高度，如果不设置会切换后按照16:9的高度重置
    }
}
