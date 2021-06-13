package ir.mohammadhf.painterkid.feature.paint;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.mohammadhf.painterkid.BuildConfig;
import ir.mohammadhf.painterkid.R;
import ir.mohammadhf.painterkid.base.BaseActivity;
import ir.mohammadhf.painterkid.data.PaintRepository;
import ir.mohammadhf.painterkid.data.model.ColorPalette;
import ir.mohammadhf.painterkid.feature.iapurchase.PurchaseHandler;
import ir.mohammadhf.painterkid.feature.main.MainActivity;
import ir.mohammadhf.painterkid.feature.paint_list.PaintListActivity;
import ir.mohammadhf.painterkid.feature.paint_list.PaintListAdapter;
import ir.mohammadhf.painterkid.utils.AnimationMaker;
import ir.mohammadhf.painterkid.utils.MusicPlayerSingleton;
import ir.mohammadhf.painterkid.utils.SettingsSharedPreferences;
import ir.mohammadhf.painterkid.view.PaintingView;
import me.cheshmak.android.sdk.core.config.CheshmakConfig;
import me.cheshmak.cheshmakplussdk.advertise.CheshmakInterstitialAd;

public class PaintActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ColorTag";

    private static final int ADAPTER_COLOR_MODE = 1;
    private static final int ADAPTER_PATTERN_MODE = 2;
    private PaintingView paintingView;
    private RecyclerView colorsRv;
    private FrameLayout colorPaletteFl;
    private View brushHolderLL;
    private RoundedImageView colorHighlightRIV;
    private RoundedImageView eraserHighlightRIV;
    private RoundedImageView brushHighlightRIV;
    private RoundedImageView paletteRIV;
    private ImageView pencilIV;
    private ImageView markerIV;
    private ImageView bucketIV;
    private ImageView crayonIV;
    private ImageView patternIV;

    private PaintViewModel paintViewModel;

    private ColorsAdapter colorsAdapter;

    private SoundPool buttonPushSp;
    private int pushButtonSoundID;
    private int talkerVoiceID;
    private int talkerVoiceDuration;

    private String folderName;
    private String fileName;
    private String category;

    private boolean isInEraserMode = false;
    private boolean isFirstLoad = true;
    private int currentSelectedColorId;
    private int currentAdapterMode;
    private int currentShaderId;

    private MusicPlayerSingleton musicPlayerSingleton;

    private CheshmakInterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);

        EventBus.getDefault().register(this);

        paintViewModel = new PaintViewModel(new PaintRepository(),
                new SettingsSharedPreferences(this));
        setUpViews();
        observe();
        setUpAd();
    }

    private void setUpAd() {
        interstitialAd = new CheshmakInterstitialAd(this);
    }

    private void showAd() {
        paintViewModel.addAdsPeriodCounter();

        if (BuildConfig.isTestAds |
                (CheshmakConfig.getBoolean("show_add", false)
                        && !PurchaseHandler.isPremiumAccount()
                        && paintViewModel.canShowAd())) {
            interstitialAd.show();
        }
    }

    private void observe() {
        currentAdapterMode = ADAPTER_COLOR_MODE;
        setUpAdapter(currentAdapterMode);
    }

    private void setUpViews() {
        folderName = getIntent().getStringExtra(PaintListAdapter.EXTRA_FOLDER_NAME);
        fileName = getIntent().getStringExtra(PaintListAdapter.EXTRA_FILE_NAME);
        category = getIntent().getStringExtra(PaintListAdapter.EXTRA_CATEGORY);

        buttonPushSp = new SoundPool(2, AudioManager.STREAM_MUSIC, 100);
        int talkerVoiceRawID = getTalkerVoiceID();
        talkerVoiceID = buttonPushSp.load(this, talkerVoiceRawID, 1);
        talkerVoiceDuration = getTalkerDuration(talkerVoiceRawID);
        pushButtonSoundID = buttonPushSp.load(this, R.raw.push_button, 1);

        paintingView = findViewById(R.id.paintingView_paint);
        colorPaletteFl = findViewById(R.id.fl_paint_colors);
        brushHolderLL = findViewById(R.id.ll_paint_brushSelector);
        colorHighlightRIV = findViewById(R.id.riv_paint_colorShow);
        eraserHighlightRIV = findViewById(R.id.riv_paint_eraser);
        brushHighlightRIV = findViewById(R.id.riv_paint_brush);
        pencilIV = findViewById(R.id.iv_paint_brushPencil);
        markerIV = findViewById(R.id.iv_paint_brushMarker);
        bucketIV = findViewById(R.id.iv_paint_brushBucket);
        crayonIV = findViewById(R.id.iv_paint_brushCrayon);
        patternIV = findViewById(R.id.iv_paint_brushPattern);
        paletteRIV = findViewById(R.id.iv_paint_chooseColor);
        colorsRv = findViewById(R.id.rv_paint_colors);
        ImageView homeIv = findViewById(R.id.iv_paint_home);
        ImageView clearViewIv = findViewById(R.id.iv_paint_eraseAll);
        ImageView undoIv = findViewById(R.id.iv_paint_undo);
        ImageView eraseIv = findViewById(R.id.iv_paint_eraser);
        ImageView brushIV = findViewById(R.id.iv_paint_chooseBrush);
        ImageView saveIv = findViewById(R.id.iv_paint_save);
        ImageView soundIv = findViewById(R.id.iv_paint_sound);

        currentSelectedColorId = R.color.palette_blue_light;
        currentAdapterMode = ADAPTER_COLOR_MODE;
        currentShaderId = -1;

        homeIv.setOnClickListener(v -> saveIntoInternalStorage());

        soundIv.setOnClickListener(v -> {
            musicPlayerSingleton.decreaseToHalf();

            startSound(talkerVoiceID);
            compositeDisposable.add(Completable
                    .create(emitter -> {
                        if (!emitter.isDisposed()) {
                            Thread.sleep(talkerVoiceDuration);
                            emitter.onComplete();
                        }
                    })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        musicPlayerSingleton.increaseToMax();
                    }));
        });

        clearViewIv.setOnClickListener(v -> {
            handleItemSelection(v.getId());
            paintingView.clearView();

            startSound(pushButtonSoundID);
        });

        undoIv.setOnClickListener(v -> {
            handleItemSelection(v.getId());
            paintingView.undoBitmap();

            startSound(pushButtonSoundID);
        });

        eraseIv.setOnClickListener(v -> {
            handleItemSelection(v.getId());
            if (!isInEraserMode) {
                paintingView.setMode(PaintingView.PAINT_MODE_MARKER);
                Log.i(TAG, "from eraser");
                paintingView.setCurrentColor(Color.WHITE);
                isInEraserMode = true;
                eraserHighlightRIV.setVisibility(View.VISIBLE);

                paintingView.setShader(-1);
            } else {
                turnEraseModeOff();
            }

            startSound(pushButtonSoundID);
        });


        paletteRIV.setOnClickListener(v -> {
            colorHighlightRIV.setVisibility(View.VISIBLE);
            handleItemSelection(v.getId());
            colorsAdapter.notifyDataSetChanged();

            startSound(pushButtonSoundID);
        });

        brushIV.setOnClickListener(v -> {
            brushHighlightRIV.setVisibility(View.VISIBLE);
            handleItemSelection(v.getId());

            startSound(pushButtonSoundID);
        });

        saveIv.setOnClickListener(v -> {
            checkForPermissionAndSaveBitmap();
        });

        colorsAdapter = new ColorsAdapter();
        colorsAdapter.setOnItemSelected(colorPalette -> {
            handleItemSelection(-1);

            if (currentAdapterMode == ADAPTER_COLOR_MODE) {
                Log.i(TAG, "from adapter color selection");
                currentSelectedColorId = colorPalette.getColorID();
                paintingView.setCurrentColor(ContextCompat.getColor(PaintActivity.this, colorPalette.getColorID()));
            } else if (currentAdapterMode == ADAPTER_PATTERN_MODE) {
                currentShaderId = colorPalette.getColorID();
                paintingView.setShader(currentShaderId);
            }

            paletteRIV.setImageResource(colorPalette.getBackID());
            turnEraseModeOff();
        });

        colorsRv.setLayoutManager(new GridLayoutManager(
                this, 2, RecyclerView.VERTICAL, false
        ));
        colorsRv.setAdapter(colorsAdapter);
    }

    private int getTalkerDuration(int id) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, id);
        int talkerVoiceDuration = mediaPlayer.getDuration();
        mediaPlayer.release();
        return talkerVoiceDuration;
    }

    private int getTalkerVoiceID() {
        String identifierName = "talker_" +
                new SettingsSharedPreferences(this).getTalkerName().toLowerCase() +
                "_" +
                fileName.replace(".jpg", "");
        return getResources().getIdentifier(identifierName.toLowerCase(),
                "raw",
                getApplicationContext().getPackageName());
    }

    private void checkForPermissionAndSaveBitmap() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isValidateToSave()) {
                saveIntoExternalStorage();
            } else {
                ActivityCompat.requestPermissions(PaintActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
            }
        } else {
            saveIntoExternalStorage();
        }
    }

    private void saveIntoExternalStorage() {
        paintViewModel.saveBitmapToGallery(paintingView.getBitmap(), fileName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(PaintActivity.this, getString(R.string.save_bitmap_success), Toast.LENGTH_LONG).show();
                        saveIntoInternalStorage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(PaintActivity.this, getString(R.string.save_bitmap_error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean isValidateToSave() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void startSound(int id) {
        buttonPushSp.play(id, 1f, 1f, 1, 0, 1f);
    }

    private void setUpAdapter(int adapterMode) {
        switch (adapterMode) {
            case ADAPTER_COLOR_MODE:
                paintViewModel.getColors()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<List<ColorPalette>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onSuccess(List<ColorPalette> colorPalettes) {
                                Log.i(TAG, "from first color in list");
                                currentShaderId = -1;
                                paletteRIV.setImageResource(currentSelectedColorId);
                                colorsAdapter.setColorPalettes(colorPalettes);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
                break;
            case ADAPTER_PATTERN_MODE:
                paintViewModel.getPatterns()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<List<ColorPalette>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                compositeDisposable.add(d);
                            }

                            @Override
                            public void onSuccess(List<ColorPalette> colorPalettes) {
                                currentShaderId = colorPalettes.get(0).getColorID();
                                paintingView.setShader(currentShaderId);
                                paletteRIV.setImageResource(currentShaderId);
                                colorsAdapter.setColorPalettes(colorPalettes);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
                break;
        }
    }

    @Override
    public void onBackPressed() {
        saveIntoInternalStorage();
    }

    private void saveIntoInternalStorage() {
        Bitmap bitmap = paintingView.getBitmap();
        paintViewModel.saveBitmap(PaintActivity.this, bitmap, folderName, fileName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        Intent intent = new Intent(PaintActivity.this, PaintListActivity.class);
                        intent.putExtra(MainActivity.TITLE, folderName);
                        startActivity(intent);
                        finish();
                        showAd();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void turnEraseModeOff() {
        if (isInEraserMode) {
            Log.i(TAG, "from turning off eraser");
            paintingView.setCurrentColor(ContextCompat.getColor(this, currentSelectedColorId));
            isInEraserMode = false;
            eraserHighlightRIV.setVisibility(View.GONE);
            paintingView.setShader(currentShaderId);
        }
    }

    public void handleItemSelection(int id) {

        if (colorPaletteFl.getVisibility() == View.VISIBLE) {
            Animation animation = AnimationMaker.transitOutFromBottom(300);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    colorPaletteFl.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            colorPaletteFl.startAnimation(animation);

            colorHighlightRIV.setVisibility(View.GONE);
        } else {
            if (id == R.id.iv_paint_chooseColor) {
                colorPaletteFl.setVisibility(View.VISIBLE);
                colorPaletteFl.startAnimation(AnimationMaker.transitInFromTop(300));
            }
        }

        if (brushHolderLL.getVisibility() == View.VISIBLE) {
            Animation animation = AnimationMaker.transitOutFromBottom(300);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    brushHolderLL.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            brushHolderLL.startAnimation(animation);

            brushHighlightRIV.setVisibility(View.GONE);
        } else {
            if (id == R.id.iv_paint_chooseBrush) {
                brushHolderLL.setVisibility(View.VISIBLE);
                brushHolderLL.startAnimation(AnimationMaker.transitInFromTop(300));
            }
        }
    }

    @Override
    public void onClick(View v) {
        turnBrushesOff();

        int paintMode = -1;

        paintingView.setShader(paintMode);
        switch (v.getId()) {
            case R.id.iv_paint_brushPencil:
                pencilIV.setBackground(ContextCompat.getDrawable(this, R.color.transparent_black));
                paintMode = PaintingView.PAINT_MODE_PENCIL;
                currentAdapterMode = ADAPTER_COLOR_MODE;
                break;
            case R.id.iv_paint_brushMarker:
                markerIV.setBackground(ContextCompat.getDrawable(this, R.color.transparent_black));
                paintMode = PaintingView.PAINT_MODE_MARKER;
                currentAdapterMode = ADAPTER_COLOR_MODE;
                break;
            case R.id.iv_paint_brushBucket:
                bucketIV.setBackground(ContextCompat.getDrawable(this, R.color.transparent_black));
                paintMode = PaintingView.PAINT_MODE_BUCKET;
                currentAdapterMode = ADAPTER_COLOR_MODE;
                break;
            case R.id.iv_paint_brushCrayon:
                crayonIV.setBackground(ContextCompat.getDrawable(this, R.color.transparent_black));
                paintMode = PaintingView.PAINT_MODE_CRAYONS;
                break;
            case R.id.iv_paint_brushPattern:
                patternIV.setBackground(ContextCompat.getDrawable(this, R.color.transparent_black));
                paintMode = PaintingView.PAINT_MODE_PATTERN;
                currentAdapterMode = ADAPTER_PATTERN_MODE;
                break;
        }

        setUpAdapter(currentAdapterMode);


        paintingView.setMode(paintMode);
        handleItemSelection(-1);
        turnEraseModeOff();
    }

    private void turnBrushesOff() {
        pencilIV.setBackground(null);
        markerIV.setBackground(null);
        bucketIV.setBackground(null);
        crayonIV.setBackground(null);
        patternIV.setBackground(null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPaintViewTouchEvent(TouchEvent touchEvent) {
        if (touchEvent.getTouchID() == TouchEvent.TOUCH_DOWN) {
            handleItemSelection(-1);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPaintCreated(Boolean isCreated) {
        if (isCreated & isFirstLoad) {
            paintViewModel.getSavedBitmap(this, folderName, fileName)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<Bitmap>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            compositeDisposable.add(d);
                        }

                        @Override
                        public void onSuccess(Bitmap bitmap) {
                            isFirstLoad = false;
                            paintingView.setBitmap(bitmap);

                            paintViewModel.getRawBitmap(PaintActivity.this, fileName, category)
                                    .subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new SingleObserver<Bitmap>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {
                                            compositeDisposable.add(d);
                                        }

                                        @Override
                                        public void onSuccess(Bitmap bitmap) {
                                            paintingView.setPixelModes(bitmap);
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }
                                    });
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SettingsSharedPreferences settings = new SettingsSharedPreferences(this);
        musicPlayerSingleton = MusicPlayerSingleton.singleInstance(
                this, settings.getMusicName()
        );
        musicPlayerSingleton.startMusic(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        musicPlayerSingleton.releaseMusic();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 & grantResults[0] == PackageManager.PERMISSION_GRANTED)
            saveIntoExternalStorage();
    }
}
