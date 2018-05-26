package com.example.sergeyvankovich.letsdoit;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.sergeyvankovich.letsdoit.DB.DBHelper;
import com.example.sergeyvankovich.letsdoit.DB.Task;

import java.util.List;

public class ResCompleteAdapter extends RecyclerView.Adapter<ResCompleteAdapter.MyViewHolder> {
    private List<Task> tasks;

    public ResCompleteAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.completed_task_card, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.taskName.setText(tasks.get(position).getName());
        int year = tasks.get(position).getYear();
        int month = tasks.get(position).getMonth();
        int day = tasks.get(position).getDay();
        String important = tasks.get(position).getImportant();
        if (tasks.get(position).getMonth() < 10) {
            if (tasks.get(position).getDay() < 10) {
                holder.date.setText("0" + String.valueOf(day) + ".0" + String.valueOf(month) + "." + String.valueOf(year).substring(2, 4));
            } else {
                holder.date.setText(String.valueOf(day) + ".0" + String.valueOf(month) + "." + String.valueOf(year).substring(2, 4));

            }
        } else {
            if (tasks.get(position).getDay() < 10) {
                holder.date.setText("0" + String.valueOf(day) + "." + String.valueOf(month) + "." + String.valueOf(year).substring(2, 4));

            } else {
                holder.date.setText(String.valueOf(day) + "." + String.valueOf(month) + "." + String.valueOf(year).substring(2, 4));
            }
        }

        switch (important) {
            case "low":
                holder.important.setBackgroundResource(R.drawable.low_important_shape);
                break;
            case "mid":
                holder.important.setBackgroundResource(R.drawable.mid_important_shape);
                break;
            case "high":
                holder.important.setBackgroundResource(R.drawable.high_important_shape);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView taskName;
        private CardView cardView;
        private TextView date;
        private View important;

        public MyViewHolder(View itemView) {
            super(itemView);

            taskName = itemView.findViewById(R.id.taskName);
            cardView = itemView.findViewById(R.id.card_view);
            date = itemView.findViewById(R.id.date);
            important = itemView.findViewById(R.id.important);
        }
    }

}
