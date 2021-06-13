package ir.mohammadhf.painterkid.data.model;

import android.graphics.Bitmap;

public class PaintModel {
    private Bitmap bitmap;
    private String nameWithFormat;
    private String rawName;
    private String paintCategory;

    private int paintHolderId;
    private int paintButtonBgId;
    private int nameColor;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getNameWithFormat() {
        return nameWithFormat;
    }

    public void setNameWithFormat(String name) {
        if (!name.contains(".")) {
            name += ".jpg";
        }
        this.nameWithFormat = name;
        int dotPos = name.indexOf(".");
        setRawName(name.substring(0, dotPos));
    }

    public String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    public int getPaintHolderId() {
        return paintHolderId;
    }

    public void setPaintHolderId(int paintHolderId) {
        this.paintHolderId = paintHolderId;
    }

    public int getPaintButtonBgId() {
        return paintButtonBgId;
    }

    public void setPaintButtonBgId(int paintButtonBgId) {
        this.paintButtonBgId = paintButtonBgId;
    }

    public int getNameColor() {
        return nameColor;
    }

    public void setNameColor(int nameColor) {
        this.nameColor = nameColor;
    }

    public String getPaintCategory() {
        return paintCategory;
    }

    public void setPaintCategory(String paintCategory) {
        this.paintCategory = paintCategory;
    }
}
