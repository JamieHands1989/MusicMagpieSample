package uk.co.jamiehands.musicmagpiesample.data;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import uk.co.jamiehands.musicmagpiesample.data.remote.LookupService;
import uk.co.jamiehands.musicmagpiesample.injection.component.DaggerDataManagerComponent;
import uk.co.jamiehands.musicmagpiesample.injection.module.DataManagerModule;
import uk.co.jamiehands.musicmagpiesample.model.UPCLookup;
import uk.co.jamiehands.musicmagpiesample.view.SampleApplication;

public class DataManager {

    /**
     * Private variables
     */
    private Context context;
    private Gson gson;

    @Inject
    protected LookupService lookupService;
    @Inject
    protected Scheduler scheduler;

    public DataManager(Context context) {
        this.context = context;
        this.gson = new Gson();
        injectDependencies(context);
    }

    private void injectDependencies(Context context) {
        DaggerDataManagerComponent.builder()
                .applicationComponent(SampleApplication.get(context).getComponent())
                .dataManagerModule(new DataManagerModule())
                .build()
                .inject(this);
    }

    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public Observable<UPCLookup> lookupUPC(String upc) {
        return lookupService.lookupUPC(upc);
    }
}
