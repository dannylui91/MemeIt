package com.example.andresarango.memeit;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.andresarango.memeit.edit_meme_activity.memes.FragmentAdapter;
import com.example.andresarango.memeit.edit_meme_activity.memes.VanillaMemeListener;
import com.example.andresarango.memeit.edit_meme_activity.memes.vanilla_meme.VanillaMemeWrapper;
import com.example.andresarango.memeit.edit_meme_activity.memes.vanilla_meme.adapter.EditVanillaMemeAdapter;
import com.example.andresarango.memeit.edit_meme_activity.utility.EditorViewHolder;

public class EditMemeActivity extends AppCompatActivity implements EditorViewHolder.Listener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter fragmentAdapter;
    private ImageView memeImage;
    private Button mNextActivityButton;
    private Button mChooseMemeButton;
    private Button mEditMemeButton;
    private RecyclerView.Adapter mEditAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meme);
        initialize(savedInstanceState);
    }


    private void initialize(Bundle savedInstanceState) {
        memeImage = (ImageView) findViewById(R.id.meme_image);
        mNextActivityButton = (Button) findViewById(R.id.btn_next_activity);
        mChooseMemeButton = (Button) findViewById(R.id.btn_choose_meme);
        mEditMemeButton = (Button) findViewById(R.id.btn_edit_meme);
        mChooseMemeButton.setOnClickListener(onClickButton());
        mEditMemeButton.setOnClickListener(onClickButton());

        VanillaMemeWrapper vanillaMemeWrapper = new VanillaMemeWrapper();

        startFragment(savedInstanceState, vanillaMemeWrapper.getFragment());
        setUpRecyclerView(vanillaMemeWrapper);


        mNextActivityButton.setOnClickListener(onClick());
    }

    private void setUpRecyclerView(VanillaMemeWrapper vanillaMemeWrapper) {
        mRecyclerView = (RecyclerView) findViewById(R.id.meme_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        fragmentAdapter = new FragmentAdapter(this);
        ((FragmentAdapter) fragmentAdapter).addMemeWrapper(vanillaMemeWrapper);
        mRecyclerView.setAdapter(fragmentAdapter);
        mEditAdapter = new EditVanillaMemeAdapter((VanillaMemeListener) vanillaMemeWrapper.getFragment());
    }

    private View.OnClickListener onClickButton() {
        return view -> {
            switch (view.getId()) {
                case R.id.btn_choose_meme:
                    mRecyclerView.setAdapter(fragmentAdapter);
                    break;
                case R.id.btn_edit_meme:
                    mRecyclerView.setAdapter(mEditAdapter);
            }
        };
    }

    private void startFragment(Bundle savedInstanceState, Fragment memeFragment) {
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.meme_overlay_fragment, memeFragment)
                    .commit();
        }
    }

    @NonNull
    private View.OnClickListener onClick() {
        return view -> {
            Intent intent = new Intent(getApplicationContext(), SaveMemeActivity.class);
            startActivity(intent);
        };
    }

    @Override
    public void setEditMemeAdapter(RecyclerView.Adapter editMemeAdapter) {
        mEditAdapter = editMemeAdapter;
    }

    @Override
    public void swapFragment(Fragment memeFragment) {
        startFragment(null, memeFragment);
    }

    private void showPicture() {
        Bitmap picture = getIntent().getParcelableExtra("BitmapCamera");
        Uri pictureUri = Uri.parse(getIntent().getStringExtra("CameraPhotoUri"));
        if (picture != null) {
            memeImage.setImageBitmap(picture);
        }
    }
}
