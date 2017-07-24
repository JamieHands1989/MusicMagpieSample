package uk.co.jamiehands.musicmagpiesample.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;

public class CameraUtils {

    private static Uri imageUri;

    private static File createTempFile(Context context) throws IOException {
        File imageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile("barcode", ".jpg", imageDirectory);
        return image;
    }

    public static Intent getPhotoIntent(Context context) {
        Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imageFile = null;
        try {
            imageFile = createTempFile(context);
        } catch (Exception ignored) {}

        imageUri = FileProvider.getUriForFile(context, "uk.co.jamiehands.musicmagpiesample.fileprovider", imageFile);
        imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        return imageIntent;
    }

    public static Bitmap getImage(Context context) {
        try {
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
        } catch (Exception ignored) {
            return null;
        }
    }
}
