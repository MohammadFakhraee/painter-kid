package ir.mohammadhf.painterkid.feature.paint_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.mohammadhf.painterkid.BuildConfig;
import ir.mohammadhf.painterkid.R;
import ir.mohammadhf.painterkid.base.BaseActivity;
import ir.mohammadhf.painterkid.data.PaintListRepository;
import ir.mohammadhf.painterkid.data.model.PaintModel;
import ir.mohammadhf.painterkid.feature.iapurchase.PurchaseHandler;
import ir.mohammadhf.painterkid.feature.main.MainActivity;
import ir.mohammadhf.painterkid.feature.paint.PaintActivity;
import ir.mohammadhf.painterkid.utils.MusicPlayerSingleton;
import ir.mohammadhf.painterkid.utils.SettingsSharedPreferences;
import me.cheshmak.android.sdk.core.config.CheshmakConfig;
import me.cheshmak.cheshmakplussdk.advertise.CheshmakBannerAd;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static ir.mohammadhf.painterkid.feature.paint_list.PaintListAdapter.EXTRA_CATEGORY;
import static ir.mohammadhf.painterkid.feature.paint_list.PaintListAdapter.EXTRA_FILE_NAME;
import static ir.mohammadhf.painterkid.feature.paint_list.PaintListAdapter.EXTRA_FOLDER_NAME;

public class PaintListActivity extends BaseActivity {
    private PaintListAdapter adapter;
    private RecyclerView paintsRecyclerView;

    private String folderName;

    private MusicPlayerSingleton musicPlayerSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_list);

        folderName = getIntent().getStringExtra(MainActivity.TITLE);

        View lottieAnimationView = findViewById(R.id.lav_paintList_waiting);

        PaintListViewModel paintListViewModel = new PaintListViewModel(new PaintListRepository());
        paintListViewModel.getPaintsList(this, folderName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<PaintModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<PaintModel> paintModels) {
                        lottieAnimationView.setVisibility(GONE);

                        adapter = new PaintListAdapter(paintModels, folderName);

                        paintsRecyclerView = findViewById(R.id.rv_paintList_listOfPaints);
                        paintsRecyclerView.setLayoutManager(new LinearLayoutManager(
                                PaintListActivity.this, RecyclerView.HORIZONTAL, false
                        ));
                        paintsRecyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

        ImageButton backBtn = findViewById(R.id.btn_paintList_back);
        backBtn.setOnClickListener(v -> {
            finish();
        });

        CheshmakBannerAd cheshmakBannerAd = findViewById(R.id.cheshmak_banner);
        if (BuildConfig.isTestAds |
                (CheshmakConfig.getBoolean("show_add", false)
                        && !PurchaseHandler.isPremiumAccount()))
            cheshmakBannerAd.setVisibility(VISIBLE);
        else cheshmakBannerAd.setVisibility(GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChoosePaintSubscribe(PaintChooseEvent paintChooseEvent) {
        Intent intent = new Intent(this, PaintActivity.class);
        intent.putExtra(EXTRA_FOLDER_NAME, paintChooseEvent.getFolderName());
        intent.putExtra(EXTRA_FILE_NAME, paintChooseEvent.getPaintName());
        intent.putExtra(EXTRA_CATEGORY, paintChooseEvent.getCategory());

        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);

        SettingsSharedPreferences settings = new SettingsSharedPreferences(this);
        musicPlayerSingleton = MusicPlayerSingleton.singleInstance(
                this, settings.getMusicName()
        );
        musicPlayerSingleton.startMusic(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        musicPlayerSingleton.releaseMusic();
    }
}
