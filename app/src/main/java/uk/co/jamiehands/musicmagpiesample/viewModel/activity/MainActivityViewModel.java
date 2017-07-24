package uk.co.jamiehands.musicmagpiesample.viewModel.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.View;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.util.ArrayList;
import java.util.List;

import uk.co.jamiehands.musicmagpiesample.BR;
import uk.co.jamiehands.musicmagpiesample.model.UPCLookup;
import uk.co.jamiehands.musicmagpiesample.view.adapter.ItemAdapter;

public class MainActivityViewModel extends BaseObservable {

    /**
     * Private variables
     */
    private final Context context;
    private String upc;
    private final List<UPCLookup.LookupItem> items;
    private final ItemAdapter itemAdapter;
    private final CameraSource cameraSource;

    /**
     * Default constructor
     * @param context - Context to use for the view model
     */
    public MainActivityViewModel(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
        this.itemAdapter = new ItemAdapter(context, items);

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context)
                .build();
        barcodeDetector.setProcessor(getBarcodeProcessor());

        this.cameraSource = new CameraSource.Builder(context, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedFps(30)
                .build();
    }

    /**
     * Sets the UPC to search for
     * @param upc - UPC to search
     */
    public void setUpc(String upc) {
        this.upc = upc;
        notifyPropertyChanged(BR.upc);
    }

    /**
     * Gets the UPC to search for
     * @return - UPC to search
     */
    @Bindable
    public String getUpc() {
        return this.upc;
    }

    /**
     * Returns the adapter to use to display the items
     * @return - ItemAdapter
     */
    public ItemAdapter getItemAdapter() {
        return itemAdapter;
    }

    /**
     * Returns the layout manager to use to display the items
     * @return - LinearLayoutManager
     */
    public LinearLayoutManager getItemLayoutManager() {
        return new LinearLayoutManager(context);
    }

    /**
     * Adds a new item to display to the user
     * @param item - Item to add
     */
    public void addItem(UPCLookup.LookupItem item) {
        items.add(0, item);
        itemAdapter.notifyDataSetChanged();
        notifyPropertyChanged(BR.showIfNoResults);
    }

    /**
     * Returns the callbacks to use for the surface holder
     * @return - Surface holder callbacks
     */
    public SurfaceHolder.Callback getSurfaceHolderCallback() {
        return new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    int permission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
                    if(permission == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceHolder);
                    } else {
                        Activity activity = (Activity) context;
                        String[] permissions = new String[] { Manifest.permission.CAMERA };
                        ActivityCompat.requestPermissions(activity, permissions, 0);
                    }
                } catch (Exception ignored) { }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) { }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        };
    }

    /**
     * Returns visible if no results
     * @return - Visibility of no result message
     */
    @Bindable
    public int getShowIfNoResults() {
        if(items.size() == 0) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

    /**
     * Creates a new processor to use for the barcode detector
     * @return - Barcode processor
     */
    private Detector.Processor<Barcode> getBarcodeProcessor() {
        return new Detector.Processor<Barcode>() {
            @Override
            public void release() { }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if(barcodes.size() > 0) {
                    setUpc(barcodes.valueAt(0).rawValue);
                }
            }
        };
    }
}
