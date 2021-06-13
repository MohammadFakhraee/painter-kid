package ir.mohammadhf.painterkid.feature.iapurchase;

public class PurchaseDetail {
    private String product_id;
    private PurchaseResult purchaseResult;

    public PurchaseDetail(String product_id, String purchaseMessage, boolean isPurchased) {
        this(product_id, new PurchaseResult(purchaseMessage, isPurchased));
    }

    public PurchaseDetail(String product_id, PurchaseResult purchaseResult) {
        this.product_id = product_id;
        this.purchaseResult = purchaseResult;
    }

    public PurchaseDetail() {
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public PurchaseResult getPurchaseResult() {
        return purchaseResult;
    }

    public void setPurchaseResult(PurchaseResult purchaseResult) {
        this.purchaseResult = purchaseResult;
    }
}
