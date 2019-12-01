package com.example.doortodoor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText inputName;
    EditText inputAddress;
    EditText inputemail;
    EditText inputmobile;
    EditText inputPassword;
    EditText inputRetypePassword;
    Button signupButton;
    TextView loginLinkTextView;
    FirebaseAuth mauth;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupxml);
        inputName = findViewById(R.id.input_name);
        inputAddress = findViewById(R.id.input_address);
        inputemail = findViewById(R.id.input_email);
        inputmobile = findViewById(R.id.input_mobile);
        inputPassword = findViewById(R.id.input_password);
        inputRetypePassword = findViewById(R.id.input_reEnterPassword);
        signupButton = findViewById(R.id.btn_signup);
        loginLinkTextView = findViewById(R.id.link_login);
        mauth = FirebaseAuth.getInstance();
      progressDialog = new ProgressDialog(SignUpActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signup();
            }
        });
        loginLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void signup() {
        if (!validate()) {
            return;
        }
        progressDialog.show();
        final String name = inputName.getText().toString();
        final String address = inputAddress.getText().toString();
        final String email = inputemail.getText().toString();
        final String mobile = inputmobile.getText().toString();
        final String password = inputPassword.getText().toString();
        String reEnterPassword = inputRetypePassword.getText().toString();

        mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id=FirebaseAuth.getInstance().getUid();
                    User user = new User(name, address, email, mobile);
                    FirebaseDatabase.getInstance().getReference("User").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.cancel();
                                Toast.makeText(SignUpActivity.this, "registered succesfully", Toast.LENGTH_LONG).show();
                                FirebaseAuth.getInstance().signOut();
                                Intent i=new Intent(SignUpActivity.this,LoginActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                progressDialog.cancel();
                                Toast.makeText(SignUpActivity.this, "User not registered", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "User not registered", Toast.LENGTH_LONG).show();
                    progressDialog.cancel();
                }
            }
        });
    }
    public boolean validate() {
        boolean valid = true;
        String name = inputName.getText().toString();
        String address = inputAddress.getText().toString();
        String email = inputemail.getText().toString();
        String mobile = inputmobile.getText().toString();
        String password = inputPassword.getText().toString();
        String reEnterPassword = inputRetypePassword.getText().toString();
        if (name.isEmpty() || name.length() < 3) {
            inputName.setError("at least 3 characters");
            valid = false;
        } else {
            inputName.setError(null);
        }

        if (address.isEmpty()) {
            inputAddress.setError("Enter Valid Address");
            valid = false;
        } else {
            inputAddress.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputemail.setError("enter a valid email address");
            valid = false;
        } else {
            inputemail.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            inputmobile.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            inputmobile.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            inputPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            inputPassword.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            inputRetypePassword.setError("Password Do not match");
            valid = false;
        } else {
            inputRetypePassword.setError(null);
        }
        return valid;
    }
}
