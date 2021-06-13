package ir.mohammadhf.painterkid.feature.iapurchase;

public class PurchaseResult {
    private String message;
    private boolean isPurchased;

    public PurchaseResult(String message, boolean isPurchased) {
        this.message = message;
        this.isPurchased = isPurchased;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
