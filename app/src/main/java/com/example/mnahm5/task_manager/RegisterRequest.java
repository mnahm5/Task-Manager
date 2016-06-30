package com.example.mnahm5.task_manager;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://mnahm5.x10host.com/Task-Manager/register.php";
    private Map<String, String> params;

    public RegisterRequest(String username, String password, String fullName, String email, String companyName, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("fullName", fullName);
        params.put("email", email);
        params.put("companyName", companyName);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

