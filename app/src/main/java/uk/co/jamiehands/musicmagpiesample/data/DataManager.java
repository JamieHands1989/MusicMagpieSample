package uk.co.jamiehands.musicmagpiesample.data;

import android.content.Context;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import uk.co.jamiehands.musicmagpiesample.data.remote.LookupService;
import uk.co.jamiehands.musicmagpiesample.injection.component.DaggerDataManagerComponent;
import uk.co.jamiehands.musicmagpiesample.injection.module.DataManagerModule;
import uk.co.jamiehands.musicmagpiesample.model.UPCLookup;
import uk.co.jamiehands.musicmagpiesample.view.SampleApplication;

@SuppressWarnings("WeakerAccess")
public class DataManager {

    /**
     * Variables
     */
    @Inject
    protected LookupService lookupService;
    @Inject
    protected Scheduler scheduler;

    /**
     * Default constructor
     * @param context - Context to use for data manager
     */
    public DataManager(Context context) {
        injectDependencies(context);
    }

    /**
     * Configuration of injection for Retrofit2
     * @param context - Context to use for injection
     */
    private void injectDependencies(Context context) {
        DaggerDataManagerComponent.builder()
                .applicationComponent(SampleApplication.get(context).getComponent())
                .dataManagerModule(new DataManagerModule())
                .build()
                .inject(this);
    }

    /**
     * Returns the scheduler used for waiting for responses
     * @return - Scheduler to use
     */
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    /**
     * Returns information on a specified barcode
     * @param upc - upc to lookup
     * @return - Result of barcode lookup
     */
    public Observable<UPCLookup> lookupUPC(String upc) {
        return lookupService.lookupUPC(upc);
    }
}
