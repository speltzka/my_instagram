package com.example.myinstagram;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            final Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        */
       // setContentView(R.layout.activity_main);
        //final FragmentManager fragmentManager = getSupportFragmentManager();

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new TestFragmentPagerAdapter(getSupportFragmentManager(),
                MainActivity.this));

        // Give the TabLayout the ViewPager

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        /*
        bottomNavigationView  = findViewById(R.id.bottom_nav1);
        bottomNavigationView.setupWithViewPager(viewPager);

        bottomNavigationView.setOnNavigationItemSelectedListener(
        new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.action_compose:
                        fragment = new TimelineFragment();
                        break;
                    case R.id.action_logout:
                        fragment = new TimelineFragment();
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
*/

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);


    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public boolean composePost(MenuItem item) {
        final Intent intent = new Intent(MainActivity.this, CreatePostActivity.class);
        startActivity(intent);
        return true;
    }
    */
}
