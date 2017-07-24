package uk.co.jamiehands.musicmagpiesample.view.activity;

import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import uk.co.jamiehands.musicmagpiesample.R;
import uk.co.jamiehands.musicmagpiesample.data.DataManager;
import uk.co.jamiehands.musicmagpiesample.databinding.ActivityMainBinding;
import uk.co.jamiehands.musicmagpiesample.model.UPCLookup;
import uk.co.jamiehands.musicmagpiesample.view.SampleApplication;
import uk.co.jamiehands.musicmagpiesample.viewModel.activity.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Private variables
     */
    private DataManager dataManager;
    private CompositeSubscription subscription;
    private MainActivityViewModel viewModel;
    private SurfaceView viewCamera;

    /**
     * Called when the activity is created
     * @param savedInstanceState - Saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataManager = SampleApplication.get(this).getComponent().dataManager();
        subscription = new CompositeSubscription();

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new MainActivityViewModel(this);
        binding.setViewModel(viewModel);

        AppCompatImageButton btnSubmit = (AppCompatImageButton) findViewById(R.id.btnSubmit);
        AppCompatImageButton btnCamera = (AppCompatImageButton) findViewById(R.id.btnCamera);
        viewCamera = (SurfaceView) findViewById(R.id.viewCamera);

        btnSubmit.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        viewCamera.getHolder().addCallback(viewModel.getSurfaceHolderCallback());
    }

    /**
     * Called when the activity is destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        subscription.unsubscribe();
    }

    /**
     * Called when the user clicks to search for the specified barcode
     * @param view - View received the click
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit:
                lookupUpc();
                break;
            case R.id.btnCamera:
                showCamera();
                break;
        }
    }

    /**
     * Called as a result of a permissions request
     * @param requestCode - Request code for permissions
     * @param permissions - Permissions requested
     * @param results - Result of permission
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] results) {
        super.onRequestPermissionsResult(requestCode, permissions, results);

        if(results[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.notifyChange();
        }
    }

    /**
     * Looks up the information for the UPC
     */
    private void lookupUpc() {
        subscription.add(dataManager.lookupUPC(viewModel.getUpc())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribe(new Subscriber<UPCLookup>() {
                    @Override
                    public void onCompleted() { }

                    @Override
                    public void onError(Throwable error) {
                        error.printStackTrace();
                    }

                    @Override
                    public void onNext(UPCLookup upcLookup) {
                        if(upcLookup.items.size() > 0) {
                            viewModel.addItem(upcLookup.items.get(0));
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.no_results_found, Toast.LENGTH_LONG).show();
                        }
                        viewModel.setUpc("");
                    }
                }));
    }

    /**
     * Show or hide the camera view
     */
    private void showCamera() {
        if(viewCamera.getVisibility() == View.VISIBLE) {
            Animation animSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
            viewCamera.startAnimation(animSlideDown);
            viewCamera.setVisibility(View.GONE);
        } else {
            Animation animSlideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            viewCamera.startAnimation(animSlideUp);
            viewCamera.setVisibility(View.VISIBLE);
        }
    }
}
