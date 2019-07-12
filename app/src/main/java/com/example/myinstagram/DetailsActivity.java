package com.example.myinstagram;

import android.graphics.Movie;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myinstagram.model.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

    private TextView username;
    private TextView description;
    private  TextView time;
    private ImageView ivPostImageDetail;
    Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // show the signup or login screen
        setContentView(R.layout.detail_view);
        post = getIntent().getParcelableExtra(Post.class.getSimpleName());

        username = findViewById(R.id.userName);
        description =  findViewById(R.id.tvPostText);
        time = findViewById(R.id.timeStamp);
        ivPostImageDetail = findViewById(R.id.ivPostImageDetail);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                username = null;
            } else {
                username.setText(post.getName());
                description.setText(post.getDescription());
                time.setText(post.getTime());
                ParseFile image = post.getImage();
                if (image != null){
                    Glide.with(this)
                            .load(image.getUrl())
                            .into(ivPostImageDetail);
                }
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
