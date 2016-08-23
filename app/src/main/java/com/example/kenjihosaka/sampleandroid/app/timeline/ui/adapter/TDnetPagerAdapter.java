package com.example.kenjihosaka.sampleandroid.app.timeline.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.kenjihosaka.sampleandroid.app.timeline.ui.component.TDnetArticlesFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class TDnetPagerAdapter extends FragmentStatePagerAdapter {

    public enum ArticleType {
        TODAY(0, "今日の記事"),
        YESTERDAY(1, "昨日の記事"),
        OLD(2, "古い記事");

        private int position;
        private String title;

        ArticleType(int position, String title) {
            this.position = position;
            this.title = title;
        }

        public static ArticleType valueOf(int position) {
            for (ArticleType tab : values()) {
                if (tab.getPosition() == position) return tab;
            }
            return null;
        }

        public int getPosition() {
            return position;
        }

        public String getTitle() {
            return title;
        }
    }

    private Fragment[] fragments;
    private ViewPager pager;

    public TDnetPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void initialize(ViewPager pager, final SmartTabLayout tab) {
        this.pager = pager;

        fragments = new Fragment[ArticleType.values().length];
        pager.setAdapter(this);
        tab.setViewPager(pager);
    }

    @Override
    public Fragment getItem(int position) {
        return TDnetArticlesFragment.newInstance(ArticleType.valueOf(position));
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ArticleType.valueOf(position).getTitle();
    }

}
