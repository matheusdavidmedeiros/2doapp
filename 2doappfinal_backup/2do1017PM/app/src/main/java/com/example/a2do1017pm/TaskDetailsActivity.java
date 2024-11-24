package com.example.a2do1017pm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.a2do1017pm.database.SQLiteHelper;
import com.example.a2do1017pm.models.Task;

public class TaskDetailsActivity extends AppCompatActivity {

    private EditText editTextTaskName;
    private EditText editTextTaskDescription;
    private Button buttonSaveTask;
    private SQLiteHelper dbHelper;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        editTextTaskName = findViewById(R.id.editTextTaskName);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        buttonSaveTask = findViewById(R.id.buttonSaveTask);
        dbHelper = new SQLiteHelper(this);

        Intent intent = getIntent();
        taskId = intent.getIntExtra("TASK_ID", -1);

        if (taskId != -1) {
            Task task = dbHelper.getTask(taskId);
            if (task != null) {
                editTextTaskName.setText(task.getName());
                editTextTaskDescription.setText(task.getDescription());
            }
        }

        buttonSaveTask.setOnClickListener(v -> {
            String taskName = editTextTaskName.getText().toString().trim();
            String taskDescription = editTextTaskDescription.getText().toString().trim();

            if (taskName.isEmpty() || taskDescription.isEmpty()) {
                Toast.makeText(TaskDetailsActivity.this, "Please enter both name and description", Toast.LENGTH_SHORT).show();
                return;
            }

            Task task = new Task(taskName, taskDescription);
            task.setId(taskId);
            dbHelper.updateTask(task);

            Toast.makeText(TaskDetailsActivity.this, "Task updated", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
