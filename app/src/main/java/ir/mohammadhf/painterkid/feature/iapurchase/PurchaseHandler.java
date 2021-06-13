package ir.mohammadhf.painterkid.feature.iapurchase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.BehaviorSubject;
import ir.mohammadhf.painterkid.BuildConfig;
import ir.mohammadhf.painterkid.R;
import ir.mohammadhf.painterkid.data.model.Purchasable;
import ir.mohammadhf.painterkid.utils.util.IabHelper;
import ir.mohammadhf.painterkid.utils.util.IabResult;
import ir.mohammadhf.painterkid.utils.util.Inventory;
import ir.mohammadhf.painterkid.utils.util.Purchase;
import ir.mohammadhf.painterkid.utils.util.SkuDetails;

public class PurchaseHandler implements IabHelper.OnIabSetupFinishedListener,
        IabHelper.QueryInventoryFinishedListener,
        IabHelper.OnIabPurchaseFinishedListener {
    public static final String PRODUCT_PACKAGE_PRINCESS = "package_princess";
    public static final String PRODUCT_PACKAGE_TRANSPORT = "package_transport";

    // Should keep the following product name(s) for all of the marketplaces such as Bazaar or Google play ... .
    public static final String PRODUCT_PREMIUM_ACCOUNT = "premium_account";
    public static final String PRODUCT_PACKAGE_FOOD = "package_food";
    public static final String PRODUCT_PACKAGE_ANIMATION_CHARACTER = "package_animation_character";
    private static final String TAG = "PurchaseHandler";
    private static final String BASE_64_PUBLIC_KEY = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwC6BJCRL06lBC7RaGP6Z/LLK7/eY9+btry7c+ZXjeNQ1w2seh6Y3QmvwBy2aaEVCuxVTM1jULKYCPjCE7//DyYzT2z+jj20xjNwg6tbssUfI9r0JkROJC6UbPcMPoQ/b5STKgsRSkbuBDc+LrfPuBOtCNY9CDXTfY0bJgqV0Yf0rf97i7UNBbQHNyhFPCDR8y8CGfbmGrnxkqfx3o0emMLGiOwYzPqfSN4lH/nGD98CAwEAAQ==";
    // If purchase was successful, this code works.
    private static final int PURCHASE_STATE_SUCCESS = 0;
    private static boolean premiumAccount = BuildConfig.isDebuging;
    private static boolean packagePrincessPurchased = BuildConfig.isDebuging;
    private static boolean packageTransportPurchased = BuildConfig.isDebuging;
    private static boolean packageFoodPurchased = BuildConfig.isDebuging;
    private static boolean packageAnimationCharacterPurchased = BuildConfig.isDebuging;

    private IabHelper iabHelper;

    private Context context;
    // This variable is used to check if user code wants only purchased items or all in-app purchase items.
    private boolean purchasedOnly;

    //Should keep the following BehaviorSubjects for all of the marketplaces such as Bazaar or GooglePlay ... .
    private BehaviorSubject<List<Purchasable>> productDetailsBehaviorSub =
            BehaviorSubject.create();
    private BehaviorSubject<List<PurchaseDetail>> allPurchaseDetailBehaviorSub =
            BehaviorSubject.create();
    private BehaviorSubject<PurchaseDetail> singlePurchaseDetailBehaviorSub =
            BehaviorSubject.create();
    private BehaviorSubject<String> errorBehaviorSub =
            BehaviorSubject.create();

    public static boolean isPremiumAccount() {
        return premiumAccount;
    }

    public void setPremiumAccount(boolean isPremiumAccount) {
        PurchaseHandler.premiumAccount = isPremiumAccount;
    }

    public static boolean isPackagePrincessPurchased() {
        return packagePrincessPurchased;
    }

    public void setPackagePrincessPurchased(boolean packagePrincessPurchased) {
        PurchaseHandler.packagePrincessPurchased = packagePrincessPurchased;
    }

    public static boolean isPackageTransportPurchased() {
        return packageTransportPurchased;
    }

    public void setPackageTransportPurchased(boolean packageTransportPurchased) {
        PurchaseHandler.packageTransportPurchased = packageTransportPurchased;
    }

    public static boolean isPackageFoodPurchased() {
        return packageFoodPurchased;
    }

    public void setPackageFoodPurchased(boolean packageFoodPurchased) {
        PurchaseHandler.packageFoodPurchased = packageFoodPurchased;
    }

    public static boolean isPackageAnimationCharacterPurchased() {
        return packageAnimationCharacterPurchased;
    }

    public void setPackageAnimationCharacterPurchased(boolean packageAnimationCharacterPurchased) {
        PurchaseHandler.packageAnimationCharacterPurchased = packageAnimationCharacterPurchased;
    }

    //Should keep the following method for all of the marketplaces such as Bazaar or GooglePlay ... .
    //This method is used to check whether user has purchased anything from "in-app purchase" or not.
    //The result will return with "allPurchaseDetailBehaviorSub". So any Activity that wants to handle purchased products,
    // should call "getAllPurchaseDetailBehaviorSub" method and subscribe to BehaviorSubject.
    public void getProductPurchasesFromMarket(Context context) {
        Log.i(TAG, "getting only purchased items from Bazaar.");
        purchasedOnly = true;
        startSetup(context);
    }

    //Should keep the following method for all of the marketplaces such as Bazaar or GooglePlay ... .
    // This method is used to get every available products (and their detail) the app has.
    // The result will return with "productDetailsBehaviorSub". So any Activity that wants to get product details,
    // should call "getProductDetailsBehaviorSub" method and subscribe to BehaviorSubject.
    public void getAllProductsDetailFromMarket(Context context) {
        Log.i(TAG, "getting all product's detail from Bazaar.");
        purchasedOnly = false;
        startSetup(context);
    }

    //Should keep the following method for all of the marketplaces such as Bazaar or GooglePlay ... .
    public BehaviorSubject<PurchaseDetail> getSinglePurchaseDetailBehaviorSub() {
        return singlePurchaseDetailBehaviorSub;
    }

    //Should keep the following method for all of the marketplaces such as Bazaar or GooglePlay ... .
    private void startSetup(Context context) {
        this.context = context;
        iabHelper = new IabHelper(context, BASE_64_PUBLIC_KEY);
        iabHelper.startSetup(this);
    }

    //Should keep the following method for all of the marketplaces such as Bazaar or GooglePlay ... .
    public void purchaseProduct(Activity act, String productId, int requestCode) {
        iabHelper.launchPurchaseFlow(act, productId, requestCode, this);
    }

    //Should keep the following method for all of the marketplaces such as Bazaar or GooglePlay ... .
    public boolean handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        return iabHelper.handleActivityResult(requestCode, resultCode, data);
    }

    //Should keep the following method for all of the marketplaces such as Bazaar or GooglePlay ... .
    //This method is used to free space that "iabHelper" has held.
    public void dismiss() {
        if (iabHelper != null) {
            iabHelper.dispose();
            iabHelper = null;
        }
    }

    //Should keep the following method for all of the marketplaces such as Bazaar or GooglePlay ... .
    public BehaviorSubject<List<PurchaseDetail>> getAllPurchaseDetailBehaviorSub() {
        return allPurchaseDetailBehaviorSub;
    }

    //Should keep the following method for all of the marketplaces such as Bazaar or GooglePlay ... .
    public BehaviorSubject<List<Purchasable>> getProductDetailsBehaviorSub() {
        return productDetailsBehaviorSub;
    }

    //Should keep the following method for all of the marketplaces such as Bazaar or GooglePlay ... .
    public BehaviorSubject<String> getErrorBehaviorSub() {
        return errorBehaviorSub;
    }

    //Should change the following method according to the marketplace. This is done for Bazaar.
    @Override
    public void onIabSetupFinished(IabResult result) {
        if (result.isSuccess()) {
            // Connection to market is successful. you can send a request here.
            if (purchasedOnly) {
                Log.i(TAG, "requesting for owned products");
                iabHelper.queryInventoryAsync(this);
            } else {
                Log.i(TAG, "requesting for all available products");
                List<String> productsList = new ArrayList<>();
                productsList.add(PRODUCT_PREMIUM_ACCOUNT);
                productsList.add(PRODUCT_PACKAGE_PRINCESS);
                productsList.add(PRODUCT_PACKAGE_TRANSPORT);
                productsList.add(PRODUCT_PACKAGE_FOOD);
                productsList.add(PRODUCT_PACKAGE_ANIMATION_CHARACTER);
                iabHelper.queryInventoryAsync(true, productsList, this);
            }
        } else {
            errorBehaviorSub.onNext(context.getString(R.string.purchase_message_error_setup));
        }
    }

    //Should change the following method according to the marketplace. This is done for Bazaar.
    @Override
    public void onQueryInventoryFinished(IabResult result, Inventory inv) {
        if (result.isSuccess()) {
            if (purchasedOnly) {
                List<PurchaseDetail> myAppPurchaseDetailList = new ArrayList<>();

                List<Purchase> marketPurchaseDetailList = inv.getAllPurchases();
                for (int i = 0; i < marketPurchaseDetailList.size(); i++) {
                    Purchase purchase = marketPurchaseDetailList.get(i);
                    myAppPurchaseDetailList.add(new PurchaseDetail(purchase.getSku(),
                            purchase.getPurchaseState() == PURCHASE_STATE_SUCCESS
                                    ? "Success" : "Failed",
                            purchase.getPurchaseState() == PURCHASE_STATE_SUCCESS));
                }

                allPurchaseDetailBehaviorSub.onNext(myAppPurchaseDetailList);
            } else {
                List<Purchasable> productsList = new ArrayList<>();

                List<SkuDetails> marketAllProducts = inv.getAllProductsWithoutPurchased();
                for (SkuDetails skuDetail :
                        marketAllProducts) {
                    Purchasable purchasable = new Purchasable();
                    purchasable.setTitle(skuDetail.getTitle());
                    purchasable.setPrice(skuDetail.getPrice());
                    purchasable.setDetail(skuDetail.getDescription());
                    purchasable.setPurchaseSku(skuDetail.getSku());
                    productsList.add(purchasable);
                }
                productDetailsBehaviorSub.onNext(productsList);
            }
        } else {
            errorBehaviorSub.onNext(context.getString(R.string.purchase_message_error_query));
        }
    }

    //Should change the following method according to the marketplace. This is done for Bazaar.
    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase info) {
        if (result.isSuccess()) {
            if (info != null) {
                singlePurchaseDetailBehaviorSub.onNext(new PurchaseDetail(info.getSku(),
                        "Success",
                        info.getPurchaseState() == PURCHASE_STATE_SUCCESS));
            }
        } else {
            errorBehaviorSub.onNext(context
                    .getString(R.string.purchase_message_error_failed_purchase));
        }
    }
}
