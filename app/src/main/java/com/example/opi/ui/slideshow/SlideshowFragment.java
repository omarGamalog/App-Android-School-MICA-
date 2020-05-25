package com.example.opi.ui.slideshow;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.opi.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    TextView fname,email,Phono;
    ImageButton B ;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String UserID;
    TextView forgetPassword ,Hname;
    FirebaseUser user;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        fname = root.findViewById(R.id.name);
        Hname=root.findViewById(R.id.Hname);
        email = root.findViewById(R.id.email);
        Phono = root.findViewById(R.id.Phono);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        UserID = fAuth.getCurrentUser().getUid();
        forgetPassword =root. findViewById(R.id.forgetPassword);
        user = fAuth.getCurrentUser();
        B = root.findViewById(R.id.button);








        DocumentReference deDocumentReference = fStore.collection("users").document(UserID);
        deDocumentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                fname.setText(documentSnapshot.getString("name"));
                Phono.setText(documentSnapshot.getString("Phono"));
                email.setText(documentSnapshot.getString("email"));
                Hname.setText("Hello "+documentSnapshot.getString("name")+" \n Welcome To MICA");
            }
        });



        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final EditText ResetPassword = new EditText(v.getContext());


                final AlertDialog.Builder Forget_Passowrd = new AlertDialog.Builder(v.getContext());
                Forget_Passowrd.setTitle("Reset Password");
                Forget_Passowrd.setMessage("Enter Your  New Password >6 char");
                Forget_Passowrd.setView(ResetPassword);


                Forget_Passowrd.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        String newPassword = ResetPassword.getText().toString();
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getActivity(), "Password Reset Succcessfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Password Filed", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Forget_Passowrd.setNegativeButton("no", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });


                    }
                });
                Forget_Passowrd.create().show();

            }
        });








        return root;
    }
}