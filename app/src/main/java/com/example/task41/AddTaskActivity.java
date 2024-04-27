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

import java.util.List;

public class AddTaskActivity extends AppCompatActivity {

    EditText nameText;
    EditText descriptionText;
    DatePicker datePicker;
    TimePicker timePicker;
    SQLiteDB dbHelper;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dbHelper = new SQLiteDB(this);

        nameText = findViewById(R.id.nameText);
        descriptionText = findViewById(R.id.descriptionText);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);

        //OnCLickListener for the confirm button in add task screen
        Button addNewButton = findViewById(R.id.addNewButton);
        addNewButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                String title = nameText.getText().toString();
                String description = descriptionText.getText().toString();
                String dueDate = getFormattedDate();
                String time = getFormattedTime();

                long result = dbHelper.insertData(title, description, time, dueDate);

                if (result != -1) {
                    Toast.makeText(AddTaskActivity.this, "Task added successfully.", Toast.LENGTH_SHORT).show();
                    //Retrieval of updated list from DB
                    List<Task> updatedTaskList = dbHelper.getAllTasks();
                    //Update dataset used by adapter with the updated list
                    recyclerAdapter.setTaskList(updatedTaskList);
                    //Notify adapter of data set change
                    recyclerAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(AddTaskActivity.this, "Failed to add task.", Toast.LENGTH_SHORT).show();
                }

                Log.d("AddTaskActivity", "Insertion Result: " + result);

                Intent intent = new Intent(AddTaskActivity.this, HomePageActivity.class);
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