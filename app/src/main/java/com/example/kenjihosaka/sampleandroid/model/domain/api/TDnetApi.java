package com.example.kenjihosaka.sampleandroid.model.domain.api;

import com.example.kenjihosaka.sampleandroid.model.domain.entity.TDnetEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface TDnetApi {

    @GET("{fileName}.json?limit=1000")
    Observable<TDnetEntity> fetchTDnet(@Path("fileName") String fileName);
}
