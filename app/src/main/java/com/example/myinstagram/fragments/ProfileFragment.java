package com.example.myinstagram.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myinstagram.MainActivity;
import com.example.myinstagram.R;
import com.parse.ParseFile;
import com.parse.ParseUser;

public class ProfileFragment extends Fragment {

    Button logoutButton;
    TextView currentUser;
    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    public static final String ARG_PAGE = "ARG_PAGE";
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_profile, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        super.onViewCreated(view, savedInstanceState);
        logoutButton = view.findViewById(R.id.logoutButton);
      //  currentUser = view.findViewById(R.id.currentUser);
        //currentUser.setText("Click below to logout");
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                getActivity().finish();
                    }
                });
    }

    public static ProfileFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }
}

