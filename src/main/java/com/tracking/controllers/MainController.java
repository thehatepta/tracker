package com.tracking.controllers;

import com.tracking.dbclient.entity.Task;
import com.tracking.services.TaskServiceInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tasks")
public class MainController {
    @Autowired
    TaskServiceInterface taskService;

    private static Logger logger = LogManager.getLogger(MainController.class);

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getAllTasks() {
        logger.info("Returning all tasks");
        return taskService.findAllTasks();
    }

    @PostMapping("/createTask")
    public void createTask(@RequestBody Task task) {
        logger.info("Saving Task{}", task.getName());
        logger.debug("Task id{}", task.getId());
        taskService.saveTask(task);
    }

    @PostMapping("/updateTask")
    public void updateTask(@RequestBody Task task) {
        logger.info("Updating Task{}", task.getName());
        logger.debug("Task id{}", task.getId());
        taskService.updateTask(task);
    }

    @PostMapping("/startTask")
    public void startTask(@RequestParam int id) {
        logger.debug("Starting Task id{}", id);
        taskService.startTask(id);
    }

    @PostMapping("/endTask")
    public void endTask(@RequestParam int id) {
        logger.debug("Starting Task id{}", id);
        taskService.endTask(id);
    }
}
