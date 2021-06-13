package ir.mohammadhf.painterkid.feature.paint_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import ir.mohammadhf.painterkid.R;
import ir.mohammadhf.painterkid.data.model.PaintModel;
import ir.mohammadhf.painterkid.utils.AnimationMaker;

public class PaintListAdapter extends RecyclerView.Adapter<PaintListAdapter.PaintViewHolder> {
    public static final String EXTRA_FOLDER_NAME = "FOLDER_NAME";
    public static final String EXTRA_FILE_NAME = "FILE_NAME";
    public static final String EXTRA_CATEGORY = "CATEGORY";
    private List<PaintModel> paintModels;
    private String folderName;
    private final int DEF_ANIM_DURATION = 500;

    public PaintListAdapter(List<PaintModel> paintModels, String folderName) {
        this.paintModels = paintModels;
        this.folderName = folderName;
    }

    @NonNull
    @Override
    public PaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PaintViewHolder(
                LayoutInflater.from(
                        parent.getContext()
                ).inflate(
                        R.layout.item_paint, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PaintViewHolder holder, int position) {
        holder.bindViews(paintModels.get(position));
    }

    @Override
    public int getItemCount() {
        return paintModels.size();
    }

    public class PaintViewHolder extends RecyclerView.ViewHolder {
        private ImageView paintRiv;
        private ImageView paintHolderIv;
        private Button paintNameBtn;

        public PaintViewHolder(@NonNull View itemView) {
            super(itemView);
            paintRiv = itemView.findViewById(R.id.riv_itemPaint_paint);
            paintHolderIv = itemView.findViewById(R.id.iv_itemPaint_paintHolder);
            paintNameBtn = itemView.findViewById(R.id.btn_itemPaint_name);
        }

        public void bindViews(PaintModel paintModel) {
            paintRiv.startAnimation(AnimationMaker.fadeIn(DEF_ANIM_DURATION + getAdapterPosition() * 100));
            paintRiv.setOnClickListener(v -> {
                PaintChooseEvent paintChooseEvent = new PaintChooseEvent(folderName, paintModel.getNameWithFormat(), paintModel.getPaintCategory());
                EventBus.getDefault().post(paintChooseEvent);
            });
            paintRiv.setImageBitmap(paintModel.getBitmap());

            paintNameBtn.setText(paintModel.getRawName());
            paintNameBtn.setBackgroundResource(paintModel.getPaintButtonBgId());
            paintNameBtn.setTextColor(paintModel.getNameColor());

            paintHolderIv.setImageResource(paintModel.getPaintHolderId());
        }
    }
}
