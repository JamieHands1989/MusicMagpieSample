package uk.co.jamiehands.musicmagpiesample.view;

import android.app.Application;
import android.content.Context;

import uk.co.jamiehands.musicmagpiesample.injection.component.ApplicationComponent;
import uk.co.jamiehands.musicmagpiesample.injection.component.DaggerApplicationComponent;
import uk.co.jamiehands.musicmagpiesample.injection.module.ApplicationModule;

public class SampleApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static SampleApplication get(Context context) {
        return (SampleApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }
}
