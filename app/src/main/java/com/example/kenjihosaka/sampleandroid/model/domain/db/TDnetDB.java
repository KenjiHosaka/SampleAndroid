package com.example.kenjihosaka.sampleandroid.model.domain.db;


import com.example.kenjihosaka.sampleandroid.model.domain.entity.realmobject.Article;

import java.util.ArrayList;
import java.util.Date;

public interface TDnetDB {
    ArrayList<Article> getTodayArticle();
    ArrayList<Article> getYesterdayArticle();
    ArrayList<Article> getOldArticle();
    Article getLatestArticle();
    boolean saveArticle(Article article, Date date);
    boolean readArticle(String id, Date date, boolean read);
    boolean deleteOldArticles();
}
