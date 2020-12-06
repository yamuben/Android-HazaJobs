package com.example.hazajobsfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.hazajobsfinal.Adapters.Adapter;
import com.example.hazajobsfinal.Modal.JobModal;
public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    List<JobModal> jobs;
    View view;

    public HomeFragment(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.jobsContainer);
        final Loading spinner = new Loading(getActivity());
        spinner.startLoadingDialog();
        jobs = new ArrayList<>();
        loadJobs(spinner);


//        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                recyclerViewAdapter.deleteItem(viewHolder.getAdapterPosition());
//                recyclerViewAdapter.notifyDataSetChanged();
//            }
//        }).attachToRecyclerView(recyclerView);

        return view;
    }

    private void loadJobs(final Loading spinner) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("tokenPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        JSONObject data = new JSONObject();
        try {
            data.put("token", token);
        }catch (Exception ex){

        }

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.URL_GET_JOBS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject resObj = response.getJSONObject("data");
                    JSONArray resArr = resObj.getJSONArray("jobInfos");

                    for (int index = 0; index < resArr.length(); index++) {
                            JSONObject jobObject = resArr.getJSONObject(index);
                            JobModal job = new JobModal();
                            job.setTitle(jobObject.getString("jobTitle").toString());
                            job.setDescription(jobObject.getString("jobDescription").toString());
                            job.setCity(jobObject.getJSONObject("jobLocation").getString("district").toString());
                            job.setCountry(jobObject.getJSONObject("jobLocation").getString("country").toString());
                            job.setJobId(jobObject.getString("_id").toString());
                            job.setJobSalary(jobObject.getString("jobSalary").toString());
                            //job.setJobOwner(jobObject.getString("jobOwner").toString());
                            job.setJobRecommendation(jobObject.getString("jobRecommandation").toString());
                            jobs.add(job);
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                final Adapter recyclerViewAdapter = new Adapter(getContext(), jobs);
                recyclerView.setAdapter(recyclerViewAdapter);
                spinner.dismissDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Connect to the internet, If you're connect try again later!.", Toast.LENGTH_LONG).show();
            }
        });
//        {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError{
//                Map<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                headers.put("x_auth_token", token);
//                System.out.println(headers);
//                return headers;
//            }
//        };

        queue.add(jsonObjectRequest);
}
}
