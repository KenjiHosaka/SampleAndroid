package com.example.kenjihosaka.sampleandroid.model.domain.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("headerKey","value");
        return chain.proceed(builder.build());
    }
}