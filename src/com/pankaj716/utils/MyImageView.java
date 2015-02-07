package com.pankaj716.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MyImageView extends ImageView {

	public MyImageView(Context context) {
		super(context);
	}

	OnImageChangeListiner onImageChangeListiner;

	public MyImageView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	public void setImageChangeListiner(
			OnImageChangeListiner onImageChangeListiner) {
		this.onImageChangeListiner = onImageChangeListiner;
	}

	@Override
	public void setBackgroundResource(int resid) {
		super.setBackgroundResource(resid);
		if (onImageChangeListiner != null)
			onImageChangeListiner.imageChangedinView();
	}

	@Override
	public void setBackgroundDrawable(Drawable background) {
		super.setBackgroundDrawable(background);
		if (onImageChangeListiner != null)
			onImageChangeListiner.imageChangedinView();
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
		if (onImageChangeListiner != null)
			onImageChangeListiner.imageChangedinView();
	}

	@Override
	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);
		if (onImageChangeListiner != null)
			onImageChangeListiner.imageChangedinView();
	}
	
	@Override
	public void setImageResource(int resId) {
		super.setImageResource(resId);
		if (onImageChangeListiner != null)
			onImageChangeListiner.imageChangedinView();
	}
	
	public interface OnImageChangeListiner {
		public void imageChangedinView();
	}
}