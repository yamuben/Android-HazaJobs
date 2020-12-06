package com.example.hazajobsfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button  loginBtn;

    TextView createBtn, txtEmail, txtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createBtn = findViewById(R.id.createAccountIdMain);
        loginBtn = findViewById(R.id.loginIdMain);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Signup.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loading spinner = new Loading(MainActivity.this);
                spinner.startLoadingDialog();
                JSONObject data = new JSONObject();

                try {
                    data.put("email", txtEmail.getText().toString());
                    data.put("password", txtPass.getText().toString());
                }catch (Exception ex){

                }

                submitData(data, spinner);

            }
        });

    }

    public void submitData(JSONObject authData, Loading spinner){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Constants.URL_LOGIN_UP, authData, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    spinner.dismissDialog();
                    Toast.makeText(MainActivity.this, response.getString("status"), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    spinner.dismissDialog();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spinner.dismissDialog();
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "ERROR: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjReq);
    }
}