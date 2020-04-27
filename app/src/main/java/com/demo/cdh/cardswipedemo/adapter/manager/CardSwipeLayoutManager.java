package com.demo.cdh.cardswipedemo.adapter.manager;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by hang on 2017/4/27.
 */

public class CardSwipeLayoutManager extends RecyclerView.LayoutManager {

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        //解除所有子view，添加到scrap集合缓存
        detachAndScrapAttachedViews(recycler);
        int count = Math.min(getItemCount(), CardConfig.CARD_SHOW_COUNT);
        if(count < 1) {
            return;
        }
        //遍历前count个itemView加载显示
        for (int i=0; i<count; i++) {
            View child = recycler.getViewForPosition(i);
            //添加至头部，显示在底层
            addView(child, 0);
            //测量child的大小
            measureChildWithMargins(child, 0, 0);
            //获取child外边距=(recyclerview的宽度-child包含了decorate间距的总宽度) / 2
            int widthSpace = (getWidth()-getDecoratedMeasuredWidth(child)) / 2;
            int heightSpace = (getHeight()-getDecoratedMeasuredHeight(child)) / 2;
            //摆放child的位置
            layoutDecorated(child, widthSpace, heightSpace,
                    widthSpace+getDecoratedMeasuredWidth(child),
                    heightSpace+getDecoratedMeasuredHeight(child));

            //设置Y轴偏移和长宽缩放，层叠错开显示
            int fraction = i;
            if(fraction == count-1) {
                //最后一个被倒二个完整覆盖
                fraction = count - 2;
            }
            child.setTranslationY(CardConfig.TRANS_Y_GAP * fraction);
            child.setScaleX(1 - CardConfig.SCALE_GAP*fraction);
            child.setScaleY(1 - CardConfig.SCALE_GAP*fraction);
        }
    }

    public static void initCardConfig(Context context) {
        CardConfig.init(context);
    }

    public static class CardConfig {
        public static int CARD_SHOW_COUNT;    //最多同时显示个数
        public static float SCALE_GAP;  //缩放比例
        public static int TRANS_Y_GAP;  //偏移量

        public static void init(Context context) {
            CARD_SHOW_COUNT = 4;
            SCALE_GAP = 0.05f;
            TRANS_Y_GAP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, context.getResources().getDisplayMetrics());
        }
    }
}
