package com.tracking.services;

import com.tracking.dbclient.entity.Task;
import com.tracking.dbclient.entity.repository.TaskRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class TaskService implements TaskServiceInterface {
    @Autowired
    TaskRepository taskRepository;

    private static Logger logger = LogManager.getLogger(TaskService.class);

    public TaskService() {
    }

    public void saveTask(Task task) {
        taskRepository.save(task);
        logger.info("Task{} saved", task.getName());
    }

    public void updateTask(Task task) {
        taskRepository.save(task);
        logger.info("Task{} updated", task.getName());
    }

    public List<Task> findAllTasks() {
        return taskRepository.findByStatus((short) 1);
    }

    public void startTask(int id) {
        Task task = taskRepository.findById(id);
        task.setStartTime(LocalTime.now());
        taskRepository.save(task);
        logger.info("Task{} started", task.getName());
    }

    public void endTask(int id) {
        Task task = taskRepository.findById(id);
        task.setEndTime(LocalTime.now());
        taskRepository.save(task);
        logger.info("Task{} ended", task.getName());
    }

    @Scheduled(cron = "59 23 * * *")
    public void endOfDay() {
        LocalTime now = LocalTime.now();
        List<Task> tasks = taskRepository.findByStatus((short) 1);
        tasks
                .stream()
                .filter(task -> task.getEndTime() == null)
                .forEach(task -> {
                    task.setEndTime(now);
                    taskRepository.save(task);
                });
        logger.info("All tasks are closed");
    }
}
