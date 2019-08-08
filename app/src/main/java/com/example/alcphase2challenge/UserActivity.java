package com.example.alcphase2challenge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.example.alcphase2challenge.databinding.ActivityUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Objects;

public class UserActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    ActivityUserBinding binding;
    private FirebaseFirestore mFirestore;
    ArrayList<TravelModel> travelModelArrayList;
    TravelAdapter travelAdapter;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user);
        travelModelArrayList = new ArrayList<>();
        // Setting up Firebase image upload folder path in databaseReference.
// The path is already defined in MainActivity.
        databaseReference = FirebaseDatabase.getInstance().getReference("images");

        setUpRecycler();
        setUpFirebase();
        loadDataFromDataBase();
        changeListener();
        binding.getRoot();
    }

private void changeListener(){
    mFirestore.collection("Holidays")
            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot snapshots,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w("TAG", "listen:error", e);
                        return;
                    }
                    for (DocumentChange dc : Objects.requireNonNull(snapshots).getDocumentChanges()) {
                        switch (dc.getType()) {
                            case ADDED:
                                Log.d("TAG", "New Msg: " + dc.getDocument().toObject(TravelModel.class));
                                break;
                            case MODIFIED:
                                Log.d("TAG", "Modified Msg: " + dc.getDocument().toObject(TravelModel.class));
                                break;
                            case REMOVED:
                                Log.d("TAG", "Removed Msg: " + dc.getDocument().toObject(TravelModel.class));
                                break;
                        }
                    }

                }
            });
}

    private void loadDataFromDataBase() {
        mFirestore.collection("Holidays")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    TravelModel travelModel = documentSnapshot.toObject(TravelModel.class);
                    travelModelArrayList.add(travelModel);
                }
                travelAdapter = new TravelAdapter(UserActivity.this, travelModelArrayList);
                binding.recycler.setAdapter(travelAdapter);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserActivity.this, "There's a problem", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setUpFirebase() {
        mFirestore = FirebaseFirestore.getInstance();
    }

    private void setUpRecycler() {
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}
