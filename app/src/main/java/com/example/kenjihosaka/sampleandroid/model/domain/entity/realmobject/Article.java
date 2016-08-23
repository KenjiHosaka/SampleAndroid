package com.example.kenjihosaka.sampleandroid.model.domain.entity.realmobject;


import com.example.kenjihosaka.sampleandroid.util.StringUtil;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Article extends RealmObject {
    @PrimaryKey
    @SerializedName("id")             String id;
    @SerializedName("pubdate")        String pubdate;
    @SerializedName("company_code")   String companyCode;
    @SerializedName("company_name")   String companyName;
    @SerializedName("title")          String title;
    @SerializedName("document_url")   String documentUrl;
                                      boolean read;
                                      Date published;
                                      Date updated;

    public String getId() {
        return id;
    }

    public String getPubdate() {
        return pubdate;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getTitle() {
        return title;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public Date getUpdated() {
        return updated;
    }

    public Date getPublished() {
        return published;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean hasPubdate() {
        return !StringUtil.isBlank(pubdate);
    }

    public boolean hasCompanyCode() {
        return !StringUtil.isBlank(companyCode);
    }

    public boolean hasCompanyName() {
        return !StringUtil.isBlank(companyName);
    }

    public boolean hasTitle() {
        return !StringUtil.isBlank(title);
    }

    public boolean hasDocumentUrl() {
        return !StringUtil.isBlank(documentUrl);
    }

    public boolean isRead() {
        return read;
    }
}
