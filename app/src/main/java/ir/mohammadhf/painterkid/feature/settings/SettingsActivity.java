package ir.mohammadhf.painterkid.feature.settings;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import ir.mohammadhf.painterkid.R;
import ir.mohammadhf.painterkid.base.BaseActivity;
import ir.mohammadhf.painterkid.utils.AnimationMaker;
import ir.mohammadhf.painterkid.utils.MusicPlayerSingleton;
import ir.mohammadhf.painterkid.utils.SettingsSharedPreferences;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    private SettingsViewModel settingsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView talkerTv = findViewById(R.id.tv_settings_talkerName);

        settingsViewModel = new SettingsViewModel(
                new SettingsSharedPreferences(this)
        );

        compositeDisposable.add(settingsViewModel.getCurrentTalkerName()
                .subscribe(s -> changeTextWithAnim(talkerTv, s)));
    }

    private void changeTextWithAnim(TextView view, String text) {
        Animation animation = AnimationMaker.fadeOut(500);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setText(text);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
    }

    @Override
    public void onClick(View view) {
        Animation animation = AnimationMaker.scaleInAnimation(200);
        animation.setRepeatMode(Animation.REVERSE);

        switch (view.getId()) {
            case R.id.iv_settings_nextTalker:
                settingsViewModel.nextTalker();
                view.startAnimation(animation);
                break;
            case R.id.iv_settings_prevTalker:
                settingsViewModel.prevTalker();
                view.startAnimation(animation);
                break;
            case R.id.iv_settings_home:
                MusicPlayerSingleton.ResetMusicPlayer();
                finish();
        }
    }
}
