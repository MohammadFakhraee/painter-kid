package ir.mohammadhf.painterkid.utils;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class AnimationMaker {

    public static AlphaAnimation fadeIn(int duration) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1.0f);
        alphaAnimation.setDuration(duration);
        return alphaAnimation;
    }

    public static AlphaAnimation fadeOut(int duration) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0);
        alphaAnimation.setDuration(duration);
        return alphaAnimation;
    }

    public static TranslateAnimation transitInFromTop(int duration) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0);
        translateAnimation.setDuration(duration);
        translateAnimation.setFillAfter(false);

        return translateAnimation;
    }

    public static TranslateAnimation transitOutFromBottom(int duration) {
        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, 0,
                Animation.ABSOLUTE, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
        translateAnimation.setDuration(duration);
        translateAnimation.setFillAfter(false);

        return translateAnimation;
    }

    public static ScaleAnimation scaleInAnimation(int duration) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.9f, 1, 0.9f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setInterpolator(new AccelerateInterpolator());
        return scaleAnimation;
    }

    public static Animation scaleAndFadeFromZero(int duration) {
        AnimationSet animationSet = createDefaultAnimationSet(duration);
        animationSet.addAnimation(new ScaleAnimation(
                0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        ));
        animationSet.addAnimation(fadeIn(duration));
        return animationSet;
    }

    public static Animation scaleAndFadeToZero(int duration) {
        AnimationSet animationSet = createDefaultAnimationSet(duration);
        animationSet.addAnimation(new ScaleAnimation(
                1, 0, 1, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        ));
        animationSet.addAnimation(fadeOut(duration));

        return animationSet;
    }

    public static AnimationSet createDefaultAnimationSet(int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setInterpolator(new LinearInterpolator());
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }
}
