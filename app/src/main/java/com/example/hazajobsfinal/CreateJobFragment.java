package com.example.hazajobsfinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateJobFragment extends Fragment {
    EditText txtTitle, txtDeadline, txtCountry, txtCity, txtRecommended,txtDesc;
    TextView btnCancel;
    Button btnPost;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.creating_job, container, false);
        View view = inflater.inflate(R.layout.creating_job, container, false);
        txtTitle = view.findViewById(R.id.jobTitleId);
        txtDeadline = view.findViewById(R.id.deadlineId);
        txtCountry = view.findViewById(R.id.countryId);
        txtCity = view.findViewById(R.id.cityId);
        txtRecommended = view.findViewById(R.id.recommendId);
        txtDesc = view.findViewById(R.id.jobDescId);

        btnPost = view.findViewById(R.id.postJobId);
        btnCancel = view.findViewById(R.id.cancelJobId);


        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Loading spinner = new Loading(getActivity());
                submitData(txtTitle.getText().toString(),txtDeadline.getText().toString(),txtCountry.getText().toString(), txtCity.getText().toString(), txtRecommended.getText().toString(), txtDesc.getText().toString() , spinner);
                txtTitle.setText("");
                txtDeadline.setText("");
                txtCountry.setText("");
                txtCity.setText("");
                txtRecommended.setText("");
                txtDesc.setText("");

            }
        });
        return view;
    }

    public void submitData(final String title, final String deadline, final String country, final String city, final String recommended, final String desc , final Loading spinner) {
        if (txtRecommended.getText().toString().equals("")) {
            txtRecommended.setError("Recommendation is required");
        } else if (txtCity.getText().toString().equals("")) {
            txtCity.setError("City is required");
        } else if (txtCountry.getText().toString().length() < 8) {
            txtCountry.setError("Country is required");
        } else if (txtDeadline.getText().toString().equals("")) {
            txtDeadline.setError("Deadline is required");
        } else if (txtTitle.getText().toString().equals("")) {
            txtTitle.setError("Title is required");
        } else if (txtDesc.getText().toString().equals("")) {
            txtDesc.setError("Description is Required");
        } else {
            spinner.startLoadingDialog();
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("tokenPrefs", Context.MODE_PRIVATE);
            String token = sharedPreferences.getString("token", "");
            JSONObject location = new JSONObject();
            JSONObject ob = new JSONObject();
            try{
                location.put("country", country);
                location.put("district", city);
                ob.put("jobTitle", title);
                ob.put("jobDays", deadline);
                ob.put("jobDescription", desc);
                ob.put("jobLocation", location);
                ob.put("jobRecommandation", recommended);
                ob.put("jobSalary", "1000$");
            }catch (Exception e){

            }

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Constants.URL_CREATE_JOB, ob, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        spinner.dismissDialog();
                        Toast.makeText(getContext(), response.getString("status"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        spinner.dismissDialog();
                        Toast.makeText(getContext(), "Big Error" + e.toString() , Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    spinner.dismissDialog();
                    error.printStackTrace();
                    Toast.makeText(getContext(), "ERROR: " + error.toString(), Toast.LENGTH_LONG).show();
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
            requestQueue.add(jsonObjReq);

        }

    }
}
