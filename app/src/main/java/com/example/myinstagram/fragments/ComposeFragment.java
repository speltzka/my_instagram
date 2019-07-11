package com.example.myinstagram.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myinstagram.R;
import com.example.myinstagram.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static android.os.SystemClock.sleep;

public class ComposeFragment extends Fragment {

    private Button cameraButton;
    private Button postButton;
    private EditText descriptionInput;

    public final String APP_TAG = "my_instagram";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    Uri fileProvider;
    File photoFile;
    Bitmap takenImage;
    ImageView ivPreview;
    String description;
    ParseUser user;
    ParseFile parseFile;
    ProgressBar miActionProgressItem;
    public static final String ARG_PAGE = "ARG_PAGE";

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,@Nullable  Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        Log.i("FRAGMENT", "GRGOERG");
        return inflater.inflate(R.layout.fragment_compose, parent, false);

    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        super.onViewCreated(view, savedInstanceState);
        Log.i("VIEW_WAS_CREATED", "GRGOERG");
        descriptionInput = view.findViewById(R.id.description_et);
        cameraButton = view.findViewById(R.id.cameraButton);
        ivPreview = view.findViewById(R.id.ivCameraPhoto);
        postButton = view.findViewById(R.id.postButton);
        miActionProgressItem = getView().findViewById(R.id.pbLoading);
        ivPreview.setImageResource(R.drawable.camera_shadow_fill);

        hideProgressBar();
        final boolean clicked = false;
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLaunchCamera();
            }
         });
         postButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showProgressBar();
                 description = descriptionInput.getText().toString();
                 user = ParseUser.getCurrentUser();
                 parseFile = new ParseFile(photoFile);
                 sleep(2000);
                 createPost(description, parseFile, user);
                 descriptionInput.setText("");
                 ivPreview.setImageResource(R.drawable.camera_shadow_fill);
                 hideProgressBar();
             }
         });

    }



    public static ComposeFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        ComposeFragment fragment = new ComposeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void createPost(String description, ParseFile imageFile, ParseUser user) {
        showProgressBar();
        final Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setUser(user);

        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("CreatePostActivity", "Create Post Success");
                } else {
                    e.printStackTrace();
                }

            }
        });
    }

    public void onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        fileProvider = FileProvider.getUriForFile(getContext(), "com.example.myinstagram", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        photoFile = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return photoFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                ivPreview.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisibility(View.INVISIBLE);
    }
}
