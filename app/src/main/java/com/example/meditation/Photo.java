package com.example.meditation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

public class Photo extends AppCompatActivity {

    SubsamplingScaleImageView imageView;
    //ImageView img;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        imageView = findViewById(R.id.image);
        if (Profile.maskImage.getImageProfile().exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(Profile.maskImage.getImageProfile().getAbsolutePath());
            imageView.setImage(ImageSource.bitmap(myBitmap));

            /*Bitmap myBitmap = BitmapFactory.decodeFile(ProfileActivity.maskImage.getImageProfile().getAbsolutePath());
            img.setImage(myBitmap);*/
        }
        view = findViewById(R.id.view);

    }

    public void onClose(View view) {
            startActivity(new Intent(this, Profile.class));
        }

    public void onDelete(View view) {
        try
        {
            Profile.maskImage.imageProfile.delete();
        }
        catch(Exception exception)
        {
            Toast.makeText(this, "При удаление возникла ошибка!", Toast.LENGTH_LONG).show();
        }
        startActivity(new Intent(this, Profile.class));
    }
}