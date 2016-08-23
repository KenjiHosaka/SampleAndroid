package com.example.kenjihosaka.sampleandroid.model.domain.event;

import com.example.kenjihosaka.sampleandroid.model.domain.entity.TDnetEntity;

public class TDnetFetchedEvent extends Event<TDnetEntity> {

    private static TDnetFetchedEvent create(Result result, TDnetEntity entity, Throwable e) {
        TDnetFetchedEvent event = new TDnetFetchedEvent();
        event.setTarget(entity);
        event.setResult(result);
        event.setError(e);
        return event;
    }

    public static TDnetFetchedEvent success(TDnetEntity entity) {
        return create(Result.SUCCESS, entity, null);
    }

    public static TDnetFetchedEvent failed(TDnetEntity entity, Throwable e) {
        return create(Result.FAILED, entity, e);
    }

    public TDnetEntity getTDnetEntity() {
        return getTarget();
    }

}

