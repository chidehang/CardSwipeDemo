package com.demo.cdh.cardswipedemo.adapter.callback;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.view.View;
import android.widget.Toast;

import com.demo.cdh.cardswipedemo.adapter.CardRecycleAdapter;
import com.demo.cdh.cardswipedemo.adapter.manager.CardSwipeLayoutManager.CardConfig;
import com.demo.cdh.cardswipedemo.bean.CardBean;

/**
 * Created by hang on 2017/4/27.
 */

public class CardSwipeCallback extends SimpleCallback {

    private Context context;
    private CardRecycleAdapter adapter;

    public CardSwipeCallback(Context context, CardRecycleAdapter adapter) {
        super(0, ItemTouchHelper.LEFT|ItemTouchHelper.UP|ItemTouchHelper.RIGHT|ItemTouchHelper.DOWN);
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if(direction==ItemTouchHelper.LEFT) {
            Toast.makeText(context, "不喜欢", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "喜欢", Toast.LENGTH_SHORT).show();
        }

        //移除滑出的item添加到尾部
        CardBean item = adapter.getmData().remove(viewHolder.getLayoutPosition());
        adapter.getmData().add(item);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        //设置滑动临界值，避免无限偏移缩放
        double maxDistance = recyclerView.getWidth() / 2;
        //当前滑动距离
        double distance = Math.sqrt(dX*dX + dX*dX);
        //计算比例
        double ratio = distance / maxDistance;
        if(ratio > 1)
            ratio = 1;

        //获取当前recyclerview显示item的个数，遍历计算偏移和缩放
        int count = recyclerView.getChildCount();
        for (int i=0; i<count; i++) {
            View child = recyclerView.getChildAt(i);
            //越前面越底层
            int level = count - i - 1;
            //最底层的view不需要改变
            if(level != count-1) {
                //在原来的位置下加上偏移量
                child.setTranslationY((float) (CardConfig.TRANS_Y_GAP * (level-ratio)));
                child.setScaleX((float) (1 - CardConfig.SCALE_GAP * level + CardConfig.SCALE_GAP*ratio));
                child.setScaleY((float) (1 - CardConfig.SCALE_GAP * level + CardConfig.SCALE_GAP*ratio));
            }
        }
    }
}
