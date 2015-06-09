package com.login.android.semut.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.login.android.semut.inicial.R;


public class LoadImage extends AsyncTask<String, Void, Drawable> {
	
	private ImageView image;
	private Context mContext = null;
	private DrawableManager dm = null;
	
	
	public LoadImage(ImageView image, Context context) {
		this.image = image;
		this.mContext = context;
		this.dm = DrawableManager.getDrawableManager();
	}


	@Override
	protected Drawable doInBackground(String... params) {
		return dm.fetchDrawable(params[0]);
	}
	
	@Override
	protected void onPostExecute(Drawable result) {
		this.image.setBackgroundResource(0);
		if(result == null && mContext != null){
			this.image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.imagem_cancelada));
		}else{
			this.image.setImageDrawable(result);
		}
	}
	
}
