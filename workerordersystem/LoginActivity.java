package com.zohausman.workerordersystem;

import static android.widget.Toast.makeText;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.BreakIterator;

public class LoginActivity extends AppCompatActivity {
    //find views by id
    final EditText edtName = findViewById(R.id.edtName);
    final EditText edtpass = findViewById(R.id.edtpass);
    final Button btnlogin = findViewById(R.id.btnlogin);

    //creating object of DatabaseReference class to access fire base
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("https://console.firebase.google.com/project/workerordersystem-32947/database/workerordersystem-32947-default-rtdb/data/~2F");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            private BreakIterator compassword;

            @SuppressLint("ShowToast")

            @Override
            public void onClick(View v) {
                final String usernameTxt = edtName.getText().toString();
                final String passwordTxt = edtpass.getText().toString();
                final String compasswordtxt = compassword.getText().toString();
                //checking the edittext fields are empty or not
                if (usernameTxt.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Must Enter Your Username and Password for login !", Toast.LENGTH_SHORT).show();
                }
                //checking password is unique or not
                else if (!passwordTxt.equals(compasswordtxt)) {
                    Toast.makeText(LoginActivity.this, "Password is unique", Toast.LENGTH_SHORT).show();
                } else {

                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if phone is registered  before
                            if (snapshot.hasChild(passwordTxt)) {
                                makeText(LoginActivity.this, "Password is already present !!!  ......Try Again.....", Toast.LENGTH_SHORT).show();
                            } else {
                                // Storing Data to fire base RealTime storage
                                //phone no is the unique identity to every user
                                //all the details of user comes
                                databaseReference.child("user").child(passwordTxt).child(usernameTxt).setValue(usernameTxt);
                                databaseReference.child("user").child(passwordTxt).child(passwordTxt).setValue(passwordTxt);

                                Toast.makeText(LoginActivity.this, "Successfully longin", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }
}