package com.example.gauranggoel.eatenglishmail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    TextView tv1,tv2,tv3;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

         tv1= (TextView) findViewById(R.id.editText);
         tv2= (TextView) findViewById(R.id.editText6);
         tv3= (TextView) findViewById(R.id.editText5);


        pref=getSharedPreferences("jadu", Context.MODE_PRIVATE);
        editor=pref.edit();

        tv1.setText("Log_Password:"+pref.getString("Log_Password",null));
        tv2.setText("Log_Device:"+pref.getString("Log_Device",null));
        tv3.append("Log_Username:"+pref.getString("Log_Username",null));

        Button btn= (Button) findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.clear();

                editor.commit();

                Intent intent=new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
