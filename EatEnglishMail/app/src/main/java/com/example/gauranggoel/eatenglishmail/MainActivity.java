package com.example.gauranggoel.eatenglishmail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText et1,et2,et3;
    Button btn;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    //   String url="https://vireshapt.000webhostapp.com/eatFile.php";
 String url="https://vireshapt.000webhostapp.com/login_adroid_new.php";

    RequestQueue requestQueue;
    String id="",pass="",username="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1= (EditText) findViewById(R.id.editText1);
        et2= (EditText) findViewById(R.id.editText2);
        et3= (EditText) findViewById(R.id.editText3);
        btn= (Button) findViewById(R.id.button);

        pref=getSharedPreferences("jadu",Context.MODE_PRIVATE);
        editor=pref.edit();

        if(pref.getString("Log_Password",null)!= null && pref.getString("Log_Device",null)!= null && pref.getString("Log_Username",null)!= null)
        {
            Intent intent = new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent);
            finish();
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo info = cm.getActiveNetworkInfo();

                if(info!=null && info.isConnectedOrConnecting())
                {

                    pass = et1.getText().toString();
                    id = et2.getText().toString();
                    username = et3.getText().toString();

                    if(id.equals("") && pass.equals("") && username.equals(""))
                    {
                        if(pass.equals(""))
                            et1.setError("Enter password please");
                        if(id.equals(""))
                            et2.setError("Enter device id please");
                        if(username.equals(""))
                            et3.setError("Enter username please");
                    }
                    else{

                        requestQueue = Volley.newRequestQueue(MainActivity.this);
                        sendRequest();
                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void sendRequest() {

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            String name1="";
            String pass1="";
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject response1 =  new JSONObject(response);

                    pass1=response1.getString("status");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Toast.makeText(MainActivity.this, ""+pass1, Toast.LENGTH_SHORT).show();
                if(pass1.equals(""))
                {
                    Snackbar.make(findViewById(android.R.id.content), "Login Failed", Snackbar.LENGTH_LONG).show();
                    et1.setText("");
                }
                else if(pass1.equals("success"))
                {
                    editor.putString("Log_Password",pass);
                    editor.putString("Log_Device",id);
                    editor.putString("Log_Username",username);

                    editor.commit();

                    et1.setText("");
                    et2.setText("");
                    et3.setText("");
                    Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                    startActivity(intent);
                    finish();
                }

                //Toast.makeText(MainActivity.this, ""+response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Failed To Connect please try again", Toast.LENGTH_SHORT).show();
                et2.setText("");
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> list=new HashMap<>();
                list.put("Log_Password",pass);
                list.put("Log_Device",id);
                list.put("Log_Username",username);

                return list;

            }
        };

        requestQueue.add(request);

    }
}
