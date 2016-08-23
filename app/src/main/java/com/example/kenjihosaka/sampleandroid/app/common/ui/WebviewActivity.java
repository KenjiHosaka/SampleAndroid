package com.example.kenjihosaka.sampleandroid.app.common.ui;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.kenjihosaka.sampleandroid.R;
import com.example.kenjihosaka.sampleandroid.app.common.ui.component.WebviewFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebviewActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;

    private WebviewFragment webViewFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(android.support.design.R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        initWebView(bundle.getString(getString(R.string.bundle_key_webview_url)));
    }


    private void initWebView(String url) {
        webViewFragment = (WebviewFragment) getFragmentManager().findFragmentById(R.id.fragment_webview);
        webViewFragment.buildWebView(url);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
