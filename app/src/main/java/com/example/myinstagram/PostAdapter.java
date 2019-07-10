package com.example.myinstagram;

import android.content.Context;
import android.content.Intent;
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
        for (int i = 0; i < mPosts.size(); ++i) {
            // posts.clear();
            Log.d("ADAPTERActivity", "Post[" + i + "] = " + mPosts.get(i)
                    .getDescription()
                    + "\nusername= " + mPosts.get(i).getUser().getUsername());
        }
    }

   // Post post;
    //for each row, inflate the layout and cache references into ViewHolder


    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        View postView = inflater.inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        TextView tvPostText;
        ImageView ivPostImage;

        public ViewHolder(View itemView){
            super(itemView);
            TextView tvPostText = itemView.findViewById(R.id.tvPostText);
            ImageView ivPostImage = itemView.findViewById(R.id.ivPostImage);
        }
    }

    //bind the values based on the position of the element

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //get the data according to the position
         post = mPosts.get(position);
            Log.d("CreatePostActivity", "Post[" + position + "] = " + mPosts.get(position)
                    .getDescription()
                    + "\nusername= " + mPosts.get(position).getUser().getUsername());
        //populate the views according to this
         holder.tvPostText.setText(post.getDescription());
         Glide.with(context)
                .load(post.getImage())
                .into(holder.ivPostImage);
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

