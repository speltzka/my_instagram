package com.example.myinstagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.myinstagram.fragments.ComposeFragment;
import com.example.myinstagram.fragments.ProfileFragment;
import com.example.myinstagram.fragments.TimelineFragment;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;

import static com.example.myinstagram.fragments.ComposeFragment.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE;

public class MainActivity extends AppCompatActivity {



    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            final Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_main);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        bottomNavigationView  = findViewById(R.id.bottom_nav1);
        bottomNavigationView.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new TimelineFragment();
                        break;
                    case R.id.action_compose:
                        fragment = new ComposeFragment();
                        break;
                    case R.id.action_logout:
                        fragment = new ProfileFragment();
                        break;
                    default:
                        fragment = new TimelineFragment();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);


    }


}
