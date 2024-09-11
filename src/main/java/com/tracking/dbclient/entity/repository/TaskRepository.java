package com.tracking.dbclient.entity.repository;

import com.tracking.dbclient.entity.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {
    Task findById(int id);

    List<Task> findByStatus(short status);
}
