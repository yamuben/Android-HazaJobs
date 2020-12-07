package com.example.hazajobsfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hazajobsfinal.Adapters.Adapter;
import com.example.hazajobsfinal.Adapters.UsersAdapter;
import com.example.hazajobsfinal.Modal.JobModal;
import com.example.hazajobsfinal.Modal.UserModal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class UsersFragment extends Fragment {
    RecyclerView recyclerView;
    List<UserModal> users;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.users_fragment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.usersContainer);
        final Loading spinner = new Loading(getActivity());
        spinner.startLoadingDialog();
        users = new ArrayList<>();
        loadUsers(spinner);
       return view;

    }

    private void loadUsers(final Loading spinner) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("tokenPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.URL_GET_USERS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject resObj = response.getJSONObject("data");
                    JSONArray resArr = resObj.getJSONArray("users");

                    for (int index = 0; index < resArr.length(); index++) {
                        JSONObject jobObject = resArr.getJSONObject(index);
                        UserModal user = new UserModal();
                        user.setTitle(jobObject.getString("title").toString());
                        user.setFirstName(jobObject.getString("firstName").toString());
                        user.setLastName(jobObject.getString("lastName").toString());
                        user.setMotor(jobObject.getString("motor").toString());
                        user.setId(jobObject.getString("_id").toString());
                        user.setPhone(jobObject.getString("phone").toString());
                        user.setImageUrl(jobObject.getString("profilePicture").toString());
                        users.add(user);
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                final UsersAdapter recyclerViewAdapter = new UsersAdapter(getContext(), users);
                recyclerView.setAdapter(recyclerViewAdapter);
                spinner.dismissDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Connect to the internet, If you're connect try again later!.", Toast.LENGTH_LONG).show();
            }
        }){
            @NonNull
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                System.out.println("Token in header: " + token);
                headers.put("Authorization", token);
                return headers;
            }

        };

        queue.add(jsonObjectRequest);
    }



}
