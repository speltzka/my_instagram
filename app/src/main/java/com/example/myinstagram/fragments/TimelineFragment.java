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

import com.example.myinstagram.PostAdapter;
import com.example.myinstagram.R;
import com.example.myinstagram.model.Post;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;

public class TimelineFragment extends Fragment {

    RecyclerView rvInsta;
    SwipeRefreshLayout swipeContainer;
    ProgressBar progressBar;
    PostAdapter postAdapter;
    public static final String ARG_PAGE = "ARG_PAGE";

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,@Nullable  Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        Log.i("FRAGMENT", "GRGOERG");
        return inflater.inflate(R.layout.fragment_timeline, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        super.onViewCreated(view, savedInstanceState);
        Log.i("VIEW_WAS_CREATED", "GRGOERG");
        rvInsta = view.findViewById(R.id.rvInsta);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        progressBar = view.findViewById(R.id.pbLoading);

        progressBar.setVisibility(ProgressBar.INVISIBLE);

        loadTopPosts();

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
        postsQuery.setLimit(20);
        postsQuery.addDescendingOrder(Post.KEY_CREATED_AT);

        postsQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                //construct the adapter from this data source
                postAdapter = new PostAdapter(objects);
                //recycler User user = new User();iew setup
                rvInsta.setAdapter(postAdapter);

                rvInsta.setLayoutManager(new LinearLayoutManager(getContext()));
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
    }


    public static TimelineFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TimelineFragment fragment = new TimelineFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
