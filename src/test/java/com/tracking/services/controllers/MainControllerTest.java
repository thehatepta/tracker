package com.tracking.services.controllers;

import com.tracking.controllers.MainController;
import com.tracking.dbclient.entity.Task;
import com.tracking.services.TaskServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MainControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskServiceInterface taskService;

    @InjectMocks
    private MainController mainController;

    Task task1;
    Task task2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
        task1 = new Task("Task 1", "Description 1");
        task2 = new Task("Task 2", "Description 2");
    }

    @Test
    public void testGetAllTasks() throws Exception {

        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskService.findAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/tasks")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'name':'Task 1','description':'Description 1'},{'name':'Task 2','description':'Description 2'}]"));

        verify(taskService, times(1)).findAllTasks();
    }

    @Test
    public void testCreateTask() throws Exception {
        Task task = new Task("Task 1", "Description 1");

        mockMvc.perform(post("/tasks/createTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Task 1\",\"description\":\"Description 1\"}"))
                .andExpect(status().isOk());

        verify(taskService, times(1)).saveTask(any(Task.class));
    }

    @Test
    public void testUpdateTask() throws Exception {
        Task task = new Task("Task 1", "Updated Description");

        mockMvc.perform(post("/tasks/updateTask")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Task 1\",\"description\":\"Updated Description\"}"))
                .andExpect(status().isOk());

        verify(taskService, times(1)).updateTask(any(Task.class));
    }

    @Test
    public void testStartTask() throws Exception {
        int taskId = 1;

        mockMvc.perform(post("/tasks/startTask")
                        .param("id", String.valueOf(taskId)))
                .andExpect(status().isOk());

        verify(taskService, times(1)).startTask(taskId);
    }

    @Test
    public void testEndTask() throws Exception {
        int taskId = 1;

        mockMvc.perform(post("/tasks/endTask")
                        .param("id", String.valueOf(taskId)))
                .andExpect(status().isOk());

        verify(taskService, times(1)).endTask(taskId);
    }
}
