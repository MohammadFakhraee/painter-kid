package ir.mohammadhf.painterkid.feature.parent.fargments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;

import io.reactivex.disposables.CompositeDisposable;
import ir.mohammadhf.painterkid.R;
import ir.mohammadhf.painterkid.data.model.Purchasable;
import ir.mohammadhf.painterkid.feature.iapurchase.PurchaseHandler;
import ir.mohammadhf.painterkid.feature.parent.ParentActivity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PurchasableListFragment extends Fragment {
    private PurchaseAdapter purchaseAdapter;
    private RecyclerView purchaseRv;
    private Button purchaseBtn;
    private TextView titleTv;
    private View waitingProgressFl;
    private View premiumShowLayout;
    private View connectionFailedLayout;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_purchasables, container, false);
        purchaseRv = view.findViewById(R.id.rv_purchasable_purchasableList);
        purchaseBtn = view.findViewById(R.id.btn_purchasable_purchase);
        premiumShowLayout = view.findViewById(R.id.ll_purchasable_premiumShow);
        connectionFailedLayout = view.findViewById(R.id.ll_purchasable_connectionFailed);
        titleTv = view.findViewById(R.id.tv_purchasable_title);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            purchaseBtn.setStateListAnimator(null);
        }
        waitingProgressFl = view.findViewById(R.id.fl_purchasable_waiting);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PurchaseHandler purchaseHandler = ((ParentActivity) requireActivity()).getPurchaseHandler();

        waitingProgressFl.setVisibility(VISIBLE);
        connectionFailedLayout.setVisibility(GONE);

        compositeDisposable.add(purchaseHandler
                .getProductDetailsBehaviorSub().subscribe(purchasableList -> {
                    waitingProgressFl.setVisibility(GONE);
                    connectionFailedLayout.setVisibility(GONE);
                    boolean reverse = false;
                    for (int i = 0; i < purchasableList.size(); i++) {
                        Purchasable purchasable = purchasableList.get(i);
                        String skuStr = purchasable.getPurchaseSku();
                        switch (skuStr) {
                            case PurchaseHandler.PRODUCT_PREMIUM_ACCOUNT:
                                if (i == purchasableList.size() - 1)
                                    reverse = true;
                                purchasable.setImageId(R.raw.lottie_premium);
                                break;
                            case PurchaseHandler.PRODUCT_PACKAGE_PRINCESS:
                                purchasable.setImageId(R.drawable.gif_main_avatar_princess);
                                break;
                            case PurchaseHandler.PRODUCT_PACKAGE_ANIMATION_CHARACTER:
                                purchasable.setImageId(R.drawable.gif_main_avatar_cartoon);
                                break;
                            case PurchaseHandler.PRODUCT_PACKAGE_FOOD:
                                purchasable.setImageId(R.drawable.gif_main_avatar_food);
                                break;
                            case PurchaseHandler.PRODUCT_PACKAGE_TRANSPORT:
                                purchasable.setImageId(R.drawable.gif_main_avatar_vehicle);
                                break;
                        }
                    }
                    premiumShowLayout.setVisibility(purchasableList.size() == 0
                            ? VISIBLE : GONE);
                    purchaseRv.setVisibility(purchasableList.size() == 0
                            ? GONE : VISIBLE);
                    purchaseBtn.setVisibility(purchasableList.size() == 0
                            ? GONE : VISIBLE);
                    titleTv.setVisibility(purchasableList.size() == 0
                            ? GONE : VISIBLE);

                    if (reverse)
                        Collections.reverse(purchasableList);
                    purchaseAdapter = new PurchaseAdapter(purchasableList);
                    purchaseRv.setAdapter(purchaseAdapter);
                }));

        compositeDisposable.add(purchaseHandler
                .getErrorBehaviorSub().subscribe(s -> {
                    purchaseRv.setVisibility(GONE);
                    purchaseBtn.setVisibility(GONE);
                    titleTv.setVisibility(GONE);
                    connectionFailedLayout.setVisibility(VISIBLE);
                    Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                    waitingProgressFl.setVisibility(GONE);
                }));

        purchaseRv.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        purchaseBtn.setOnClickListener(view1 -> {
            Purchasable purchasable = purchaseAdapter.getSelectedItem();
            if (purchasable != null) {
                purchaseHandler.purchaseProduct(requireActivity(),
                        purchasable.getPurchaseSku(), 30001);
            } else {
                // User didn't select any purchasable item
            }
        });

        compositeDisposable.add(purchaseHandler
                .getSinglePurchaseDetailBehaviorSub().subscribe(purchaseDetail -> {
                    if (purchaseDetail.getPurchaseResult().isPurchased()) {
                        switch (purchaseDetail.getProduct_id()) {
                            case PurchaseHandler.PRODUCT_PREMIUM_ACCOUNT:
                                purchaseHandler.setPremiumAccount(true);
                                break;
                            case PurchaseHandler.PRODUCT_PACKAGE_FOOD:
                                purchaseHandler.setPackageFoodPurchased(true);
                                break;
                            case PurchaseHandler.PRODUCT_PACKAGE_ANIMATION_CHARACTER:
                                purchaseHandler.setPackageAnimationCharacterPurchased(true);
                                break;
                            case PurchaseHandler.PRODUCT_PACKAGE_TRANSPORT:
                                purchaseHandler.setPackageTransportPurchased(true);
                                break;
                            case PurchaseHandler.PRODUCT_PACKAGE_PRINCESS:
                                purchaseHandler.setPackagePrincessPurchased(true);
                                break;
                        }
                    }
                }));
    }
}
