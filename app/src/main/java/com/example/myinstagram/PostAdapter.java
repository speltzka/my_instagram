package com.example.myinstagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myinstagram.model.Post;
import com.parse.ParseFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> mPosts;
    Context context;
    Post post;

    //pass in post array to constructor
    public PostAdapter(List<Post> objects) {
        mPosts = objects;
        for (int i = 0; i < objects.size(); ++i) {
            // posts.clear();
            Log.d("ADAPTERActivity", "Post[" + i + "] = " + mPosts.get(i)
                    .getDescription()
                    + "\nusername= " + mPosts.get(i).getUser().getUsername());
        }
    }



    // Post post;
    //for each row, inflate the layout and cache references into ViewHolder


    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        Log.i("createViewHolder", "create");
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }


    //bind the values based on the position of the element

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //get the data according to the position
        Log.i("BINDING", "BINDVIEW");
           post = mPosts.get(position);
            Log.d("CreatePostActivity", "Post[" + position + "] = " + post
                    .getDescription()
                    + "\nusername= " + post.getUser().getUsername());
        //populate the views according to this
        holder.tvPostText.setText(post.getDescription());
        holder.timeStamp.setText(post.getTime());
        holder.username.setText(post.getName());

        ParseFile image = post.getImage();
        //ParseFile profilePhoto = post.getProfileImage();
        if (image != null){
            Glide.with(context)
                    .load(image.getUrl())
                    .into(holder.ivPostImage);
        }
       /* if (profilePhoto != null){
            Glide.with(context)
                    .load(image.getUrl())
                    .into(holder.profilePhotoPost);
        }
        */
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvPostText;
        TextView timeStamp;
        TextView username;
        TextView usernameProfile;
        ImageView ivPostImage;
        ImageView likeButton;
        ImageView profilePhotoPost;

        public ViewHolder(View itemView) {
            super(itemView);
            tvPostText = itemView.findViewById(R.id.tvPostText);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            timeStamp = itemView.findViewById(R.id.timeStampPost);
            username = itemView.findViewById(R.id.userNamePost);
            likeButton = itemView.findViewById(R.id.likeButton);
            usernameProfile = itemView.findViewById(R.id.userName);
            profilePhotoPost = itemView.findViewById(R.id.profilePhotoPost);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // gets position
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(post.getName(), "name" );
                context.startActivity(intent);
            }

        }
    }



    public int getItemCount(){
        return mPosts.size();
    }


    // Clean all elements of the recycler
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }

}

