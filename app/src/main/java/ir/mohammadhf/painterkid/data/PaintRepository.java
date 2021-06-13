package ir.mohammadhf.painterkid.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ir.mohammadhf.painterkid.data.model.ColorPalette;
import ir.mohammadhf.painterkid.utils.DataFakeGenerator;
import ir.mohammadhf.painterkid.utils.DataStorage;

public class PaintRepository {

    public Completable saveBitmapToFolder(Context context, Bitmap bitmap, String folderName, String fileName) {
        return Completable.create(emitter -> {
            if (!emitter.isDisposed()) {
                File folder = context.getDir(folderName, Context.MODE_PRIVATE);
                if (DataStorage.saveBitmapFile(bitmap, folder, fileName))
                    emitter.onComplete();
            }
        });
    }

    public Single<Bitmap> getBitmapFromFolder(Context context, String folderName, String fileName) {
        return Single.create(emitter -> {
            if (!emitter.isDisposed()) {
                File direction = context.getDir(folderName, Context.MODE_PRIVATE);
                Bitmap source = DataStorage.loadBitmapFile(direction, fileName, false);
                if (source != null) {
                    emitter.onSuccess(source);
                } else
                    emitter.onError(new Throwable("Bitmap is null"));
            }
        });
    }

    public Single<Bitmap> getRawBitmap(Context context, String rawName) {
        return Single.create(emitter -> {
            if (!emitter.isDisposed()) {
                emitter.onSuccess(DataStorage.loadRawBitmap(context, rawName));
            }
        });
    }

    public Completable saveBitmapToGallery(Bitmap bitmap, String bitmapName) {
        return Completable.create(emitter -> {
            if (isExternalStorageWritable()) {
                File galleryFile = new File(Environment.getExternalStorageDirectory().toString());
                if (DataStorage.saveBitmapFile(bitmap, galleryFile, bitmapName)) {
                    emitter.onComplete();
                } else {
                    emitter.onError(new Throwable("Couldn't save file"));
                }
            } else
                emitter.onError(new Throwable("External storage is not available for read / write"));
        });
    }

    private boolean isExternalStorageWritable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public Single<List<ColorPalette>> getColors() {
        return DataFakeGenerator.Palette.getColorPack();
    }

    public Single<List<ColorPalette>> getPatterns() {
        return DataFakeGenerator.Palette.getPatternPack();
    }
}
