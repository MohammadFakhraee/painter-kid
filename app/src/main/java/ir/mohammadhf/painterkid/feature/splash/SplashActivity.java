package ir.mohammadhf.painterkid.feature.splash;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import io.reactivex.subjects.BehaviorSubject;
import ir.mohammadhf.painterkid.R;
import ir.mohammadhf.painterkid.base.BaseActivity;
import ir.mohammadhf.painterkid.feature.iapurchase.PurchaseHandler;
import ir.mohammadhf.painterkid.feature.main.MainActivity;

public class SplashActivity extends BaseActivity {
    private BehaviorSubject<Boolean> finishListenerBS = BehaviorSubject.create();

    private SplashViewModel splashViewModel;
    private MediaPlayer bgVideoPlayer;

    private SurfaceView surfaceView;

    private int finishedPartCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        surfaceView = findViewById(R.id.sv_splash_bgVid);

        compositeDisposable.add(finishListenerBS.subscribe(aBoolean -> {
            finishedPartCounter++;
            if (finishedPartCounter == 1) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }));
    }

    private void checkUserPurchases() {
        splashViewModel.getPurchaseHandler().getProductPurchasesFromMarket(this);
        compositeDisposable.add(splashViewModel.getPurchaseHandler()
                .getAllPurchaseDetailBehaviorSub()
                .subscribe(purchaseDetailsMap -> {
                    // Only purchased items return and we only handle purchased items here.
                    splashViewModel.handlePurchasedItems(purchaseDetailsMap);
                    // finishListenerBS.onNext(true);
                }));
    }

    private void startBgVideo() {
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                bgVideoPlayer = MediaPlayer.create(SplashActivity.this, R.raw.bg_vid_splash);
                bgVideoPlayer.setLooping(false);
                bgVideoPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                bgVideoPlayer.setDisplay(surfaceHolder);
                bgVideoPlayer.start();

                addVideoListener();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
    }

    private void addVideoListener() {
        bgVideoPlayer.setOnCompletionListener(mediaPlayer -> {
            finishListenerBS.onNext(true);
        });
    }

    private void releaseBgVideo() {
        if (bgVideoPlayer != null) {
            bgVideoPlayer.stop();
            bgVideoPlayer.release();
            bgVideoPlayer = null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startBgVideo();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseBgVideo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        splashViewModel = new SplashViewModel(new PurchaseHandler());
        checkUserPurchases();
    }

    @Override
    protected void onPause() {
        splashViewModel.getPurchaseHandler().dismiss();
        super.onPause();
    }
}
