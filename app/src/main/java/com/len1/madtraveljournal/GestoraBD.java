package com.len1.madtraveljournal;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class GestoraBD {
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser usuarioFB;

    public GestoraBD(){
        db = FirebaseFirestore.getInstance();
    }

}
