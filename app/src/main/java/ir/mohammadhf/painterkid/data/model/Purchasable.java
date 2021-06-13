package ir.mohammadhf.painterkid.data.model;

public class Purchasable {
    private String purchaseSku;
    private String title;
    private String price;
    private String detail;
    private boolean isPurchased;

    private int imageId;

    public String getPurchaseSku() {
        return purchaseSku;
    }

    public void setPurchaseSku(String purchaseSku) {
        this.purchaseSku = purchaseSku;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }
}
