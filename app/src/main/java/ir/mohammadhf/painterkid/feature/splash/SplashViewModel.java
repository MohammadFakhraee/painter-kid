package ir.mohammadhf.painterkid.feature.splash;

import java.util.List;

import ir.mohammadhf.painterkid.base.BaseViewModel;
import ir.mohammadhf.painterkid.feature.iapurchase.PurchaseDetail;
import ir.mohammadhf.painterkid.feature.iapurchase.PurchaseHandler;

public class SplashViewModel extends BaseViewModel {
    private PurchaseHandler purchaseHandler;

    public SplashViewModel(PurchaseHandler purchaseHandler) {
        this.purchaseHandler = purchaseHandler;
    }

    public void handlePurchasedItems(List<PurchaseDetail> purchaseDetailList) {
        for (PurchaseDetail purchaseDetailTemp :
                purchaseDetailList) {
            setPurchaseState(purchaseDetailTemp.getProduct_id(), purchaseDetailTemp.getPurchaseResult().isPurchased());
        }
    }

    private void setPurchaseState(String productId, boolean purchased) {
        switch (productId) {
            case PurchaseHandler.PRODUCT_PREMIUM_ACCOUNT:
                purchaseHandler.setPremiumAccount(purchased);
                break;
            case PurchaseHandler.PRODUCT_PACKAGE_PRINCESS:
                purchaseHandler.setPackagePrincessPurchased(purchased);
                break;
            case PurchaseHandler.PRODUCT_PACKAGE_ANIMATION_CHARACTER:
                purchaseHandler.setPackageAnimationCharacterPurchased(purchased);
                break;
            case PurchaseHandler.PRODUCT_PACKAGE_FOOD:
                purchaseHandler.setPackageFoodPurchased(purchased);
                break;
            case PurchaseHandler.PRODUCT_PACKAGE_TRANSPORT:
                purchaseHandler.setPackageTransportPurchased(purchased);
                break;
        }
    }

    public PurchaseHandler getPurchaseHandler() {
        return purchaseHandler;
    }
}
