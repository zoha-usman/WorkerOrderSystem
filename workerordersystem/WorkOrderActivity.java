package com.zohausman.workerordersystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WorkOrderActivity extends AppCompatActivity {
    //find view by id
    final EditText edtWname = findViewById(R.id.edtWname);
    final EditText edtDateIssue = findViewById(R.id.edtDateIssue);
    final EditText edtDateComplet = findViewById(R.id.edtDateComplet);
    final EditText customcodetxt = findViewById(R.id.customcoder);
    final RadioButton rbtn = findViewById(R.id.rbtn);
    final Button upbtn = findViewById(R.id.upbtn);

    //creating object of DatabaseReference class to access fire base
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("https://console.firebase.google.com/project/workerordersystem-32947/database/workerordersystem-32947-default-rtdb/data/~2F");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_order);

        upbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String WnameTxt  = edtWname.getText().toString();
                final String DateIssueTxt  = edtDateIssue.getText().toString();
                final String DateComplet  = edtDateComplet.getText().toString();
                final String customCodeTxt = customcodetxt.getText().toString();
                final String rbtnTxt  = rbtn.getText().toString();


            }
        });
    }
}