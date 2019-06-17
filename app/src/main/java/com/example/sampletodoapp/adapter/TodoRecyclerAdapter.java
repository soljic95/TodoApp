package com.example.sampletodoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annimon.stream.Optional;
import com.example.sampletodoapp.R;
import com.example.sampletodoapp.model.Todo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TodoRecyclerAdapter extends RecyclerView.Adapter<TodoRecyclerAdapter.MyViewHolder> {

    private static final String SIMPLE_DATE_FORMAT_DATE = "dd.MM.yyyy";

    private final LayoutInflater layoutInflater;
    private final List<Todo> todoList = new ArrayList<>();
    private final SimpleDateFormat sdfDate = new SimpleDateFormat(SIMPLE_DATE_FORMAT_DATE, Locale.getDefault());
    private Optional<TodoRecyclerAdapterListener> listenerOptional = Optional.empty();

    public interface TodoRecyclerAdapterListener {

        void onAdapterItemClicked(long todoId);
    }

    public TodoRecyclerAdapter(LayoutInflater inflater) {
        this.layoutInflater = inflater;
    }

    public void setAdapterListener(TodoRecyclerAdapterListener listener) {
        listenerOptional = Optional.of(listener);
    }

    @NonNull
    @Override
    public TodoRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MyViewHolder(layoutInflater.inflate(R.layout.todo_display_item, parent, false));
    }

    public void addTodoList(List<Todo> todos) {
        todoList.clear();
        todoList.addAll(todos);
        notifyDataSetChanged();
    }

    public long getTodoAt(int position) { //used for itemTouchHelper
        return todoList.get(position).getTodoId();
    }

    @Override
    public void onBindViewHolder(@NonNull TodoRecyclerAdapter.MyViewHolder holder, final int position) {

        Todo todo = todoList.get(position);

        holder.tvHead.setText(todo.getTodoHead());
        holder.tvText.setText(todo.getTodoText());
        holder.tvDeadline.setText(sdfDate.format(todo.getTodoDeadline()));
        holder.todo = todo;

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTodoHead)
        TextView tvHead;
        @BindView(R.id.tvTodoText)
        TextView tvText;
        @BindView(R.id.todoDeadline)
        TextView tvDeadline;
        @BindView(R.id.contraintLayout)
        ConstraintLayout layout;
        Todo todo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        @OnClick(R.id.contraintLayout)
        public void seeToDoDetails() {
            listenerOptional.ifPresent(listener -> listener.onAdapterItemClicked(todo.getTodoId()));

        }
    }

}