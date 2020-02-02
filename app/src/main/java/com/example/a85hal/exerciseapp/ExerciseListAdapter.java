package com.example.a85hal.exerciseapp;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ExerciseListAdapter extends RecyclerView.Adapter<ExerciseListAdapter.ExerciseViewHolder> {

    private Cursor cursor;
    private LayoutInflater inflater;
    private ItemClickListener clickListener;

    public ExerciseListAdapter(Context context, Cursor cursor) {
        this.cursor = cursor;
        inflater = LayoutInflater.from(context);
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nameText;
        public TextView minMaxText;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.text_ex_name);
            minMaxText = itemView.findViewById(R.id.text_ex_min_max);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());

        }
    }

    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.view_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }
        String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME));
        int min = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_MIN));
        int max = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_MAX));
        String typeString = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TYPE));
        ExerciseType type = ExerciseType.getTypeFromString(typeString);
        holder.nameText.setText(name);
        String typeText = type == ExerciseType.REPEATED ? "repetitions" : "minutes";
        holder.minMaxText.setText(min + " to " + max + " " + typeText);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}