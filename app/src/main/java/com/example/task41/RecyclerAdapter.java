package com.example.task41;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private Context context;
    private OnRemoveClickListener removeClickListener;

    //Constructor
    public RecyclerAdapter(List<Task> taskList, Context context, OnRemoveClickListener removeClickListener) {
        this.taskList = taskList;
        this.context = context;
        this.removeClickListener = removeClickListener;
    }

    //ViewHolder class to hold views for each task
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        TextView dueDateTextView;
        TextView timeTextView;
        Button removeButton;
        Button editButton;

        @SuppressLint("WrongViewCast")
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dueDateTextView = itemView.findViewById(R.id.dueDateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            removeButton = itemView.findViewById(R.id.removeButton);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }

    //Create view holders for Recycler View items
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_layout, parent, false);
        return new TaskViewHolder(view);
    }

    //Bind data to views in each Recycler view item
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Task task = taskList.get(position);
        holder.titleTextView.setText(Html.fromHtml("<b>" + task.getTitle() + "</b>"));
        holder.descriptionTextView.setText(task.getDescription());
        holder.dueDateTextView.setText("Date: " +task.getDueDate());
        holder.timeTextView.setText("Time: " +task.getTime());

        //Set OnClickListener for the Remove button
        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Trigger callback when button is clicked
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    removeClickListener.onRemoveClick(position);
                }
            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int taskId = taskList.get(position).getId();

                Log.i("TASK ID: ", String.valueOf(taskId));

                Intent intent = new Intent(context, EditTaskActivity.class);
                intent.putExtra("TASK_ID", String.valueOf(taskId));
                intent.putExtra("TASK_TITLE", taskList.get(position).getTitle());
                intent.putExtra("TASK_DESCRIPTION", taskList.get(position).getDescription());
                intent.putExtra("TASK_DUE_DATE", taskList.get(position).getDueDate());
                intent.putExtra("TASK_TIME", taskList.get(position).getTime());
                context.startActivity(intent);
            }
        });
    }

    //Return number of items in recycler view
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    public interface OnRemoveClickListener {
        void onRemoveClick(int position);
    }
}
