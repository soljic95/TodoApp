package com.example.sampletodoapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static com.example.sampletodoapp.model.Todo.TODO_TABLE_NAME;

@Entity(tableName = TODO_TABLE_NAME)
public class Todo implements Comparable<Todo> {

    public static final String TODO_TABLE_NAME = "todo_table";
    private static final String COLUMN_TODO_ID = "todo_id";
    private static final String COLUMN_TODO_HEADLINE = "todo_headline";
    private static final String COLUMN_TODO_TEXT = "todo_text";
    private static final String COLUMN_TODO_PRIORITY = "todo_priority";
    private static final String COLUMN_TODO_DEADLINE = "todo_deadline";

    @Ignore
    public Todo() {
    }

    @ColumnInfo(name = COLUMN_TODO_ID)
    @PrimaryKey()
    private long todoId;

    @ColumnInfo(name = COLUMN_TODO_HEADLINE)
    private String todoHead;

    @ColumnInfo(name = COLUMN_TODO_TEXT)
    private String todoText;

    @ColumnInfo(name = COLUMN_TODO_PRIORITY)
    public int todoPriority;

    @ColumnInfo(name = COLUMN_TODO_DEADLINE)
    public long todoDeadline;

    public Todo(long todoId, String todoHead, String todoText, int todoPriority, long todoDeadline) {
        this.todoId = todoId;
        this.todoHead = todoHead;
        this.todoText = todoText;
        this.todoPriority = todoPriority;
        this.todoDeadline = todoDeadline;
    }

    public String getTodoHead() {
        return todoHead;
    }

    public void setTodoHead(String todoHead) {
        this.todoHead = todoHead;
    }

    public String getTodoText() {
        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    public int getTodoPriority() {
        return todoPriority;
    }

    public void setTodoPriority(int todoPriority) {
        this.todoPriority = todoPriority;
    }

    public long getTodoId() {
        return todoId;
    }

    public void setTodoId(long todoId) {
        this.todoId = todoId;
    }

    public long getTodoDeadline() {
        return todoDeadline;
    }

    public void setTodoDeadline(long todoDeadline) {
        this.todoDeadline = todoDeadline;
    }

    @Override
    public int compareTo(Todo o) {
        return Integer.compare(this.todoPriority, o.getTodoPriority());
    }

}
