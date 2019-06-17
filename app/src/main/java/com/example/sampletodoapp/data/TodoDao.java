package com.example.sampletodoapp.data;

import com.example.sampletodoapp.model.Todo;
import com.example.sampletodoapp.model.User;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface TodoDao {

    @Query("SELECT * FROM TODO_TABLE WHERE todo_id= :todoId")
    Single<Todo> getTodoFromDb(long todoId);

    @Insert
    void insertUserInDb(User user);

    @Query("SELECT * FROM user_table")
    Single<User> getUserFromDatabase();

    @Insert()
    void insertTodo(Todo todo);

    @Query("DELETE FROM todo_table WHERE todo_id = :todoId")
    void deleteTodo(long todoId);

    @Query("SELECT * FROM todo_table")
    Observable<List<Todo>> getAllTodos();
}
