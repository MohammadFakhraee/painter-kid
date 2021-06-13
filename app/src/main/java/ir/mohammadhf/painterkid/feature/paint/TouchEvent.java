package ir.mohammadhf.painterkid.feature.paint;

public class TouchEvent {
    public static final int TOUCH_DOWN = 1;
    public static final int TOUCH_MOVE = 2;
    public static final int TOUCH_UP = 3;

    private int touchID;
    private int xTouch;
    private int yTouch;

    public TouchEvent(int touchID) {
        this.touchID = touchID;
    }

    public int getTouchID() {
        return touchID;
    }

    public void setCoordinate(int xTouch, int yTouch) {
        this.xTouch = xTouch;
        this.yTouch = yTouch;
    }

    public int getxTouch() {
        return xTouch;
    }

    public int getyTouch() {
        return yTouch;
    }
}
