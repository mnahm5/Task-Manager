package com.example.mnahm5.task_manager;

public class taskCard {
    public String projectId, taskTitle, description, dueDate;

    public taskCard(String projectId, String taskTitle, String description, String dueDate) {
        this.projectId = projectId;
        this.taskTitle = taskTitle;
        this.description = description;
        this.dueDate = dueDate;
    }
}
