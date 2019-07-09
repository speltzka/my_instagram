package com.example.myinstagram;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myinstagram.model.Post;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String imagePath = "https://www.w3schools.com/images/picture.jpg";
    private Button refreshButton;
    private Button createButton;
    private Button logoutButton;
    private EditText descriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        descriptionInput = findViewById(R.id.description_et);
        refreshButton = findViewById(R.id.refresh_btn);
        createButton = findViewById(R.id.create_btn);
        logoutButton = findViewById(R.id.logoutButton);


        loadTopPosts();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String description = descriptionInput.getText().toString();
                ParseUser user = ParseUser.getCurrentUser();

                final File file = new File(imagePath);
                final ParseFile parseFile = new ParseFile(file);

                createPost(description, parseFile, user);

            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadTopPosts();
            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                ParseUser currentUser = null; // this will now be null
                finish();
            }
        });
    }


        private void createPost(String description, ParseFile imageFile, ParseUser user){
            final Post newPost = new Post();
            newPost.setDescription(description);
            newPost.setImage(imageFile);
            newPost.setUser(user);

            newPost.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.d("HomeActivity", "Create Post Success");
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }

        private void loadTopPosts(){
            final Post.Query postsQuery = new Post.Query();
            postsQuery.getTop().withUser();

            postsQuery.findInBackground(new FindCallback<Post>() {
                @Override
                public void done(List<Post> objects, ParseException e) {
                    if (e == null) {
                        for (int i = 0; i < objects.size(); ++i) {
                            Log.d("HomeActivity", "Post[" + i + "] = " + objects.get(i)
                                    .getDescription()
                                    + "\nusername= " + objects.get(i).getUser().getUsername());
                        }
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }




}
