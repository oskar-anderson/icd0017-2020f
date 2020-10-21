package com.example.contactbook;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.contactbook.Fragment.ContactFragment;
import com.example.contactbook.Fragment.ContactTypeFragment;
import com.example.contactbook.Fragment.PersonFragment;
import com.example.contactbook.Model.Person;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static DbHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(MainActivity.this);

        AddBottomNav(savedInstanceState, new PersonFragment(this));

        Log.d("G","onCreate end MainActivity");
    }


    private void AddBottomNav(@Nullable Bundle savedInstanceState, Fragment fragment) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.fragment_container,
                    fragment).commit();
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.nav_person:
                        selectedFragment = new PersonFragment(MainActivity.this);
                        break;
                    case R.id.nav_contacts:
                        selectedFragment = new ContactFragment(MainActivity.this);
                        break;
                    case R.id.nav_contactType:
                        selectedFragment = new ContactTypeFragment(MainActivity.this);
                        break;
                    default:
                        throw new RuntimeException("unexpected");
                }
                getSupportFragmentManager().beginTransaction().replace(
                        R.id.fragment_container,
                        selectedFragment).commit();
                return true;
            }
        });
    }


}
