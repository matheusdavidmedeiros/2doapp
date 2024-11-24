package com.example.a2do1017pm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.a2do1017pm.database.SQLiteHelper;
import com.example.a2do1017pm.models.Task;
import com.example.a2do1017pm.adapters.TaskAdapter;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SQLiteHelper dbHelper;
    private TaskAdapter taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        Button buttonAddTask = findViewById(R.id.buttonAddTask);
        Button buttonDeleteAllTasks = findViewById(R.id.buttonDeleteAllTasks);
        dbHelper = new SQLiteHelper(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadTasks();

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddTaskActivity.class));
            }
        });

        buttonDeleteAllTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteAllTasks();
                loadTasks();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();  // Recarrega as tarefas sempre que a atividade for retomada
    }

    private void loadTasks() {
        List<Task> tasks = dbHelper.getAllTasks();
        if (tasks.isEmpty()) {
            Log.d("MainActivity", "No tasks found in database");
        } else {
            for (Task task : tasks) {
                Log.d("MainActivity", "Task ID: " + task.getId() + ", Name: " + task.getName());
            }
        }
        taskAdapter = new TaskAdapter(tasks, this);
        recyclerView.setAdapter(taskAdapter);
    }
}
