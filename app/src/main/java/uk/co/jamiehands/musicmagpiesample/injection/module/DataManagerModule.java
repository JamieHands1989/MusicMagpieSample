package uk.co.jamiehands.musicmagpiesample.injection.module;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;
import uk.co.jamiehands.musicmagpiesample.data.remote.LookupHelper;
import uk.co.jamiehands.musicmagpiesample.data.remote.LookupService;
import uk.co.jamiehands.musicmagpiesample.injection.scope.PerDataManager;

@Module
public class DataManagerModule {

    public DataManagerModule() { }

    @Provides
    @PerDataManager
    LookupService provideLookupService() {
        return new LookupHelper().newLookupService();
    }

    @Provides
    @PerDataManager
    Scheduler provideSubscribeScheduler() {
        return Schedulers.io();
    }
}
