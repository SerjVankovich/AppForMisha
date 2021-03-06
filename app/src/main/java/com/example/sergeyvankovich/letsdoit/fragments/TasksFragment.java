package com.example.sergeyvankovich.letsdoit.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sergeyvankovich.letsdoit.DB.DBHelper;
import com.example.sergeyvankovich.letsdoit.DB.Task;
import com.example.sergeyvankovich.letsdoit.R;
import com.example.sergeyvankovich.letsdoit.ResAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TasksFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Task> tasks;
    private ResAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_res_layout, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        tasks = getTasksFromDB();

        recyclerView.setAdapter(new ResAdapter(tasks, getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }
    public List<Task> getTasksFromDB() {
        List<Task> tasks = new ArrayList<>();

        DBHelper helper = new DBHelper(getContext());

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.query(DBHelper.TASKS_TABLE, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int nameId = cursor.getColumnIndex(DBHelper.TASK_NAME);
            int yearId = cursor.getColumnIndex(DBHelper.YEAR);
            int monthId = cursor.getColumnIndex(DBHelper.MONTH);
            int dayId = cursor.getColumnIndex(DBHelper.DAY);
            int absoluteId = cursor.getColumnIndex(DBHelper.KEY_ID);
            int importantId = cursor.getColumnIndex(DBHelper.IMPORTANT);

            while (cursor.moveToNext()) {
                Task task = new Task(cursor.getString(nameId), cursor.getInt(yearId), cursor.getInt(monthId), cursor.getInt(dayId), cursor.getInt(absoluteId));
                task.setImportant(cursor.getString(importantId));
                tasks.add(task);
            }
        }
        cursor.close();
        db.close();
        helper.close();
        return tasks;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public ResAdapter getAdapter() {
        return adapter;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
