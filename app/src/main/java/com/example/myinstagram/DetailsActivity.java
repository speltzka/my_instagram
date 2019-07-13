package com.example.myinstagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myinstagram.model.Post;
import com.parse.ParseFile;

public class DetailsActivity extends AppCompatActivity {

    private TextView username;
    private TextView description;
    private TextView time;
    private ImageView ivPostImageDetail;
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // show the signup or login screen
        setContentView(R.layout.detail_view);
        post = getIntent().getParcelableExtra(Post.class.getSimpleName());

        username = findViewById(R.id.userName);
        description = findViewById(R.id.tvPostText);
        time = findViewById(R.id.timeStamp);
        ivPostImageDetail = findViewById(R.id.ivPostImageDetail);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                username = null;
            } else {
                username.setText(post.getName());
                description.setText(post.getDescription());
                time.setText(post.getTime());
                ParseFile image = post.getImage();
                if (image != null) {
                    Glide.with(this)
                            .load(image.getUrl())
                            .into(ivPostImageDetail);
                }
            }
        }

    }
}
