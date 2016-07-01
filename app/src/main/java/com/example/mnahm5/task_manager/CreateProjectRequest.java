package com.example.mnahm5.task_manager;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CreateProjectRequest extends StringRequest{
    private static final String LOGIN_REQUEST_URL = "http://mnahm5.x10host.com/Task-Manager/addProject.php";
    private Map<String, String> params;

    public CreateProjectRequest(String username, String projectName, String description, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("projectName",projectName);
        params.put("description", description);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
