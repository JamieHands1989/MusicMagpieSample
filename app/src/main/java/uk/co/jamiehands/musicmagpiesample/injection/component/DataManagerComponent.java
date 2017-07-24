package uk.co.jamiehands.musicmagpiesample.injection.component;

import dagger.Component;
import uk.co.jamiehands.musicmagpiesample.data.DataManager;
import uk.co.jamiehands.musicmagpiesample.injection.module.DataManagerModule;
import uk.co.jamiehands.musicmagpiesample.injection.scope.PerDataManager;

@PerDataManager
@Component(dependencies = ApplicationComponent.class, modules = DataManagerModule.class)
public interface DataManagerComponent {

    void inject(DataManager dataManager);
}
