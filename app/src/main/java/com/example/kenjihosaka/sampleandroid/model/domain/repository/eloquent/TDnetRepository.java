package com.example.kenjihosaka.sampleandroid.model.domain.repository.eloquent;

import android.util.Log;

import com.example.kenjihosaka.sampleandroid.app.timeline.ui.adapter.TDnetPagerAdapter;
import com.example.kenjihosaka.sampleandroid.model.domain.api.ApiClientTDnet;
import com.example.kenjihosaka.sampleandroid.model.domain.api.TDnetApi;
import com.example.kenjihosaka.sampleandroid.model.domain.db.TDnetDB;
import com.example.kenjihosaka.sampleandroid.model.domain.entity.TDnetEntity;
import com.example.kenjihosaka.sampleandroid.model.domain.entity.realmobject.Article;
import com.example.kenjihosaka.sampleandroid.model.domain.event.TDnetFetchedEvent;
import com.example.kenjihosaka.sampleandroid.model.domain.repository.TDnetRepositoryInterface;
import com.example.kenjihosaka.sampleandroid.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class TDnetRepository implements TDnetRepositoryInterface {

    public static final String FILE_TODAY = "today";
    public static final String FILE_YESTERDAY = "yesterday";
    public static final String FILE_RECENT = "recent";

    private TDnetDB tDnetDB;

    @Inject
    public TDnetRepository(TDnetDB tDnetDB) {
        this.tDnetDB = tDnetDB;
    }

    @Override
    public void fetchTodayArticles(boolean force) {
        if (force) {
            fetchTDnetFile(FILE_TODAY, TDnetPagerAdapter.ArticleType.TODAY);
            return;
        }

        ArrayList<Article> articles = tDnetDB.getTodayArticle();
        TDnetEntity entity = new TDnetEntity(FILE_TODAY, articles);
        entity.setType(TDnetPagerAdapter.ArticleType.TODAY);

        if (entity.hasTDnets() && useCache()) {
            TDnetFetchedEvent.success(entity).publish();
        } else {
            fetchTDnetFile(FILE_TODAY, TDnetPagerAdapter.ArticleType.TODAY);
        }
    }

    @Override
    public void fetchYesterdayArticles() {
        ArrayList<Article> articles = tDnetDB.getYesterdayArticle();
        TDnetEntity entity = new TDnetEntity(FILE_YESTERDAY, articles);
        entity.setType(TDnetPagerAdapter.ArticleType.YESTERDAY);
        if (entity.hasTDnets()) {
            TDnetFetchedEvent.success(entity).publish();
        } else {
            fetchTDnetFile(FILE_YESTERDAY, TDnetPagerAdapter.ArticleType.YESTERDAY);
        }
    }

    @Override
    public void fetchOldArticles() {
        tDnetDB.deleteOldArticles();
        ArrayList<Article> articles = tDnetDB.getOldArticle();
        TDnetEntity entity = new TDnetEntity("old", articles);
        entity.setType(TDnetPagerAdapter.ArticleType.OLD);
        if (entity.hasTDnets()) {
            TDnetFetchedEvent.success(entity).publish();
        } else {
            fetchTDnetFile(FILE_RECENT, TDnetPagerAdapter.ArticleType.OLD);
        }
    }

    @Override
    public boolean readArticle(String id) {
        return tDnetDB.readArticle(id, new Date(), true);
    }

    @Override
    public boolean useCache() {
        Article article = tDnetDB.getLatestArticle();
        if (article == null) {
            return false;
        }
        // 最後の取得から10分以内はローカルデータを使用する
        if (DateUtil.diffMinute(article.getUpdated(), new Date()) <= 10) {
            return true;
        }
        return false;
    }

    private void fetchTDnetFile(String fileName, final TDnetPagerAdapter.ArticleType type) {
        Retrofit retrofit = ApiClientTDnet.retrofitInstance();
        retrofit.create(TDnetApi.class).fetchTDnet(fileName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<TDnetEntity, Boolean>() {
                    @Override
                    public Boolean call(TDnetEntity tDnetEntity) {
                        return tDnetEntity != null;
                    }
                })
                .subscribe(new Observer<TDnetEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.d("TDnetRepository", "onCompleted call");
                    }

                    @Override
                    public void onError(Throwable e) {
                        TDnetFetchedEvent.failed(null, e).publish();
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TDnetEntity tDnetEntity) {
                        tDnetEntity.setType(type);
                        if (tDnetDB != null) {
                            Date now = new Date();
                            for (Article article: tDnetEntity.getArticles()) {
                                tDnetDB.saveArticle(article, now);
                            }
                        }
                        if (type.equals(TDnetPagerAdapter.ArticleType.OLD)) {
                            tDnetEntity = new TDnetEntity("old", tDnetDB.getOldArticle());
                            tDnetEntity.setType(TDnetPagerAdapter.ArticleType.OLD);
                        }
                        TDnetFetchedEvent.success(tDnetEntity).publish();
                    }
                });
    }
}
