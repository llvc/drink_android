package org.wsbd.library;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;

public class ViewAnimations {

	public static void addScale(View view, float interpolator, float fromScaleX, float toScaleX, float fromScaleY, float toScaleY,
								int pivotXType, float pivotXValue, int pivotYType, float pivotYValue,
								int duration, int repearCount, boolean reverse, AnimationListener listener){
		
		ScaleAnimation animation = new ScaleAnimation(fromScaleX, toScaleX, fromScaleY, toScaleY, pivotXType, pivotXValue, pivotYType, pivotYValue);
		animation.setDuration(duration);
		animation.setRepeatCount(repearCount);
		animation.setInterpolator(new AccelerateInterpolator(interpolator));
		animation.setAnimationListener(listener);
		animation.setRepeatMode(reverse ? Animation.REVERSE : Animation.RESTART);
		view.startAnimation(animation);
	}
	
	public static void fade(View view, float fromAlpha, float toAlpha, int duration, int repeatCount, boolean reverse, AnimationListener listener){
		AlphaAnimation anim = new AlphaAnimation(fromAlpha, toAlpha);
		anim.setDuration(duration);
		anim.setRepeatMode(reverse ? Animation.REVERSE : Animation.RESTART);
		anim.setRepeatCount(repeatCount);
		anim.setFillAfter(true);
		anim.setAnimationListener(listener);
		view.startAnimation(anim);
	}
	
	public static void translate(View view, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, int duration, int repeatCount, boolean reverse, AnimationListener listener) {
		TranslateAnimation anim = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
		anim.setDuration(duration);
		anim.setRepeatCount(repeatCount);
		anim.setRepeatMode(reverse ? Animation.REVERSE : Animation.RESTART);
		anim.setFillAfter(true);
		anim.setAnimationListener(listener);
		view.startAnimation(anim);
	}
	
	
	public static void fadeTranslate(View view, float interpolator, float fromAlpha, float toAlpha, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, int duration, int repeatCount, boolean reverse, AnimationListener listener) {
		AnimationSet set  = new AnimationSet(true);	
		
		TranslateAnimation anim = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
		anim.setDuration(duration);
		anim.setRepeatCount(repeatCount);
		anim.setRepeatMode(reverse ? Animation.REVERSE : Animation.RESTART);
		anim.setFillAfter(true);
		
		set.addAnimation(anim);
		
		AlphaAnimation alphaAnim = new AlphaAnimation(fromAlpha, toAlpha);
		alphaAnim.setDuration(duration);
		alphaAnim.setRepeatMode(reverse ? Animation.REVERSE : Animation.RESTART);
		alphaAnim.setRepeatCount(repeatCount);
			
		set.addAnimation(alphaAnim);
		
		if (interpolator >= 0) {
			AccelerateInterpolator inter = new AccelerateInterpolator(interpolator);
			set.setInterpolator(inter);
		} else {
			LinearInterpolator inter = new LinearInterpolator();
			set.setInterpolator(inter);
		}
			
		set.setAnimationListener(listener);
		
		view.startAnimation(set);
	}
	
}
