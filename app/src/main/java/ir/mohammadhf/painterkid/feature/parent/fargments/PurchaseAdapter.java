package ir.mohammadhf.painterkid.feature.parent.fargments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.mohammadhf.painterkid.R;
import ir.mohammadhf.painterkid.data.model.Purchasable;
import ir.mohammadhf.painterkid.feature.iapurchase.PurchaseHandler;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseItemViewHolder> {

    private List<Purchasable> purchasableList;
    private int currentSelectedItem;

    public PurchaseAdapter(List<Purchasable> purchasableList) {
        this.purchasableList = purchasableList;
        currentSelectedItem = -1;
    }

    @NonNull
    @Override
    public PurchaseItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PurchaseItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_purchasable, parent, false
                ));
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseItemViewHolder holder, int position) {
        holder.onBindView(purchasableList.get(position));
    }

    @Override
    public int getItemCount() {
        return purchasableList.size();
    }

    public Purchasable getSelectedItem() {
        return currentSelectedItem != -1 ? purchasableList.get(currentSelectedItem) : null;
    }

    public class PurchaseItemViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTv;
        private TextView priceTv;
        private TextView detailTv;
        private ImageView gifIv;
        private View cardViewBG;
        private View lottieIv;

        public PurchaseItemViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.tv_purchasableItem_title);
            priceTv = itemView.findViewById(R.id.tv_purchasableItem_price);
            detailTv = itemView.findViewById(R.id.tv_purchasableItem_detail);
            gifIv = itemView.findViewById(R.id.iv_purchasableItem_image);
            lottieIv = itemView.findViewById(R.id.lav_purchasableItem_image);
            cardViewBG = itemView.findViewById(R.id.cv_purchasableItem_selectedBG);
        }

        public void onBindView(Purchasable purchasable) {

            if (purchasable.getPurchaseSku().equals(PurchaseHandler.PRODUCT_PREMIUM_ACCOUNT)) {
                lottieIv.setVisibility(View.VISIBLE);
                gifIv.setVisibility(View.GONE);
            } else {
                lottieIv.setVisibility(View.GONE);
                gifIv.setVisibility(View.VISIBLE);
            }


            titleTv.setText(purchasable.getTitle());

            priceTv.setText(purchasable.getPrice());

            detailTv.setText(purchasable.getDetail());

            gifIv.setImageResource(purchasable.getImageId());

            if (currentSelectedItem == getAdapterPosition()) {
                cardViewBG.setVisibility(View.VISIBLE);
            } else
                cardViewBG.setVisibility(View.GONE);

            itemView.setOnClickListener(view -> {
                if (currentSelectedItem != getAdapterPosition()) {
                    currentSelectedItem = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
