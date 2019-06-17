package com.example.sampletodoapp.utils;

import com.example.sampletodoapp.model.Todo;

import java.util.Collections;
import java.util.List;

public class CollectionsUtils {//ima li smisla ovo šta sam napravio?

    public List<Todo> sortList(List<Todo> unsortedList) {
        List<Todo> sortedList;
        sortedList = unsortedList;
        Collections.sort(sortedList);
        return sortedList;
    }
}
