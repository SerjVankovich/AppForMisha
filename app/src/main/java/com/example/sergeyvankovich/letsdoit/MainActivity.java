package com.example.sergeyvankovich.letsdoit;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sergeyvankovich.letsdoit.DB.DBHelper;
import com.example.sergeyvankovich.letsdoit.DB.Task;
import com.example.sergeyvankovich.letsdoit.fragments.AboutMeFragment;
import com.example.sergeyvankovich.letsdoit.fragments.CompletedFragment;
import com.example.sergeyvankovich.letsdoit.fragments.TasksFragment;
import com.example.sergeyvankovich.letsdoit.fragments.TodayFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TasksFragment tasksFragment;
    private TodayFragment todayFragment;
    private CompletedFragment completedFragment;
    private AboutMeFragment aboutMeFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        tasksFragment = new TasksFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, tasksFragment).commit();
        navigationView.setCheckedItem(R.id.tasks);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildDialog();
                navigationView.setCheckedItem(R.id.tasks);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        tasksFragment = new TasksFragment();
        todayFragment = new TodayFragment();
        completedFragment = new CompletedFragment();
        aboutMeFragment = new AboutMeFragment();

        int id = item.getItemId();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.tasks) {
           transaction.replace(R.id.container, tasksFragment);
        } else if (id == R.id.today_tasks) {
            transaction.replace(R.id.container, todayFragment);
        } else if (id == R.id.completed) {
            transaction.replace(R.id.container, completedFragment);
        } else if (id == R.id.about_me) {
            transaction.replace(R.id.container, aboutMeFragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        transaction.commit();
        return true;
    }
    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view_dialog = getLayoutInflater().inflate(R.layout.dialog_new_task, null);

        Button cancell = view_dialog.findViewById(R.id.cancel_action);
        Button add = view_dialog.findViewById(R.id.add);
        final RadioGroup radioGroup = view_dialog.findViewById(R.id.radioGroup);
        RadioButton low = view_dialog.findViewById(R.id.low_important);

        final EditText editTaskName = view_dialog.findViewById(R.id.edit_task_name);
        final CalendarView calendarView = view_dialog.findViewById(R.id.calendarView);


        builder.setView(view_dialog);
        final AlertDialog dialog = builder.create();
        final Task[] task = new Task[1];
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = format.format(Calendar.getInstance().getTime());
        Log.d("CurrentDate: ", currentDate);
        final String[] dates = currentDate.split("-");




        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                task[0] = new Task(editTaskName.getText().toString(), year, month, dayOfMonth);
            }
        });

        cancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (task[0] == null) {
                    task[0] = new Task(editTaskName.getText().toString(), Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]));
                }

                int checkedButtonId = radioGroup.getCheckedRadioButtonId();
                switch (checkedButtonId) {
                    case R.id.low_important:
                        task[0].setImportant("low");
                        break;
                    case R.id.mid_important:
                        task[0].setImportant("mid");
                        break;
                    case R.id.high_important:
                        task[0].setImportant("high");
                }

                writeToDb(task[0].getName(), task[0].getYear(), task[0].getMonth(), task[0].getDay(), task[0].getImportant());
                tasksFragment = new TasksFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, tasksFragment).commit();
                dialog.dismiss();

            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
    }

    private void writeToDb(String name, int year, int month, int day, String important) {
        DBHelper helper = new DBHelper(MainActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DBHelper.TASK_NAME, name);
        cv.put(DBHelper.YEAR, year);
        cv.put(DBHelper.MONTH, month);
        cv.put(DBHelper.DAY, day);
        cv.put(DBHelper.IMPORTANT, important);
        db.insert(DBHelper.TASKS_TABLE, null, cv);
        db.close();
        helper.close();
    }
}
