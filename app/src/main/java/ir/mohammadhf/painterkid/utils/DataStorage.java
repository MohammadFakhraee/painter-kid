package ir.mohammadhf.painterkid.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ir.mohammadhf.painterkid.R;

public class DataStorage {

    public static boolean saveBitmapFile(Bitmap source, File destFile, String sourceName) {
        if (source == null | destFile == null | sourceName.length() == 0)
            return false;

        File imageFile = new File(destFile, sourceName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);

            source.compress(Bitmap.CompressFormat.PNG, 100, fos);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap loadBitmapFile(File destFile, String sourceName, boolean compress) {
        File imageFile = new File(destFile, sourceName);

        try {
            BitmapFactory.Options options;
            FileInputStream fileInputStream;
            int inSampleSize = 1;
            if (compress) {
                options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                fileInputStream = new FileInputStream(imageFile);
                BitmapFactory.decodeStream(fileInputStream, null, options);
                fileInputStream.close();
                inSampleSize = calculateInSampleSize(options);
            }
            fileInputStream = new FileInputStream(imageFile);
            options = new BitmapFactory.Options();
            options.inSampleSize = inSampleSize;
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream, null, options);
            fileInputStream.close();
            return bitmap;
        } catch (IOException e) {
            return null;
        }
    }

    private static int calculateInSampleSize(BitmapFactory.Options options) {
        int width = 250;
        int height = 250;
        int sampleSize = 0;

        if (width < options.outWidth) {
            sampleSize = options.outWidth / width;
        }
        if (height < options.outHeight) {
            int temp = options.outHeight / height;
            if (temp > sampleSize)
                sampleSize = temp;
        }
        return sampleSize;
    }

    public static Bitmap loadRawBitmap(Context context, String rawName) {
        int identifierID = context.getResources().getIdentifier(rawName.toLowerCase(), "drawable", "ir.mohammadhf.painterkid");

        if (identifierID > 0)
            return BitmapFactory.decodeResource(context.getResources(), identifierID);

        return BitmapFactory.decodeResource(context.getResources(), R.drawable.paint_musics_xylophone);
    }
}
