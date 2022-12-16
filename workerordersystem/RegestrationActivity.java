package com.zohausman.workerordersystem;

import static android.widget.Toast.makeText;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.BreakIterator;
import java.util.UUID;

public class RegestrationActivity extends AppCompatActivity {
    private Uri imageUri;
    private StorageReference storageReference;

    //find Views By id
    final ImageView customerpic = findViewById(R.id.customerpic);
    final EditText edtCustName = findViewById(R.id.edtCustName);
    final EditText edtcustomerid = findViewById(R.id.edtcustomerid);
    final EditText edtphone = findViewById(R.id.edtphone);
    final Button btnsave = findViewById(R.id.btnsave);
    final Button Regbtn = findViewById(R.id.Regbtn);

    //creating object of DatabaseReference class to access fire base
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("https://console.firebase.google.com/project/workerordersystem-32947/database/workerordersystem-32947-default-rtdb/data/~2F");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        // customer pic
        customerpic.setOnClickListener(new View.OnClickListener() {
            private Uri file;
            private StorageMetadata metadata;

            @Override
            public void onClick(View v) {
                //method that allow user to select image from their own device
                choosePicture();
            }

            private void choosePicture() {
                Intent intent = new Intent();
                intent.setType("image");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityResult(intent,validateRequestPermissionsRequestCode());

            }
            //method of Start Activity Result
            private void startActivityResult(Intent intent, int validateRequestPermissionsRequestCode) {
                int requestCode = 0, resultCode = 0;
                @Nullable Intent data = null;
                RegestrationActivity.super.onActivityResult(resultCode, resultCode, data);
                if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
                {
                    imageUri = data.getData();
                    customerpic.setImageURI(imageUri);
                    
                    uploadPicture(metadata);
                }
            }

            private void uploadPicture(StorageMetadata metadata) {

                final String RandomdKey = UUID.randomUUID().toString();

                StorageReference riversRef = storageReference.child("images/"+RandomdKey);
               riversRef.putFile(file)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads\
                    }
                })
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            private Object Snackbar;

                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    }
                });

            }

            private int validateRequestPermissionsRequestCode() {
                return 0;
            }
        });
        Regbtn.setOnClickListener(new View.OnClickListener() {
            private BreakIterator comphone;

            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                //get the data from the Edit text into string
                //  final String customerpicTxt =customerpic.getText(),toString();
                final String customeridtxt = edtcustomerid.getText().toString();
                final String CustNameTxt = edtCustName.getText().toString();
                final String phoneTxt = edtphone.getText().toString();
                final String comphoneTxt =comphone.getText().toString();

                //checking dta fields before sending to firebase
                if (CustNameTxt.isEmpty() || customeridtxt.isEmpty() || phoneTxt.isEmpty())
                {
                    makeText(RegestrationActivity.this, "please fill all fields", Toast.LENGTH_SHORT);
                }

                //checking phone no must be unique
                //if it unique then
                else if (!phoneTxt.equals(comphoneTxt)) {

                    makeText(RegestrationActivity.this, "Password is unique", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if phone is registered  before
                            if(snapshot.hasChild(phoneTxt)){
                                makeText(RegestrationActivity.this, "phone is already Registered", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                // Storing Data to fire base RealTime storage
                                //phone no is the unique identity to every user
                                //all the details of user comes
                                //databaseReference.child("user").child(phoneTxt).child(customerpic).setValue(customerpic);
                                databaseReference.child("users").child(phoneTxt).child(customeridtxt).setValue(customeridtxt);
                                databaseReference.child("users").child(phoneTxt).child(CustNameTxt).setValue(CustNameTxt);
                                databaseReference.child("users").child(phoneTxt).child(phoneTxt).setValue(phoneTxt);

                                makeText(RegestrationActivity.this, "Successfully Registered ", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }



                MainActivity.setOnClickLis(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();

                    }
                });

            }
        });
    }
}