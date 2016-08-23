package com.example.kenjihosaka.sampleandroid.model.domain.provider;

import com.example.kenjihosaka.sampleandroid.model.domain.db.eloquent.TDnetRealm;
import com.example.kenjihosaka.sampleandroid.model.domain.db.TDnetDB;
import com.example.kenjihosaka.sampleandroid.model.domain.repository.TDnetRepositoryInterface;
import com.example.kenjihosaka.sampleandroid.model.domain.repository.eloquent.TDnetRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class TDnetModule {

    @Provides
    public TDnetRepositoryInterface provideTDnetRepositoryInterface(TDnetDB tDnetDB) {
        return new TDnetRepository(tDnetDB);
    }

    @Provides
    public TDnetDB provideTDnetDB() {
        return new TDnetRealm();
    }
}
