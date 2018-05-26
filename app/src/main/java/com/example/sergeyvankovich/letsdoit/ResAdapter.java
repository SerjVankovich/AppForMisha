package com.example.sergeyvankovich.letsdoit;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
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

public class ResAdapter extends RecyclerView.Adapter<ResAdapter.MyViewHolder> {
    private List<Task> tasks;
    private ViewBinderHelper helper;
    private Context context;

    public ResAdapter(List<Task> tasks, Context context) {
        this.tasks = tasks;
        this.helper = new ViewBinderHelper();
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);

        holder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                deleteCardView(position);
            }
        });
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

        helper.bind(holder.cardView, tasks.get(position).toString());
        helper.setOpenOnlyOne(true);

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView taskName;
        private Button complete;
        private SwipeRevealLayout cardView;
        private TextView date;
        private View important;

        public MyViewHolder(View itemView) {
            super(itemView);

            taskName = itemView.findViewById(R.id.taskName);
            complete = itemView.findViewById(R.id.completed);
            cardView = itemView.findViewById(R.id.card_view);
            date = itemView.findViewById(R.id.date);
            important = itemView.findViewById(R.id.important);
        }
    }
    public void deleteCardView(int position){
        DBHelper helper = new DBHelper(context);
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TASK_NAME, tasks.get(position).getName());
        cv.put(DBHelper.YEAR, tasks.get(position).getYear());
        cv.put(DBHelper.MONTH, tasks.get(position).getMonth());
        cv.put(DBHelper.DAY, tasks.get(position).getDay());
        cv.put(DBHelper.IMPORTANT, tasks.get(position).getImportant());
        database.insert(DBHelper.COMPLETED_TASKS, null, cv);
        database.delete(DBHelper.TASKS_TABLE, DBHelper.KEY_ID  + "='" + Integer.toString(tasks.get(position).getAbsoluteID()) + "'", null);
        tasks.remove(position);
        notifyItemRemoved(position);
    }
}
