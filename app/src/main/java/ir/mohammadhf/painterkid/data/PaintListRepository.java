package ir.mohammadhf.painterkid.data;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Single;
import ir.mohammadhf.painterkid.R;
import ir.mohammadhf.painterkid.data.model.PaintModel;
import ir.mohammadhf.painterkid.utils.DataFakeGenerator;
import ir.mohammadhf.painterkid.utils.DataStorage;

public class PaintListRepository {
    private static final String TAG = "PaintListRepository";

    public Single<List<PaintModel>> extractPaintList(Context context, String folderName) {
        File myDir = context.getDir(folderName, Context.MODE_PRIVATE);
        final List<PaintModel> paintModels = new ArrayList<>();

        return Single.create(emitter -> {
            if (!emitter.isDisposed()) {
                if (Objects.requireNonNull(myDir.listFiles()).length < 10) {
                    paintModels.addAll(DataFakeGenerator.getRawPaintModels(context, folderName));

                    for (PaintModel model :
                            paintModels) {
                        if (DataStorage.saveBitmapFile(model.getBitmap(), myDir, model.getNameWithFormat())) {
                            model.setBitmap(null);
                            Log.i(TAG, "extractPaintList: " + model.getNameWithFormat() + " saved successfully!");
                        } else {
                            Log.e(TAG, "extractPaintList: " + model.getNameWithFormat() + " could not be saved!");
                        }
                    }
                    paintModels.clear();
                }

                List<String> strings = DataFakeGenerator.getRawPaintNames(folderName);

                for (int i = 0; i < strings.size(); i++) {
                    String string = strings.get(i);
                    PaintModel model = new PaintModel();
                    model.setNameWithFormat(string);
                    model.setBitmap(DataStorage.loadBitmapFile(myDir, string, true));
                    model.setPaintCategory(folderName);
                    model.setPaintHolderId(getBitmapHolderId(i));
                    model.setPaintButtonBgId(getBitmapNameButtonBg(i));
                    model.setNameColor(Color.WHITE);
                    paintModels.add(model);
                }

                emitter.onSuccess(paintModels);
            }
        });
    }

    private int getBitmapHolderId(int position) {
        switch (position % 5) {
            case 0:
                return R.drawable.template_paint_blue_dark;
            case 1:
                return R.drawable.template_paint_red_dark;
            case 2:
                return R.drawable.template_paint_green_dark;
            case 3:
                return R.drawable.template_paint_orange_dark;
            case 4:
                return R.drawable.template_paint_purple;
        }
        return R.drawable.template_paint_blue_dark;
    }

    private int getBitmapNameButtonBg(int position) {
        switch (position % 5) {
            case 0:
                return R.drawable.template_name_blue_dark;
            case 1:
                return R.drawable.template_name_red_dark;
            case 2:
                return R.drawable.template_name_green_dark;
            case 3:
                return R.drawable.template_name_orange_dark;
            case 4:
                return R.drawable.template_name_purple;
        }
        return R.drawable.template_name_blue_dark;
    }
}
