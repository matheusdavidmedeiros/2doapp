package com.example.a2do1017pm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.a2do1017pm.models.Task;
import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "2do.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_TASKS_TABLE);
        Log.d("SQLiteHelper", "Database created with table: " + TABLE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
        Log.d("SQLiteHelper", "Database upgraded from version " + oldVersion + " to " + newVersion);
    }

    // Método para adicionar uma nova tarefa
    public void addTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, task.getName());
        values.put(COLUMN_DESCRIPTION, task.getDescription());

        long result = db.insert(TABLE_TASKS, null, values);
        db.close();

        if (result != -1) {
            Log.d("SQLiteHelper", "Task added: " + task.getName());
        } else {
            Log.d("SQLiteHelper", "Failed to add task: " + task.getName());
        }
    }

    // Método para buscar todas as tarefas
    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task();
                task.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                task.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                task.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
                taskList.add(task);

                Log.d("SQLiteHelper", "Task ID: " + task.getId() + ", Name: " + task.getName() + ", Description: " + task.getDescription());
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return taskList;
    }

    // Método para buscar uma tarefa pelo ID
    public Task getTask(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_TASKS, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            Task task = new Task(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
            cursor.close();
            db.close();
            Log.d("SQLiteHelper", "Task fetched: " + task.getName());
            return task;
        } else {
            db.close();
            Log.d("SQLiteHelper", "No task found with ID: " + id);
            return null;
        }
    }

    // Método para atualizar uma tarefa
    public int updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, task.getName());
        values.put(COLUMN_DESCRIPTION, task.getDescription());

        int rowsAffected = db.update(TABLE_TASKS, values, COLUMN_ID + "=?", new String[]{String.valueOf(task.getId())});
        db.close();

        Log.d("SQLiteHelper", "Task updated: " + task.getName() + ", Rows affected: " + rowsAffected);
        return rowsAffected;
    }

    // Método para excluir uma tarefa
    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_TASKS, COLUMN_ID + "=?", new String[]{String.valueOf(task.getId())});
        db.close();

        Log.d("SQLiteHelper", "Task deleted: " + task.getName() + ", Rows affected: " + rowsAffected);
    }
    public void deleteAllTasks() { SQLiteDatabase db = this.getWritableDatabase(); db.delete(TABLE_TASKS, null, null); db.close(); Log.d("SQLiteHelper", "All tasks deleted"); }
}
