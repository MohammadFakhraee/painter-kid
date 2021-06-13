package ir.mohammadhf.painterkid.feature.main;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import io.reactivex.subjects.BehaviorSubject;
import ir.mohammadhf.painterkid.R;
import ir.mohammadhf.painterkid.base.BaseActivity;
import ir.mohammadhf.painterkid.data.model.PaintPackage;
import ir.mohammadhf.painterkid.feature.paint_list.PaintListActivity;
import ir.mohammadhf.painterkid.feature.parent.ParentActivity;
import ir.mohammadhf.painterkid.feature.parent.control.ParentControlFragment;
import ir.mohammadhf.painterkid.feature.settings.SettingsActivity;
import ir.mohammadhf.painterkid.utils.AnimationMaker;
import ir.mohammadhf.painterkid.utils.MusicPlayerSingleton;
import ir.mohammadhf.painterkid.utils.SettingsSharedPreferences;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends BaseActivity {
    public final static String TITLE = "message";
    private static final String TAG = "MainActivity";

    private MainViewModel mainViewModel;

    private GifImageView currentPackageGiv;
    private GifImageView titleGiv;
    private View lockIv;
    private FrameLayout fragHolderFl;

    private SoundPool selectSp;
    private int selectSpId;

    private boolean isPrevPackageLocked = true;

    private MusicPlayerSingleton musicPlayerSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initial();
    }

    private void initial() {
        mainViewModel = new MainViewModel();

        selectSp = new SoundPool(1, AudioManager.STREAM_MUSIC, 100);
        selectSpId = selectSp.load(this, R.raw.push_button, 1);

        Button playBtn = findViewById(R.id.btn_main_start);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            playBtn.setStateListAnimator(null);
        }
        playBtn.setOnClickListener(view -> {
            PaintPackage paintPackage = mainViewModel.getPackageAtPos(mainViewModel.getCurrentSelectedId());
            if (paintPackage.isLocked()) {

            } else {
                Intent intent = new Intent(this, PaintListActivity.class);
                intent.putExtra(TITLE, paintPackage.getTitle());
                startActivity(intent);
            }
            playBtnPressSound();
        });

        fragHolderFl = findViewById(R.id.fl_main_fragmentHolder);

        Button shopBtn = findViewById(R.id.btn_main_shop);
        shopBtn.setOnClickListener(view -> {
            startActivityWithParentPermission(new Intent(this,
                    ParentActivity.class));
        });
        Button settingBtn = findViewById(R.id.btn_main_setting);
        settingBtn.setOnClickListener(view -> {
            startActivityWithParentPermission(new Intent(this,
                    SettingsActivity.class));
        });

        ImageView nextPackageIv = findViewById(R.id.iv_main_nextPackage);
        nextPackageIv.setOnClickListener(view -> {
            animateButtonAndPlaySound(view);
            onPackageChange(mainViewModel.getCurrentSelectedId() + 1);
        });

        ImageView prevPackageIv = findViewById(R.id.iv_main_prevPackage);
        prevPackageIv.setOnClickListener(view -> {
            animateButtonAndPlaySound(view);
            onPackageChange(mainViewModel.getCurrentSelectedId() - 1);
        });

        titleGiv = findViewById(R.id.giv_main_packageTitle);
        currentPackageGiv = findViewById(R.id.giv_main_currentPackage);
        lockIv = findViewById(R.id.iv_main_lock);
    }

    private void startParentControlFragment(BehaviorSubject<Boolean> callback) {
        ParentControlFragment fragment = new ParentControlFragment();
        compositeDisposable.add(fragment.getAnswerCallBackBehaveSub().subscribe(isCorrect -> {
            if (isCorrect) {
                callback.onNext(true);
            } else {
                // TODO: 3/29/2020 apply a sound for wrong answer.
            }

            getSupportFragmentManager().popBackStack();
            fragHolderFl.setVisibility(View.GONE);
        }));

        compositeDisposable.add(fragment.getExitBehaveSun().subscribe(aBoolean -> {
            getSupportFragmentManager().popBackStack();
            fragHolderFl.setVisibility(View.GONE);
        }));

        getSupportFragmentManager().beginTransaction()
                .replace(fragHolderFl.getId(), fragment)
                .addToBackStack(null)
                .commit();
    }

    private void animateButtonAndPlaySound(View view) {
        Animation animation = AnimationMaker.scaleInAnimation(200);
        animation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(animation);
        playBtnPressSound();
    }

    private void onPackageChange(int newPos) {
        mainViewModel.setCurrentSelectedId(newPos);
        changePackageGif(mainViewModel.getCurrentSelectedId());
    }

    private void changePackageGif(int pos) {
        PaintPackage paintPackage = mainViewModel.getPackageAtPos(pos);
        currentPackageGiv.setImageResource(paintPackage.getImage());
        titleGiv.setImageResource(paintPackage.getTitleDrawableId());
        checkForLockAnimationValidation(paintPackage.isLocked());
    }

    private void checkForLockAnimationValidation(boolean isCurrentPaintLocked) {
        if (isCurrentPaintLocked) {
            if (!isPrevPackageLocked) {
                animateLockIc(AnimationMaker.scaleAndFadeFromZero(200));
            }
        } else {
            if (isPrevPackageLocked) {
                animateLockIc(AnimationMaker.scaleAndFadeToZero(200));
            }
        }
        isPrevPackageLocked = isCurrentPaintLocked;
    }

    private void animateLockIc(Animation animation) {
        animation.setFillAfter(true);
        lockIv.startAnimation(animation);
    }

    private void playBtnPressSound() {
        selectSp.play(selectSpId, 1, 1, 1, 0, 1);
    }

    private void startActivityWithParentPermission(Intent intent) {
        if (fragHolderFl.getVisibility() != View.VISIBLE) {
            fragHolderFl.setVisibility(View.VISIBLE);
            BehaviorSubject<Boolean> callback = BehaviorSubject.create();
            compositeDisposable.add(callback.subscribe(aBoolean -> {
                startActivity(intent);
            }));

            startParentControlFragment(callback);
        }
        playBtnPressSound();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainViewModel.clearData();
        mainViewModel.loadData();
        onPackageChange(0);

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
    protected void onDestroy() {
        super.onDestroy();
        if (!((GifDrawable) currentPackageGiv.getDrawable()).isRecycled()) {
            ((GifDrawable) currentPackageGiv.getDrawable()).recycle();
            ((GifDrawable) titleGiv.getDrawable()).recycle();
        }
    }
}
