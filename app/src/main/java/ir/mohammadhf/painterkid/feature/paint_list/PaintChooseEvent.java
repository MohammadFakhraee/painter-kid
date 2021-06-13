package ir.mohammadhf.painterkid.feature.paint_list;

public class PaintChooseEvent {
    private String folderName;
    private String paintName;
    private String category;

    public PaintChooseEvent(String folderName, String paintName, String category) {
        this.folderName = folderName;
        this.paintName = paintName;
        this.category = category;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getPaintName() {
        return paintName;
    }

    public void setPaintName(String paintName) {
        this.paintName = paintName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
