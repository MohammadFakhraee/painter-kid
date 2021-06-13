package ir.mohammadhf.painterkid.data.model;

public class ColorPalette {
    private int colorID;
    private int backID;

    public ColorPalette(int colorID, int backID) {
        this.colorID = colorID;
        this.backID = backID;
    }

    public int getColorID() {
        return colorID;
    }

    public int getBackID() {
        return backID;
    }
}
