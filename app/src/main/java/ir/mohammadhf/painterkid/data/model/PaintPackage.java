package ir.mohammadhf.painterkid.data.model;

public class PaintPackage {
    private int id;
    private int image;
    private int titleDrawableId;
    private String title;
    private boolean isLocked;

    public PaintPackage(int id, String title, int image, int titleDrawableId, boolean isLocked) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.titleDrawableId = titleDrawableId;
        this.isLocked = isLocked;
    }

    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public int getTitleDrawableId() {
        return titleDrawableId;
    }

    public String getTitle() {
        return title;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
