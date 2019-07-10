package com.example.myinstagram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myinstagram.model.Post;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    RecyclerView rvInsta;
    SwipeRefreshLayout swipeContainer;
    ProgressBar progressBar;
    PostAdapter postAdapter;
   // List<Post> posts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            final Intent intent = new Intent(TimelineActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_timeline);
        rvInsta =  findViewById(R.id.rvInsta);
        swipeContainer = findViewById(R.id.swipeContainer);
        progressBar = findViewById(R.id.pbLoading);
        loadTopPosts();

        progressBar.setVisibility(ProgressBar.INVISIBLE);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadTopPosts();
                Log.i("trying to refresh", "refreshing");
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }




    private void loadTopPosts() {
      //  showProgressBar();
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                //construct the adapter from this data source
                postAdapter = new PostAdapter(objects);
                //recycler User user = new User();iew setup
                rvInsta.setAdapter(postAdapter);

                rvInsta.setLayoutManager(new LinearLayoutManager(TimelineActivity.this));
                if (e == null) {
                    //postAdapter.clear();
                    for (int i = 0; i < objects.size(); ++i) {
                       // posts.clear();
                        Log.d("CreatePostActivity", "Post[" + i + "] = " + objects.get(i)
                                .getDescription()
                                + "\nusername= " + objects.get(i).getUser().getUsername());
                    }
                   // postAdapter.addAll(objects);
                } else {
                    e.printStackTrace();
                }
            }
        });
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // Specify the object id
        query.getInBackground("aFuEsvjoHt", new GetCallback<Post>() {
            public void done(Post item, ParseException e) {
                if (e == null) {
                    // Access data using the `get` methods for the object
                    //Intent i
                    //  = item.getDescription();
                    // Do whatever you want with the data...
                    Toast.makeText(TimelineActivity.this, "no error", Toast.LENGTH_SHORT).show();
                } else {
                    // something went wrong
                    Toast.makeText(TimelineActivity.this, "post is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // final Intent intent = new Intent(LoginActivity.this, CreatePostActivity.class);
    // startActivity(intent);
    // finish();

    /*
    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }
    */
}
