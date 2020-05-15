package com.demo.cdh.cardswipedemo;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.demo.cdh.cardswipedemo.adapter.base.BaseRecyclerAdapter;
import com.demo.cdh.cardswipedemo.adapter.base.ViewHolder;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SampleRecyclerActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_recycler);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final List<String> data = getData();
        final SampleAdapter adapter = new SampleAdapter(this, data);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(data, from, to);
                adapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                data.remove(pos);
                adapter.notifyItemRemoved(pos);
            }
        };
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private List<String> getData() {
        List<String> data = new LinkedList<>();
        for (int i=0; i<30; i++) {
            data.add("item " + i);
        }
        return data;
    }

    class SampleAdapter extends BaseRecyclerAdapter<String> {

        public SampleAdapter(Context context, List<String> data) {
            super(context, data);
            putItemLayoutId(VIEW_TYPE_DEFAULT, R.layout.item_sample_text);
        }

        @Override
        public void onBind(ViewHolder holder, String item, int position) {
            holder.setText(R.id.tvItem, item);
        }
    }
}
