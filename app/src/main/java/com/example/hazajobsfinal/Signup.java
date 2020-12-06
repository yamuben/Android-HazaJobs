package com.example.hazajobsfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Signup extends AppCompatActivity {
    EditText txtEmail, txtPassword, txtConfirmPass;
    TextView  btnCancel;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        txtEmail = findViewById(R.id.emailId);
        txtPassword = findViewById(R.id.passwordId);
        txtConfirmPass = findViewById(R.id.rePasswordId);
        btnCreate = findViewById(R.id.registerId);
        btnCancel = findViewById(R.id.cancelId);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loading spinner = new Loading(Signup.this);
                spinner.startLoadingDialog();

                JSONObject data = new JSONObject();
                try {
                    data.put("email", txtEmail.getText().toString());
                    data.put("password", txtPassword.getText().toString());
                    data.put("confirmPassword", txtConfirmPass.getText().toString());
                }catch (Exception ex){

                }
                submitData(data, spinner);
            }
        });


    }

    public void submitData(JSONObject authData, Loading spinner){
        RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Constants.URL_SIGN_UP, authData, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    spinner.dismissDialog();
                    SharedPreferences sharedPreferences = getSharedPreferences("tokenPrefs", Context.MODE_PRIVATE);
                    String saveToken = response.getJSONObject("auth").getString("token");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", saveToken);
                    System.out.println(saveToken);
                    Toast.makeText(Signup.this, response.getString("status"), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    spinner.dismissDialog();
                    Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(Signup.this, Dashboard.class);
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spinner.dismissDialog();
                error.printStackTrace();
                Toast.makeText(Signup.this, "ERROR: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(jsonObjReq);
    }
}