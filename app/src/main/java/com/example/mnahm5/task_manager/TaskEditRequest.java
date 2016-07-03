package com.example.mnahm5.task_manager;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TaskEditRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "http://mnahm5.x10host.com/Task-Manager/editTask.php";
    private Map<String, String> params;

    public TaskEditRequest(String taskId, String taskTitle, String taskType, String dueDate, String description, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("taskId", taskId);
        params.put("taskTitle",taskTitle);
        params.put("description", description);
        params.put("taskType", taskType);
        params.put("dueDate", dueDate);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
