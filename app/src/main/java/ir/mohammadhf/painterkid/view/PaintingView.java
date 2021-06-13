package ir.mohammadhf.painterkid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.mohammadhf.painterkid.R;
import ir.mohammadhf.painterkid.feature.paint.TouchEvent;

public class PaintingView extends View {
    private static final String TAG = "ColorTag";

    public static final int PAINT_MODE_PENCIL = 1;
    public static final int PAINT_MODE_MARKER = 2;
    public static final int PAINT_MODE_BUCKET = 3;
    public static final int PAINT_MODE_CRAYONS = 4;
    public static final int PAINT_MODE_SPRAY = 5;
    public static final int PAINT_MODE_PATTERN = 6;

    private final int MODE_PIXEL_LOCKED = -1;
    private final int MODE_PIXEL_DEFAULT = 0;
    private final int MODE_PIXEL_CHANGEABLE = 1;

    private final int MARKER_STROKE_WIDTH = 60;
    private final int CRAYON_STROKE_WIDTH_LARGE = 70;
    private final int PENCIL_STROKE_WIDTH = 30;

    private boolean isCompletelyLoaded;

    private Paint paint;
    private Path path;
    private Bitmap bitmap;
    private Canvas tempCanvas;

    private List<Bitmap> bitmapList = new ArrayList<>();

    private boolean isTouchUpEvent;
    private boolean isBucketMode = false;

    private int[] currentBitmapPixels;
    private int[] pixelsMode;
    private int[] tempPixels;
    private int mode;
    private int paintAlpha;

    public int[] getCurrentBitmapPixels() {
        return currentBitmapPixels;
    }

    public PaintingView(Context context) {
        this(context, null);
    }

    public PaintingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initial(attrs);
    }

    private void initial(AttributeSet attrs) {
        isCompletelyLoaded = false;

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        setStrokeWidth(MARKER_STROKE_WIDTH);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PaintingView);
        Log.i(TAG, "setting color from type array");
        setCurrentColor(typedArray.getColor(R.styleable.PaintingView_default_color, Color.RED));
        typedArray.recycle();

        path = new Path();

        setMode(PAINT_MODE_MARKER);
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap source) {
        if (this.bitmap == null) {
            int width = getWidth();
            int height = getHeight();
            this.bitmap = Bitmap.createScaledBitmap(source, width, height, false);

            if (!this.bitmap.isMutable())
                this.bitmap = this.bitmap.copy(Bitmap.Config.ARGB_8888, true);

            currentBitmapPixels = new int[width * height];
            this.bitmap.getPixels(currentBitmapPixels, 0, width, 0, 0, width, height);
            tempPixels = Arrays.copyOf(currentBitmapPixels, currentBitmapPixels.length);

            setTempCanvas(getBitmap());
        }
    }

    public void setPixelModes(Bitmap rawBMP) {
        int width = getWidth();
        int height = getHeight();
        rawBMP = Bitmap.createScaledBitmap(rawBMP, width, height, false);
        int[] pixels = new int[width * height];
        rawBMP.getPixels(pixels, 0, width, 0, 0, width, height);

        pixelsMode = new int[width * height];
        for (int i = 0; i < pixels.length; i++) {
            if (((pixels[i] >> 16) & 0xff) < 150) {
                pixelsMode[i] = MODE_PIXEL_LOCKED;
            } else {
                pixelsMode[i] = MODE_PIXEL_DEFAULT;
            }
        }

        if (!isCompletelyLoaded) {
            isCompletelyLoaded = true;
            postInvalidate();
        }
    }

    public int getStrokeWidth() {
        return (int) this.paint.getStrokeWidth();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.paint.setStrokeWidth(strokeWidth);
    }

    public int getCurrentColor() {
        return this.paint.getColor();
    }

    public void setCurrentColor(int currentColor) {
        Log.i(TAG, "setting color to paint = " + currentColor);
        paint.setColor(currentColor);
        paint.setAlpha(paintAlpha);
    }

    public void setTempCanvas(Bitmap bitmap) {
        if (tempCanvas == null) {

            tempCanvas = new Canvas(bitmap);
            postInvalidate();
        } else {
            tempCanvas.setBitmap(bitmap);
        }
    }

    public void setShader(int newPatternID) {
        if (isPaintLoaded()) {
            if (isPatternIdValid(newPatternID)) {
                invalidate();

                Bitmap patternBMP = BitmapFactory.decodeResource(getResources(), newPatternID);

                BitmapShader bitmapSHDR = new BitmapShader(patternBMP, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
                paint.setStrokeWidth(MARKER_STROKE_WIDTH);
                paint.setShader(bitmapSHDR);
            } else
                paint.setShader(null);
        }
    }

    private boolean isPaintLoaded() {
        return bitmap != null & tempCanvas != null & paint != null;
    }

    private boolean isPatternIdValid(int newPatternID) {
        return newPatternID > 0;
    }

    public int getOpacity() {
        return paint.getAlpha() / 255 * 100;
    }

    public void setOpacity(int opacity) {
        paint.setAlpha(opacity / 100 * 255);
    }

    public void setMode(int mode) {
        this.mode = mode;

        isBucketMode = false;
        paint.setAlpha(255);
        paintAlpha = 255;

        switch (this.mode) {
            case PAINT_MODE_PENCIL:
                paint.setStrokeWidth(PENCIL_STROKE_WIDTH);
                break;
            case PAINT_MODE_CRAYONS:
                paint.setAlpha(30);
                paintAlpha = 30;
                paint.setStrokeWidth(CRAYON_STROKE_WIDTH_LARGE);
                break;
            case PAINT_MODE_MARKER:
                paint.setStrokeWidth(MARKER_STROKE_WIDTH);
                break;
            case PAINT_MODE_BUCKET:
                isBucketMode = true;
                break;

            case PAINT_MODE_SPRAY:
                break;
            case PAINT_MODE_PATTERN:
                break;
        }
    }

    public int getMode() {
        return mode;
    }

    private void addToBitmapList() {
        if (bitmapList.size() == 3) {
            bitmapList.remove(0);
        }

        bitmapList.add(Bitmap.createBitmap(this.bitmap));
    }

    public void undoBitmap() {
        if (bitmapList.size() > 0) {
            int lastIndexOfBitmapList = bitmapList.size() - 1;

            this.bitmap = bitmapList.get(lastIndexOfBitmapList);
            this.bitmap.getPixels(currentBitmapPixels, 0, getWidth(), 0, 0, getWidth(), getHeight());
            setTempCanvas(getBitmap());
            bitmapList.remove(lastIndexOfBitmapList);

            postInvalidate();
        }
    }

    public void clearView() {
        addToBitmapList();

        for (int i = 0; i < currentBitmapPixels.length; i++) {
            if (pixelsMode[i] != MODE_PIXEL_LOCKED) {
                currentBitmapPixels[i] = Color.WHITE;
            }
        }

        tempPixels = Arrays.copyOf(currentBitmapPixels, currentBitmapPixels.length);

        this.bitmap.setPixels(currentBitmapPixels, 0, getWidth(), 0, 0, getWidth(), getHeight());

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isCompletelyLoaded) {
            Log.i(TAG, "color is = " + paint.getColor());
            if (tempCanvas != null && bitmap != null) {
                tempCanvas.drawPath(path, paint);
                if (!isTouchUpEvent) {
                    bitmap.getPixels(currentBitmapPixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
                    for (int i = 0; i < currentBitmapPixels.length; i++) {
                        if (pixelsMode[i] != MODE_PIXEL_CHANGEABLE && currentBitmapPixels[i] != tempPixels[i]) {
                            currentBitmapPixels[i] = tempPixels[i];
                        }
                    }
                    bitmap.setPixels(currentBitmapPixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
                }
                canvas.drawBitmap(bitmap, 0, 0, null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isCompletelyLoaded) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchDownEvent(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_MOVE:
                    moveEvent(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_UP:
                    touchUpEvent(event.getX(), event.getY());
                    break;
            }
            postInvalidate();
        }
        return true;
    }

    private void touchDownEvent(float x, float y) {
        publishTouchEvent(TouchEvent.TOUCH_DOWN, (int) x, (int) y);

        isTouchUpEvent = false;

        path.moveTo(x, y);
        path.lineTo(x, y);

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Vector2D v2D = new Vector2D((int) x, (int) y);

        addToBitmapList();

        if (!isBucketMode) {
            simpleSetPixelsMode(v2D, width, height)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        } else {
            single(v2D, width, height);
        }
        bitmap.getPixels(tempPixels, 0, width, 0, 0, width, height);
    }

    private void moveEvent(float x, float y) {
        if (!isBucketMode)
            path.lineTo(x, y);
    }

    private void touchUpEvent(float x, float y) {
        publishTouchEvent(TouchEvent.TOUCH_UP, (int) x, (int) y);

        isTouchUpEvent = true;

        for (int i = 0; i < pixelsMode.length; i++) {
            if (pixelsMode[i] == MODE_PIXEL_CHANGEABLE) {
                pixelsMode[i] = MODE_PIXEL_DEFAULT;
            }
        }

        path.reset();
    }

    private Completable simpleSetPixelsMode(Vector2D v2D, int width, int height) {
        return Completable.create(emitter -> {
            if (pixelsMode[v2D.x + (v2D.y - 1) * width] != -1) {
                Queue<Vector2D> queue = new ArrayDeque<>();
                queue.add(v2D);

                final Vector2D xDirectionVector = new Vector2D(1, 0);
                final Vector2D yDirectionVector = new Vector2D(0, 1);

                int color = paint.getColor();

                while (!queue.isEmpty()) {
                    Vector2D temp2D = queue.remove();

                    int i;
                    if (temp2D.y == 0) {
                        i = temp2D.x;
                    } else {
                        i = temp2D.x + (temp2D.y - 1) * width;
                    }


                    if (pixelsMode[i] != MODE_PIXEL_LOCKED & pixelsMode[i] != MODE_PIXEL_CHANGEABLE) {
                        if (isBucketMode) {
                            currentBitmapPixels[i] = color;
                        }
                        pixelsMode[i] = MODE_PIXEL_CHANGEABLE;

                        //add up pixel
                        if (temp2D.y > 0 && (i > width && pixelsMode[i - width] == MODE_PIXEL_DEFAULT)) {
                            queue.add(Vector2D.subtract(temp2D, yDirectionVector));
                        }

                        //add down pixel
                        if (temp2D.y < height - 1 && (i + width < pixelsMode.length && pixelsMode[i + width] == MODE_PIXEL_DEFAULT)) {
                            queue.add(Vector2D.add(temp2D, yDirectionVector));
                        }

                        //add left pixel
                        if (temp2D.x > 0 && pixelsMode[i - 1] == MODE_PIXEL_DEFAULT) {
                            queue.add(Vector2D.subtract(temp2D, xDirectionVector));
                        }

                        //add right pixel
                        if (temp2D.x < width - 1 && pixelsMode[i + 1] == MODE_PIXEL_DEFAULT) {
                            queue.add(Vector2D.add(temp2D, xDirectionVector));
                        }
                    }
                }
            }
        });
    }

    private void single(Vector2D v2D, int width, int height) {
        if (pixelsMode[v2D.x + (v2D.y - 1) * width] != -1) {
            Queue<Vector2D> queue = new ArrayDeque<>();
            queue.add(v2D);

            final Vector2D xDirectionVector = new Vector2D(1, 0);
            final Vector2D yDirectionVector = new Vector2D(0, 1);

            int color = paint.getColor();

            while (!queue.isEmpty()) {
                Vector2D temp2D = queue.remove();

                int i;
                if (temp2D.y == 0) {
                    i = temp2D.x;
                } else {
                    i = temp2D.x + (temp2D.y - 1) * width;
                }


                if (pixelsMode[i] != MODE_PIXEL_LOCKED & pixelsMode[i] != MODE_PIXEL_CHANGEABLE) {
                    if (isBucketMode) {
                        currentBitmapPixels[i] = color;
                    }
                    pixelsMode[i] = MODE_PIXEL_CHANGEABLE;

                    //add up pixel
                    if (temp2D.y > 0 && (i > width && pixelsMode[i - width] == MODE_PIXEL_DEFAULT)) {
                        queue.add(Vector2D.subtract(temp2D, yDirectionVector));
                    }

                    //add down pixel
                    if (temp2D.y < height - 1 && (i + width < pixelsMode.length && pixelsMode[i + width] == MODE_PIXEL_DEFAULT)) {
                        queue.add(Vector2D.add(temp2D, yDirectionVector));
                    }

                    //add left pixel
                    if (temp2D.x > 0 && pixelsMode[i - 1] == MODE_PIXEL_DEFAULT) {
                        queue.add(Vector2D.subtract(temp2D, xDirectionVector));
                    }

                    //add right pixel
                    if (temp2D.x < width - 1 && pixelsMode[i + 1] == MODE_PIXEL_DEFAULT) {
                        queue.add(Vector2D.add(temp2D, xDirectionVector));
                    }
                }
            }

            if (isBucketMode)
                bitmap.setPixels(currentBitmapPixels, 0, width, 0, 0, width, height);
        }
    }

    private void publishTouchEvent(int touchID, int xTouch, int yTouch) {
        TouchEvent touchEvent = new TouchEvent(touchID);
        touchEvent.setCoordinate(xTouch, yTouch);

        EventBus.getDefault().post(touchEvent);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        EventBus.getDefault().post(true);
    }
}
