package com.example.mnahm5.task_manager;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ProjectsRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://mnahm5.x10host.com/Task-Manager/getAllProjects.php";
    private Map<String, String> params;

    public ProjectsRequest(String username, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
