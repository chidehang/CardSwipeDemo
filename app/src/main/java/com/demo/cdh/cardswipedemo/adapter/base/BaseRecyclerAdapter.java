package com.demo.cdh.cardswipedemo.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
	
	public static final Integer VIEW_TYPE_DEFAULT = 0;
	
	protected LayoutInflater mInflater;
	protected Context mContext;
	protected List<T> mData;
	protected Map<Integer, Integer> mItemLayoutIds;
	
	protected OnRecyclerItemClickListener onRecyclerItemClickListener;
	
	public BaseRecyclerAdapter(Context context, List<T> data) {
		this.mContext = context;
		this.mData = data;
		this.mInflater = LayoutInflater.from(context);
		mItemLayoutIds = new HashMap<Integer, Integer>();
	}
	
	/**
	 * 添加item布局id
	 */
	protected void putItemLayoutId(Integer viewType, Integer layoutId) {
		if(mItemLayoutIds.containsKey(viewType))
			mItemLayoutIds.remove(viewType);
		mItemLayoutIds.put(viewType, layoutId);
	}

	@Override
	public int getItemCount() {
		return mData==null? 0:mData.size();
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, final int position) {
		holder.itemView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(onRecyclerItemClickListener != null) {
					onRecyclerItemClickListener.onRecyclerItemClicked(BaseRecyclerAdapter.this, holder.itemView, position);
				}
			}
		});
		onBind(holder, mData.get(position), position);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final View view = mInflater.inflate(mItemLayoutIds.get(viewType), parent, false);
		ViewHolder holder = new ViewHolder(view);
		holder.viewType = viewType;
		return holder;
	}
	
	@Override
	public int getItemViewType(int position) {
		return getItemViewType(position, mData.get(position));
	}
	
	public List<T> getmData() {
		return mData;
	}

	public void setmData(List<T> data) {
		this.mData = data;
	}

	public int getCount() {
		return mData==null? 0:mData.size();
	}
	
	public void remove(T item) {
		if(mData != null) {
			int position = mData.indexOf(item);
			mData.remove(position);
			notifyItemRemoved(position);
		}
	}

	public void insert(T item) {
		if(mData != null) {
			mData.add(item);
			notifyItemInserted(mData.size()-1);
		}
	}
	
	public void insert(int position, T item) {
		if(mData != null) {
			mData.add(position, item);
			notifyItemInserted(position);
		}
	}
	
	/**
	 * 获取item视图类型
	 */
	public int getItemViewType(final int position, final T item) {
		return VIEW_TYPE_DEFAULT;
	}
	
	/**
	 * 绑定数据
	 */
	public abstract void onBind(final ViewHolder holder, final T item, final int position);
	
	public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener listener) {
		this.onRecyclerItemClickListener = listener;
	}
	
	public interface OnRecyclerItemClickListener {
		public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, int position);
	}
}
