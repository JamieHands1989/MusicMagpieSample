package uk.co.jamiehands.musicmagpiesample.injection.module;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.jamiehands.musicmagpiesample.data.DataManager;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return this.application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager() {
        return new DataManager(application);
    }
}
