package ir.mohammadhf.painterkid.feature.paint;

import android.content.Context;
import android.graphics.Bitmap;

import java.text.SimpleDateFormat;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ir.mohammadhf.painterkid.base.BaseViewModel;
import ir.mohammadhf.painterkid.data.PaintRepository;
import ir.mohammadhf.painterkid.data.model.ColorPalette;
import ir.mohammadhf.painterkid.utils.SettingsSharedPreferences;

public class PaintViewModel extends BaseViewModel {
    private PaintRepository repository;

    private List<ColorPalette> colorPack;
    private List<ColorPalette> patternPack;

    private SettingsSharedPreferences settingsSharedPreferences;

    public PaintViewModel(PaintRepository repository,
                          SettingsSharedPreferences settingsSharedPreferences) {
        this.repository = repository;
        this.settingsSharedPreferences = settingsSharedPreferences;
    }

    public Completable saveBitmap(Context context, Bitmap bitmap, String folder, String fileName) {
        return repository.saveBitmapToFolder(context, bitmap, folder, fileName);
    }

    public Single<Bitmap> getSavedBitmap(Context context, String folder, String fileName) {
        return repository.getBitmapFromFolder(context, folder, fileName);
    }

    public Single<Bitmap> getRawBitmap(Context context, String fileName, String category) {
        fileName = fileName.replace(".jpg", "");
        fileName = "paint_" + category + "_" + fileName;
        return repository.getRawBitmap(context, fileName.toLowerCase())
                .map(bitmap -> bitmap.copy(Bitmap.Config.ARGB_8888, true));
    }

    public Completable saveBitmapToGallery(Bitmap bitmap, String bitmapName) {

        bitmapName = bitmapName.replace(".jpg", "");
        return repository.saveBitmapToGallery(bitmap, "Image_"
                + bitmapName
                + new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss").format(new java.util.Date(System.currentTimeMillis()))
                + ".jpg");
    }

    public Single<List<ColorPalette>> getColors() {
        if (colorPack != null) {
            return Single.create(emitter -> emitter.onSuccess(colorPack));
        }
        return repository.getColors()
                .map(colorPalettes -> PaintViewModel.this.colorPack = colorPalettes);
    }

    public Single<List<ColorPalette>> getPatterns() {
        if (patternPack != null) {
            return Single.create(emitter -> emitter.onSuccess(patternPack));
        }
        return repository.getPatterns()
                .map(colorPalettes -> PaintViewModel.this.patternPack = colorPalettes);
    }

    public void addAdsPeriodCounter() {
        int currentCount = settingsSharedPreferences.getAdsRepeatPeriod();
        currentCount++;
        settingsSharedPreferences.setAdsRepeatPeriod(currentCount);
    }

    public boolean canShowAd() {
        int currentCount = settingsSharedPreferences.getAdsRepeatPeriod();
        return currentCount % 3 == 0;
    }
}
