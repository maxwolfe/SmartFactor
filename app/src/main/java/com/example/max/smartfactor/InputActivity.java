package com.example.max.smartfactor;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        TextView uname = (TextView)findViewById(R.id.uname);
        TextView sname = (TextView)findViewById(R.id.sname);
        TextView snum  = (TextView)findViewById(R.id.snum);
        final EditText uname_enter = (EditText)findViewById(R.id.uname_enter);
        final EditText sname_enter = (EditText)findViewById(R.id.sname_enter);
        final EditText snum_enter = (EditText)findViewById(R.id.snum_enter);
        Button complete = (Button)findViewById(R.id.complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] info = new String[3];
                info[0] = uname_enter.getText().toString();
                info[1] = sname_enter.getText().toString();
                info[2] = snum_enter.getText().toString();
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",info);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}
