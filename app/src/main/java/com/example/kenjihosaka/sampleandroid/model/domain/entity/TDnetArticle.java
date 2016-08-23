package com.example.kenjihosaka.sampleandroid.model.domain.entity;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.kenjihosaka.sampleandroid.model.domain.entity.realmobject.Article;

/**
 * RealmObjectがParcelableをサポートしたら不要なクラス
 */
public class TDnetArticle implements Parcelable {

    private String  id;
    private String  company;
    private String  title;
    private String  pubdate;
    private String  url;
    private boolean read;

    public TDnetArticle(Article article) {
        setId(article.getId());
        setCompany(article.getCompanyName());
        setTitle(article.getTitle());
        setPubdate(article.getPubdate());
        setUrl(article.getDocumentUrl());
        setRead(article.isRead());
    }

    protected TDnetArticle(Parcel in) {
        id = in.readString();
        company = in.readString();
        title = in.readString();
        pubdate = in.readString();
        url = in.readString();
    }

    public static final Creator<TDnetArticle> CREATOR = new Creator<TDnetArticle>() {
        @Override
        public TDnetArticle createFromParcel(Parcel in) {
            return new TDnetArticle(in);
        }

        @Override
        public TDnetArticle[] newArray(int size) {
            return new TDnetArticle[size];
        }
    };

    public void setId(String id) {
        this.id = id;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getTitle() {
        return title;
    }

    public String getPubdate() {
        return pubdate;
    }

    public String getUrl() {
        return url;
    }

    public boolean isRead() {
        return read;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(company);
        dest.writeString(title);
        dest.writeString(pubdate);
        dest.writeString(url);
        dest.writeByte((byte) (read ? 1 : 0));
    }
}
