package com.example.task41;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EditTaskActivity extends AppCompatActivity {

    EditText nameText;
    EditText descriptionText;
    DatePicker datePicker;
    TimePicker timePicker;
    SQLiteDB dbHelper;

    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        dbHelper = new SQLiteDB(this);

        nameText = findViewById(R.id.nameText);
        descriptionText = findViewById(R.id.descriptionText);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);


        String taskTitle = getIntent().getStringExtra("TASK_TITLE");
        String taskDescription = getIntent().getStringExtra("TASK_DESCRIPTION");
        String taskDueDate = getIntent().getStringExtra("TASK_DUE_DATE");
        String taskTime = getIntent().getStringExtra("TASK_TIME");

        nameText.setText(taskTitle);
        descriptionText.setText(taskDescription);

        //OnClickListener for the save button
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                String title = nameText.getText().toString();
                String description = descriptionText.getText().toString();
                String dueDate = getFormattedDate();
                String time = getFormattedTime();
                long taskId = getTaskId();

                long result = dbHelper.updateData(taskId, title, description, time, dueDate);

                if (result != -1) {

                    Log.d("EDIT_TASK", "Updated task information:");
                    Log.d("EDIT_TASK", "ID" + taskId);
                    Log.d("EDIT_TASK","Title: " + title);
                    Log.d("EDIT_TASK", "Description: " + description);
                    Log.d("EDIT_TASK", "Due Date: " + dueDate);
                    Log.d("EDIT_TASK", "Time: " + time);

                    Toast.makeText(EditTaskActivity.this, "Task edited successfully.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditTaskActivity.this, "Failed to edit task.", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(EditTaskActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });

        //OnCLickListener for the cancel button
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String getFormattedDate() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        return String.format("%02d-%02d-%d", day, month, year);
    }

    private String getFormattedTime() {
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        return String.format("%02d:%02d", hour, minute);
    }
}