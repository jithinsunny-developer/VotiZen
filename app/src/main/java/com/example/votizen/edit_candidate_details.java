package com.example.votizen;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class edit_candidate_details extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner candidate_state;
    EditText candidate_ward_number,candidate_first_name,candidate_last_name,candidate_party_name,candidate_promises_made,candidate_promises_fulfilled,candidate_criminal_records;
    Button candidate_save_button,candidate_discard_button,candidate_delete_button;
    String SelectedCandidate;
    FirebaseStorage storage;
    StorageReference storageReference;

    ImageView candidate_photo_edit, candidate_party_symbol_edit;
    private Button btnChooseSymbol, btnUploadSymbol, btnChoosePhoto,btnUploadPhoto;
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
        setContentView(R.layout.editcandidatedetails);
        Intent i = getIntent();
        SelectedCandidate = i.getStringExtra("SelectedCandidate");
        candidate_criminal_records = findViewById(R.id.candidate_criminal_records_edit);
        candidate_first_name = findViewById(R.id.candidate_first_name_edit);
        candidate_last_name = findViewById(R.id.candidate_last_name_edit);
        candidate_party_name = findViewById(R.id.candidate_party_name_edit);
        candidate_ward_number = findViewById(R.id.candidate_ward_number_edit);
        candidate_promises_fulfilled = findViewById(R.id.candidate_promises_fulfilled_edit);
        candidate_promises_made = findViewById(R.id.candidate_promises_made_edit);
        candidate_state = findViewById(R.id.candidate_state_edit);
        candidate_save_button = findViewById(R.id.candidate_save_button);
        candidate_delete_button = findViewById(R.id.candidate_delete_button);
        candidate_discard_button = findViewById(R.id.candidate_discard_button);
        btnChooseSymbol = findViewById(R.id.choose_symbol_button_edit);
        btnUploadSymbol = findViewById(R.id.upload_symbol_button_edit);
        btnChoosePhoto = findViewById(R.id.choose_photo_button_edit);
        btnUploadPhoto = findViewById(R.id.upload_photo_button_edit);
        candidate_party_symbol_edit = findViewById(R.id.candidate_party_symbol_edit);
        candidate_photo_edit = findViewById(R.id.candidate_photo_edit);

        candidate_state.setOnItemSelectedListener(this);
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
                imageView = findViewById(R.id.candidate_party_symbol_edit);
                chooseImage();
                uploadImage();
            }
        });

        btnUploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView = findViewById(R.id.candidate_photo_edit);
                chooseImage();
                uploadImage();
            }
        });

        FirebaseDatabase databaseToShow = FirebaseDatabase.getInstance();
        DatabaseReference reference = databaseToShow.getReference("Candidates").child(SelectedCandidate);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Candidate candidate = dataSnapshot.getValue(Candidate.class);
                    candidate_criminal_records.setText(candidate.criminal_records);
                    String state_in_firebase = candidate.state_name;
                    String photoURL = candidate.photo;
                    String symbolURL = candidate.symbol;
//                    Glide.with(getApplicationContext()).using(new FirebaseImageLoader()).load(IMAGE_URL).into(IMAGE_VIEW);
                    Picasso.with(getApplicationContext()).load(photoURL).into(candidate_photo_edit);
                    Picasso.with(getApplicationContext()).load(symbolURL).into(candidate_party_symbol_edit);
                    candidate_promises_made.setText(candidate.promises_made);
                    candidate_promises_fulfilled.setText(candidate.promises_fulfilled);
                    candidate_first_name.setText(candidate.first_name);
                    candidate_last_name.setText(candidate.last_name);
                    candidate_ward_number.setText(candidate.ward_number);
                    candidate_party_name.setText(candidate.party_name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

    public void EditCandidateDetails(View V){
        final String first_name = candidate_first_name.getText().toString();
        final String last_name = candidate_last_name.getText().toString();
        final String party_name = candidate_party_name.getText().toString();
        final String ward_number = candidate_ward_number.getText().toString();
        final String promises_fulfilled = candidate_promises_fulfilled.getText().toString();
        final String promises_made = candidate_promises_made.getText().toString();
        final String criminal_records = candidate_criminal_records.getText().toString();
        final String state_name = ((Spinner)findViewById(R.id.candidate_state_edit)).getSelectedItem().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        switch (V.getId()){
            case R.id.candidate_discard_button:
                Toast.makeText(this, "Discard Is Pressed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,edit_candidates.class));

                break;
            case R.id.candidate_delete_button:
                DatabaseReference delref = database.getReference("Candidates").child(first_name + " " + last_name);
                delref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                childSnapshot.getRef().removeValue();
                            }
                            Toast.makeText(edit_candidate_details.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(edit_candidate_details.this,add_or_edit_candidates.class));
                        }
                        else{
                            Toast.makeText(edit_candidate_details.this, "Candidate Doesn't Exist", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                break;
            case R.id.candidate_save_button:

                if (TextUtils.isEmpty(first_name)) {
                    Toast.makeText(getApplicationContext(), "Enter First Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(last_name)) {
                    Toast.makeText(getApplicationContext(), "Enter Last Name ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ward_number)) {
                    Toast.makeText(getApplicationContext(), "Enter Ward Number ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(party_name)) {
                    Toast.makeText(getApplicationContext(), "Enter Party Name ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(promises_fulfilled)) {
                    Toast.makeText(getApplicationContext(), "Enter Promises Fulfilled ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(promises_made)) {
                    Toast.makeText(getApplicationContext(), "Enter Promises Made ", Toast.LENGTH_SHORT).show();
                    return;
                }

                final DatabaseReference myRef = database.getReference("Candidates").child(first_name +" "+ last_name);

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        /*if (dataSnapshot.exists())
                        {
                            Toast.makeText(getApplicationContext(), "Candidate Exists", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {*/
                            myRef.child("first_name").setValue(first_name);
                            myRef.child("last_name").setValue(last_name);
                            myRef.child("party_name").setValue(party_name);
                            myRef.child("promises_fulfilled").setValue(promises_fulfilled);
                            myRef.child("promises_made").setValue(promises_made);
                            myRef.child("ward_number").setValue(ward_number);
                            myRef.child("criminal_records").setValue(criminal_records);
                            myRef.child("state_name").setValue(state_name);


                            Intent i = new Intent(edit_candidate_details.this, add_or_edit_candidates.class);
                            startActivity(i);
                            Toast.makeText(getApplicationContext(), "Candidate successfully edited and saved", Toast.LENGTH_SHORT).show();
//                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                break;
        }
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

            StorageReference ref = storageReference.getRoot().child("Candidates").child(candidate_first_name.getText().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(edit_candidate_details.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(edit_candidate_details.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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
    public static class Candidate {

        public String state_name,first_name,last_name,party_name,ward_number,promises_fulfilled,promises_made,criminal_records,symbol, photo;
        public Candidate()
        {}
        public String getState_name() { return state_name; }
        public String getFirst_name() { return first_name; }
        public String getLast_name() { return last_name; }
        public String getParty_name() { return party_name; }
        public String getWard_number() { return ward_number; }
        public String getPromises_fulfilled() { return promises_fulfilled; }
        public String getPromises_made() { return promises_made; }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getCriminal_records() { return criminal_records; }

        public void setState_name(String state_name) {
            this.state_name = state_name;
        }
        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }
        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }
        public void setParty_name(String party_name) {
            this.party_name = party_name;
        }
        public void setWard_number(String ward_number) {
            this.ward_number = ward_number;
        }
        public void setPromises_fulfilled(String promises_fulfilled) {
            this.promises_fulfilled = promises_fulfilled;
        }
        public void setPromises_made(String promises_made) {
            this.promises_made = promises_made;
        }
        public void setCriminal_records(String criminal_records) {
            this.criminal_records = criminal_records;
        }
    }

}
