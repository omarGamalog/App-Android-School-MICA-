package com.example.opi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText Fname,Email,password,phohn;
    Button Done;
    TextView NLogin;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String UserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Fname=findViewById(R.id.EName);
        Email=findViewById(R.id.email);
        password=findViewById(R.id.Password);
        phohn=findViewById(R.id.Phono);


        fAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();

        Done=findViewById(R.id.button);

        NLogin=findViewById(R.id.NLogin);
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), FirestActivity.class));
        }
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=Email.getText().toString().trim();
                String pass=password.getText().toString().trim();
                final String name=Fname.getText().toString();
                final String Phono=phohn.getText().toString();



                if(TextUtils.isEmpty(email)){
                    Email.setError("Email is Require");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    password.setError("password is Require");
                    return;
                }
                if(pass.length()<6 ){
                    password.setError("password  Must be >=6 char");
                    return;
                }
//                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            UserID =fAuth.getCurrentUser().getUid();
                            DocumentReference deDocumentReference=fStore.collection("users").document(UserID);

                            Map<String, Object> user = new HashMap<>();



                            user.put("name",name);
                            user.put("email",email);
                            user.put("Phono",Phono);



                            deDocumentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG","OnSuccess user profel is Creatd For" +UserID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG","OnFiluer" +e.toString());
                                }
                            });




                            startActivity(new Intent(getApplicationContext(),Register.class));
                        }else{
                            Toast.makeText(Register.this, "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }}}); } });


        NLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}
