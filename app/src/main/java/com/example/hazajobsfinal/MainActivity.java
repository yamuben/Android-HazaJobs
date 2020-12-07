package com.example.hazajobsfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Button  loginBtn;
private static int Splash_Time = 3000;
    TextView createBtn;
    EditText txtEmail, txtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        createBtn = findViewById(R.id.createAccountIdMain);
        loginBtn = findViewById(R.id.loginIdMain);
        txtEmail = findViewById(R.id.emailIdMain);
        txtPass = findViewById(R.id.passwordIdMain);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Signup.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regex = "^(.+)@(.+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(txtEmail.getText().toString());
                if(txtEmail.getText().toString().equals("")){
                    txtEmail.setError("email is required");
                }else if(txtPass.getText().toString().equals("")){
                    txtPass.setError("Password is required");
                }else if(txtPass.getText().toString().length() < 8){
                    txtPass.setError("Password must have at least 8 characters");
                }else if (!matcher.matches()){
                    txtEmail.setError("Invalid Email");
                }else{
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
                    SharedPreferences sharedPreferences = getSharedPreferences("tokenPrefs", Context.MODE_PRIVATE);
                    String saveToken = response.getJSONObject("auth").getString("token");
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", saveToken);
                    editor.commit();
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