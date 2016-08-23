package com.example.kenjihosaka.sampleandroid.app.timeline.ui;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.example.kenjihosaka.sampleandroid.R;
import com.example.kenjihosaka.sampleandroid.app.common.ui.BaseActivity;
import com.example.kenjihosaka.sampleandroid.app.timeline.ui.adapter.TDnetPagerAdapter;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class TimelineActivity extends BaseActivity {

    private Realm realm;

    @BindView(R.id.pager) ViewPager pager;
    @BindView(R.id.tab) SmartTabLayout tab;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();

        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initPager();
    }

    private void initPager() {
        TDnetPagerAdapter paagerAdapter = new TDnetPagerAdapter(getSupportFragmentManager());
        paagerAdapter.initialize(pager, tab);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
