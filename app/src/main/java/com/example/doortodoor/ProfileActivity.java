package com.example.doortodoor;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationVIew;
    DatabaseReference firebaseDatabaseref;
    String nameToBeSetOnNavigationBarHeader;
    String emailToBeSetOnNavigationBarHeader;
    List<Integer> list=new ArrayList<Integer>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        list.add( R.drawable.electricity);
        list.add( R.drawable.plumbing);
        list.add( R.drawable.cleaning);
        list.add( R.drawable.laundry);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationVIew=findViewById(R.id.navigationView);
        GridView gridview;
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(ProfileActivity.this,drawerLayout,toolbar,R.string.openNavigationDrawer,R.string.closeNavigationDrawer);
        drawerLayout.addDrawerListener(toggle);
        firebaseDatabaseref=FirebaseDatabase.getInstance().getReference();
        View headerview=navigationVIew.getHeaderView(0);
        final TextView nameText=headerview.findViewById(R.id.drawer_name_textview);
        final TextView emailText=headerview.findViewById(R.id.drawer_email_textview);
        final TextView homeNameText=findViewById(R.id.HomeActivity_name);
        final TextView homeemailText=findViewById(R.id.home_user_email);
        final TextView homephoneText=findViewById(R.id.home_user_phone);
        gridview=findViewById(R.id.gridview);
        firebaseDatabaseref.child("User").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               User user=dataSnapshot.getValue(User.class);
               nameToBeSetOnNavigationBarHeader=user.name;
               emailToBeSetOnNavigationBarHeader=user.email;
                nameText.setText(nameToBeSetOnNavigationBarHeader);
                emailText.setText(emailToBeSetOnNavigationBarHeader);
                homeNameText.setText(nameToBeSetOnNavigationBarHeader);
                homeemailText.setText(user.email);
                homephoneText.setText(user.mobile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        gridviewAdapter adapter=new gridviewAdapter(ProfileActivity.this);
        adapter.set_data(list);
        gridview.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}
