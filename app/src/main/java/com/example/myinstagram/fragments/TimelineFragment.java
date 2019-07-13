package com.example.myinstagram.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.myinstagram.EndlessRecyclerViewScrollListener;
import com.example.myinstagram.PostAdapter;
import com.example.myinstagram.R;
import com.example.myinstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

public class TimelineFragment extends Fragment {

    private EndlessRecyclerViewScrollListener scrollListener;
    RecyclerView rvInsta;
    SwipeRefreshLayout swipeContainer;
    ProgressBar progressBar;
    PostAdapter postAdapter;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_timeline, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        super.onViewCreated(view, savedInstanceState);
        loadTopPosts();
        rvInsta = view.findViewById(R.id.rvInsta);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        progressBar = view.findViewById(R.id.pbLoading);

        progressBar.setVisibility(ProgressBar.INVISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvInsta.setLayoutManager(linearLayoutManager);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                Log.i("SCROLL", "SCROOLL");
                final Post.Query postsQuery = new Post.Query();
                postsQuery.setSkip(totalItemsCount);
                postsQuery.getTop().withUser();
                postsQuery.setLimit(20);
                postsQuery.addDescendingOrder(Post.KEY_CREATED_AT);

                postsQuery.findInBackground(new FindCallback<Post>() {
                    @Override
                    public void done(List<Post> objects, ParseException e) {

                        if (e == null) {
                            for (int i = 0; i < objects.size(); ++i) {
                                Log.d("TIMELINE", "Post[" + i + "] = " + objects.get(i)
                                        .getDescription()
                                        + "\nusername= " + objects.get(i).getUser().getUsername());
                            }
                            postAdapter.addAll(objects);
                        } else {
                            e.printStackTrace();
                        }
                    }
                });
                postAdapter.notifyDataSetChanged();
            }
        };
        rvInsta.addOnScrollListener(scrollListener);

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
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();
        postsQuery.setLimit(20);
        postsQuery.addDescendingOrder(Post.KEY_CREATED_AT);

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                //construct the adapter from this data source
                postAdapter = new PostAdapter(objects);
                rvInsta.setAdapter(postAdapter);

                if (e == null) {
                    for (int i = 0; i < objects.size(); ++i) {
                        Log.d("TIMELINE", "Post[" + i + "] = " + objects.get(i)
                                .getDescription()
                                + "\nusername= " + objects.get(i).getUser().getUsername());
                    }
                } else {
                    e.printStackTrace();
                }
                postAdapter.notifyDataSetChanged();
            }
        });
    }
}