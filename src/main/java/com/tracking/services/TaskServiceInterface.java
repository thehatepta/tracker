package com.tracking.services;

import com.tracking.dbclient.entity.Task;

import java.util.List;

public interface TaskServiceInterface {
    void saveTask(Task task);

    void updateTask(Task task);

    void endOfDay();

    List<Task> findAllTasks();

    void startTask(int id);

    void endTask(int id);
}
