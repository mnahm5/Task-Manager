package com.example.mnahm5.task_manager;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateTaskRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://mnahm5.x10host.com/Task-Manager/addTask.php";
    private Map<String, String> params;

    public CreateTaskRequest(String projectId, String taskType, String taskTitle, String description, String dueDate, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("projectId", projectId);
        params.put("taskType", taskType);
        params.put("taskTitle", taskTitle);
        params.put("description", description);
        params.put("dueDate", dueDate);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}