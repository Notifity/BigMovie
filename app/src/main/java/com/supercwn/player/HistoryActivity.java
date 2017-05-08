package com.supercwn.player;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.supercwn.player.fragments.HistoryFragment;

/**
 * Created by JinYu on 2017/4/28.
 */

public class HistoryActivity extends AppCompatActivity {

    private HistoryFragment mHistoryFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mHistoryFragment = HistoryFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, mHistoryFragment);
        ft.commit();
        getFragmentManager().executePendingTransactions();
        setTitle(getResources().getString(R.string.channel_history));
    }

    public static void launch(Activity activity) {
        Intent mpdIntent = new Intent(activity, HistoryActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(mpdIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
