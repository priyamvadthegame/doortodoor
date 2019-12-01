package com.example.doortodoor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

 public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

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
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list.add( R.drawable.electricity);
        list.add( R.drawable.plumbing);
        list.add( R.drawable.cleaning);
        list.add( R.drawable.laundry);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationVIew=findViewById(R.id.navigationView);
        navigationVIew.setNavigationItemSelectedListener(this);
        GridView gridview;
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openNavigationDrawer,R.string.closeNavigationDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        firebaseDatabaseref=FirebaseDatabase.getInstance().getReference();
        View headerview=navigationVIew.getHeaderView(0);
        final TextView nameText=headerview.findViewById(R.id.drawer_name_textview);
        final TextView emailText=headerview.findViewById(R.id.drawer_email_textview);
        final TextView homeNameText=findViewById(R.id.HomeActivity_name);
        final TextView homeemailText=findViewById(R.id.home_user_email);
        final TextView homephoneText=findViewById(R.id.home_user_phone);
        final TextView contactUsQuery=findViewById(R.id.contact_us_query);
        contactUsQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDialogClass queryDialog=new OrderDialogClass(ProfileActivity.this,"Send","Query");
                queryDialog.show();
            }
        });
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
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (list.get(i))
                {
                    case R.drawable.electricity:
                        Intent intent=new Intent(ProfileActivity.this,ElectricityActivity.class);
                        startActivity(intent);
                        break;
                    case R.drawable.laundry:
                        Intent intent1=new Intent(ProfileActivity.this,LaundryActivity.class);
                        startActivity(intent1);
                        break;
                    case R.drawable.cleaning:
                        Intent intent2=new Intent(ProfileActivity.this,cleaningActivity.class);
                        startActivity(intent2);
                        break;
                    case R.drawable.plumbing:
                        Intent intent3=new Intent(ProfileActivity.this,plumbingActivity.class);
                        startActivity(intent3);
                        break;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
           FirebaseAuth.getInstance().signOut();
        }
    }

     @Override
     public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
         switch (menuItem.getItemId())
         {
             case R.id.profile_menu:
                 drawerLayout.closeDrawer(GravityCompat.START);
                 profileDialogClass profiledialog=new profileDialogClass(ProfileActivity.this);
                 profiledialog.show();
                 break;
             case R.id.logout_menu:
                 FirebaseAuth.getInstance().signOut();
                 Toast.makeText(ProfileActivity.this,"Logged Out succesfully",Toast.LENGTH_LONG).show();
                 Intent intent =new Intent(ProfileActivity.this,LoginActivity.class);
                 startActivity(intent);
                 finish();
                 break;
         }
        return true;
     }
 }
