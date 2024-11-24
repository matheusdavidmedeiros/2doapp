package com.example.a2do1017pm;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.a2do1017pm.database.SQLiteHelper;
import com.example.a2do1017pm.models.Task;

public class AddTaskActivity extends AppCompatActivity {

    private EditText editTextTaskName;
    private EditText editTextTaskDescription;
    private SQLiteHelper dbHelper;
    private MediaPlayer mediaPlayer;
    private ImageView imageViewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextTaskName = findViewById(R.id.editTextTaskName);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        Button buttonSaveTask = findViewById(R.id.buttonSaveTask);
        imageViewImage = findViewById(R.id.imageViewImage);
        dbHelper = new SQLiteHelper(this);

        // Inicializa o MediaPlayer com o arquivo de áudio
        mediaPlayer = MediaPlayer.create(this, R.raw.audioshia);

        buttonSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = editTextTaskName.getText().toString().trim();
                String taskDescription = editTextTaskDescription.getText().toString().trim();

                if (taskName.isEmpty() || taskDescription.isEmpty()) {
                    Toast.makeText(AddTaskActivity.this, "Please enter both name and description", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("AddTaskActivity", "Task Name: " + taskName);
                Log.d("AddTaskActivity", "Task Description: " + taskDescription);

                Task task = new Task(taskName, taskDescription);
                dbHelper.addTask(task);

                Log.d("AddTaskActivity", "Task added to database");

                // Toca o áudio e exibe a imagem quando o botão é pressionado
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mediaPlayer.release();
                            mediaPlayer = null;
                            imageViewImage.setVisibility(View.GONE);  // Esconde a imagem após o áudio terminar
                            finish();  // Finaliza a activity
                        }
                    });
                }

                imageViewImage.setVisibility(View.VISIBLE);  // Mostra a imagem
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
