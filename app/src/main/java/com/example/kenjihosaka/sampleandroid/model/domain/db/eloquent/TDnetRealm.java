package com.example.kenjihosaka.sampleandroid.model.domain.db.eloquent;


import android.util.Log;

import com.example.kenjihosaka.sampleandroid.model.domain.db.TDnetDB;
import com.example.kenjihosaka.sampleandroid.model.domain.entity.realmobject.Article;
import com.example.kenjihosaka.sampleandroid.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class TDnetRealm implements TDnetDB {
    @Override
    public ArrayList<Article> getTodayArticle() {
        final Realm realm = Realm.getDefaultInstance();
        ArrayList<Article> articles = new ArrayList();
        try {
            RealmResults<Article> results = realm.where(Article.class).greaterThan("published", DateUtil.getToday()).findAllSorted("published", Sort.DESCENDING);
            for (Article result: results) {
                articles.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            articles = null;
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return articles;
    }

    @Override
    public ArrayList<Article> getYesterdayArticle() {
        final Realm realm = Realm.getDefaultInstance();
        ArrayList<Article> articles = new ArrayList();
        try {
            RealmResults<Article> results = realm.where(Article.class).between("published", DateUtil.getYesterday(), DateUtil.getToday()).findAllSorted("published", Sort.DESCENDING);
            for (Article result: results) {
                articles.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            articles = null;
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return articles;
    }

    @Override
    public ArrayList<Article> getOldArticle() {
        final Realm realm = Realm.getDefaultInstance();
        ArrayList<Article> articles = new ArrayList();
        try {
            RealmResults<Article> results = realm.where(Article.class).lessThan("published", DateUtil.getYesterday()).findAllSorted("published", Sort.DESCENDING);
            for (Article result: results) {
                articles.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            articles = null;
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return articles;
    }

    @Override
    public Article getLatestArticle() {
        final Realm realm = Realm.getDefaultInstance();
        Article article = null;
        try {
            article = realm.where(Article.class).findAllSorted("updated", Sort.DESCENDING).first();
        } catch (Exception e) {
            e.printStackTrace();
            article = null;
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return article;
    }

    @Override
    public boolean readArticle(final String id, final  Date date, final boolean read) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Article current = realm.where(Article.class).equalTo("id", id).findFirst();
                    if (current != null) {
                        current.setRead(read);
                        realm.copyToRealmOrUpdate(current);
                    }
                }
            });
        } catch (Exception e) {
            Log.e("TDnetRealm", "Realm save fault");
            e.printStackTrace();
            return false;
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return true;
    }

    @Override
    public boolean saveArticle(final Article article, final Date date) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (article.hasPubdate()) {
                        article.setPublished(DateUtil.getDateFromString(article.getPubdate()));
                    }
                    article.setUpdated(date);
                    Article current = realm.where(Article.class).equalTo("id", article.getId()).findFirst();
                    if (current != null) {
                        article.setRead(current.isRead());
                        realm.copyToRealmOrUpdate(article);
                    } else {
                        realm.insert(article);
                    }
                }
            });
        } catch (Exception e) {
            Log.e("TDnetRealm", "Realm save fault");
            e.printStackTrace();
            return false;
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return true;
    }

    @Override
    public boolean deleteOldArticles() {
        final Realm realm = Realm.getDefaultInstance();
        try {
            realm.where(Article.class).lessThan("pubdate", DateUtil.getDayBeforeTarget(7)).findAll().deleteAllFromRealm();
        } catch (Exception e) {
            Log.e("TDnetRealm", "Realm delete fault");
            e.printStackTrace();
            return false;
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return true;
    }
}
