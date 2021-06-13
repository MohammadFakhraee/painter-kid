package ir.mohammadhf.painterkid.feature.paint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import ir.mohammadhf.painterkid.R;
import ir.mohammadhf.painterkid.data.model.ColorPalette;

public class ColorsAdapter extends RecyclerView.Adapter<ColorsAdapter.ColorViewHolder> {
    private OnItemSelected onItemSelected;

    private List<ColorPalette> colorPalettes;

    public ColorsAdapter(List<ColorPalette> colorPalettes) {
        this.colorPalettes = colorPalettes;
    }

    public ColorsAdapter() {
    }

    public void setColorPalettes(List<ColorPalette> colorPalettes) {
        this.colorPalettes = colorPalettes;
//        notifyDataSetChanged();
    }

    public void setOnItemSelected(OnItemSelected onItemSelected) {
        this.onItemSelected = onItemSelected;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ColorViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_color, parent, false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        holder.bindView(colorPalettes.get(position));
    }

    @Override
    public int getItemCount() {
        return colorPalettes.size();
    }

    public interface OnItemSelected {
        void onSelect(ColorPalette colorPalette);
    }

    public class ColorViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.riv_itemColor);
        }

        public void bindView(ColorPalette colorPalette) {
            imageView.setImageResource(colorPalette.getColorID());

            itemView.setOnClickListener(v -> {
                if (onItemSelected != null) {
                    onItemSelected.onSelect(colorPalette);
                }
            });
        }
    }
}
