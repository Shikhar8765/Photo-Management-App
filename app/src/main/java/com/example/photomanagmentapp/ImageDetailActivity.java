package com.example.photomanagmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Matrix;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class ImageDetailActivity extends AppCompatActivity {
    String imgpath;
    ImageView imageView;
    ScaleGestureDetector scaleGestureDetector;
    float mscalefator=1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        imgpath=getIntent().getStringExtra("imgpath");
        imageView=findViewById(R.id.idIVImage);
        scaleGestureDetector=new ScaleGestureDetector(this,new ScaleListener());
        File imgfile=new File(imgpath);
        if (imgfile.exists())
        {
            Picasso.get().load(imgfile).placeholder(R.drawable.ic_launcher_background).into(imageView);
        }
    }
    public boolean onTouchEvent(MotionEvent motionEvent)
    {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
    class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(@NonNull ScaleGestureDetector detector) {
            mscalefator*=scaleGestureDetector.getScaleFactor();
            mscalefator=Math.max(0.1f,Math.min(mscalefator,10.0f));
            imageView.setScaleX(mscalefator);
            imageView.setScaleY(mscalefator);return true;
        }
    }
}