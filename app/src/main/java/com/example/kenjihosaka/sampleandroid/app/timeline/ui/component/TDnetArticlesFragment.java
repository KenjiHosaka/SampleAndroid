package com.example.kenjihosaka.sampleandroid.app.timeline.ui.component;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kenjihosaka.sampleandroid.BusProvider;
import com.example.kenjihosaka.sampleandroid.R;
import com.example.kenjihosaka.sampleandroid.app.common.ui.BaseActivity;
import com.example.kenjihosaka.sampleandroid.app.common.ui.BaseFragment;
import com.example.kenjihosaka.sampleandroid.app.common.ui.WebviewActivity;
import com.example.kenjihosaka.sampleandroid.app.timeline.ui.adapter.TDnetArticleListAdapter;
import com.example.kenjihosaka.sampleandroid.app.timeline.ui.adapter.TDnetPagerAdapter;
import com.example.kenjihosaka.sampleandroid.model.domain.entity.TDnetArticle;
import com.example.kenjihosaka.sampleandroid.model.domain.entity.TDnetEntity;
import com.example.kenjihosaka.sampleandroid.model.domain.entity.realmobject.Article;
import com.example.kenjihosaka.sampleandroid.model.domain.event.TDnetFetchedEvent;
import com.example.kenjihosaka.sampleandroid.model.domain.repository.TDnetRepositoryInterface;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class TDnetArticlesFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener
        , AdapterView.OnItemClickListener {

    private static final String ARG_SECTION = "section";
    private static final String ARG_ARTICLES= "articles";

    @BindView(R.id.refresh_tdnet_articles)  SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.listview_tdnet_articles) ListView listView;
    @Inject TDnetRepositoryInterface tdnetRepository;

    private Context context;
    private CompositeSubscription subscription;
    private TDnetPagerAdapter.ArticleType type;

    public TDnetArticlesFragment() {
    }

    public static TDnetArticlesFragment newInstance(TDnetPagerAdapter.ArticleType type) {
        TDnetArticlesFragment fragment = new TDnetArticlesFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SECTION, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        ((BaseActivity)context).getEquipmentComponent().inject(this);
        setSubscribe();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        TDnetArticleListAdapter adapter = ((TDnetArticleListAdapter)listView.getAdapter());
        ArrayList<TDnetArticle> articles = new ArrayList<>();
        for (int i = 0; i < adapter.getCount(); i++) {
            articles.add(adapter.getItem(i));
        }
        outState.putSerializable(ARG_ARTICLES, articles);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tdnet_articles, container, false);
        ButterKnife.bind(this, rootView);

        swipeRefreshLayout.setColorSchemeColors(getResources().getIntArray(R.array.loading_colors));
        swipeRefreshLayout.setOnRefreshListener(this);

        type = (TDnetPagerAdapter.ArticleType) getArguments().getSerializable(ARG_SECTION);

        ArrayList<TDnetArticle> articles = null;
        if (savedInstanceState != null) {
            articles = (ArrayList<TDnetArticle>) savedInstanceState.getSerializable(ARG_ARTICLES);
        }
        TDnetArticleListAdapter adapter = new TDnetArticleListAdapter(context, R.layout.list_element_tdnet_article);

        if (articles != null && articles.size() > 0) {
            adapter.addAll(articles);
        } else {
            showProgress();
            fetchTDnetArticles(false);
        }
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        return rootView;
    }

    private void fetchTDnetArticles(boolean force) {
        switch (type) {
            case TODAY:
                tdnetRepository.fetchTodayArticles(force);
                break;
            case YESTERDAY:
                tdnetRepository.fetchYesterdayArticles();
                break;
            case OLD:
                tdnetRepository.fetchOldArticles();
                break;
            default:
                new Exception("Article type is undefined");
                break;
        }
    }

    private void setSubscribe() {
        subscription = new CompositeSubscription();
        subscription.add(
                BusProvider.instance()
                        .observable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Object>() {
                            @Override
                            public void call(Object event) {
                                subscribe(event);
                            }
                        })
        );
    }

    private void subscribe(Object event) {
        if (event instanceof TDnetFetchedEvent) {
            subscribeTDnetFetched((TDnetFetchedEvent) event);
        }
    }

    /**
     * Subscribe method
     */
    private void subscribeTDnetFetched(TDnetFetchedEvent event) {
        TDnetEntity entity = event.getTDnetEntity();
        if (!entity.getType().equals(type)) {
            return;
        }
        swipeRefreshLayout.setRefreshing(false);
        hideProgress();
        if (event.isSuccess()) {
            if (entity.hasTDnets()) {
                hideNotFoundText();
            } else {
                showNotfoundText();
                return;
            }
            TDnetArticleListAdapter adapter = (TDnetArticleListAdapter) listView.getAdapter();
            adapter.clear();
            for (Article article: entity.getArticles()) {
                adapter.add(new TDnetArticle(article));
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        fetchTDnetArticles(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (listView.getAdapter().getItem(position) == null) {
            return;
        }
        TDnetArticle article = (TDnetArticle) listView.getAdapter().getItem(position);
        article.setRead(true);
        if (tdnetRepository.readArticle(article.getId())) {
            ((TDnetArticleListAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.bundle_key_webview_url), article.getUrl());
        Intent intent = new Intent(context, WebviewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        subscription.unsubscribe();
    }
}
