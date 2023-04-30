package com.example.votizen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class add_candidates extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    FirebaseStorage storage;
    StorageReference storageReference;
    Spinner candidate_state;
    EditText candidate_ward_number,candidate_first_name,candidate_last_name,candidate_party_name,candidate_promises_made,candidate_promises_fulfilled,candidate_criminal_records;
    Button candidate_save_button;

    private Button btnChooseSymbol, btnUploadSymbol,btnChoosePhoto,btnUploadPhoto;
    private ImageView imageView;

    private Uri filePath;

    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,add_or_edit_candidates.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcandidate);
        candidate_criminal_records = findViewById(R.id.candidate_criminal_records);
        candidate_first_name = findViewById(R.id.candidate_first_name);
        candidate_last_name = findViewById(R.id.candidate_last_name);
        candidate_party_name = findViewById(R.id.candidate_party_name);
        candidate_ward_number = findViewById(R.id.candidate_ward_number);
        candidate_promises_fulfilled = findViewById(R.id.candidate_promises_fulfilled);
        candidate_promises_made = findViewById(R.id.candidate_promises_made);
        candidate_state = findViewById(R.id.candidate_state);
        candidate_save_button = findViewById(R.id.candidate_save_button);
//        candidate_party_symbol = findViewById(R.id.candidate_party_symbol);
        btnChooseSymbol = findViewById(R.id.choose_symbol_button);
        btnUploadSymbol = findViewById(R.id.upload_symbol_button);
        btnChoosePhoto = findViewById(R.id.choose_photo_button);
        btnUploadPhoto = findViewById(R.id.upload_photo_button);


        candidate_state.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> states = new ArrayList<>();
        states.add(0,"Select State");
        states.add("Andra Pradesh");
        states.add("Arunachal Pradesh");
        states.add("Assam");
        states.add("Bihar");
        states.add("Goa");
        states.add("Gujurat");
        states.add("Haryana");
        states.add("Himachal Pradesh");
        states.add("Jammu And Kashmir");
        states.add("Jharkhand");
        states.add("Karnataka");
        states.add("Kerala");
        states.add("Madya Pradesh");
        states.add("Maharashtra");
        states.add("Manipur");
        states.add("Meghalaya");
        states.add("Mizoram");
        states.add("Nagaland");
        states.add("Orissa");
        states.add("Punjab");
        states.add("Rajasthan");
        states.add("Sikkim");
        states.add("Tamil Nadu");
        states.add("Telangana");
        states.add("Tripura");
        states.add("Uttaranchal");
        states.add("Uttar Pradesh");
        states.add("West Bengal");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, states);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        candidate_state.setAdapter(dataAdapter);

        btnUploadSymbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView = findViewById(R.id.candidate_party_symbol);
                chooseImage();
                uploadImage();

            }
        });

        btnUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView = findViewById(R.id.candidate_photo);
                chooseImage();
                uploadImage();
            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            StorageReference ref = storageReference.getRoot().child(candidate_first_name.getText().toString() + " " + candidate_last_name.getText().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(add_candidates.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(add_candidates.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }


    public void SaveCandidate(View V){
        final String first_name = candidate_first_name.getText().toString();
        final String last_name = candidate_last_name.getText().toString();
        final String party_name = candidate_party_name.getText().toString();
        final String ward_number = candidate_ward_number.getText().toString();
        final String promises_fulfilled = candidate_promises_fulfilled.getText().toString();
        final String promises_made = candidate_promises_made.getText().toString();
        final String criminal_records = candidate_criminal_records.getText().toString();
        final String state_name = ((Spinner)findViewById(R.id.candidate_state)).getSelectedItem().toString();
        //Add Two Images

        if (TextUtils.isEmpty(first_name)) {
            Toast.makeText(getApplicationContext(), "Enter FirstName", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(last_name)) {
            Toast.makeText(getApplicationContext(), "Enter LastName ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(ward_number)) {
            Toast.makeText(getApplicationContext(), "Enter WardNumber ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(party_name)) {
            Toast.makeText(getApplicationContext(), "Enter PartyName ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(promises_fulfilled)) {
            Toast.makeText(getApplicationContext(), "Enter PromisesFulfilled ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(promises_made)) {
            Toast.makeText(getApplicationContext(), "Enter PromisesMade ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(state_name)) {
            Toast.makeText(getApplicationContext(), "Enter StateName ", Toast.LENGTH_SHORT).show();
            return;
        }



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Candidates").child(first_name + " " + last_name);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
//                    Toast.makeText(getApplicationContext(), "Candidate Exists", Toast.LENGTH_SHORT).show();

                }
                else

                {
                    myRef.child("first_name").setValue(first_name);
                    myRef.child("last_name").setValue(last_name);
                    myRef.child("party_name").setValue(party_name);
                    myRef.child("promises_fulfilled").setValue(promises_fulfilled);
                    myRef.child("promises_made").setValue(promises_made);
                    myRef.child("ward_number").setValue(ward_number);
                    myRef.child("criminal_records").setValue(criminal_records);
                    myRef.child("state_name").setValue(state_name);
                    myRef.child("live_poll").setValue(0);

                    Toast.makeText(getApplicationContext(), "Candidate successfully added", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(add_candidates.this, admin_options.class);
                    startActivity(i);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
