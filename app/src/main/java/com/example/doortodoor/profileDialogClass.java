package com.example.doortodoor;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.doortodoor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profileDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Button ok, cancel;
    public TextView username,useremail,usermobile,useraddress;
    User user;
    String nameToBeSetOnDialog,emailToBeSetOnDialog,addressToBeSetOnDialog,mobileToBeSetOnDialog;

    public profileDialogClass(Activity a) {
        super(a);
        this.c = a;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseReference firebaseDatabaseref= FirebaseDatabase.getInstance().getReference();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.profile_dialog_state);
        ok = findViewById(R.id.btn_yes);
        cancel =   findViewById(R.id.btn_no);
        username=findViewById(R.id.profile_dialog_username);
        useremail=findViewById(R.id.profile_dialog_useremail);
        usermobile=findViewById(R.id.profile_dialog_mobile);
        useraddress=findViewById(R.id.profile_dialog_address);
        ok.setOnClickListener(this);

        cancel.setOnClickListener(this);
        firebaseDatabaseref.child("User").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
                nameToBeSetOnDialog=user.name;
                emailToBeSetOnDialog=user.email;
                addressToBeSetOnDialog=user.address;
                mobileToBeSetOnDialog=user.mobile;
                username.setText("Name:"+nameToBeSetOnDialog);
                useremail.setText("Email:"+emailToBeSetOnDialog);
                usermobile.setText("Mobile:"+mobileToBeSetOnDialog);
                useraddress.setText("Address:"+addressToBeSetOnDialog);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                dismiss();

                break;
            case R.id.btn_no:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}