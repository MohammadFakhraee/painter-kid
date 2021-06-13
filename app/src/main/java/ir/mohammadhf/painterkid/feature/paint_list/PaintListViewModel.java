package ir.mohammadhf.painterkid.feature.paint_list;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.List;

import io.reactivex.Single;
import ir.mohammadhf.painterkid.base.BaseViewModel;
import ir.mohammadhf.painterkid.data.PaintListRepository;
import ir.mohammadhf.painterkid.data.model.PaintModel;

public class PaintListViewModel extends BaseViewModel {
    private PaintListRepository repository;

    public PaintListViewModel(PaintListRepository repository) {
        this.repository = repository;
    }

    public Single<List<PaintModel>> getPaintsList(Context context, String folderName) {
        return repository.extractPaintList(context, folderName)
                .map(paintModels -> {
                    for (PaintModel paintModel :
                            paintModels) {
                        if (paintModel.getBitmap() != null) {
//                            Bitmap bitmap = paintModel.getBitmap();
                            int width = paintModel.getBitmap().getWidth();
                            int height = paintModel.getBitmap().getHeight();

                            paintModel.setBitmap(Bitmap.createBitmap(paintModel.getBitmap(), (width - height) / 2, 0, height, height));
                        }
                    }
                    return paintModels;
                });
    }
}
