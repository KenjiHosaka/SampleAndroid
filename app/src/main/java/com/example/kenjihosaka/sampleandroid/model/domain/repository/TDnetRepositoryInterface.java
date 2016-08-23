package com.example.kenjihosaka.sampleandroid.model.domain.repository;

public interface TDnetRepositoryInterface {

    void fetchTodayArticles(boolean force);
    void fetchYesterdayArticles();
    void fetchOldArticles();
    boolean readArticle(String id);
    boolean useCache();

}
