package uk.co.jamiehands.musicmagpiesample.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import uk.co.jamiehands.musicmagpiesample.R;
import uk.co.jamiehands.musicmagpiesample.data.DataManager;
import uk.co.jamiehands.musicmagpiesample.databinding.ActivityMainBinding;
import uk.co.jamiehands.musicmagpiesample.model.UPCLookup;
import uk.co.jamiehands.musicmagpiesample.util.CameraUtils;
import uk.co.jamiehands.musicmagpiesample.viewModel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private BarcodeDetector barcodeDetector;
    private DataManager dataManager;
    private CompositeSubscription subscription;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataManager = SampleApplication.get(this).getComponent().dataManager();
        subscription = new CompositeSubscription();

        viewModel = new MainActivityViewModel(this);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(viewModel);

        barcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        if(barcodeDetector.isOperational()) {
            Toast.makeText(this, "Barcode Detection Available", Toast.LENGTH_LONG).show();
        }

        AppCompatButton btnSubmit = (AppCompatButton) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        subscription.unsubscribe();
    }

    @Override
    public void onClick(View view) {
        lookupUpc();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MainActivityViewModel.REQUEST_BARCODE && resultCode == RESULT_OK) {
            Bitmap bitmap = CameraUtils.getImage(this);
            viewModel.setBitmap(bitmap);
            decodeBarcode(bitmap);
        }
    }

    private void lookupUpc() {
        subscription.add(dataManager.lookupUPC(viewModel.getUpc())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribe(new Subscriber<UPCLookup>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UPCLookup upcLookup) {

                    }
                }));
    }

    /**
     * Decodes the barcode and returns the UPC
     * @param bitmap - Bitmap to decode the barcode
     * @return - Barcode UPC
     */
    private String decodeBarcode(Bitmap bitmap) {
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Barcode> barcodes = barcodeDetector.detect(frame);

        Log.e("Barcodes Found", String.valueOf(barcodes.size()));

        /**
        if(barcodes.size() > 0) {
            return barcodes.get(0).rawValue;
        } else {
            return null;
        }
         **/
        return "";
    }
}
