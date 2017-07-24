package uk.co.jamiehands.musicmagpiesample.injection.component;

import android.app.Activity;
import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import uk.co.jamiehands.musicmagpiesample.data.DataManager;
import uk.co.jamiehands.musicmagpiesample.injection.module.ApplicationModule;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(Activity activity);

    Application application();
    DataManager dataManager();
}
