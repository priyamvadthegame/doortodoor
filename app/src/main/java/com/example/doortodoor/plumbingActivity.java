package com.example.doortodoor;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class plumbingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plumbingactivity);

        FloatingActionButton fab=findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDialogClass dialog=new OrderDialogClass(plumbingActivity.this,"Order Now","Plumbing Service");
                dialog.show();
            }
        });
    }

}
