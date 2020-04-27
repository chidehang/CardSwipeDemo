package com.demo.cdh.cardswipedemo;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.cdh.cardswipedemo.adapter.CardRecycleAdapter;
import com.demo.cdh.cardswipedemo.adapter.animat.SwipeItemAnimator;
import com.demo.cdh.cardswipedemo.adapter.callback.CardSwipeCallback;
import com.demo.cdh.cardswipedemo.adapter.manager.CardSwipeLayoutManager;
import com.demo.cdh.cardswipedemo.bean.CardBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private RecyclerView cardLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardSwipeLayoutManager.initCardConfig(getApplicationContext());
        cardLayout = (RecyclerView) findViewById(R.id.cardLayout);
        cardLayout.setLayoutManager(new CardSwipeLayoutManager());
        cardLayout.setItemAnimator(new SwipeItemAnimator());

        List<CardBean> data = new ArrayList<>();
        data.add(new CardBean(R.mipmap.tu15));
        data.add(new CardBean(R.mipmap.tu16));
        data.add(new CardBean(R.mipmap.tu17));
        data.add(new CardBean(R.mipmap.xiaotu_50));
        data.add(new CardBean(R.mipmap.xiaotu_51));
        data.add(new CardBean(R.mipmap.xiaotu_122));
        data.add(new CardBean(R.mipmap.xiaotu_131));
        data.add(new CardBean(R.mipmap.xiaotu_134));
        CardRecycleAdapter adapter = new CardRecycleAdapter(this, data);
        cardLayout.setAdapter(adapter);

        CardSwipeCallback callback = new CardSwipeCallback(getApplicationContext(), adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(cardLayout);
    }
}
