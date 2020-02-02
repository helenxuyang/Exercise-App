package com.example.a85hal.exerciseapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class StatsListAdapter extends RecyclerView.Adapter<StatsListAdapter.StatsViewHolder> {

    private HashMap<Exercise, Integer> stats;
    String[] names;
    Integer[] nums;
    private LayoutInflater inflater;

    public StatsListAdapter(Context context, HashMap<Exercise, Integer> stats) {
        inflater = LayoutInflater.from(context);
        this.stats = stats;
        names = stats.keySet().toArray(new String[0]);
        nums = stats.values().toArray(new Integer[0]);
    }

    @Override
    public StatsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_stats, parent, false);
        return new StatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatsViewHolder holder, int position) {
        holder.nameText.setText(names[position]);
        holder.numText.setText("" + nums[position]);
    }

    @Override
    public int getItemCount() {
        return stats.size();
    }

    public class StatsViewHolder extends RecyclerView.ViewHolder {

        public TextView nameText;
        public TextView numText;

        public StatsViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.text_stats_name);
            numText = itemView.findViewById(R.id.text_stats_num);
        }
    }
}
