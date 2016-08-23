package com.example.kenjihosaka.sampleandroid;

import com.example.kenjihosaka.sampleandroid.app.timeline.ui.TimelineActivity;
import com.example.kenjihosaka.sampleandroid.app.timeline.ui.component.TDnetArticlesFragment;
import com.example.kenjihosaka.sampleandroid.model.domain.provider.TDnetModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, TDnetModule.class})
public interface EquipmentComponent {
    void inject(TimelineActivity timelineActivity);
    void inject(TDnetArticlesFragment tDnetArticlesFragment);
}
