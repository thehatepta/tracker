package com.tracking.dbclient.entity;


import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String name;
    //status field is responsible for soft delete, 1 is active and 0 is deleted
    private short status;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public short getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Task(String name, String description) {
        this.status = 1;
        this.name = name;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
