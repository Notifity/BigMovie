package com.supercwn.player;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.supercwn.player.database.BookmarkDbHelper;
import com.supercwn.player.database.HistoryDbHelper;
import com.supercwn.player.fragments.AlbumPlayGridFragment;
import com.supercwn.player.model.SCAlbum;
import com.supercwn.player.model.SCVideo;
import com.superplayer.library.SuperPlayer;

/**
 *
 * 类描述：视频详情页
 *
 * @author Super南仔
 * @time 2016-9-19
 */
public class SuperVideoDetailsActivity extends AppCompatActivity
        implements SuperPlayer.OnNetChangeListener{

    private SuperPlayer player;
    private boolean isLive;
    private SCAlbum mAlbum;
    private int mInitialVideoNoInAlbum = 0;
    private boolean mIsShowTitle = false;
    private boolean mIsBackward = false;
    private BookmarkDbHelper mBookmarkDb;
    private HistoryDbHelper mHistoryDb;
    private boolean mIsFav;
    private AlbumPlayGridFragment mFragment;
    private ImageView albumImage;
    private TextView albumTitle,albumDirector,albumActor,albumDesc,albumType,albumArea;
    private final String v_url = null;
    private String title = null;
    private SCVideo scVideo = null;

    /**
     * 测试地址
     */
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_super_video_layout);
        initData();
        initView();
        initPlayer();
    }

    /**
     * 初始化相关的信息
     */
    private void initData(){
        System.out.println("initData__on");
        isLive = getIntent().getBooleanExtra("isLive",false);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
//        scVideo = getIntent().getParcelableExtra("video");
    }

    /**
     * 初始化视图
     */
    private void initView(){
        player = (SuperPlayer) findViewById(R.id.view_super_player);
    }

    /**
     * 初始化播放器
     */
    private void initPlayer(){
        if(isLive){
            player.setLive(true);//设置该地址是直播的地址
        }
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

//                        player.pause();
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

                    }
                }).setTitle(title)//设置视频的titleName
                .play(url);//开始播放视频
        player.setScaleType(SuperPlayer.SCALETYPE_FILLPARENT);
        player.setPlayerWH(0,player.getMeasuredHeight());//设置竖屏的时候屏幕的高度，如果不设置会切换后按照16:9的高度重置
    }

    /**
     * 网络链接监听类
     */
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
}
