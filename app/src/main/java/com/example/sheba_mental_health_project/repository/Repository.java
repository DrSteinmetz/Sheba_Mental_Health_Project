package com.example.sheba_mental_health_project.repository;

import com.google.firebase.firestore.FirebaseFirestore;

public class Repository {

    final FirebaseFirestore mCloudDB = FirebaseFirestore.getInstance();

    private final String TAG = "Repository";
}
