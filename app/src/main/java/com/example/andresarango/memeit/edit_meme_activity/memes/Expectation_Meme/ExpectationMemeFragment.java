package com.example.andresarango.memeit.edit_meme_activity.memes.expectation_Meme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.andresarango.memeit.R;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jordansmith on 1/20/17.
 */

public class ExpectationMemeFragment extends Fragment implements View.OnClickListener {
    View rootView;
    Button imageA;
    Button imageB;
    EditText editText;
    TextView titleTextView;
    boolean isImageASet = true;
    boolean isImageBSet = false;
    boolean wasImageAClicked = false;
    boolean wasImageBClicked = false;
    private int PICK_IMAGE_REQUEST = 1;
    Intent changeImageIntent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_expectation_meme, container, false);
        initializations(rootView);

        Bundle bundle = getArguments();
        String passedUriString = bundle.getString("GalleryImage");
        Uri uri = Uri.parse(passedUriString);


        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap( getActivity().getContentResolver(), uri );
            Drawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            imageA.setBackgroundDrawable(bitmapDrawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        areButtonsSet();

        return rootView;
    }

    private void initializations(View rootView) {
        imageA = (Button) rootView.findViewById(R.id.image_A);
        imageB = (Button) rootView.findViewById(R.id.image_B);
        editText = (EditText) rootView.findViewById(R.id.title_edit_text);
        titleTextView = (TextView) rootView.findViewById(R.id.title_text_view);
        imageA.setOnClickListener(this);
        imageB.setOnClickListener(this);
        titleTextView.setOnClickListener(this);
        titleTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.image_A:
                wasImageAClicked = true;
                changeImageIntent = new Intent();
                changeImageIntent.setType("image/*");
                changeImageIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(changeImageIntent, "Select Picture"), PICK_IMAGE_REQUEST);
                break;
            case R.id.image_B:
                wasImageBClicked = true;
                changeImageIntent = new Intent();
                changeImageIntent.setType("image/*");
                changeImageIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(changeImageIntent, "Select Picture"), PICK_IMAGE_REQUEST);
                break;
        }

    }

    public void areButtonsSet(){
        if(isImageASet){
            imageA.setText("");
        }
        if(isImageBSet){
            imageB.setText("");
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            if(wasImageAClicked){
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Drawable bitmapDrawable = new BitmapDrawable(getResources(),bitmap);
                imageA.setBackgroundDrawable(bitmapDrawable);;
                wasImageAClicked = false;
            }
            if(wasImageBClicked){
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Drawable bitmapDrawable = new BitmapDrawable(getResources(),bitmap);
                imageB.setBackgroundDrawable(bitmapDrawable);;
                isImageBSet = true;
                areButtonsSet();
                wasImageBClicked = false;
            }

        }
    }
}
