package org.wsbd.library;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

public class LayoutController {
	
	public static final String TAG = "LayoutController";
	
	public static void fitToScreen(Context context, FrameLayout topLayout, float rate) {
		for (int i = 0; i < topLayout.getChildCount(); i++) {
			if (topLayout.getChildAt(i) instanceof FrameLayout) {
				if (((FrameLayout) topLayout.getChildAt(i)).getChildCount() > 0)
					fitToScreen(context, (FrameLayout) topLayout.getChildAt(i), rate);
			}
						
			View layout = topLayout.getChildAt(i);
			String tag = (String) layout.getTag();
			
			Log.v(TAG, "" + tag);
			
			if (tag == null) continue;
			
			if (tag.endsWith("_ltwh")) {
				int orgWidth = layout.getWidth();
				int orgHeight = layout.getHeight();
				int orgLeft = layout.getLeft();
				int orgTop = layout.getTop();
				
				FrameLayout.LayoutParams param = new FrameLayout.LayoutParams((int)(orgWidth * rate), (int)(orgHeight * rate), Gravity.LEFT | Gravity.TOP);
				param.setMargins((int)(orgLeft * rate), (int)(orgTop * rate), layout.getRight(), layout.getBottom());
				layout.setLayoutParams(param);
				
			} else if (tag.endsWith("_wh")) {
				int orgWidth = layout.getWidth();
				int orgHeight = layout.getHeight();
				
				if (tag.startsWith("c")) {
					FrameLayout.LayoutParams param = new FrameLayout.LayoutParams((int)(orgWidth * rate), (int)(orgHeight * rate), Gravity.CENTER);
					layout.setLayoutParams(param);
				} else if (tag.startsWith("bch")) {
					FrameLayout.LayoutParams param = new FrameLayout.LayoutParams((int)(orgWidth * rate), (int)(orgHeight * rate), Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
					layout.setLayoutParams(param);
				} else if (tag.startsWith("rcv")) {
					FrameLayout.LayoutParams param = new FrameLayout.LayoutParams((int)(orgWidth * rate), (int)(orgHeight * rate), Gravity.RIGHT|Gravity.CENTER_VERTICAL);
					layout.setLayoutParams(param);
				} else if (tag.startsWith("lcv")) {
					FrameLayout.LayoutParams param = new FrameLayout.LayoutParams((int)(orgWidth * rate), (int)(orgHeight * rate), Gravity.LEFT|Gravity.CENTER_VERTICAL);
					layout.setLayoutParams(param);
				}  else if (tag.startsWith("b")) {
					FrameLayout.LayoutParams param = new FrameLayout.LayoutParams((int)(orgWidth * rate), (int)(orgHeight * rate), Gravity.BOTTOM);
					layout.setLayoutParams(param);
				}else {
					FrameLayout.LayoutParams param = new FrameLayout.LayoutParams((int)(orgWidth * rate), (int)(orgHeight * rate), Gravity.LEFT|Gravity.TOP);
					layout.setLayoutParams(param);
				}
			} else if (tag.endsWith("_padjust")) {
				layout.setPadding((int)(layout.getPaddingLeft() * rate), (int)(layout.getPaddingTop() * rate), (int)(layout.getPaddingRight() * rate), (int)(layout.getPaddingBottom() * rate));
			}		
		}
	}
}
