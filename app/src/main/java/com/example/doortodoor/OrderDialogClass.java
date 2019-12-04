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
import android.widget.ScrollView;
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

import jp.wasabeef.blurry.Blurry;

public class OrderDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Button ordernow, cancel;
    public TextView userService,username,useremail;
    public EditText messageText;
    User user;
    String nameToBeSetOnDialog,emailToBeSetOnDialog,addressToBeSetOnDialog,messageBody,messageSubject,buttonText,ServiceText;

    public OrderDialogClass(Activity a,String buttonText,String serviceText) {
        super(a);
        this.c = a;
        this.buttonText=buttonText;
        this.ServiceText=serviceText;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseReference firebaseDatabaseref= FirebaseDatabase.getInstance().getReference();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_state);
        ordernow = findViewById(R.id.btn_yes);
        ordernow.setText(buttonText);
        cancel =   findViewById(R.id.btn_no);
        userService=findViewById(R.id.dialog_userService);
        userService.setText(ServiceText);
        username=findViewById(R.id.dialog_username);
        useremail=findViewById(R.id.dialog_useremail);
        messageText=findViewById(R.id.messageText);
        ordernow.setOnClickListener(this);

        cancel.setOnClickListener(this);
        firebaseDatabaseref.child("User").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(User.class);
                nameToBeSetOnDialog=user.name;
                emailToBeSetOnDialog=user.email;
                addressToBeSetOnDialog=user.address;
                username.setText("Name: "+nameToBeSetOnDialog);
                useremail.setText("Email: "+ emailToBeSetOnDialog);
                messageSubject=ServiceText+" By:"+nameToBeSetOnDialog;
                messageBody="Order has been placed for " +ServiceText+" by:\n"+"Name: "+nameToBeSetOnDialog+"\n"+"Address: "+addressToBeSetOnDialog+"\n"+"Email: "+emailToBeSetOnDialog+"\n"+"Message: ";
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
                if(messageText.getText().toString().matches(""))
                {
                    Toast.makeText(c.getApplicationContext(),"Please enter a message to continue",Toast.LENGTH_LONG).show();
                }
                else {
                    new SendMailTask(c).execute("aabhishek100001@gmail.com",
                            "7050025156", "abhishekanand143009@gmail.com ", messageSubject, messageBody + messageText.getText());
                }
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