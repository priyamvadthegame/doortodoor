package com.example.doortodoor;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ElectricityActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.electrictyactivity);
        String serviceName="Service Name";
        FloatingActionButton fab=findViewById(R.id.floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDialogClass dialog=new OrderDialogClass(ElectricityActivity.this,"Order Now","Electricity Service");
                dialog.show();
            }
        });
    }
}
