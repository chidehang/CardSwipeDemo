package com.demo.cdh.cardswipedemo.adapter.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class ViewHolder extends RecyclerView.ViewHolder {
	
	private Context mContext;
	private View mConvertView;
	private SparseArray<View> mViews;
	public int viewType;

	public ViewHolder(View view) {
		super(view);
		mContext = view.getContext();
		mConvertView = view;
		mViews = new SparseArray<View>();
	}
	
	/**
	 * 通过控件ID获取对应控件
	 */
	public <T extends View>T getView(int viewId) {
		View view  = mViews.get(viewId);
		if(view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T)view;
	}
	
	public ViewHolder setText(int viewId, String text) {
		TextView tv = getView(viewId);
		tv.setText(text);
		return this;
	}
	
	public ViewHolder setImageResource(int viewId, int drawableId) {
		ImageView iv = getView(viewId);
		iv.setImageResource(drawableId);
		return this;
	}
	
	public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
		ImageView iv = getView(viewId);
		iv.setImageBitmap(bm);
		return this;
	}
}
