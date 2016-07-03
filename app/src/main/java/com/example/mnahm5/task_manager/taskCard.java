package com.example.mnahm5.task_manager;

public class TaskCard {
    public String taskId, taskTitle, description, dueDate;

    public TaskCard(String taskId, String taskTitle, String description, String dueDate) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.description = description;
        this.dueDate = dueDate;
    }
}
