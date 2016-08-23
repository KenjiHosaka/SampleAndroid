package com.example.kenjihosaka.sampleandroid.model.domain.entity;

import com.example.kenjihosaka.sampleandroid.app.timeline.ui.adapter.TDnetPagerAdapter;
import com.example.kenjihosaka.sampleandroid.model.domain.entity.realmobject.Article;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TDnetEntity {

    @SerializedName("total_count")    String totalCount;
    @SerializedName("condition_desc") String conditionDesc;
    @SerializedName("items")          ArrayList<TDnet> tDnets;
                                      TDnetPagerAdapter.ArticleType type;

    public TDnetEntity (String conditionDesc, ArrayList<Article> articles) {
        setConditionDesc(conditionDesc);
        ArrayList<TDnet> tDnets = new ArrayList<>();
        if (articles != null) {
            for (Article article : articles) {
                tDnets.add(new TDnet(article));
            }
        }
        setTDnets(tDnets);
        setTotalCount(hasTDnets() ? String.valueOf(tDnets.size()) : "0");
    }

    public class TDnet {
        @SerializedName("Tdnet") Article article;

        public TDnet(Article article) {
            setArticle(article);
        }

        public Article getArticle() {
            return article;
        }

        public void setArticle(Article article) {
            this.article = article;
        }
    }

    public String getTotalCount() {
        return totalCount;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    public ArrayList<TDnet> getTDnets() {
        return tDnets;
    }

    public TDnetPagerAdapter.ArticleType getType() {
        return type;
    }

    public ArrayList<Article> getArticles() {
        ArrayList<Article> articles = new ArrayList<>();
        for (TDnet tdnet: tDnets) {
            articles.add(tdnet.getArticle());
        }
        return articles;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public void setTDnets(ArrayList<TDnet> tDnets) {
        this.tDnets = tDnets;
    }

    public void setConditionDesc(String conditionDesc) {
        this.conditionDesc = conditionDesc;
    }

    public void setType(TDnetPagerAdapter.ArticleType type) {
        this.type = type;
    }

    public boolean hasTDnets() {
        return tDnets != null && tDnets.size() > 0;
    }
}
