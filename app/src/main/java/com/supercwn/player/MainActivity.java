package com.supercwn.player;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.supercwn.player.fragments.AlbumListFragment;
import com.supercwn.player.model.SCChannel;
import com.supercwn.player.model.SCChannelFilter;
import com.supercwn.player.siteapi.SiteApi;
import com.supercwn.player.uiutils.SlidingTabLayout;

import java.util.HashMap;

/**
 *
 * 类描述：首页
 *
 * @author Super南仔
 * @time 2016-9-19
 */
public class MainActivity extends AppCompatActivity
        implements AlbumFilterDialog.OnAlbumFilterDialogAction {

    ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private SitePagerAdapter mPagerAdapter;
    private int mChannelID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);
        mChannelID = 1;
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mPagerAdapter = new SitePagerAdapter(getSupportFragmentManager(), this, mChannelID);
        mViewPager.setAdapter(mPagerAdapter);
        mSlidingTabLayout.setViewPager(mViewPager);
        SCChannel channel = new SCChannel(mChannelID);
        setTitle(channel.toString());
    }

    private class SitePagerAdapter extends FragmentPagerAdapter {

        private Context mContext;
        private int mChannelID;
        private HashMap<Integer,AlbumListFragment> mPageReferenceMap;

        public SitePagerAdapter(FragmentManager fm, Context context, int mChannelID) {
            super(fm);
            this.mContext = context;
            this.mChannelID = mChannelID;
            mPageReferenceMap = new HashMap<>();
        }

        private int positionToSiteID(int position) {
            int mSiteID = position;
            return mSiteID;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            if(obj instanceof AlbumListFragment) {
                mPageReferenceMap.put(position, (AlbumListFragment) obj);
            }
            return obj;
        }

        @Override
        public AlbumListFragment getItem(int position) {
            AlbumListFragment fragment = AlbumListFragment.newInstance(positionToSiteID(position),mChannelID);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            mPageReferenceMap.remove(position);
        }

        public AlbumListFragment getFragment(int position) {
            return mPageReferenceMap.get(position);
        }

        @Override
        public int getCount() {
            return SiteApi.getSupportSiteNumber();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return SiteApi.getSiteName(positionToSiteID(position), mContext);
        }
    }

    @Override
    public void onAlbumFilterSelected(SCChannelFilter filter) {

    }
}
