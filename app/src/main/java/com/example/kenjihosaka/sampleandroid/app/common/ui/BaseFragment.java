package com.example.kenjihosaka.sampleandroid.app.common.ui;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.kenjihosaka.sampleandroid.R;

import butterknife.BindView;

public class BaseFragment extends Fragment {
    @Nullable @BindView(R.id.common_progress_layout) FrameLayout progress;
    @Nullable @BindView(R.id.layout_not_found) RelativeLayout notFoundText;

    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }

    public void showNotfoundText() {
        notFoundText.setVisibility(View.VISIBLE);
    }

    public void hideNotFoundText() {
        notFoundText.setVisibility(View.GONE);
    }
}
