package com.example.task41;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private SQLiteDB dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        dbHelper = new SQLiteDB(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Retrieval of tasks from DB
        List<Task> taskList = dbHelper.getAllTasks();

        //Sort the taskList based on the associated due dates
        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    Date dueDate1 = sdf.parse(o1.getDueDate());
                    Date dueDate2 = sdf.parse(o2.getDueDate());
                    return dueDate1.compareTo(dueDate2);
                } catch (ParseException e) {
                    Log.e("DATE PARSE ERROR", "Error passing due date", e);
                }
                return 1;
            }
        });

        Log.d("DATABASE_CONTENT", "NUMBER OF TASKS IN DATABASE: " + taskList.size());
        for (Task task : taskList) {
            Log.d("DATABASE_CONTENT", task.toString());
        }

        //Initialize RecyclerView adapter
        recyclerAdapter = new RecyclerAdapter(taskList, this, new RecyclerAdapter.OnRemoveClickListener() {
            @Override
            public void onRemoveClick(int position) {
                //Remove task from task list
                Task removedTask = taskList.remove(position);
                //Notify adapter
                recyclerAdapter.notifyDataSetChanged();
                //Remove task from database
                dbHelper.deleteData(removedTask.getId());
            }
        });

        //Set adapter to recycler view
        recyclerView.setAdapter(recyclerAdapter);

        //Get button reference
        Button addNewButton = findViewById(R.id.addNewButton);

        //OnClickListener for the Add button
        addNewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomePageActivity.this, AddTaskActivity.class);
                    startActivity(intent);
                }
        });

        //OnCLickListener for the exit button
        Button exitButton = findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }
}