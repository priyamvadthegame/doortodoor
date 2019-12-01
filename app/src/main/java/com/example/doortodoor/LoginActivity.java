package com.example.doortodoor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText inputemail;
    EditText inputPassword;
    Button siginButton;
    FirebaseAuth mauth;
    ProgressDialog progressDialog;
    TextView signuptext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginactivity);
        inputemail=findViewById(R.id.input_email);
        inputPassword=findViewById(R.id.input_password);
        siginButton=findViewById(R.id.btn_login);
        mauth=FirebaseAuth.getInstance();
        signuptext=findViewById(R.id.link_signup);
        signuptext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });
        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Signing In...");
        siginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signin();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
       if(user!=null)
       {
           FirebaseAuth.getInstance().signOut();
       }

    }

    public void signin()
    {    if(!validate())
        {
            return;
        }
        progressDialog.show();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user==null)
        {
            mauth.signInWithEmailAndPassword(inputemail.getText().toString(),inputPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.cancel();
                        Intent i=new Intent(LoginActivity.this,ProfileActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {   progressDialog.cancel();
                        Toast.makeText(LoginActivity.this,"Authentication Failed",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else
        {   progressDialog.cancel();
            Toast.makeText(LoginActivity.this,"User Already Loggged in",Toast.LENGTH_LONG).show();
        }

    }
    public boolean validate() {
        boolean valid = true;

        String email = inputemail.getText().toString();
        String password =inputPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputemail.setError("enter a valid email address");
            valid = false;
        } else {
            inputemail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            inputPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            inputPassword.setError(null);
        }

        return valid;
    }
}
