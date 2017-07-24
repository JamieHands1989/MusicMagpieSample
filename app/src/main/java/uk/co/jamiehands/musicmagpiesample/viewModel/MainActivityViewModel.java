package uk.co.jamiehands.musicmagpiesample.viewModel;

import android.app.Activity;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.view.View;

import uk.co.jamiehands.musicmagpiesample.BR;
import uk.co.jamiehands.musicmagpiesample.util.CameraUtils;

public class MainActivityViewModel extends BaseObservable {

    public static int REQUEST_BARCODE = 0;

    /**
     * Private variables
     */
    private Activity activity;
    private Bitmap bitmap;
    private String upc;

    public MainActivityViewModel(Activity activity) {
        this.activity = activity;
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
     * Onclick listener to call the camera app of the device
     * @return - Onclick listener
     */
    public View.OnClickListener showCamera() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = CameraUtils.getPhotoIntent(activity);
                activity.startActivityForResult(cameraIntent, REQUEST_BARCODE);
            }
        };
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        notifyPropertyChanged(BR.bitmapDrawable);
    }

    @Bindable
    public Drawable getBitmapDrawable() {
        return new BitmapDrawable(activity.getResources(), bitmap);
    }
}
