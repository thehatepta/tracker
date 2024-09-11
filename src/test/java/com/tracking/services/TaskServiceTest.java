package com.tracking.services;

import com.tracking.dbclient.entity.Task;
import com.tracking.dbclient.entity.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task1;
    private Task task2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        task1 = new Task("task1", "description");
        task2 = new Task("task2", "description");
    }

    @Test
    public void testSaveTask() {
        taskService.saveTask(task1);
        verify(taskRepository, times(1)).save(task1);
    }

    @Test
    public void testUpdateTask() {
        taskService.updateTask(task1);
        verify(taskRepository, times(1)).save(task1);
    }

    @Test
    public void testFindAllTasks() {
        List<Task> tasks = Arrays.asList(task1, task2);
        when(taskRepository.findByStatus((short) 1)).thenReturn(tasks);
        List<Task> result = taskService.findAllTasks();
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findByStatus((short) 1);
    }

    @Test
    public void testStartTask() {
        task1.setName("Test Task");
        when(taskRepository.findById(1)).thenReturn(task1);

        taskService.startTask(1);

        assertNotNull(task1.getStartTime());
        verify(taskRepository, times(1)).save(task1);
        verify(taskRepository, times(1)).findById(1);
    }

    @Test
    public void testEndTask() {
        task1.setName("Test Task");
        when(taskRepository.findById(1)).thenReturn(task1);

        taskService.endTask(1);

        assertNotNull(task1.getEndTime());
        verify(taskRepository, times(1)).save(task1);
        verify(taskRepository, times(1)).findById(1);
    }

    @Test
    public void testEndOfDay() {
        task1.setStatus((short) 1);
        task2.setStatus((short) 1);
        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskRepository.findByStatus((short) 1)).thenReturn(tasks);

        taskService.endOfDay();

        assertNotNull(task1.getEndTime());
        assertNotNull(task1.getEndTime());
        verify(taskRepository, times(1)).save(task1);
        verify(taskRepository, times(1)).save(task2);
    }

}

